package com.s12.mobdeve.coloma.caballero.vibesense


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.s12.mobdeve.coloma.caballero.vibesense.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtLogin.setOnClickListener {
            val goToLogin = Intent(applicationContext, LoginActivity::class.java)
            startActivity(goToLogin)
        }

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener{
            val firstName = binding.etFirstname.text.toString()
            val lastName = binding.etLastname.text.toString()
            val email = binding.txtEmail.text.toString()
            val password = binding.txtPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty()){
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                    if(it.isSuccessful){
                        val goToLogin = Intent(applicationContext, LoginActivity::class.java)
                        startActivity(goToLogin)
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