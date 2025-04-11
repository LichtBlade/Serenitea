package com.example.serenitea

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.serenitea.ui.authentication.LoginActivity
import com.example.serenitea.ui.authentication.SignUpActivity

class Welcome1Activity : AppCompatActivity() {
    private lateinit var logIn: Button
    private lateinit var signUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        logIn = findViewById(R.id.button6)
        signUp = findViewById(R.id.button7)

        logIn.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
        signUp.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}