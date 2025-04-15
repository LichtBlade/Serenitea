package com.example.serenitea.ui.order

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.serenitea.R
import com.example.serenitea.WelcomeActivity
import com.example.serenitea.data.api.RetrofitClient
import com.example.serenitea.data.model.Order
import com.example.serenitea.glovalvariable.GlobalVariable
import com.example.serenitea.ui.CompletedActivity
import com.example.serenitea.ui.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreparingActivity : AppCompatActivity() {
    private lateinit var orderListView: ListView
    private lateinit var orderList: MutableList<Order>
    private lateinit var ofd: TextView
    private lateinit var completed: TextView
    private lateinit var btnBack: TextView



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_preparing)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnBack = findViewById(R.id.btn_back1)

        btnBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        orderListView = findViewById(R.id.listview_preparing)
        orderList = mutableListOf()

        completed = findViewById(R.id.textView7)
        ofd = findViewById(R.id.textView6)


        completed.setOnClickListener {
            val intent = Intent(this, CompletedActivity::class.java)
            startActivity(intent)
        }

        ofd.setOnClickListener {
            val intent = Intent(this, OutForDeliveryActivity::class.java)
            startActivity(intent)
        }


        fetchOrders()
    }


    private fun fetchOrders() {
        RetrofitClient.apiService.getOrders().enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        // Filter the orders to only include those with status "Add to Cart"
                        val filteredOrders = it.filter { order ->
                            order.status == "Pending" && order.customer_name == GlobalVariable.userName
                        }

                        // Clear the previous list and add the filtered orders
                        orderList.clear()
                        orderList.addAll(filteredOrders)

                        // Set the adapter to display the filtered orders
                        val adapter = OrderAdapter(this@PreparingActivity, orderList)
                        orderListView.adapter = adapter
                    }
                } else {
                    Toast.makeText(this@PreparingActivity, "Failed to load orders", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Toast.makeText(this@PreparingActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.d("CARD ACTIVITY", "Fetched: ${t.message}")
            }
        })
    }
}