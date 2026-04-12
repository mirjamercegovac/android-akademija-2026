package com.example.notesapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(
    existingNote: Note?,
    onBackClick: () -> Unit,
    onSaveClick: (String, String) -> Unit
){
    // ZASLON 2 - State za naslov i opis
    var title by rememberSaveable { mutableStateOf(existingNote?.title ?: "") }
    var description by rememberSaveable { mutableStateOf(existingNote?.description ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                // Gumb za povratak
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
                        text = if (existingNote == null) "Nova biljeska" else "Uredi biljesku",
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
            // Polje za unos naslova
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Naslov") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF006CE0),
                    unfocusedBorderColor = Color(0xFF006CE0),
                    focusedLabelColor = Color(0xFF006CE0),
                    cursorColor = Color(0xFF006CE0)
                )
            )
            // Polje za unos opisa
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Opis")},
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

            // Gumb za spremanje
            Button(
                onClick = {
                    onSaveClick(title, description)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF006CE0),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(vertical = 14.dp)
            ) {
                Text(
                    text = "Spremi")
            }
        }

    }
}