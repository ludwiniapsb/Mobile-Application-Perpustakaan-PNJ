package com.example.perpus

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.perpus.databinding.ProfileBinding
import com.example.perpus.module.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class profile : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var binding: ProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseStorage: FirebaseStorage
    var ImageUri = Uri.EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        auth = FirebaseAuth.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        userInfo()

        binding = ProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pp.setOnClickListener {
            selectImage()
        }

        val imgv1 = this.findViewById<ImageView>(R.id.back)
        imgv1.setOnClickListener {
            val intent = Intent(this@profile, dashboard::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        val button = this.findViewById<Button>(R.id.button5)
        button.setOnClickListener {
            val intent = Intent(this@profile, editprofile::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

    }

    private fun userInfo() {
        val userRef = FirebaseDatabase.getInstance().getReference().child("PENGGUNA").child(firebaseUser.uid)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val user = snapshot.getValue<Users>(Users::class.java)
                    val nama = findViewById<TextView>(R.id.nama)
                    val email = findViewById<TextView>(R.id.email)
                    val number = findViewById<TextView>(R.id.number)
                    val nim = findViewById<TextView>(R.id.nim)
                    val jurusan = findViewById<TextView>(R.id.jurusan)
                    nama.text = user!!.nama
                    email.text = firebaseUser.email
                    number.text = user!!.number
                    nim.text = user!!.nim
                    jurusan.text = user!!.jurusan


                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)

        binding.button2.setOnClickListener {
            save()
        }
    }

    private fun save() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Menyimpan Photo...")
        progressDialog.setCancelable(true)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val storageReference = FirebaseStorage.getInstance().getReference("image/$fileName")
        storageReference.putFile(ImageUri)
            .addOnSuccessListener {
                binding.pp.setImageURI(ImageUri)
                firebaseUser.photoUrl
                Toast.makeText(this@profile, "Photo Tersimpan", Toast.LENGTH_SHORT)
                    .show()
                if (progressDialog.isShowing) progressDialog.dismiss()
                val fotoprofil = binding.pp
                Picasso.get().load(ImageUri).into(fotoprofil)
            }
            .addOnFailureListener {
                if (progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(this@profile, "Failed", Toast.LENGTH_SHORT).show()

            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            ImageUri = data?.data!!
            binding.pp.setImageURI(ImageUri)
        }
    }
}
