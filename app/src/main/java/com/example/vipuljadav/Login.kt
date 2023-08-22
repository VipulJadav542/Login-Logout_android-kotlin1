package com.example.vipuljadav

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)


        val register1=findViewById<Button>(R.id.register1)
        register1.setOnClickListener {
            intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }

        auth = FirebaseAuth.getInstance()

        val login=findViewById<Button>(R.id.login)

        login.setOnClickListener {
            if(Checking())
            {
                val email=findViewById<EditText>(R.id.email)
                val password=findViewById<EditText>(R.id.pwd)
                auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "login successfully", Toast.LENGTH_SHORT).show()
                            val intent=Intent(this,Home::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "wrong details", Toast.LENGTH_SHORT).show()
                            email.text.clear()
                            password.text.clear()
                        }
                    }
            }
            else
            {
                Toast.makeText(this,"Enter your details!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun Checking():Boolean
    {

        val email=findViewById<TextView>(R.id.email)
        val password=findViewById<TextView>(R.id.pwd)
        if(email.text.toString().trim{it<=' '}.isNotEmpty() && password.text.toString().trim{it<=' '}.isNotEmpty())
        {
            return true
        }
        return false
    }
}