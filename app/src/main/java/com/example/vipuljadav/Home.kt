package com.example.vipuljadav


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore


class Home : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val sharedpref=getSharedPreferences("mypre", Context.MODE_PRIVATE)
        val isLogin = sharedpref.getString("email", "1")

        if (isLogin == "1") {
            val email = intent.getStringExtra("email").toString()
            setText(email)
            with(sharedpref.edit())
            {
                putString("email", email)
                apply()
            }
        } else {
            setText(isLogin)
        }

        val logout = findViewById<Button>(R.id.logout)
        logout.setOnClickListener {
            sharedpref.edit().remove("email").apply()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setText(email: String?) {

        val uname = findViewById<TextView>(R.id.uname)
        val mnumber = findViewById<TextView>(R.id.mnumber)
        val email1 = findViewById<TextView>(R.id.email)
        db = FirebaseFirestore.getInstance()
        if (email != null) {
            db.collection("USERS").document(email.toString()).get()
                .addOnSuccessListener { tasks ->
                    uname.text = tasks.get("Name").toString()
                    mnumber.text = tasks.get("Mobile").toString()
                    email1.text = tasks.get("email").toString()
                }
        }
    }
}