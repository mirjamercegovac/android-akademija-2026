package com.example.notesapp.data.repository

import com.example.notesapp.data.local.TaskDao
import com.example.notesapp.data.local.TaskEntity
import com.example.notesapp.data.local.toEntity
import com.example.notesapp.data.local.toTask
import com.example.notesapp.data.model.CreateTaskRequest
import com.example.notesapp.data.model.LoginRequest
import com.example.notesapp.data.model.Task
import com.example.notesapp.data.model.UpdateTaskRequest
import com.example.notesapp.data.network.TokenProvider
import com.example.notesapp.data.network.apiservice.RetrofitTaskieApiService
import com.example.notesapp.di.AppLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
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
    suspend fun refreshTasks() {
        logger.debug("Refreshing tasks from server")

        val localTasks = taskDao.getAllTasksSnapshot()

        val favoriteById = localTasks.associate { it.id to it.isFavorite }
        val categoryById = localTasks.associate { it.id to it.category }

        val favoriteByContent = localTasks.associate { (it.title.trim() + "|" + it.body.trim()) to it.isFavorite }
        val categoryByContent = localTasks.associate { (it.title.trim() + "|" + it.body.trim()) to it.category }

        val createdAtById = localTasks.associate { it.id to it.createdAt }
        val createdAtByContent = localTasks.associate { (it.title.trim() + "|" + it.body.trim()) to it.createdAt }

        val remoteTasks = api.getTasks("Bearer ${TokenProvider.token}").tasks

        val mergedTasks = remoteTasks.map { task ->
            val contentKey = task.title.trim() + "|" + task.body.trim()

            task.toEntity().copy(
                isFavorite = favoriteById[task.id] ?: favoriteByContent[contentKey] ?: false,
                category = categoryById[task.id] ?: categoryByContent[contentKey] ?: "Other",
                createdAt = createdAtById[task.id] ?: createdAtByContent[contentKey] ?: ""
            )
        }

        taskDao.deleteAllTasks()
        taskDao.insertTasks(mergedTasks)

        logger.debug("Tasks saved to Room database")
    }

    suspend fun getTaskById(id: String): Task? {
        logger.debug("Loading task by id=$id from local database")
        return taskDao.getTaskById(id)?.toTask()
    }

    suspend fun createTask(title: String, body: String, category: String) {
        logger.debug("Creating local task with title=$title")
        val createdAt = SimpleDateFormat(
            "dd.MM.yyyy",
            Locale.getDefault()
        ).format(Date())
        val localTask = TaskEntity(
            id = UUID.randomUUID().toString(),
            title = title,
            body = body,
            category = category,
            createdAt = createdAt,
            isFavorite = false,
            isSynced = false
        )

        taskDao.insertTask(localTask)

        try {
            api.createTask(
                "Bearer ${TokenProvider.token}",
                CreateTaskRequest(title, body)
            )
            logger.debug("Task sent to server successfully")
        } catch (e: Exception) {
            logger.error("Failed to sync created task", e)
        }
    }

    suspend fun updateTask(id: String, title: String, body: String, category: String) {
        logger.debug("Updating task id=$id")
        val existingTask = taskDao.getTaskById(id)
        taskDao.insertTask(
            TaskEntity(
                id = id,
                title = title,
                body = body,
                category = category,
                createdAt = existingTask?.createdAt ?: "",
                isFavorite = existingTask?.isFavorite ?: false,
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

    suspend fun toggleFavorite(id: String, isFavorite: Boolean) {
        logger.debug("Updating favorite for task id=$id to $isFavorite")
        taskDao.updateFavorite(id, isFavorite)
    }
}