package com.aliduman.instagramclone0

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.aliduman.instagramclone0.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            Toast.makeText(applicationContext,"Welcome: ${auth.currentUser?.email.toString()}",
                Toast.LENGTH_LONG).show()
            val intent = Intent(applicationContext, FeedActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun signinClicked(view: View){

        val email = binding.mailText.text.toString()
        val password = binding.passwordText.text.toString()


        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    //Signed In
                    Toast.makeText(applicationContext,"Welcome: ${auth.currentUser?.email.toString()}",
                        Toast.LENGTH_LONG).show()
                    val intent = Intent(applicationContext, FeedActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this,task.exception?.localizedMessage, Toast.LENGTH_LONG).show()
                }

            }.addOnFailureListener { exception ->
                Toast.makeText(applicationContext,exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this,"Enter email and password!", Toast.LENGTH_LONG).show()
        }

    }

    fun signupClicked(view: View){


        val email = binding.mailText.text.toString()
        val password = binding.passwordText.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val intent = Intent(applicationContext, FeedActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this,task.exception?.localizedMessage, Toast.LENGTH_LONG).show()
                }

            }.addOnFailureListener { exception ->
                Toast.makeText(applicationContext,exception.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this,"Enter email and password!", Toast.LENGTH_SHORT).show()
        }

    }
}