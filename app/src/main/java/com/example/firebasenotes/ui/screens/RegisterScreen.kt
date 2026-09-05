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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebasenotes.ui.theme.BackgroundDark
import com.example.firebasenotes.ui.theme.ErrorColor
import com.example.firebasenotes.ui.theme.PrimaryDark
import com.example.firebasenotes.ui.theme.SecondaryDark
import com.example.firebasenotes.ui.theme.SurfaceDark
import com.example.firebasenotes.ui.theme.TextPrimaryDark
import com.example.firebasenotes.ui.theme.TextSecondaryDark
import com.example.firebasenotes.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    onLoginClick: () -> Unit = {},
    onRegistrationSuccess: () -> Unit = {},
    viewModel: AuthViewModel = viewModel()
) {
    var name by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }
    var passwordVisible by remember {
        mutableStateOf(false)
    }
    var confirmPasswordVisible by remember {
        mutableStateOf(false)
    }
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val authSuccess by viewModel.authSuccess.collectAsState()
    LaunchedEffect(authSuccess) {
        if (authSuccess) {
            onRegistrationSuccess()
        }
    }
    // Same Purple Gradient
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
                    .padding(14.dp), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "📝", fontSize = 40.sp, color = TextPrimaryDark
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Screen Title
            Text(
                text = "Create Account",
                style = MaterialTheme.typography.headlineLarge,
                color = TextPrimaryDark
            )
            Spacer(modifier = Modifier.height(6.dp))
            // Subtitle
            Text(
                text = "Start organizing your thoughts.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondaryDark
            )
            Spacer(modifier = Modifier.height(28.dp))
            // Name
            OutlinedTextField(
                value = name, onValueChange = {
                    name = it
                    viewModel.clearError()
                }, modifier = Modifier.fillMaxWidth(), singleLine = true, label = {
                    Text("Name")
                }, leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person, contentDescription = "Name"
                    )
                }, keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ), shape = RoundedCornerShape(16.dp), colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SecondaryDark,
                    unfocusedBorderColor = Color(0xFF6D5A82),
                    focusedLabelColor = SecondaryDark,
                    unfocusedLabelColor = TextSecondaryDark,
                    cursorColor = SecondaryDark,
                    focusedTextColor = TextPrimaryDark,
                    unfocusedTextColor = TextPrimaryDark,
                    focusedLeadingIconColor = SecondaryDark,
                    unfocusedLeadingIconColor = TextSecondaryDark
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
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
                    unfocusedLabelColor = TextSecondaryDark,
                    cursorColor = SecondaryDark,
                    focusedTextColor = TextPrimaryDark,
                    unfocusedTextColor = TextPrimaryDark,
                    focusedLeadingIconColor = SecondaryDark,
                    unfocusedLeadingIconColor = TextSecondaryDark
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
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
                    unfocusedLabelColor = TextSecondaryDark,
                    cursorColor = SecondaryDark,
                    focusedTextColor = TextPrimaryDark,
                    unfocusedTextColor = TextPrimaryDark,
                    focusedLeadingIconColor = SecondaryDark,
                    unfocusedLeadingIconColor = TextSecondaryDark
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            // Confirm Password
            OutlinedTextField(
                value = confirmPassword, onValueChange = {
                    confirmPassword = it
                    viewModel.clearError()
                }, modifier = Modifier.fillMaxWidth(), singleLine = true, label = {
                    Text("Confirm Password")
                }, leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock, contentDescription = "Confirm Password"
                    )
                }, trailingIcon = {
                    IconButton(
                        onClick = {
                            confirmPasswordVisible = !confirmPasswordVisible
                        }) {
                        Icon(
                            imageVector = if (confirmPasswordVisible) {
                                Icons.Default.VisibilityOff
                            } else {
                                Icons.Default.Visibility
                            }, contentDescription = if (confirmPasswordVisible) {
                                "Hide password"
                            } else {
                                "Show password"
                            }
                        )
                    }
                }, visualTransformation = if (confirmPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                }, keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ), shape = RoundedCornerShape(16.dp), colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SecondaryDark,
                    unfocusedBorderColor = Color(0xFF6D5A82),
                    focusedLabelColor = SecondaryDark,
                    unfocusedLabelColor = TextSecondaryDark,
                    cursorColor = SecondaryDark,
                    focusedTextColor = TextPrimaryDark,
                    unfocusedTextColor = TextPrimaryDark,
                    focusedLeadingIconColor = SecondaryDark,
                    unfocusedLeadingIconColor = TextSecondaryDark
                )
            )
            // Error Message
            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = errorMessage ?: "",
                    color = ErrorColor,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            // Create Account Button
            Button(
                onClick = {
                    viewModel.registerUser(
                        name = name,
                        email = email,
                        password = password,
                        confirmPassword = confirmPassword
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                enabled = !isLoading,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryDark, contentColor = TextPrimaryDark
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp), color = TextPrimaryDark, strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Create Account", style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
            // Login
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already have an account?",
                    color = TextSecondaryDark,
                    style = MaterialTheme.typography.bodyMedium
                )
                TextButton(
                    onClick = onLoginClick
                ) {
                    Text(
                        text = "Login", color = SecondaryDark, fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}