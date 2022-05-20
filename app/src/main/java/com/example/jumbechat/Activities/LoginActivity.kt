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
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var login_button: Button
    private lateinit var register_textView: TextView
    private lateinit var email_editText: EditText
    private lateinit var password_editText: EditText

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        login_button= findViewById(R.id.login_button)
        register_textView= findViewById(R.id.register)
        email_editText= findViewById(R.id.edit_email)
        password_editText= findViewById(R.id.edit_password)

        auth = FirebaseAuth.getInstance()


        register_textView.setOnClickListener {
            val  intent= Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        login_button.setOnClickListener {
            val email= email_editText.text.toString()
            val password = password_editText.text.toString()
            
            login(email, password)
        }
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(this, "Unable to Sign In, please try again", Toast.LENGTH_SHORT).show()
                }
            }

    }
}