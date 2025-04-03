package com.example.serenitea.data.model

import com.google.gson.annotations.SerializedName

data class Stock(
    val id: Int,
    @SerializedName("item_name") val itemName: String,  // Maps JSON "item_name" to Kotlin "itemName"
    val quantity: Int,
    @SerializedName("updated_at") val updatedAt: String // Maps JSON "updated_at" to Kotlin "updatedAt"
)