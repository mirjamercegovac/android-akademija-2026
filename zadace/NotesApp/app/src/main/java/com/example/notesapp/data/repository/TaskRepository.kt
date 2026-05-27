package com.example.notesapp.data.repository

import com.example.notesapp.data.model.CreateTaskRequest
import com.example.notesapp.data.model.LoginRequest
import com.example.notesapp.data.model.Task
import com.example.notesapp.data.model.UpdateTaskRequest
import com.example.notesapp.data.network.RetrofitTaskieInstance
import com.example.notesapp.data.network.TokenProvider

class TaskRepository{
    private val api = RetrofitTaskieInstance.api

    //ZADACA 5 - login poziv i spremanje tokena
    suspend fun login(username: String, password: String){
        val response = api.login(LoginRequest(username, password))
        TokenProvider.token = response.token
    }

    suspend fun getTasks(): List<Task>{
        return api.getTasks("Bearer ${TokenProvider.token}").tasks
    }

    suspend fun getTaskById(id: String): Task {
        return api.getTaskById("Bearer ${TokenProvider.token}", id)
    }

    suspend fun createTask(title: String, body: String) {
        return api.createTask(
            "Bearer ${TokenProvider.token}",
            CreateTaskRequest(title, body)
        )
    }

    suspend fun updateTask(id: String, title: String, body: String){
        return api.updateTask(
            "Bearer ${TokenProvider.token}",
            id,
            UpdateTaskRequest(title, body)
        )
    }

    suspend fun deleteTask(id: String) {
        api.deleteTask("Bearer ${TokenProvider.token}", id)
    }
}