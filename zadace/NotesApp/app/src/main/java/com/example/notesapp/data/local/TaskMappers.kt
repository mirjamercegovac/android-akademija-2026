package com.example.notesapp.data.local

import com.example.notesapp.data.model.Task

fun Task.toEntity(): TaskEntity{
    return TaskEntity(
        id = id,
        title = title,
        body = body,
        isSynced = true
    )
}

fun TaskEntity.toTask(): Task{
    return Task(
        id = id,
        title = title,
        body = body
    )
}