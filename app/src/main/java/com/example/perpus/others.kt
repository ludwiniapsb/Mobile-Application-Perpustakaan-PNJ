package com.example.perpus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perpus.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class others : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.others)
        auth = FirebaseAuth.getInstance()

        val imgv = this.findViewById<ImageView>(R.id.back)
        imgv.setOnClickListener {
            val intent = Intent(this@others, dashboard::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        val button2 = this.findViewById<Button>(R.id.buttonkeluar)
        button2.setOnClickListener {
            auth.signOut()
            Toast.makeText(this, "Succesfully log out.", Toast.LENGTH_LONG).show()
            val intent = Intent(this@others, loading::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)        }

            val TextView = this.findViewById<TextView>(R.id.bottonbantuan)
            TextView.setOnClickListener {
                startActivity(Intent(this, bantuan::class.java))
        }
    }
}