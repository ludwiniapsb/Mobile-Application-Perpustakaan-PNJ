package com.example.perpus

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.perpus.module.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class editprofile : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var database: DatabaseReference
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editprofile)
        auth = FirebaseAuth.getInstance()
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        userInfo()

        val imgv = this.findViewById<ImageView>(R.id.backedit)
        imgv.setOnClickListener {
            val intent = Intent(this@editprofile, profile::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        val button = findViewById<Button>(R.id.btnsimpan)
        button.setOnClickListener {
            val nama = findViewById<EditText>(R.id.editNamaEdit)
            val email = findViewById<EditText>(R.id.editEmailEdit)
            val notelp = findViewById<EditText>(R.id.editNumberEdit)
            val password = findViewById<EditText>(R.id.editPasswordEdit)
            val nim = findViewById<EditText>(R.id.nim)
            val jurusan = findViewById<EditText>(R.id.jurusan)

            val changenama = nama.text.toString()
            val changeemail = email.text.toString()
            val changenumber = notelp.text.toString()
            val changenim = nim.text.toString()
            val changepw = password.text.toString()
            val gantijur = jurusan.text.toString()

            editPengguna(changenama,changeemail,changenumber,changepw,changenim,gantijur)
        }
    }

    private fun editPengguna(changenama: String, changeemail: String, changenumber: String, changepw: String, changenim: String, gantijur: String) {
        database = FirebaseDatabase.getInstance().getReference("PENGGUNA")
        val user = mapOf<String,String>(
            "nama" to changenama,
            "email" to changeemail,
            "number" to changenumber,
            "nim" to changenim,
            "password" to changepw,
            "jurusan" to gantijur,
        )
        database.child(firebaseUser.uid).updateChildren(user).addOnSuccessListener {
            firebaseUser!!.updateEmail(changeemail)
            firebaseUser!!.updatePassword(changepw)
            Toast.makeText(this, "Data Disimpan", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this@editprofile, "Gagal.", Toast.LENGTH_LONG).show()
        }
    }

    private fun userInfo() {
        val userRef =
            FirebaseDatabase.getInstance().getReference().child("PENGGUNA").child(firebaseUser.uid)
        userRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("WrongViewCast")
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.getValue<Users>(Users::class.java)
                    val nama = findViewById<EditText>(R.id.editNamaEdit)
                    val email = findViewById<EditText>(R.id.editEmailEdit)
                    val notelp = findViewById<EditText>(R.id.editNumberEdit)
                    val nim = findViewById<EditText>(R.id.nim)
                    val password = findViewById<EditText>(R.id.editPasswordEdit)
                    val jurusan = findViewById<EditText>(R.id.jurusan)


                    nama.setText(user!!.nama)
                    email.setText(firebaseUser!!.email)
                    notelp.setText(user!!.number)
                    nim.setText(user!!.nim)
                    password.setText(user!!.password)
                    jurusan.setText(user!!.jurusan)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}