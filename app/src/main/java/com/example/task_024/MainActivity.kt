package com.example.task_024

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var beginBTN: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        beginBTN = findViewById(R.id.beginBTN)

        beginBTN.setOnClickListener{

            val intent = Intent(this, SqlActivity::class.java)
            startActivity(intent)

        }
    }
}