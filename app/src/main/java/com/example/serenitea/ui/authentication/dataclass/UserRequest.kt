package com.example.serenitea.ui.authentication.dataclass

data class UserRequest(
    val email: String,
    val password: String,
    val role: String,
    val name: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val status: String,
    val message: String?,
    val name: String?,
    val role: String? // Ensure this exists if you are using it in LoginActivity
)