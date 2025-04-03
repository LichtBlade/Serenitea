package com.example.serenitea.data.model

data class Order(
    val id: Int,
    val customer_name: String,
    val item_id: Item,
    val quantity: Int,
    val total_price: Double,
    val order_date: String,
    val status: String,
    val item_name: String,
    val item_price: Double,
    val item_image: String,
    val contact_number: String? = null,  // Nullable field for contact number
    val address: String? = null
)

data class Item(
    val id: Int,
    val name: String
)

data class OrderUpdateRequest(
    val ids: List<Int>,          // List of order IDs to update
    val contact_number: String,  // New contact number
    val address: String          // New address
)


