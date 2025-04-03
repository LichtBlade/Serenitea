package com.example.serenitea.data.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    val name: String,
    val email: String,
    @SerializedName("created_at") val createdAt: String // Maps "created_at" from API to "createdAt" in Kotlin
)
