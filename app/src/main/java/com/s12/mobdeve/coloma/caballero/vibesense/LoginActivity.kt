package com.s12.mobdeve.coloma.caballero.vibesense

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.s12.mobdeve.coloma.caballero.vibesense.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val goToHome = Intent(applicationContext, MainActivity::class.java)
            startActivity(goToHome)
        }
    }

}