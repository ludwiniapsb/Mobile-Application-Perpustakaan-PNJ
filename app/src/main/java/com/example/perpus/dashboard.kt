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

class dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)
        val TextView = this.findViewById<TextView>(R.id.buttontentang)
        TextView.setOnClickListener {
            startActivity(Intent(this, about::class.java))
        }

        val TextView1 = this.findViewById<TextView>(R.id.buttoncara)
        TextView1.setOnClickListener {
            startActivity(Intent(this, cara::class.java))
        }

        val TextView2 = this.findViewById<TextView>(R.id.buttonsnk)
        TextView2.setOnClickListener {
            startActivity(Intent(this, snk::class.java))
        }

        val TextView3 = this.findViewById<TextView>(R.id.buttonsekarang)
        TextView3.setOnClickListener {
            startActivity(Intent(this, pinjambuku::class.java))
        }

        val ImageView = this.findViewById<ImageView>(R.id.buttoprofile)
        ImageView.setOnClickListener {
            startActivity(Intent(this, profile::class.java))
        }

        val ImageView2 = this.findViewById<ImageView>(R.id.buttonother)
        ImageView2.setOnClickListener {
            startActivity(Intent(this, others::class.java))
        }

        val ImageView3 = this.findViewById<ImageView>(R.id.buttundashboard)
        ImageView3.setOnClickListener {
            startActivity(Intent(this, dashboard::class.java))
        }
    }
}
