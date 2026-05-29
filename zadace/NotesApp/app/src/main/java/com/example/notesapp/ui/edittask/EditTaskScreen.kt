package com.example.notesapp.ui.edittask

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    taskId: String?,
    viewModel: EditTaskViewModel,
    onBackClick: () -> Unit,
    onDoneClick: () -> Unit
) {
    LaunchedEffect(taskId) {
        if (taskId != null) {
            viewModel.loadTask(taskId)
        } else {
            viewModel.resetForNewTask()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF006CE0)
                        )
                    }
                },
                title = {
                    Text(
                        text = if (taskId == null) "New task" else "Edit task",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { }, enabled = false) {
                        Spacer(modifier = Modifier.width(24.dp))
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = viewModel.title,
                onValueChange = { viewModel.title = it },
                label = { Text("Title") },
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
                value = viewModel.body,
                onValueChange = { viewModel.body = it },
                label = { Text("Body") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF006CE0),
                    unfocusedBorderColor = Color(0xFF006CE0),
                    focusedLabelColor = Color(0xFF006CE0),
                    cursorColor = Color(0xFF006CE0)
                )
            )
            Text(
                text = "Category",
                fontWeight = FontWeight.Bold
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                CategoryChip(
                    text = "Personal",
                    isSelected = viewModel.category == "Personal",
                    onClick = { viewModel.category = "Personal" }
                )
                CategoryChip(
                    text = "Work",
                    isSelected = viewModel.category == "Work",
                    onClick = { viewModel.category = "Work" }
                )
                CategoryChip(
                    text = "Study",
                    isSelected = viewModel.category == "Study",
                    onClick = { viewModel.category = "Study" }
                )
                CategoryChip(
                    text = "Other",
                    isSelected = viewModel.category == "Other",
                    onClick = { viewModel.category = "Other" }
                )
            }

            Button(
                onClick = {
                    if (taskId == null) {
                        viewModel.createTask(onDoneClick)
                    } else {
                        viewModel.updateTask(taskId, onDoneClick)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF006CE0),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(vertical = 14.dp)
            ) {
                Text("Done")
            }

            viewModel.errorMessage?.let {
                Text(text = it, color = Color.Red)
            }
        }
    }
}

@Composable
private fun CategoryChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Text(
        text = text,
        color = if (isSelected) Color.White else Color(0xFF006CE0),
        modifier = Modifier
            .background(
                color = if (isSelected) Color(0xFF006CE0) else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    )
}