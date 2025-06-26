package com.example.thyroidpredictor

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val btnMasuk: Button = findViewById(R.id.btnMasuk)
        btnMasuk.setOnClickListener {

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
