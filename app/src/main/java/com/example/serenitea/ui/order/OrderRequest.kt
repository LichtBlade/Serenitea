package com.example.serenitea.data.model

data class OrderRequest(
    val customer_name: String,
    val item_id: Int,
    val quantity: Int,
    val total_price: Double
)





data class OrderRequest_(
    val order_id: Int,
    val contact_number: String,
    val address: String,
    val item_ids: Item // Add this field
)
