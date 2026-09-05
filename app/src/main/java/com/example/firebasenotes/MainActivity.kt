package com.example.firebasenotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.firebasenotes.ui.navigation.AppNavigation
import com.example.firebasenotes.ui.theme.FirebaseNotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirebaseNotesTheme {
                AppNavigation()
            }
        }
    }
}