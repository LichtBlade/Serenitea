package com.example.serenitea.data.api


import BestOffer
import Category
import com.example.serenitea.data.model.Order
import com.example.serenitea.data.model.OrderRequest
import com.example.serenitea.data.model.OrderRequest_
import com.example.serenitea.data.model.OrderUpdateRequest
import com.example.serenitea.ui.authentication.dataclass.LoginRequest
import com.example.serenitea.ui.authentication.dataclass.LoginResponse
import com.example.serenitea.ui.authentication.dataclass.UserRequest
import com.example.serenitea.ui.order.OrderResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {


//    @GET("fetch_items.php")
//    fun getCategories(): Call<List<Category>>

    @GET("fetch_items.php")
    fun getBestOffers(): Call<List<BestOffer>>

    @POST("place_order.php")
    fun placeOrder(@Body orderRequest: OrderRequest): Call<OrderResponse>

    @GET("fetch_orders.php")  // Change the endpoint as needed
    fun getOrders(): Call<List<Order>>

    @PUT("confirmation_order.php") // Change this to your actual API URL
    fun updateOrders(@Body request: OrderUpdateRequest): Call<OrderResponse>

    @POST("register.php")  // Make sure this matches your backend endpoint
    fun registerUser(@Body request: UserRequest): Call<Map<String, String>>

    @POST("login_user.php") // Adjust to your actual endpoint
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>
}
