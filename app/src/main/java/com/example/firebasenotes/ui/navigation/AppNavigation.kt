package com.example.firebasenotes.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.firebasenotes.ui.screens.AddNoteScreen
import com.example.firebasenotes.ui.screens.EditNoteScreen
import com.example.firebasenotes.ui.screens.ForgotPasswordScreen
import com.example.firebasenotes.ui.screens.HomeScreen
import com.example.firebasenotes.ui.screens.LoginScreen
import com.example.firebasenotes.ui.screens.RegisterScreen
import com.example.firebasenotes.ui.screens.SplashScreen
import com.example.firebasenotes.ui.theme.BackgroundDark
import com.example.firebasenotes.ui.theme.SecondaryDark
import com.example.firebasenotes.ui.theme.SurfaceDark
import com.example.firebasenotes.viewmodel.NotesViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = Screen.Splash
    ) {
        // ------------------------------------------------
        // Splash
        // ------------------------------------------------
        composable<Screen.Splash> {
            SplashScreen(
                onSplashFinished = {
                    val currentUser = FirebaseAuth.getInstance().currentUser
                    if (currentUser != null) {
                        navController.navigate(Screen.Home) {
                            popUpTo(Screen.Splash) {
                                inclusive = true
                            }
                        }
                    } else {
                        navController.navigate(Screen.Login) {
                            popUpTo(Screen.Splash) {
                                inclusive = true
                            }
                        }
                    }
                })
        }
        // ------------------------------------------------
        // Login
        // ------------------------------------------------
        composable<Screen.Login> {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Screen.Home) {
                    popUpTo(Screen.Login) {
                        inclusive = true
                    }
                }
            }, onRegisterClick = {
                navController.navigate(Screen.Register) {
                    popUpTo(Screen.Login) {
                        inclusive = true
                    }
                }
            }, onForgotPasswordClick = {
                navController.navigate(
                    Screen.ForgotPassword
                )
            })
        }
        // ------------------------------------------------
        // Register
        // ------------------------------------------------
        composable<Screen.Register> {
            RegisterScreen(onLoginClick = {
                navController.navigate(Screen.Login) {
                    popUpTo(Screen.Register) {
                        inclusive = true
                    }
                }
            }, onRegistrationSuccess = {
                navController.navigate(Screen.Home) {
                    popUpTo(Screen.Register) {
                        inclusive = true
                    }
                }
            })
        }
        // ------------------------------------------------
        // Forgot Password
        // ------------------------------------------------
        composable<Screen.ForgotPassword> {
            ForgotPasswordScreen(
                onBackClick = {
                    navController.popBackStack()
                })
        }
        // ------------------------------------------------
        // Home
        // ------------------------------------------------
        composable<Screen.Home> {
            val currentUser = FirebaseAuth.getInstance().currentUser
            HomeScreen(userName = currentUser?.displayName ?: "User", onAddNoteClick = {
                navController.navigate(
                    Screen.AddNote
                )
            }, onEditNoteClick = { note ->
                navController.navigate(
                    Screen.EditNote(note.id)
                )
            }, onLogoutSuccess = {
                navController.navigate(
                    Screen.Login
                ) {
                    popUpTo(Screen.Home) {
                        inclusive = true
                    }
                }
            })
        }
        // ------------------------------------------------
        // Add Note
        // ------------------------------------------------
        composable<Screen.AddNote> {
            AddNoteScreen(onBackClick = {
                navController.popBackStack()
            }, onNoteSaved = {
                navController.popBackStack()
            })
        }
        // ------------------------------------------------
        // Edit Note
        // ------------------------------------------------
        composable<Screen.EditNote> { backStackEntry ->
            val editRoute = backStackEntry.toRoute<Screen.EditNote>()
            val notesViewModel: NotesViewModel = viewModel()
            val notes by notesViewModel.notes.collectAsState()
            LaunchedEffect(Unit) {
                notesViewModel.loadNotes()
            }
            val note = notes.find { currentNote ->
                currentNote.id == editRoute.noteId
            }
            if (note != null) {
                EditNoteScreen(
                    note = note, onBackClick = {
                        navController.popBackStack()
                    }, onNoteUpdated = {
                        navController.popBackStack()
                    }, notesViewModel = notesViewModel
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    BackgroundDark, SurfaceDark, BackgroundDark
                                )
                            )
                        ), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = SecondaryDark
                    )
                }
            }
        }
    }
}