package com.s12.mobdeve.coloma.caballero.vibesense


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.s12.mobdeve.coloma.caballero.vibesense.databinding.ActivityRegisterBinding
import java.util.EventListener

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val database = Firebase.database
    private var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://vibesense-9523f-default-rtdb.asia-southeast1.firebasedatabase.app")

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
            val id = databaseReference.push().key

            if(email.isEmpty() && password.isEmpty() && firstName.isEmpty() && lastName.isEmpty())
                Toast.makeText(this, "Empty Fields are not allowed", Toast.LENGTH_SHORT).show()


            if (email.isNotEmpty() && password.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //send data to realtime DB
                        databaseReference.child("Users").addListenerForSingleValueEvent(object :
                            ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.hasChild(id.toString())) {
                                    Toast.makeText(this@RegisterActivity, "Email is already taken", Toast.LENGTH_SHORT).show()
                                } else {

                                    databaseReference.child("Users").child(id.toString()).child("firstName").setValue(firstName)
                                    databaseReference.child("Users").child(id.toString()).child("lastName").setValue(lastName)
                                    databaseReference.child("Users").child(id.toString()).child("email").setValue(email)
                                    databaseReference.child("Users").child(id.toString()).child("password").setValue(password)

                                    Toast.makeText(this@RegisterActivity, "User registered successfully.", Toast.LENGTH_SHORT).show()
                                    val goToLogin = Intent(applicationContext, LoginActivity::class.java)
                                    startActivity(goToLogin)
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Toast.makeText(this@RegisterActivity, error.message, Toast.LENGTH_SHORT).show()
                            }
                        })
                    } else {
                        Toast.makeText(this, task.exception?.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }


}