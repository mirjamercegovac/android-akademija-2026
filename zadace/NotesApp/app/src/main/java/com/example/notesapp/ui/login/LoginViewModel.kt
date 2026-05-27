package com.example.notesapp.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.repository.TaskRepository
import kotlinx.coroutines.launch

class LoginViewModel (
    private val repository: TaskRepository
): ViewModel() {

    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var loginSuccess by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var isLoading by mutableStateOf(false)

    // ZADACA 5 - nakon uspjesnog logina navigacija do liste taskova
    fun login(){
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                repository.login(username, password)
                loginSuccess = true
            } catch (e: Exception){
                errorMessage = e.message ?: "Login failed"
            } finally {
                isLoading = false
            }
        }
    }
}