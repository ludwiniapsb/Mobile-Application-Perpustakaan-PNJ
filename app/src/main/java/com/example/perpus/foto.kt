package com.example.perpus

import android.accounts.AccountManager.get
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.nfc.tech.NfcV.get
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ResourceManagerInternal.get
import com.example.perpus.databinding.FotoBinding
import com.example.perpus.module.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.lang.reflect.Array.get
import java.nio.file.Paths.get
import java.text.SimpleDateFormat
import java.util.*

class foto : AppCompatActivity() {

    private lateinit var binding: FotoBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var firebaseStorage: FirebaseStorage
    var ImageUri = Uri.EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        userInfo()


        binding.pp.setOnClickListener {
            selectImage()
        }
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
        progressDialog.setMessage("Uploading File...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val storageReference = FirebaseStorage.getInstance().getReference("image/$fileName")
        storageReference.putFile(ImageUri)
            .addOnSuccessListener {
                binding.pp.setImageURI(ImageUri)
                firebaseUser.photoUrl
                Toast.makeText(this@foto, "Successfully Uploaded", Toast.LENGTH_SHORT)
                    .show()
                if (progressDialog.isShowing) progressDialog.dismiss()
                val fotoprofil = binding.pp
                Picasso.get().load(ImageUri).into(fotoprofil)
            }
            .addOnFailureListener {
                if (progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(this@foto, "Failed", Toast.LENGTH_SHORT).show()

            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            ImageUri = data?.data!!
            binding.pp.setImageURI(ImageUri)
        }
    }
    private fun userInfo() {
        FirebaseDatabase.getInstance().getReference().child("PENGGUNA").child(firebaseUser.uid)

    }
}