package com.example.perpus

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class pinjambuku : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var ref: DatabaseReference

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pinjambuku)

        auth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().getReference("PENGGUNA")

        val imgv = this.findViewById<ImageView>(R.id.back)
        imgv.setOnClickListener {
            val intent = Intent(this@pinjambuku, dashboard::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        val button4 = this.findViewById<Button>(R.id.button4)
        button4.setOnClickListener {
            val kode = findViewById<EditText>(R.id.kode)
            val judul = findViewById<EditText>(R.id.judul)
            val setuju = findViewById<CheckBox>(R.id.checkBox)
            val tanggal = findViewById<TextView>(R.id.datese)

            if(kode.text.toString().isEmpty()){
                kode.error = "Tolong masukkan fullname"
                kode.requestFocus()
            }
            if(judul.text.toString().isEmpty()){
                judul.error = "Tolong masukkan email"
                judul.requestFocus()
            }
            if(!setuju.isChecked){
                setuju.error = "Centang Setuju Jika Ingin Daftar"
                setuju.requestFocus()
            }
            if(setuju.isChecked){
                savedata(kode.text.toString(),judul.text.toString(),tanggal.text.toString())
            }
        }

        val button = this.findViewById<ImageView>(R.id.button3)
        button.setOnClickListener {
            val intent = Intent(this@pinjambuku, scanner::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val btn = this.findViewById<ImageView>(R.id.pickdate)
        btn.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    // Display Selected date in textbox
                    val datese = findViewById<TextView>(R.id.datese)

                    datese.text = "" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year

                },
                year,
                month,
                day
            )

            dpd.show()
        }

    }

    private fun savedata(kode: String, judul: String, tanggal: String ) {
        val currentUserId = auth.currentUser!!.uid
        val userMap = HashMap<String,Any>()
        userMap["kode"] = kode
        userMap["judul"] = judul
        userMap["tanggal"] = tanggal

        ref.child(currentUserId).child("PEMINJAMAN").setValue(userMap);
        Toast.makeText(this, "Data Disimpan", Toast.LENGTH_SHORT).show()
    }
}