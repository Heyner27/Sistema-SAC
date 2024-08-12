package com.example.bookingclass.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterScreen(onRegister: (Boolean) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val textState = remember { mutableStateOf("") }

    var selectedDate by remember { mutableStateOf<java.time.LocalDate?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }

    var datePickerDialog by remember { mutableStateOf(false) }
    var showLoading by remember { mutableStateOf(false) }


    fun validateInput(): Boolean {
        return when {
            fullName.isEmpty() -> {
                errorMessage = "Full Name is required."
                false
            }
            phone.isEmpty() -> {
                errorMessage = "Phone number is required."
                false
            }
            dob.isEmpty() -> {
                errorMessage = "Date of Birth is required."
                false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                errorMessage = "Invalid email format."
                false
            }
            password.isEmpty() -> {
                errorMessage = "Password is required."
                false
            }
            password.length < 6 -> {
                errorMessage = "Password should be at least 6 characters."
                false
            }
            else -> true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .padding(4.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text("Nombres") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Celular") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = dob,
                        onValueChange = { dob = it },
                        label = { Text("Fecha de Nacimiento") },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            IconButton(onClick = { datePickerDialog = true }) {
                                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Select Date")
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        modifier = Modifier
                            .fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedButton(
                        onClick = {

                            showLoading = true
                            if (validateInput()) {
                                registerUserWithDetails(email, password, fullName, phone, dob) { success, error ->
                                    if (success) {
                                        val rta = success
                                        showLoading = false
                                        showErrorDialog = true
                                        errorMessage = "Registro exitoso"
                                        onRegister(true)
                                       // navegar a login
                                    } else {
                                        val rtaa = success
                                        showLoading = false
                                        showErrorDialog = true
                                        errorMessage = error
                                        onRegister(false)
                                        // Muestra el error en la pantalla de registro
                                    }
                                }
                            } else {
                                showLoading = false
                                showErrorDialog = true
                            }


                         },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        shape = RoundedCornerShape(25.dp),
                        border = BorderStroke(1.dp, Color.Gray)
                    ) {
                        Text("Register")
                       // Text(getString(R.string.register,this))
                    }
                }

            }
        }

        errorMessage?.let {
            Text(text = it, color = Color.Red)
        }
    }

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text("Error") },
            text = { Text(errorMessage ?: "An unknown error occurred.") },
            confirmButton = {
                Button(onClick = { showErrorDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
    if (showLoading) {
        LoadingDialog()
    }
}

fun registerUserWithDetails(
    email: String,
    password: String,
    fullName: String,
    phone: String,
    dob: String,
    onResult: (Boolean, String?) -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userId = auth.currentUser?.uid ?: ""
                val user = hashMapOf(
                    "fullName" to fullName,
                    "phone" to phone,
                    "dob" to dob,
                    "email" to email
                )
                firestore.collection("users").document(userId).set(user)
                    .addOnCompleteListener { firestoreTask ->
                        if (firestoreTask.isSuccessful) {
                            onResult(true, null)
                        } else {
                            onResult(false, getAuthErrorMessage(firestoreTask.exception))
                        }
                    }
            } else {
                onResult(false, getAuthErrorMessage(task.exception))
            }
        }
}

fun getAuthErrorMessage(exception: Exception?): String {
    return when (exception) {
        is FirebaseAuthException -> {
            when (exception.errorCode) {
                "ERROR_INVALID_EMAIL" -> "The email address is badly formatted."
                "ERROR_EMAIL_ALREADY_IN_USE" -> "The email address is already in use by another account."
                "ERROR_WEAK_PASSWORD" -> "The password is too weak. Password should be at least 6 characters."
                "ERROR_USER_DISABLED" -> "The user account has been disabled by an administrator."
                "ERROR_OPERATION_NOT_ALLOWED" -> "This operation is not allowed."
                "ERROR_INVALID_CREDENTIAL" -> "The credential is invalid or has expired."
                else -> exception.localizedMessage ?: "An unknown error occurred."
            }
        }
        else -> exception?.localizedMessage ?: "An unknown error occurred."
    }
}



@Composable
fun LoadingDialog() {
    Dialog(onDismissRequest = {}) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(100.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
        ) {
            CircularProgressIndicator()
        }
    }
}
