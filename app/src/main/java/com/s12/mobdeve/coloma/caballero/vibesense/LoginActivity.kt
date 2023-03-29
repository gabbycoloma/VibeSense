package com.s12.mobdeve.coloma.caballero.vibesense

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.s12.mobdeve.coloma.caballero.vibesense.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.txtRegister.setOnClickListener {
            val goToRegister = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(goToRegister)
        }
        binding.btnLogin.setOnClickListener {


            binding.btnLogin.setOnClickListener{
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty()){
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                        if(it.isSuccessful){
                            val goToHome = Intent(applicationContext, MainActivity::class.java)
                            startActivity(goToHome)
                        }else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT)
                        }
                    }
                }else{
                    Toast.makeText(this, "Empty Fields are not allowed", Toast.LENGTH_SHORT)
                }
            }

        }


    }
    override fun onStart() {
        super.onStart()

        if(firebaseAuth.currentUser != null){
            val goToHome = Intent(applicationContext, MainActivity::class.java)
            startActivity(goToHome)
        }
    }
}