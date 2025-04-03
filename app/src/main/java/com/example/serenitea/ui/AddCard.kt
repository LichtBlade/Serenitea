package com.example.serenitea.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.serenitea.R
import com.example.serenitea.data.api.RetrofitClient
import com.example.serenitea.data.model.OrderRequest
import com.example.serenitea.glovalvariable.GlobalVariable.userName
import com.example.serenitea.ui.order.OrderResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCard : AppCompatActivity() {
    private var quantity = 1  // Default quantity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        // Get data from Intent
        val name = intent.getStringExtra("name")
        val itemId = intent.getIntExtra("item_id", 0)  // Get item ID
        val imageResId = intent.getIntExtra("image", R.drawable.americano)  // Default image
        val description = intent.getStringExtra("description")
        val price = intent.getStringExtra("price")?.toDoubleOrNull() ?: 0.0  // Convert price to Double

        // Reference UI components
        val nameTextView: TextView = findViewById(R.id.name)
        val imageView: ImageView = findViewById(R.id.imageView)
        val descriptionTextView: TextView = findViewById(R.id.description)
        val priceTextView: TextView = findViewById(R.id.price)
        val quantityTextView: TextView = findViewById(R.id.Quantity)
        val minusButton: Button = findViewById(R.id.button2)
        val plusButton: Button = findViewById(R.id.button3)
        val orderButton: Button = findViewById(R.id.btn_addtocard) // Add order button

        // Set initial data
        nameTextView.text = name
        descriptionTextView.text = description
        Glide.with(this).load(imageResId).into(imageView)

        // Update price based on quantity
        fun updatePrice() {
            val totalPrice = price * quantity
            priceTextView.text = "PHP %.2f".format(totalPrice)  // Format price to 2 decimal places
            quantityTextView.text = quantity.toString()
        }

        // Handle quantity decrease
        minusButton.setOnClickListener {
            if (quantity > 1) {
                quantity--
                updatePrice()
            }
        }

        // Handle quantity increase
        plusButton.setOnClickListener {
            quantity++
            updatePrice()
        }

        // Handle order submission
        orderButton.setOnClickListener {
            val totalPrice = price * quantity
            val customerName = userName // Replace with actual customer name from session

            val orderRequest = OrderRequest(customerName, itemId, quantity, totalPrice)

            RetrofitClient.apiService.placeOrder(orderRequest).enqueue(object : Callback<OrderResponse> {
                override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                    if (response.isSuccessful) {
                        val orderResponse = response.body()
                        // Now you can access the status and message from the response
                        Toast.makeText(applicationContext, orderResponse?.message ?: "Order Placed Successfully!", Toast.LENGTH_SHORT).show()

                        // Navigate to HomeActivity after successful order
                        val intent = Intent(this@AddCard, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "Failed to place order", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    Log.e("Add to Card", t.message.toString())
                }
            })


        }

        // Set initial price
        updatePrice()
    }
}
