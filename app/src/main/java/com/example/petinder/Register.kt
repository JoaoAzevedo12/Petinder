package com.example.petinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.petinder.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Register : AppCompatActivity() {


    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.loginNowbtn.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }



        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference= database?.reference!!.child("profile")


        binding.registerbtn.setOnClickListener {

            val email = binding.email.text.toString()
            val username= binding.username.text.toString()
            val password = binding.password.text.toString()
            val confpassword = binding.confpassword.text.toString()
            val loginNowbtn = binding.loginNowbtn.text.toString()

            if(email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && confpassword.isNotEmpty()){
                if (password == confpassword ){

                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

                        if(it.isSuccessful){

                            val currentUser = firebaseAuth.currentUser
                            val currentUserDb= databaseReference?.child((currentUser?.uid!!))
                            currentUserDb?.child("email")?.setValue(email)
                            currentUserDb?.child("username")?.setValue(username)
                            currentUserDb?.child("password")?.setValue(password)

                            Toast.makeText(this, "Registado", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this, Login::class.java)
                           startActivity(intent)

                        }else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

                }else{
                    Toast.makeText(this, "Password não correspondem", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(this, "Preencha os dados todos", Toast.LENGTH_SHORT).show()
            }

        }

    }
}