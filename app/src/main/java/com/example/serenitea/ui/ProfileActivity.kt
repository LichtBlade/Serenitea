package com.example.serenitea.ui

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.serenitea.R
import com.example.serenitea.glovalvariable.GlobalVariable.email_
import com.example.serenitea.glovalvariable.GlobalVariable.password_
import com.example.serenitea.glovalvariable.GlobalVariable.userName

class ProfileActivity : AppCompatActivity() {

    private lateinit var tv_profileName: TextView
    private lateinit var tv_emailProfile: TextView
    private lateinit var tv_passProfile: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        // Setting up window insets (for edge-to-edge screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        tv_profileName = findViewById(R.id.tv_profileName)
        tv_emailProfile = findViewById(R.id.tv_emailProfile)
        tv_passProfile = findViewById(R.id.tv_passProfile)

        // Set the values of TextViews to global variables
        tv_profileName.text = userName
        tv_emailProfile.text = email_
        tv_passProfile.text = password_

    }
}
