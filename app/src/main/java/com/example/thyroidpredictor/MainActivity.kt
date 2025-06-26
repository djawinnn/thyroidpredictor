package com.example.thyroidpredictor

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<CardView>(R.id.btnTentang).setOnClickListener {
            startActivity(Intent(this, TentangActivity::class.java))
        }

        findViewById<CardView>(R.id.btnDataset).setOnClickListener {
            startActivity(Intent(this, DatasetActivity::class.java))
        }

        findViewById<CardView>(R.id.btnFitur).setOnClickListener {
            startActivity(Intent(this, FiturActivity::class.java))
        }

        findViewById<CardView>(R.id.btnArsitektur).setOnClickListener {
            startActivity(Intent(this, ArsitekturActivity::class.java))
        }

        findViewById<CardView>(R.id.btnPrediksi).setOnClickListener {
            startActivity(Intent(this, SimulasiActivity::class.java))
        }

        findViewById<ImageView>(R.id.btnBackToOnboarding).setOnClickListener {
            val intent = Intent(this, OnboardingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}
