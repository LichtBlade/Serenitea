
package com.example.serenitea.ui.authentication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.serenitea.R
import com.example.serenitea.data.api.RetrofitClient
import com.example.serenitea.glovalvariable.GlobalVariable.email_
import com.example.serenitea.glovalvariable.GlobalVariable.password_
import com.example.serenitea.glovalvariable.GlobalVariable.userName // Corrected import
import com.example.serenitea.ui.HomeActivity
import com.example.serenitea.ui.authentication.dataclass.LoginRequest
import com.example.serenitea.ui.authentication.dataclass.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var inputEmail: EditText
    private lateinit var inputPassword: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        inputEmail = findViewById(R.id.email_input)
        inputPassword = findViewById(R.id.password_hint)
        btnLogin = findViewById(R.id.btnlogin)

        btnLogin.setOnClickListener {
            val email = inputEmail.text.toString().trim()
            val password = inputPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loginUser(email, password)
        }
    }

    private fun loginUser(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)
        val call: Call<LoginResponse> = RetrofitClient.apiService.loginUser(loginRequest)

        Log.d("LoginActivity_req", "Sending request: $loginRequest")

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.d("LoginActivity", "Response received: $response")

                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("LoginActivity", "Response body: $body")

                    if (body != null && body.status == "success") {
                        Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()

                        // Check if the role is "user"
                        if (body.role == "user") {
                            // Set the userName in GlobalVariable and navigate to HomeActivity
                            userName = body.name.toString()

                            email_ = email
                            password_ = password

                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                            finish()  // Close login activity
                        } else {
                            // Show a message if role is not "user"
                            Toast.makeText(this@LoginActivity, "Access Denied: Invalid Role", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, body?.message ?: "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Server Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("LoginActivity", "Network Error: ${t.message}")
                Toast.makeText(this@LoginActivity, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
