package com.example.firebasenotes.data.repository

import com.example.firebasenotes.data.model.Note
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class NoteRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val notesCollection = firestore.collection("notes")

    // ------------------------------------------------
    // Add Note
    // ------------------------------------------------
    suspend fun addNote(note: Note) {
        val document = notesCollection.document()
        val noteWithId = note.copy(
            id = document.id
        )
        document.set(noteWithId).await()
    }

    // ------------------------------------------------
    // Get User Notes
    // ------------------------------------------------
    suspend fun getUserNotes(
        userId: String
    ): List<Note> {
        val snapshot = notesCollection.whereEqualTo(
            "userId", userId
        ).get().await()
        return snapshot.toObjects(
            Note::class.java
        )
    }

    // ------------------------------------------------
    // Update Note
    // ------------------------------------------------
    suspend fun updateNote(
        note: Note
    ) {
        notesCollection.document(note.id).set(note).await()
    }

    // ------------------------------------------------
    // Delete Note
    // ------------------------------------------------
    suspend fun deleteNote(
        noteId: String
    ) {
        notesCollection.document(noteId).delete().await()
    }
}