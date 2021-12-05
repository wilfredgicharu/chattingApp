package com.example.jumbechat.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.jumbechat.MainActivity
import com.example.jumbechat.R
import com.example.jumbechat.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var register_button: Button
    private lateinit var login_textView: TextView
    private lateinit var email_editText: EditText
    private lateinit var username: EditText
    private lateinit var password_editText: EditText

    private lateinit var auth: FirebaseAuth

    private lateinit var mdataRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        register_button= findViewById(R.id.register_button)
        username = findViewById(R.id.username)
        login_textView= findViewById(R.id.login)
        email_editText= findViewById(R.id.edit_email)
        password_editText= findViewById(R.id.edit_password)

        login_textView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        
        register_button.setOnClickListener {
            val name= username.text.toString()
            val email= email_editText.text.toString()
            val password= password_editText.text.toString()

            signUp(name,email,password)
        }
        
        
    }

    private fun signUp(name: String, email: String, password: String) {
        //creating new user
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    addUserToTheDatabase(name, email, auth.currentUser?.uid)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Could not create Account", Toast.LENGTH_SHORT).show()

                }
            }

    }

    private fun addUserToTheDatabase( email: String,name: String, uid: String?) {
        mdataRef = FirebaseDatabase.getInstance().getReference()

            mdataRef.child("user").child(uid!!).setValue(User(email,name,uid))


    }
}