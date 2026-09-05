package com.example.firebasenotes.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebasenotes.data.model.Note
import com.example.firebasenotes.ui.theme.BackgroundDark
import com.example.firebasenotes.ui.theme.PrimaryDark
import com.example.firebasenotes.ui.theme.SecondaryDark
import com.example.firebasenotes.ui.theme.SurfaceDark
import com.example.firebasenotes.ui.theme.TextPrimaryDark
import com.example.firebasenotes.ui.theme.TextSecondaryDark
import com.example.firebasenotes.viewmodel.AuthViewModel
import com.example.firebasenotes.viewmodel.NotesViewModel

@Composable
fun HomeScreen(
    userName: String,
    onAddNoteClick: () -> Unit = {},
    onEditNoteClick: (Note) -> Unit = {},
    onLogoutSuccess: () -> Unit = {},
    authViewModel: AuthViewModel = viewModel(),
    notesViewModel: NotesViewModel = viewModel()
) {
    var searchText by remember {
        mutableStateOf("")
    }
    var showLogoutDialog by remember {
        mutableStateOf(false)
    }
    var noteToDelete by remember {
        mutableStateOf<Note?>(null)
    }
    val notes by notesViewModel.notes.collectAsState()
    val isLoading by notesViewModel.isLoading.collectAsState()
    val errorMessage by notesViewModel.errorMessage.collectAsState()
    LaunchedEffect(Unit) {
        notesViewModel.loadNotes()
    }
    val filteredNotes = notes.filter { note ->
        note.title.contains(
            searchText, ignoreCase = true
        ) || note.content.contains(
            searchText, ignoreCase = true
        )
    }
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            BackgroundDark, SurfaceDark, Color(0xFF2D0A63)
        )
    )
    // ============================================================
    // Logout Dialog
    // ============================================================
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = {
                showLogoutDialog = false
            }, title = {
                Text(
                    text = "Logout", color = TextPrimaryDark, fontWeight = FontWeight.Bold
                )
            }, text = {
                Text(
                    text = "Are you sure you want to logout?", color = TextSecondaryDark
                )
            }, confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        authViewModel.logout()
                        onLogoutSuccess()
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryDark, contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Logout"
                    )
                }
            }, dismissButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                    }) {
                    Text(
                        text = "Cancel", color = SecondaryDark
                    )
                }
            }, containerColor = SurfaceDark, shape = RoundedCornerShape(20.dp)
        )
    }
    // ============================================================
    // Delete Confirmation Dialog
    // ============================================================
    if (noteToDelete != null) {
        AlertDialog(
            onDismissRequest = {
                if (!isLoading) {
                    noteToDelete = null
                }
            }, title = {
                Text(
                    text = "Delete Note", color = TextPrimaryDark, fontWeight = FontWeight.Bold
                )
            }, text = {
                Text(
                    text = "Are you sure you want to delete \"${noteToDelete?.title}\"?",
                    color = TextSecondaryDark
                )
            }, confirmButton = {
                Button(
                    onClick = {
                        val noteId = noteToDelete?.id
                        if (!noteId.isNullOrBlank()) {
                            notesViewModel.deleteNote(
                                noteId
                            )
                        }
                        noteToDelete = null
                    }, enabled = !isLoading, colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE53935), contentColor = Color.White
                    )
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp), color = Color.White, strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = "Delete"
                        )
                    }
                }
            }, dismissButton = {
                TextButton(
                    onClick = {
                        noteToDelete = null
                    }, enabled = !isLoading
                ) {
                    Text(
                        text = "Cancel", color = SecondaryDark
                    )
                }
            }, containerColor = SurfaceDark, shape = RoundedCornerShape(20.dp)
        )
    }
    // ============================================================
    // Main Screen
    // ============================================================
    Scaffold(
        containerColor = Color.Transparent, floatingActionButton = {
            FloatingActionButton(
                onClick = onAddNoteClick,
                containerColor = PrimaryDark,
                contentColor = Color.White,
                shape = RoundedCornerShape(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add, contentDescription = "Add Note"
                )
            }
        }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundGradient)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                // ====================================================
                // Header
                // ====================================================
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Good to see you!", color = TextSecondaryDark, fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = userName,
                            color = TextPrimaryDark,
                            fontSize = 27.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    IconButton(
                        onClick = {
                            showLogoutDialog = true
                        }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Logout",
                            tint = SecondaryDark
                        )
                    }
                }
                Spacer(modifier = Modifier.height(22.dp))
                // ====================================================
                // Search Bar
                // ====================================================
                Column(
                    modifier = Modifier.widthIn(max = 600.dp)
                ) {
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = {
                            searchText = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        placeholder = {
                            Text(
                                text = "Search notes...", color = TextSecondaryDark
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = SecondaryDark
                            )
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextPrimaryDark,
                            unfocusedTextColor = TextPrimaryDark,
                            focusedBorderColor = SecondaryDark,
                            unfocusedBorderColor = Color(0xFF6D5A82),
                            cursorColor = SecondaryDark,
                            focusedLeadingIconColor = SecondaryDark,
                            unfocusedLeadingIconColor = TextSecondaryDark
                        )
                    )
                }
                Spacer(modifier = Modifier.height(28.dp))
                // ====================================================
                // My Notes Header
                // ====================================================
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Description,
                        contentDescription = "Notes",
                        tint = SecondaryDark,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "My Notes",
                        color = TextPrimaryDark,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "${filteredNotes.size} Notes",
                        color = TextSecondaryDark,
                        fontSize = 13.sp
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                // ====================================================
                // Loading
                // ====================================================
                if (isLoading && notes.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            color = SecondaryDark, strokeWidth = 3.dp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Loading notes...", color = TextSecondaryDark, fontSize = 14.sp
                        )
                    }
                }
                // ====================================================
                // Error
                // ====================================================
                else if (errorMessage != null && notes.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = null,
                            tint = Color(0xFFFF8A80),
                            modifier = Modifier.size(70.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Something went wrong",
                            color = TextPrimaryDark,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = errorMessage ?: "Failed to load notes",
                            color = TextSecondaryDark,
                            fontSize = 14.sp
                        )
                    }
                }
                // ====================================================
                // Empty State
                // ====================================================
                else if (filteredNotes.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = null,
                            tint = Color(0xFF6D5A82),
                            modifier = Modifier.size(70.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = if (searchText.isBlank()) {
                                "No notes yet"
                            } else {
                                "No notes found"
                            },
                            color = TextPrimaryDark,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (searchText.isBlank()) {
                                "Create your first note using +"
                            } else {
                                "Try searching with a different word"
                            }, color = TextSecondaryDark, fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
                // ====================================================
                // Notes List
                // ====================================================
                else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(
                            items = filteredNotes, key = { note ->
                                note.id
                            }) { note ->
                            NoteCard(note = note, onEditClick = {
                                onEditNoteClick(note)
                            }, onDeleteClick = {
                                noteToDelete = note
                            })
                        }
                        item {
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                }
            }
        }
    }
}

// ================================================================
// Note Card
// ================================================================
@Composable
fun NoteCard(
    note: Note, onEditClick: () -> Unit, onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .widthIn(max = 600.dp)
            .shadow(
                elevation = 8.dp, shape = RoundedCornerShape(18.dp)
            )
            .border(
                width = 1.dp,
                color = SecondaryDark.copy(alpha = 0.45f),
                shape = RoundedCornerShape(18.dp)
            ), shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(
            containerColor = SurfaceDark
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            // ====================================================
            // Title + Actions
            // ====================================================
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = note.title,
                    color = TextPrimaryDark,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = onEditClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = SecondaryDark
                    )
                }
                IconButton(
                    onClick = onDeleteClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color(0xFFFF8A80)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            // ====================================================
            // Note Content
            // ====================================================
            Text(
                text = note.content, color = TextSecondaryDark, fontSize = 14.sp, lineHeight = 21.sp
            )
        }
    }
}