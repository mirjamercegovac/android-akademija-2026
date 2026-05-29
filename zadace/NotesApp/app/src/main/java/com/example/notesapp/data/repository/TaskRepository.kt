package com.example.notesapp.data.repository

import android.util.Log.e
import com.example.notesapp.data.local.TaskDao
import com.example.notesapp.data.local.TaskEntity
import com.example.notesapp.data.local.toEntity
import com.example.notesapp.data.local.toTask
import com.example.notesapp.data.model.CreateTaskRequest
import com.example.notesapp.data.model.LoginRequest
import com.example.notesapp.data.model.Task
import com.example.notesapp.data.model.UpdateTaskRequest
import com.example.notesapp.data.network.RetrofitTaskieInstance
import com.example.notesapp.data.network.TokenProvider
import com.example.notesapp.data.network.apiservice.RetrofitTaskieApiService
import com.example.notesapp.di.AppLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class TaskRepository(
    private val taskDao: TaskDao,
    private val api: RetrofitTaskieApiService,
    private val logger: AppLogger
){


    //ZADACA 5 - login poziv i spremanje tokena, ZADACA 7 - logger
    suspend fun login(username: String, password: String){
        logger.debug("Login started for username=$username")
        val response = api.login(LoginRequest(username, password))
        TokenProvider.token = response.token
        logger.debug("Login successful, token stored")
    }

    fun getTasksFlow(): Flow<List<Task>>{
        logger.debug("Observing tasks from local database")
        return taskDao.getAllTasks().map { entities ->
            entities.map { it.toTask() }
        }
    }

    //zadaca 6 -  Spremiti sve podatke sa vanjskih izvora u bazu podataka
    suspend fun refreshTasks(){
        logger.debug("Refreshing tasks from server")
        val remoteTasks = api.getTasks("Bearer ${TokenProvider.token}").tasks
        taskDao.deleteAllTasks()
        taskDao.insertTasks(remoteTasks.map { it.toEntity() })
        logger.debug("Tasks saved to Room database")
    }

    suspend fun getTaskById(id: String): Task? {
        logger.debug("Loading task by id=$id from local database")
        return taskDao.getTaskById(id)?.toTask()
    }

    suspend fun createTask(title: String, body: String) {
        logger.debug("Creating local task with title=$title")
        val localTask = TaskEntity(
            id = UUID.randomUUID().toString(),
            title = title,
            body = body,
            isSynced = false
        )
        taskDao.insertTask(localTask)

        try {
            api.createTask(
                "Bearer ${TokenProvider.token}",
                CreateTaskRequest(title, body)
            )
            refreshTasks()
        } catch (e: Exception) {
            logger.error("Failed to sync created task", e)
        }
    }

    suspend fun updateTask(id: String, title: String, body: String) {
        logger.debug("Updating task id=$id")
        taskDao.insertTask(
            TaskEntity(
                id = id,
                title = title,
                body = body,
                isSynced = false
            )
        )

        try {
            api.updateTask(
                "Bearer ${TokenProvider.token}",
                id,
                UpdateTaskRequest(title, body)
            )
            refreshTasks()
        } catch (e: Exception) {
            logger.error("Failed to sync updated task", e)
        }
    }

    suspend fun deleteTask(id: String) {
        logger.debug("Deleting task id=$id")
        taskDao.deleteTaskById(id)

        try {
            api.deleteTask("Bearer ${TokenProvider.token}", id)
            refreshTasks()
        } catch (e: Exception) {
            logger.error("Failed to sync deleted task", e)
        }
    }
}