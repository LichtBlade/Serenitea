package com.example.serenitea.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ListView
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompletedActivity : AppCompatActivity() {

    private lateinit var orderListView: ListView
    private var orderList: MutableList<Order> = mutableListOf()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_completed)

        // Handle window insets for edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize ListView and fetch orders
        orderListView = findViewById(R.id.listview_completed)

        // Fetch orders from API
        fetchOrders()
    }

    private fun fetchOrders() {
        // Make API call to fetch orders
        RetrofitClient.apiService.getOrders().enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    // Check if the response body is valid
                    response.body()?.let {
                        // Filter orders with "complete" status
                        val filteredOrders = it.filter { order ->
                            (order.status == "complete" || order.status == "delivered") && order.customer_name == GlobalVariable.userName
                        }

                        // Clear previous orders and add filtered ones
                        orderList.clear()
                        orderList.addAll(filteredOrders)

                        // Set up the adapter to display the filtered orders
                        if (::orderListView.isInitialized) {
                            val adapter = OrderAdapter(this@CompletedActivity, orderList)
                            orderListView.adapter = adapter
                        }
                    }
                } else {
                    Toast.makeText(this@CompletedActivity, "Failed to load orders", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                // Handle API call failure
                Toast.makeText(this@CompletedActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.d("CompletedActivity", "API Call Failed: ${t.message}")
            }
        })
    }
}
