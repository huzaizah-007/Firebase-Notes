package com.example.firebasenotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenotes.data.model.Note
import com.example.firebasenotes.data.repository.NoteRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotesViewModel : ViewModel() {
    private val repository = NoteRepository()
    private val auth = FirebaseAuth.getInstance()
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    private val _operationSuccess = MutableStateFlow(false)
    val operationSuccess: StateFlow<Boolean> = _operationSuccess.asStateFlow()

    // ------------------------------------------------
    // Load Notes
    // ------------------------------------------------
    fun loadNotes() {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            _errorMessage.value = "User not logged in"
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                _notes.value = repository.getUserNotes(userId)
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Failed to load notes"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ------------------------------------------------
    // Add Note
    // ------------------------------------------------
    fun addNote(
        title: String, content: String
    ) {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            _errorMessage.value = "User not logged in"
            return
        }
        if (title.isBlank()) {
            _errorMessage.value = "Please enter a title"
            return
        }
        if (content.isBlank()) {
            _errorMessage.value = "Please enter note content"
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _operationSuccess.value = false
            try {
                val note = Note(
                    title = title.trim(),
                    content = content.trim(),
                    userId = userId,
                    timestamp = System.currentTimeMillis()
                )
                repository.addNote(note)
                _operationSuccess.value = true
                _notes.value = repository.getUserNotes(userId)
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Failed to add note"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ------------------------------------------------
    // Update Note
    // ------------------------------------------------
    fun updateNote(
        note: Note
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _operationSuccess.value = false
            try {
                repository.updateNote(
                    note
                )
                _operationSuccess.value = true
                val userId = auth.currentUser?.uid
                if (userId != null) {
                    _notes.value = repository.getUserNotes(userId)
                }
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Failed to update note"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ------------------------------------------------
    // Delete Note
    // ------------------------------------------------
    fun deleteNote(
        noteId: String
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _operationSuccess.value = false
            try {
                repository.deleteNote(
                    noteId
                )
                _operationSuccess.value = true
                val userId = auth.currentUser?.uid
                if (userId != null) {
                    _notes.value = repository.getUserNotes(userId)
                }
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Failed to delete note"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ------------------------------------------------
    // Clear Error
    // ------------------------------------------------
    fun clearError() {
        _errorMessage.value = null
    }

    // ------------------------------------------------
    // Clear Operation Success
    // ------------------------------------------------
    fun clearOperationSuccess() {
        _operationSuccess.value = false
    }
}