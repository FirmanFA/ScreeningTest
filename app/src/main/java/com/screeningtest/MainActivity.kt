package com.screeningtest

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        sharedPreferences = getSharedPreferences("PREF", Context.MODE_PRIVATE)

        val btnBack = findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener {
            onBackPressed()
            finish()
        }

        val name = intent.getStringExtra("name")
        val tvName = findViewById<TextView>(R.id.tvName)
        tvName.text = name

        val btnUser= findViewById<Button>(R.id.btnUser)
        btnUser.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }


    }

    private fun getShared():String{
        return sharedPreferences.getString("USER_KEY","Selected Username").toString()
    }

    override fun onResume() {
        super.onResume()
        val username = getShared()
        val tvUsername = findViewById<TextView>(R.id.tvUsername)
        tvUsername.text = username
    }

}