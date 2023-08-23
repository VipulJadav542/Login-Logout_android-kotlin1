package com.example.vipuljadav

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class Registration : AppCompatActivity() {
    private lateinit var register:Button
    private lateinit var auth: FirebaseAuth
    private lateinit var login:Button
    private lateinit var db:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_screen)

        register=findViewById(R.id.register12)

        login=findViewById(R.id.login)
        login.setOnClickListener {
            intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        auth=FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()

        register.setOnClickListener {
            if(Checking())
            {
                val uname=findViewById<EditText>(R.id.uname).text.toString()
                val mnumber=findViewById<EditText>(R.id.mnumber).text.toString()
                val email = findViewById<EditText>(R.id.email).text.toString()
                val password = findViewById<EditText>(R.id.pwd).text.toString()
                val user= hashMapOf(
                    "Name" to uname,
                    "Mobile" to mnumber,
                    "email" to email
                )
                val Users=db.collection("USERS")
                val query=Users.whereEqualTo("email",email).get()
                    .addOnSuccessListener{
                        it->
                        if (it.isEmpty)
                        {
                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(this) { task ->
                                    if (task.isSuccessful)
                                    {
                                        Users.document(email).set(user)
                                        Toast.makeText(this, "User added successfully,please login", Toast.LENGTH_SHORT).show()
                                        val intent=Intent(this,Login::class.java)
                                        intent.putExtra("email",email)
                                        startActivity(intent)
                                        finish()
                                    } else
                                    {
                                        Toast.makeText(this, "authentication faild", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        }
                        else
                        {
                            val intent=Intent(this,Login::class.java)
                            Toast.makeText(this,"Already registered ,please login...", Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                        }
                    }
            }
            else
            {
                Toast.makeText(this,"Enter your details!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun Checking():Boolean {
        val uname=findViewById<EditText>(R.id.uname)
        val mnumber=findViewById<EditText>(R.id.mnumber)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.pwd)

        if (uname.text.trim{it<=' '}.toString().isNotEmpty() && mnumber.text.trim{it<=' '}.toString().isNotEmpty() &&
            email.text.toString().trim { it <= ' ' }.isNotEmpty() && password.text.trim{it<=' '}.toString()
                .trim { it <= ' ' }.isNotEmpty()
        ) {
            return true
        }
        return false
    }
}
