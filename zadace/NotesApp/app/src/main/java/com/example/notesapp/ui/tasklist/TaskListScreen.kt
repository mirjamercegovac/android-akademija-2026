package com.example.notesapp.ui.tasklist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel,
    onAddClick: () -> Unit,
    onTaskClick: (String) -> Unit
){
    var taskToDelete by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.loadTasks()
    }

    if (taskToDelete != null) {
        AlertDialog(
            onDismissRequest = { taskToDelete = null },
            title = { Text("Delete task") },
            text = { Text("Do you want to delete this task?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteTask(taskToDelete!!)
                        taskToDelete = null
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { taskToDelete = null }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Text(
                            text = "Taskie",
                            modifier = Modifier.align(Alignment.Center),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = Color(0xFF006CE0),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add task"
                )
            }
        }
    ) { innerPadding ->
        when{
            viewModel.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ){
                    Text("Loading...")
                }
            }

            viewModel.errorMessage != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Error: ${viewModel.errorMessage}")
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = viewModel.searchQuery,
                        onValueChange = viewModel::onSearchQueryChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Search tasks") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search"
                            )
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF006CE0),
                            unfocusedBorderColor = Color(0xFF006CE0),
                            focusedLabelColor = Color(0xFF006CE0),
                            cursorColor = Color(0xFF006CE0)
                        )
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        SummaryInfoCard(
                            number = viewModel.totalTasks.toString(),
                            title = "Tasks",
                            subtitle = "Total",
                            isPrimary = true,
                            modifier = Modifier.weight(1f)
                        )

                        SummaryInfoCard(
                            number = viewModel.favoriteTasks.toString(),
                            title = "Favorites",
                            subtitle = "Marked",
                            isPrimary = false,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SummaryChip("Work ${viewModel.workTasks}", Modifier.weight(1f))
                        SummaryChip("Study ${viewModel.studyTasks}", Modifier.weight(1f))
                        SummaryChip("Personal ${viewModel.personalTasks}", Modifier.weight(1f))
                        SummaryChip("Other ${viewModel.otherTasks}", Modifier.weight(1f))
                    }

                    viewModel.errorMessage?.let {
                        Text(
                            text = "Offline mode: prikazuju se lokalni podaci",
                            color = Color.Red
                        )
                    }


                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(viewModel.tasks) { task ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .combinedClickable(
                                        onClick = { onTaskClick(task.id) },
                                        onLongClick = { taskToDelete = task.id }
                                    ),
                                shape = RoundedCornerShape(20.dp),
                                border = BorderStroke(1.dp, Color(0xFF006CE0)),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.Transparent
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Text(
                                            text = task.title,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.weight(1f)
                                        )

                                        Icon(
                                            imageVector = if (task.isFavorite) {
                                                Icons.Filled.Star
                                            } else {
                                                Icons.Outlined.StarBorder
                                            },
                                            contentDescription = "Favorite",
                                            tint = if (task.isFavorite) {
                                                Color(0xFF006CE0)
                                            } else {
                                                Color.Gray
                                            },
                                            modifier = Modifier
                                                .clickable {
                                                    viewModel.toggleFavorite(task.id, !task.isFavorite)
                                                }
                                                .padding(start = 8.dp)
                                        )
                                    }

                                    Text(
                                        text = task.body,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = task.category,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color(0xFF006CE0)
                                        )

                                        Text(
                                            text = task.createdAt,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.Gray
                                        )
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SummaryInfoCard(
    number: String,
    title: String,
    subtitle: String,
    isPrimary: Boolean,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isPrimary) Color(0xFF2F6BFF) else Color(0xFFEAF2FF)
    val titleColor = if (isPrimary) Color.White else Color(0xFF5D7FBE)
    val numberColor = if (isPrimary) Color.White else Color(0xFF1F2A44)
    val subtitleColor = if (isPrimary) Color(0xFFDCE7FF) else Color(0xFF6E86B3)
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        border = if (isPrimary) null else BorderStroke(1.dp, Color(0xFFD4E4FF)),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = titleColor
            )

            Text(
                text = number,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = numberColor
            )

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = subtitleColor
            )
        }
    }
}

@Composable
private fun SummaryChip(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = Color(0xFFF7FAFF),
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = Color(0xFFD8E6FF),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 8.dp, vertical = 10.dp)
    ) {
        Text(
            text = text,
            color = Color(0xFF006CE0),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium
        )
    }
}