package com.example.perpus

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perpus.R

class bantuan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bantuan)

        val imgv = this.findViewById<ImageView>(R.id.back)
        imgv.setOnClickListener {
            val intent = Intent(this@bantuan, dashboard::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        val buttonwa = this.findViewById<Button>(R.id.buttonwa)
        buttonwa.setOnClickListener {
            val packageManager : PackageManager = packageManager
            val i = Intent(Intent.ACTION_VIEW)
            val url = "https://wa.me/+6289652867946"
            i.data = Uri.parse(url)
                startActivity(i)
            }
        val buttonig = this.findViewById<Button>(R.id.buttonig)
        buttonig.setOnClickListener {
            val packageManager: PackageManager = packageManager
            val i = Intent(Intent.ACTION_VIEW)
            val url = "https://www.instagram.com/bmm_pnj/"
            i.data = Uri.parse(url)
            startActivity(i)
        }
        val buttontwt = this.findViewById<Button>(R.id.buttontwt)
        buttontwt.setOnClickListener {
            val packageManager: PackageManager = packageManager
            val i = Intent(Intent.ACTION_VIEW)
            val url = "https://twitter.com/BMM_PNJ"
            i.data = Uri.parse(url)
            startActivity(i)
        }
        }
    }


