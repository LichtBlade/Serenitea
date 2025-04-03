package com.example.serenitea.ui

import BestOffer
import BestOfferAdapter
import CategoriesAdapter
import Category
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serenitea.R
import com.example.serenitea.data.api.RetrofitClient

import retrofit2.Call
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        Log.d("HomeActivity", "HomeActivity started!")

        val userAccountTextView: TextView = findViewById(R.id.useraccount)

        userAccountTextView.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }


        setupCategoriesRecyclerView()
        setupBestOffersRecyclerView()
    }

    private fun setupCategoriesRecyclerView() {
        val recyclerViewCategories: RecyclerView = findViewById(R.id.recyclerView_category)
        recyclerViewCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        RetrofitClient.apiService.getCategories().enqueue(object : retrofit2.Callback<List<Category>> {
            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                if (response.isSuccessful) {
                    val categoriesList = response.body() ?: emptyList()

                    val uniqueCategories = categoriesList
                        .groupBy { it.categoryName }
                        .map { it.value.first() }

                    recyclerViewCategories.adapter = CategoriesAdapter(uniqueCategories)
                    Log.d("Home", "Fetched Categories: $categoriesList")
                } else {
                    Log.e("Home", "Failed to fetch categories: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                Log.e("Home", "Error fetching categories", t)
            }
        })
    }


    private fun setupBestOffersRecyclerView() {
        val recyclerViewBestOffers: RecyclerView = findViewById(R.id.recyclerView_bestOffer)

        // Use GridLayoutManager with 2 columns
        recyclerViewBestOffers.layoutManager = GridLayoutManager(this, 2)

        RetrofitClient.apiService.getBestOffers().enqueue(object : retrofit2.Callback<List<BestOffer>> {
            override fun onResponse(call: Call<List<BestOffer>>, response: Response<List<BestOffer>>) {
                if (response.isSuccessful) {
                    val bestOffersList = response.body() ?: emptyList()

                    // Filter the best offers to show only "Beverage" category
                    val beverageBestOffers = bestOffersList.filter { it.categoryName.equals("Beverages", ignoreCase = true) }

                    // Pass the filtered list to the adapter
                    recyclerViewBestOffers.adapter = BestOfferAdapter(beverageBestOffers)
                    Log.d("Home", "Fetched Best Offers: $beverageBestOffers")
                } else {
                    Log.e("Home", "Failed to fetch best offers: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<BestOffer>>, t: Throwable) {
                Log.e("Home", "Error fetching best offers", t)
            }
        })
    }


}
