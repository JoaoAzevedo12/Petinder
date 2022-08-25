package com.example.petinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.petinder.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth= FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference= database?.reference!!.child("profile")


        var usernameProf = binding.usernameprof.text.toString()
        var emailProf= binding.emailprof.text.toString()
        val logout = binding.logout.text.toString()

        val user = firebaseAuth.currentUser
        val userReference = databaseReference?.child(user?.uid!!)



        userReference?.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                usernameProf = snapshot.child("username").value.toString()
                emailProf = snapshot.child("email").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        binding.logout.setOnClickListener{
            firebaseAuth.signOut()

            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}