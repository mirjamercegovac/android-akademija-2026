package com.example.notesapp

import com.example.notesapp.data.local.TaskDao
import com.example.notesapp.data.local.TaskEntity
import com.example.notesapp.data.model.Task
import com.example.notesapp.data.model.TasksResponse
import com.example.notesapp.data.network.TokenProvider
import com.example.notesapp.data.network.apiservice.RetrofitTaskieApiService
import com.example.notesapp.data.repository.TaskRepository
import com.example.notesapp.di.AppLogger
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TaskRepositoryTest {
    private val taskDao: TaskDao = mockk(relaxed = true)
    private val api: RetrofitTaskieApiService = mockk(relaxed = true)
    private val logger: AppLogger = mockk(relaxed = true)

    @Test
    fun `refreshTasks clears local tasks and inserts remote tasks`() = runTest {
        TokenProvider.token = "test-token"

        val remoteTasks = listOf(
            Task(id = "1", title = "Task 1", body = "Body 1"),
            Task(id = "2", title = "Task 2", body = "Body 2")
        )

        coEvery {
            api.getTasks("Bearer test-token")
        } returns TasksResponse(remoteTasks)

        val repository = TaskRepository(taskDao, api, logger)

        repository.refreshTasks()

        coVerify { taskDao.deleteAllTasks() }
        coVerify {
            taskDao.insertTasks(
                listOf(
                    TaskEntity(id = "1", title = "Task 1", body = "Body 1", isSynced = true),
                    TaskEntity(id = "2", title = "Task 2", body = "Body 2", isSynced = true)
                )
            )
        }
    }

    @Test
    fun `createTask stores task locally before remote sync`() = runTest {
        TokenProvider.token = "test-token"

        val repository = TaskRepository(taskDao, api, logger)

        repository.createTask("New title", "New body")

        coVerify { taskDao.insertTask(any()) }
        coVerify {
            api.createTask(
                "Bearer test-token",
                any()
            )
        }
    }
}