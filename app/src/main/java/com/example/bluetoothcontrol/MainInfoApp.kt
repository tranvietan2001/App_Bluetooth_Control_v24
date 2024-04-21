package com.example.bluetoothcontrol;

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class MainInfoApp: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info_app_main)

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN and WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val ctrIt = Intent(this, MainControl::class.java)
        startActivity(ctrIt)
        finish()
    }
}