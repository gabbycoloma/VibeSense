package com.s12.mobdeve.coloma.caballero.vibesense


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.s12.mobdeve.coloma.caballero.vibesense.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val goToHome = Intent(applicationContext, MainActivity::class.java)
            startActivity(goToHome)
        }
    }

}