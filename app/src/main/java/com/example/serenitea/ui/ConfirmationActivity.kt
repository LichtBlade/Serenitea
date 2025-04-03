package com.example.serenitea.ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
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
import com.example.serenitea.data.model.OrderUpdateRequest
import com.example.serenitea.ui.order.OrderAdapter
import com.example.serenitea.ui.order.OrderResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfirmationActivity : AppCompatActivity() {

    private lateinit var orderListView: ListView
    private lateinit var orderList: MutableList<Order>
    private lateinit var buttonConfirm: Button
    private lateinit var contactNumberEditText: EditText
    private lateinit var addressEditText: EditText

    // Stores the IDs of orders that have "Add to Cart" status
    private var orderIds: List<Int> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_confirmation)

        // Handle window insets (edge-to-edge setup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Set up UI elements
        orderListView = findViewById(R.id.listView)
        orderList = mutableListOf()
        buttonConfirm = findViewById(R.id.button4)
        contactNumberEditText = findViewById(R.id.contact_number)
        addressEditText = findViewById(R.id.address)

        // Fetch orders from API
        fetchOrders()

        // Handle order confirmation
        buttonConfirm.setOnClickListener {
            updateOrders()
        }
    }

    private fun fetchOrders() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Fetching orders...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        RetrofitClient.apiService.getOrders().enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                progressDialog.dismiss()

                if (response.isSuccessful) {
                    response.body()?.let { orders ->
                        // Filter orders with status "Add to Cart"
                        val filteredOrders = orders.filter { it.status == "Add to Cart" }
                        orderIds = filteredOrders.map { it.id } // Store IDs

                        Log.d("ORDER_IDS", "Filtered Order IDs: $orderIds")

                        // Update list and set adapter
                        orderList.clear()
                        orderList.addAll(filteredOrders)
                        orderListView.adapter = OrderAdapter(this@ConfirmationActivity, orderList)

                        // Show message if no orders found
                        if (filteredOrders.isEmpty()) {
                            Toast.makeText(this@ConfirmationActivity, "No orders in cart.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this@ConfirmationActivity, "Failed to load orders", Toast.LENGTH_SHORT).show()
                    Log.e("FETCH_ORDERS", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@ConfirmationActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("FETCH_ORDERS", "Failure: ${t.message}")
            }
        })
    }

    private fun updateOrders() {
        val contactNumber = contactNumberEditText.text.toString().trim()
        val address = addressEditText.text.toString().trim()

        if (contactNumber.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (orderIds.isEmpty()) {
            Toast.makeText(this, "No orders to update", Toast.LENGTH_SHORT).show()
            return
        }

        val request = OrderUpdateRequest(
            ids = orderIds,
            contact_number = contactNumber,
            address = address
        )

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Updating orders...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        RetrofitClient.apiService.updateOrders(request).enqueue(object : Callback<OrderResponse> {
            override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                progressDialog.dismiss()

                if (response.isSuccessful && response.body()?.status == "success") {

                    val intent = Intent(this@ConfirmationActivity, HomeActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this@ConfirmationActivity, "Orders updated successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ConfirmationActivity, "Failed to update orders", Toast.LENGTH_SHORT).show()
                    Log.e("UPDATE_ORDERS", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@ConfirmationActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("UPDATE_ORDERS", "Failure: ${t.message}")
            }
        })
    }
}
