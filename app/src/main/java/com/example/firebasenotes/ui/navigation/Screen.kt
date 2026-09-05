package com.example.firebasenotes.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {

    @Serializable
    data object Splash : Screen

    @Serializable
    data object Login : Screen

    @Serializable
    data object Register : Screen

    @Serializable
    data object ForgotPassword : Screen

    @Serializable
    data object Home : Screen

    @Serializable
    data object AddNote : Screen

    @Serializable
    data class EditNote(
        val noteId: String
    ) : Screen
}