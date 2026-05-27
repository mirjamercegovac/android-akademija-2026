package com.example.notesapp.data.network.apiservice

import com.example.notesapp.data.model.CreateTaskRequest
import com.example.notesapp.data.model.LoginRequest
import com.example.notesapp.data.model.LoginResponse
import com.example.notesapp.data.model.Task
import com.example.notesapp.data.model.TasksResponse
import com.example.notesapp.data.model.UpdateTaskRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RetrofitTaskieApiService {
    //ZADACA 5 - API poziv za login
    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("tasks/all")
    suspend fun getTasks(
        @Header("Authorization") token: String
    ): TasksResponse

    @GET("tasks/{id}")
    suspend fun getTaskById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Task

    @POST("tasks/create")
    suspend fun createTask(
        @Header("Authorization") token: String,
        @Body request: CreateTaskRequest
    )

    @PUT("tasks/{id}")
    suspend fun updateTask(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body request: UpdateTaskRequest
    )

    @DELETE("tasks/{id}")
    suspend fun deleteTask(
        @Header("Authorization") token: String,
        @Path("id") id: String
    )
}