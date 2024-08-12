package com.example.bookingclass.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun loginWithEmailAndPassword(navController: NavHostController) {
        val email = _uiState.value.email
        val password = _uiState.value.password
        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                navController.navigate("MainView") {
                    popUpTo("PreLoginView") { inclusive = false }
                }
            } catch (e: Exception) {
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false
)