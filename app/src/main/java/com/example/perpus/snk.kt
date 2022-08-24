package com.example.perpus

import android.os.Bundle
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class snk : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.snk)

        val tv = findViewById<View>(R.id.snk) as TextView
        tv.movementMethod = ScrollingMovementMethod()
}
}
