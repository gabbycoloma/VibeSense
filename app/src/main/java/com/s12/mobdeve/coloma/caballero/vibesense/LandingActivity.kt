package com.s12.mobdeve.coloma.caballero.vibesense

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.s12.mobdeve.coloma.caballero.vibesense.databinding.ActivityLandingBinding

class LandingActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLandingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnRegister.setOnClickListener {
            val goToRegister = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(goToRegister)
        }

        binding.btnLogin.setOnClickListener {
            val goToLogin = Intent(applicationContext, LoginActivity::class.java)
            startActivity(goToLogin)
        }
    }
}