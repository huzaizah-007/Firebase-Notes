package com.example.firebasenotes.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebasenotes.ui.theme.BackgroundDark
import com.example.firebasenotes.ui.theme.PrimaryDark
import com.example.firebasenotes.ui.theme.SecondaryDark
import com.example.firebasenotes.ui.theme.SurfaceDark
import com.example.firebasenotes.ui.theme.TextPrimaryDark
import com.example.firebasenotes.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    viewModel: AuthViewModel = viewModel()
) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var passwordVisible by remember {
        mutableStateOf(false)
    }
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val authSuccess by viewModel.authSuccess.collectAsState()
    LaunchedEffect(authSuccess) {
        if (authSuccess) {
            onLoginSuccess()
        }
    }
    // Same Purple Gradient for all screens
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            BackgroundDark, SurfaceDark, Color(0xFF2D0A63)
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .widthIn(max = 600.dp)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            // App Logo
            Box(
                modifier = Modifier
                    .background(
                        color = PrimaryDark, shape = RoundedCornerShape(22.dp)
                    )
                    .padding(18.dp)
            ) {
                Text(
                    text = "📝", style = MaterialTheme.typography.displayMedium, color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            // App Name
            Text(
                text = "Firebase Notes",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Tagline
            Text(
                text = "Your thoughts, organized.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFBDB6C9)
            )
            Spacer(modifier = Modifier.height(36.dp))
            // Email
            OutlinedTextField(
                value = email, onValueChange = {
                    email = it
                    viewModel.clearError()
                }, modifier = Modifier.fillMaxWidth(), singleLine = true, label = {
                    Text("Email")
                }, leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email, contentDescription = "Email"
                    )
                }, keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ), shape = RoundedCornerShape(16.dp), colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SecondaryDark,
                    unfocusedBorderColor = Color(0xFF6D5A82),
                    focusedLabelColor = SecondaryDark,
                    unfocusedLabelColor = Color(0xFFBDB6C9),
                    cursorColor = SecondaryDark,
                    focusedTextColor = TextPrimaryDark,
                    unfocusedTextColor = TextPrimaryDark,
                    focusedLeadingIconColor = SecondaryDark,
                    unfocusedLeadingIconColor = Color(0xFFBDB6C9)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Password
            OutlinedTextField(
                value = password, onValueChange = {
                    password = it
                    viewModel.clearError()
                }, modifier = Modifier.fillMaxWidth(), singleLine = true, label = {
                    Text("Password")
                }, leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock, contentDescription = "Password"
                    )
                }, trailingIcon = {
                    IconButton(
                        onClick = {
                            passwordVisible = !passwordVisible
                        }) {
                        Icon(
                            imageVector = if (passwordVisible) {
                                Icons.Default.VisibilityOff
                            } else {
                                Icons.Default.Visibility
                            }, contentDescription = if (passwordVisible) {
                                "Hide password"
                            } else {
                                "Show password"
                            }
                        )
                    }
                }, visualTransformation = if (passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                }, keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ), shape = RoundedCornerShape(16.dp), colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SecondaryDark,
                    unfocusedBorderColor = Color(0xFF6D5A82),
                    focusedLabelColor = SecondaryDark,
                    unfocusedLabelColor = Color(0xFFBDB6C9),
                    cursorColor = SecondaryDark,
                    focusedTextColor = TextPrimaryDark,
                    unfocusedTextColor = TextPrimaryDark,
                    focusedLeadingIconColor = SecondaryDark,
                    unfocusedLeadingIconColor = Color(0xFFBDB6C9)
                )
            )
            // Forgot Password
            TextButton(
                onClick = onForgotPasswordClick,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 2.dp)
            ) {
                Text(
                    text = "Forgot Password?", color = SecondaryDark, fontWeight = FontWeight.Medium
                )
            }
            // Error Message
            if (errorMessage != null) {
                Text(
                    text = errorMessage ?: "",
                    color = Color(0xFFFF6B6B),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            // Login Button
            Button(
                onClick = {
                    viewModel.loginUser(
                        email = email, password = password
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                enabled = !isLoading,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryDark, contentColor = Color.White
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.height(22.dp), color = Color.White, strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Login", style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
            // Register
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account?",
                    color = Color(0xFFBDB6C9),
                    style = MaterialTheme.typography.bodyMedium
                )
                TextButton(
                    onClick = onRegisterClick
                ) {
                    Text(
                        text = "Register", color = SecondaryDark, fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}