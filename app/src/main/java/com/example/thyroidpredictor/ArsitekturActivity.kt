package com.example.thyroidpredictor

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ArsitekturActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arsitektur)

        val backButton: ImageView = findViewById(R.id.btnBack)
        backButton.setOnClickListener {
            finish()
        }
    }
}
