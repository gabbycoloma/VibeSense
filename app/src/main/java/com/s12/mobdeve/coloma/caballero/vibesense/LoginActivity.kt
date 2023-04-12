package com.s12.mobdeve.coloma.caballero.vibesense

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.s12.mobdeve.coloma.caballero.vibesense.Model.User
import com.s12.mobdeve.coloma.caballero.vibesense.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://vibesense-9523f-default-rtdb.asia-southeast1.firebasedatabase.app")

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
                            val userReference = databaseReference.child("Users")
                            userReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object :
                                ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.exists()) {
                                        for (userSnapshot in snapshot.children) {
                                            val user = userSnapshot.getValue(User::class.java)
                                            if (user != null && user.password == password) {
                                                // Password matches, retrieve user data and do something
                                                val userId = userSnapshot.key
                                                Log.d("USER ID", userId.toString())
                                                val email = user.email
                                                Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT).show()

                                                val goToHome = Intent(applicationContext, MainActivity::class.java)
                                                startActivity(goToHome)
                                            } else {
                                                // Password does not match, show error message
                                                Toast.makeText(this@LoginActivity, "Invalid email or password", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    } else {
                                        // User does not exist, show error message
                                        Toast.makeText(this@LoginActivity, "The user does not exist.", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                override fun onCancelled(error: DatabaseError) {
                                    // Handle database error
                                }
                            })
                        }else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
                }else{
                    Toast.makeText(this, "Empty Fields are not allowed", Toast.LENGTH_SHORT).show()
                }
            }

        }


    }

}