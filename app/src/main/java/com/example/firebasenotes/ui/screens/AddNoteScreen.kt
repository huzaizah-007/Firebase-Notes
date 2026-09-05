package com.example.firebasenotes.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebasenotes.ui.theme.BackgroundDark
import com.example.firebasenotes.ui.theme.PrimaryDark
import com.example.firebasenotes.ui.theme.SecondaryDark
import com.example.firebasenotes.ui.theme.SurfaceDark
import com.example.firebasenotes.ui.theme.TextPrimaryDark
import com.example.firebasenotes.ui.theme.TextSecondaryDark
import com.example.firebasenotes.viewmodel.NotesViewModel

@Composable
fun AddNoteScreen(
    onBackClick: () -> Unit, onNoteSaved: () -> Unit, notesViewModel: NotesViewModel = viewModel()
) {
    var title by remember {
        mutableStateOf("")
    }
    var content by remember {
        mutableStateOf("")
    }
    val isLoading by notesViewModel.isLoading.collectAsState()
    val errorMessage by notesViewModel.errorMessage.collectAsState()
    val operationSuccess by notesViewModel.operationSuccess.collectAsState()
    // ------------------------------------------------
    // Save Success
    // ------------------------------------------------
    LaunchedEffect(operationSuccess) {
        if (operationSuccess) {
            notesViewModel.clearOperationSuccess()
            onNoteSaved()
        }
    }
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            BackgroundDark, SurfaceDark, Color(0xFF2D0A63)
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            IconButton(
                onClick = onBackClick,
                enabled = !isLoading,
                modifier = Modifier.align(Alignment.Start)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TextPrimaryDark
                )
            }
            Spacer(modifier = Modifier.height(28.dp))
            // ------------------------------------------------
            // Heading
            // ------------------------------------------------
            Text(
                text = "Create a new note",
                color = TextPrimaryDark,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Write down your thoughts and ideas.",
                color = TextSecondaryDark,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(28.dp))
            Column(modifier = Modifier.widthIn(max = 600.dp)) {
                // ------------------------------------------------
                // Title
                // ------------------------------------------------
                Text(
                    text = "Title",
                    color = TextPrimaryDark,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = title, onValueChange = {
                        title = it
                        notesViewModel.clearError()
                    }, modifier = Modifier.fillMaxWidth(), singleLine = true, placeholder = {
                        Text(
                            text = "Enter note title", color = TextSecondaryDark
                        )
                    }, shape = RoundedCornerShape(16.dp), colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimaryDark,
                        unfocusedTextColor = TextPrimaryDark,
                        focusedBorderColor = SecondaryDark,
                        unfocusedBorderColor = Color(0xFF6D5A82),
                        cursorColor = SecondaryDark
                    )
                )
                Spacer(modifier = Modifier.height(22.dp))
                // ------------------------------------------------
                // Content
                // ------------------------------------------------
                Text(
                    text = "Content",
                    color = TextPrimaryDark,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = content, onValueChange = {
                        content = it
                        notesViewModel.clearError()
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp), placeholder = {
                        Text(
                            text = "Write your note here...", color = TextSecondaryDark
                        )
                    }, shape = RoundedCornerShape(16.dp), colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimaryDark,
                        unfocusedTextColor = TextPrimaryDark,
                        focusedBorderColor = SecondaryDark,
                        unfocusedBorderColor = Color(0xFF6D5A82),
                        cursorColor = SecondaryDark
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                // ------------------------------------------------
                // Error
                // ------------------------------------------------
                if (errorMessage != null) {
                    Text(
                        text = errorMessage ?: "",
                        color = Color(0xFFFF8A80),
                        fontSize = 13.sp,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(28.dp))
                // ------------------------------------------------
                // Save Button
                // ------------------------------------------------
                Button(
                    onClick = {
                        notesViewModel.addNote(
                            title = title, content = content
                        )
                    },
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryDark,
                        contentColor = Color.White,
                        disabledContainerColor = PrimaryDark.copy(alpha = 0.6f)
                    )
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.height(22.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(
                            text = "Saving...", fontSize = 16.sp, fontWeight = FontWeight.SemiBold
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Save, contentDescription = "Save Note"
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(
                            text = "Save Note", fontSize = 16.sp, fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}