package com.example.serenitea.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.serenitea.R
import com.example.serenitea.data.api.RetrofitClient
import com.example.serenitea.data.model.Order
import com.example.serenitea.glovalvariable.GlobalVariable
import com.example.serenitea.ui.order.OrderAdapter
import com.example.serenitea.ui.order.OutForDeliveryActivity
import com.example.serenitea.ui.order.PreparingActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartActivity : AppCompatActivity() {

    private lateinit var orderListView: ListView
    private lateinit var orderList: MutableList<Order>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart)

        // Set up the UI elements
        orderListView = findViewById(R.id.lw_cartItem)
        orderList = mutableListOf()

        val button_checkOut: Button = findViewById(R.id.button)
        // Handle window insets (edge-to-edge setup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        button_checkOut.setOnClickListener{
            val intent = Intent(this, ConfirmationActivity::class.java)
            startActivity(intent)
        }

        // Fetch orders from API
        fetchOrders()
    }

    private fun fetchOrders() {
        RetrofitClient.apiService.getOrders().enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        // Filter the orders to only include those with status "Add to Cart"
                        val filteredOrders = it.filter { order ->
                            order.status == "Add to Cart" && order.customer_name == GlobalVariable.userName
                        }

                        // Clear the previous list and add the filtered orders
                        orderList.clear()
                        orderList.addAll(filteredOrders)

                        // Set the adapter to display the filtered orders
                        val adapter = OrderAdapter(this@CartActivity, orderList)
                        orderListView.adapter = adapter
                    }
                } else {
                    Toast.makeText(this@CartActivity, "Failed to load orders", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Toast.makeText(this@CartActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.d("CARD ACTIVITY", "Fetched: ${t.message}")
            }
        })
    }
}
