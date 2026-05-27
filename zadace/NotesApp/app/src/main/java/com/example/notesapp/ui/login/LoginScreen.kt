package com.example.notesapp.ui.login

import android.widget.Button
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit
){
    LaunchedEffect(viewModel.loginSuccess) {
        if(viewModel.loginSuccess){
            onLoginSuccess()
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Taskie Login",
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                value = viewModel.username,
                onValueChange = {viewModel.username = it},
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF006CE0),
                    unfocusedBorderColor = Color(0xFF006CE0),
                    focusedLabelColor = Color(0xFF006CE0),
                    cursorColor = Color(0xFF006CE0)
                )
            )

            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF006CE0),
                    unfocusedBorderColor = Color(0xFF006CE0),
                    focusedLabelColor = Color(0xFF006CE0),
                    cursorColor = Color(0xFF006CE0)
                )
            )

            Button(
                onClick = { viewModel.login() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF006CE0),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(if (viewModel.isLoading) "Loading..." else "Login")
            }

            viewModel.errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red
                )
            }
        }
    }
}