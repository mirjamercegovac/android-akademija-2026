package com.example.notesapp.data.repository

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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class TaskRepository(
    private val taskDao: TaskDao
){
    private val api = RetrofitTaskieInstance.api

    //ZADACA 5 - login poziv i spremanje tokena
    suspend fun login(username: String, password: String){
        val response = api.login(LoginRequest(username, password))
        TokenProvider.token = response.token
    }

    fun getTasksFlow(): Flow<List<Task>>{
        return taskDao.getAllTasks().map { entities ->
            entities.map { it.toTask() }
        }
    }

    //zadaca 6 -  Spremiti sve podatke sa vanjskih izvora u bazu podataka
    suspend fun refreshTasks(){
        val remoteTasks = api.getTasks("Bearer ${TokenProvider.token}").tasks
        taskDao.deleteAllTasks()
        taskDao.insertTasks(remoteTasks.map { it.toEntity() })
    }

    suspend fun getTaskById(id: String): Task? {
        return taskDao.getTaskById(id)?.toTask()
    }

    suspend fun createTask(title: String, body: String) {
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
        } catch (_: Exception) {
            // Lokalno je već spremljeno
        }
    }

    suspend fun updateTask(id: String, title: String, body: String) {
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
        } catch (_: Exception) {
            // Lokalna promjena ostaje spremljena
        }
    }

    suspend fun deleteTask(id: String) {
        taskDao.deleteTaskById(id)

        try {
            api.deleteTask("Bearer ${TokenProvider.token}", id)
            refreshTasks()
        } catch (_: Exception) {
            // Lokalno je obrisano
        }
    }
}