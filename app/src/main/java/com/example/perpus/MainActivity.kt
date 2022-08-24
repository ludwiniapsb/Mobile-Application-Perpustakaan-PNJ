package com.example.perpus

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        ref= FirebaseDatabase.getInstance().getReference("PENGGUNA")

        val button = findViewById<Button>(R.id.btnLogin)
        button.setOnClickListener {
            val nama = findViewById<EditText>(R.id.editNama)
            val email = findViewById<EditText>(R.id.editEmail)
            val number = findViewById<EditText>(R.id.editNumber)
            val nim = findViewById<EditText>(R.id.nim)
            val password = findViewById<EditText>(R.id.lgnPassword)
            val jurusan = findViewById<EditText>(R.id.jurusan)
            val setuju = findViewById<CheckBox>(R.id.checkBox)

            if(nama.text.toString().isEmpty()){
                nama.error = "Tolong masukkan nama"
                nama.requestFocus()
            }
            if(email.text.toString().isEmpty()){
                email.error = "Tolong masukkan email"
                email.requestFocus()
            }
            if(number.text.toString().isEmpty() || number.text.toString().length<11){
                number.error = "Tolong masukkan number minimal 11 digit"
                number.requestFocus()
            }
            if(nim.text.toString().isEmpty()){
                nim.error = "Tolong masukkan NIM"
                nim.requestFocus()
            }
            if(jurusan.text.toString().isEmpty()){
                jurusan.error = "Tolong masukkan Jurusan"
                jurusan.requestFocus()
            }
            if(password.text.toString().isEmpty()){
                password.error = "Tolong masukkan password"
                password.requestFocus()
            }
            if(!setuju.isChecked()){
                setuju.error = "Centang Setuju Jika Ingin Daftar"
                setuju.requestFocus()
            }
            if(setuju.isChecked()){
                registerPengguna(email.text.toString(), password.text.toString(), nama.text.toString(), number.text.toString(), nim.text.toString(), jurusan.text.toString())
            }
        }

        val TextView = this.findViewById<TextView>(R.id.b4)
        TextView.setOnClickListener {
            startActivity(Intent(this, register::class.java))
        }
    }
    private fun registerPengguna(email: String, nama: String, password: String, number: String, nim: String, jurusan: String){
        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setTitle("Register Pengguna")
        progressDialog.setMessage("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    saveUser(nama,email,number,password,nim,jurusan,progressDialog)
                }
                else{
                    val message = it.exception!!.toString()
                    Toast.makeText(this, "Error : $message", Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
                }
            }
    }

    private fun saveUser(nama: String,email: String,number: String,password: String,nim: String,jurusan: String,progressDialog: ProgressDialog){
        val currentUserId = auth.currentUser!!.uid
        ref = FirebaseDatabase.getInstance().reference.child("PENGGUNA")
        val userMap = HashMap<String,Any>()
        userMap["nama"] = nama
        userMap["email"] = email
        userMap["number"] = number
        userMap["password"] = password
        userMap["nim"]= nim
        userMap["jurusan"]= jurusan
        ref.child(currentUserId).setValue(userMap).addOnCompleteListener{
            if(it.isSuccessful){
                progressDialog.dismiss()
                Toast.makeText(this, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity, register::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            else{
                val message = it.exception!!.toString()
                Toast.makeText(this, "Error : $message", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
        }


    }
    override fun onStart() {
        super.onStart()
        if (auth.currentUser !=null){
            startActivity(Intent(this, dashboard::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            })
        }
    }
}
