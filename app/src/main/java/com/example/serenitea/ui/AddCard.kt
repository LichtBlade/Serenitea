package com.example.serenitea.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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

    private var quantity = 1
    private var selectedPrice = 0.0
    private var itemId = 0

    private lateinit var priceTextView: TextView
    private lateinit var quantityTextView: TextView
    private lateinit var selectedSmall: Button
    private lateinit var selectedMedium: Button
    private lateinit var selectedLarge: Button

    private var sugarLevel: String = "100%"  // Default
    private var addOns: String = "None"  // Default


    private var smallPrice = 0.0
    private var mediumPrice = 0.0
    private var largePrice = 0.0

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        val name = intent.getStringExtra("name")
        itemId = intent.getIntExtra("item_id", 0)
        val imageResId = intent.getIntExtra("image", R.drawable.americano)
        val description = intent.getStringExtra("description")
        largePrice = intent.getStringExtra("largePrice")?.toDoubleOrNull() ?: 0.0
        mediumPrice = intent.getStringExtra("MediumPrice")?.toDoubleOrNull() ?: 0.0
        smallPrice = intent.getStringExtra("smallPrice")?.toDoubleOrNull() ?: 0.0

        val circle1: View = findViewById(R.id.circle_1) // 25%
        val circle2: View = findViewById(R.id.circle_2)
        val circle3: View = findViewById(R.id.circle_3) // 25%
        val circle4: View = findViewById(R.id.circle_4)// 50%

        val circle5: View = findViewById(R.id.circle_5) // 25%
        val circle6: View = findViewById(R.id.circle_6)

        circle1.setOnClickListener {
            setSugarLevel("25%")
            // Set background color or image for circle1
            circle1.setBackgroundColor(getResources().getColor(R.color.black)) // If you want a solid color
            // OR if you want to use a drawable image:
            // circle1.setBackgroundResource(R.drawable.circle_png_)

            // Reset background for the other circles to the default image or color
            circle2.setBackgroundResource(R.drawable.circle_png_)  // Reset to the default image
            circle3.setBackgroundResource(R.drawable.circle_png_)  // Reset to the default image
            circle4.setBackgroundResource(R.drawable.circle_png_)  // Reset to the default image
        }


        circle2.setOnClickListener {
            setSugarLevel("50%")
            // Set background color to black for circle2
            circle2.setBackgroundColor(getResources().getColor(R.color.black))
            // Reset background color for the other circles
            circle1.setBackgroundResource(R.drawable.circle_png_)
            circle3.setBackgroundResource(R.drawable.circle_png_)
            circle4.setBackgroundResource(R.drawable.circle_png_)
        }

        circle3.setOnClickListener {
            setSugarLevel("75%")
            // Set background color to black for circle3
            circle3.setBackgroundColor(getResources().getColor(R.color.black))
            // Reset background color for the other circles
            circle1.setBackgroundResource(R.drawable.circle_png_)
            circle2.setBackgroundResource(R.drawable.circle_png_)
            circle4.setBackgroundResource(R.drawable.circle_png_)
        }

        circle4.setOnClickListener {
            setSugarLevel("100%")
            // Set background color to black for circle4
            circle4.setBackgroundColor(getResources().getColor(R.color.black))
            // Reset background color for the other circles
            circle1.setBackgroundResource(R.drawable.circle_png_)
            circle2.setBackgroundResource(R.drawable.circle_png_)
            circle3.setBackgroundResource(R.drawable.circle_png_)
        }

        circle5.setOnClickListener{
            setAddOns("Tapioca Pearls")
            circle5.setBackgroundColor(getResources().getColor(R.color.black))

            circle6.setBackgroundResource(R.drawable.circle_png_)
        }
        circle6.setOnClickListener{
            setAddOns("Tapioca Pearls")
            circle6.setBackgroundColor(getResources().getColor(R.color.black))

            circle5.setBackgroundResource(R.drawable.circle_png_)
        }


        val nameTextView: TextView = findViewById(R.id.name)
        val imageView: ImageView = findViewById(R.id.imageView)
        val descriptionTextView: TextView = findViewById(R.id.description)
        priceTextView = findViewById(R.id.textView28)
        quantityTextView = findViewById(R.id.Quantity)

        val minusButton: Button = findViewById(R.id.button2)
        val plusButton: Button = findViewById(R.id.button3)
        val orderButton: Button = findViewById(R.id.btn_addtocard)

        selectedSmall = findViewById(R.id.btn_small)
        selectedMedium = findViewById(R.id.btn_medium)
        selectedLarge = findViewById(R.id.btn_large)


        val uiSmall: TextView = findViewById(R.id.textView36)
        val uiMedium: TextView = findViewById(R.id.textView33)
        val uiLarge: TextView = findViewById(R.id.textView39)

        uiSmall.text = "PHP ${smallPrice}"
        uiMedium.text = "PHP ${mediumPrice}"
        uiLarge.text = "PHP ${largePrice}"

        nameTextView.text = name
        descriptionTextView.text = description
        Glide.with(this).load(imageResId).into(imageView)

        selectedPrice = smallPrice // Default selection
        updatePrice()

        minusButton.setOnClickListener { decreaseQuantity() }
        plusButton.setOnClickListener { increaseQuantity() }
        setupSizeButtons()

        orderButton.setOnClickListener { placeOrder() }
    }

    private fun updatePrice() {
        val total = selectedPrice * quantity
        priceTextView.text = "PHP %.2f".format(total)
        quantityTextView.text = quantity.toString()
    }

    private fun increaseQuantity() {
        quantity++
        updatePrice()
    }

    private fun setSugarLevel(level: String) {
        sugarLevel = level
        Toast.makeText(this, "Sugar level set to $sugarLevel", Toast.LENGTH_SHORT).show()
    }

    private fun setAddOns(level: String) {
        addOns = level
        Toast.makeText(this, "Set Add Ons $addOns", Toast.LENGTH_SHORT).show()
    }

    private fun decreaseQuantity() {
        if (quantity > 1) {
            quantity--
            updatePrice()
        }
    }

    private fun setupSizeButtons() {
        selectedSmall.setOnClickListener {
            selectSize(smallPrice, selectedSmall)
        }

        selectedMedium.setOnClickListener {
            selectSize(mediumPrice, selectedMedium)
        }

        selectedLarge.setOnClickListener {
            selectSize(largePrice, selectedLarge)
        }

        // Optional: Highlight default selected size
        updateButtonStyles(selectedSmall)
    }

    private fun selectSize(price: Double, selectedButton: Button) {
        selectedPrice = price
        updatePrice()
        updateButtonStyles(selectedButton)
    }

    private fun updateButtonStyles(selected: Button) {
        val defaultStyle = R.drawable.default_button_background
        val selectedStyle = R.drawable.selected_button_background

        selectedSmall.setBackgroundResource(defaultStyle)
        selectedMedium.setBackgroundResource(defaultStyle)
        selectedLarge.setBackgroundResource(defaultStyle)

        selected.setBackgroundResource(selectedStyle)
    }

    private fun placeOrder() {
        val customerName = userName
        val totalPrice = selectedPrice * quantity
        val orderRequest = OrderRequest(customerName, itemId, quantity, totalPrice,sugarLevel,addOns)

        RetrofitClient.apiService.placeOrder(orderRequest).enqueue(object : Callback<OrderResponse> {
            override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                if (response.isSuccessful) {
                    val message = response.body()?.message ?: "Order placed successfully!"
                    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@AddCard, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Failed to place order", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("AddCard", "Error placing order", t)
            }
        })
    }




}
