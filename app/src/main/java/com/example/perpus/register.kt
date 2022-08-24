package com.example.perpus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        auth = FirebaseAuth.getInstance()

        val button = this.findViewById<Button>(R.id.buttonlg)
        button.setOnClickListener {
            val email = findViewById<EditText>(R.id.email)
            val password = findViewById<EditText>(R.id.password)
            if (email.text.toString().isEmpty()) {
                email.error = "Tolong masukkan email"
                email.requestFocus()
            }
            if (password.text.toString().isEmpty()) {
                password.error = "Tolong masukkan password"
                password.requestFocus()
            }
            if(password.text.toString().isNotBlank() && email.text.toString().isNotBlank()) {
                loginPengguna(email.text.toString(), password.text.toString())
            }
        }
        val TextView = this.findViewById<TextView>(R.id.botton4)
        TextView.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


    private fun loginPengguna(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { login ->
                if (login.isSuccessful) {
                    Toast.makeText(this, "Berhasil Login", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@register, dashboard::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, login.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }

    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            startActivity(Intent(this, dashboard::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            })
        }
    }

}
