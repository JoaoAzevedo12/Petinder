package com.example.petinder

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.petinder.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth


class Login : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth= FirebaseAuth.getInstance()


        binding.registerNowbtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.loginBtn.setOnClickListener {

            val email= binding.email.text.toString()
            val password= binding.password.text.toString()

            if(email.isEmpty()){

                Toast.makeText(this, "Email não introduzido.", Toast.LENGTH_SHORT).show()


            }else if(password.isEmpty()){
                Toast.makeText(this, "Password não introduzido.", Toast.LENGTH_SHORT).show()
            }

                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this,
                                "Email ou Password incorretos.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }


        }

    }

}