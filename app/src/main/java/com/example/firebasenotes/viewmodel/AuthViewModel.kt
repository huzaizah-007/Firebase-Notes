package com.example.firebasenotes.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage.asStateFlow()
    private val _authSuccess = MutableStateFlow(false)
    val authSuccess: StateFlow<Boolean> = _authSuccess.asStateFlow()
    fun registerUser(
        name: String, email: String, password: String, confirmPassword: String
    ) {
        _errorMessage.value = null
        _successMessage.value = null
        _authSuccess.value = false
        if (name.isBlank()) {
            _errorMessage.value = "Please enter your name"
            return
        }
        if (email.isBlank()) {
            _errorMessage.value = "Please enter your email"
            return
        }
        if (password.isBlank()) {
            _errorMessage.value = "Please enter your password"
            return
        }
        if (password.length < 6) {
            _errorMessage.value = "Password must be at least 6 characters"
            return
        }
        if (confirmPassword.isBlank()) {
            _errorMessage.value = "Please confirm your password"
            return
        }
        if (password != confirmPassword) {
            _errorMessage.value = "Passwords do not match"
            return
        }
        _isLoading.value = true
        auth.createUserWithEmailAndPassword(
            email.trim(), password
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                val profileUpdates = com.google.firebase.auth.UserProfileChangeRequest.Builder()
                    .setDisplayName(name.trim()).build()
                user?.updateProfile(profileUpdates)?.addOnCompleteListener { profileTask ->
                    _isLoading.value = false
                    if (profileTask.isSuccessful) {
                        _authSuccess.value = true
                    } else {
                        _errorMessage.value =
                            profileTask.exception?.localizedMessage ?: "Failed to save user name"
                    }
                }
            } else {
                _isLoading.value = false
                _errorMessage.value = task.exception?.localizedMessage ?: "Registration failed"
            }
        }
    }

    fun loginUser(
        email: String, password: String
    ) {
        _errorMessage.value = null
        _successMessage.value = null
        _authSuccess.value = false
        if (email.isBlank()) {
            _errorMessage.value = "Please enter your email"
            return
        }
        if (password.isBlank()) {
            _errorMessage.value = "Please enter your password"
            return
        }
        _isLoading.value = true
        auth.signInWithEmailAndPassword(
            email.trim(), password
        ).addOnCompleteListener { task ->
            _isLoading.value = false
            if (task.isSuccessful) {
                _authSuccess.value = true
            } else {
                _errorMessage.value = task.exception?.localizedMessage ?: "Login failed"
            }
        }
    }

    fun resetPassword(email: String) {
        _errorMessage.value = null
        _successMessage.value = null
        if (email.isBlank()) {
            _errorMessage.value = "Please enter your email"
            return
        }
        _isLoading.value = true
        auth.sendPasswordResetEmail(
            email.trim()
        ).addOnCompleteListener { task ->
            _isLoading.value = false
            if (task.isSuccessful) {
                _successMessage.value = "Password reset link sent to your email"
            } else {
                _errorMessage.value =
                    task.exception?.localizedMessage ?: "Failed to send reset link"
            }
        }
    }

    fun logout() {
        auth.signOut()
        _authSuccess.value = false
    }

    fun clearError() {
        _errorMessage.value = null
    }

    fun clearSuccess() {
        _successMessage.value = null
    }
}