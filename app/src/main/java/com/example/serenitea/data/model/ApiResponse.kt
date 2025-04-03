package com.example.serenitea.data.model

data class ApiResponse(
    val success: Boolean,
    val message: String?,
    val error: String?
)