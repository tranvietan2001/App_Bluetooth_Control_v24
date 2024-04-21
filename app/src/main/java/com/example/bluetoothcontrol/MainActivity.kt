package com.example.bluetoothcontrol;

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast

private const val PERMISSION_REQUEST_CODE = 10
class MainActivity : AppCompatActivity() {

    private var permissions = arrayOf(Manifest.permission.BLUETOOTH,
                                        Manifest.permission.BLUETOOTH_ADMIN,
                                        Manifest.permission.INTERNET)
    private var statusPermissions: Boolean = false

    private lateinit var startFBtn: ImageButton
    private var defaultWidth: Int = 0
    private var defaultHeight: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        //hide status bar
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN and WindowManager.LayoutParams.FLAG_FULLSCREEN)


        if(checkPermissions(this,permissions)){
            statusPermissions = true
        }
        else{
            requestPermissions(permissions, PERMISSION_REQUEST_CODE)
        }

        startFBtn = findViewById(R.id.startFBtn)
        startFBtn.setImageResource(R.drawable.btn1)
        defaultWidth = startFBtn.layoutParams.width
        defaultHeight = startFBtn .layoutParams.height


        startFBtn.setOnClickListener {
            startFBtn.setImageResource(R.drawable.btn_2)
            goToNextActivity()
        }
    }

    override fun onResume() {
        super.onResume()
        setDefaultButtonSize()
    }

    private fun setDefaultButtonSize() {
        val layoutParams = startFBtn.layoutParams
        layoutParams.width = defaultWidth
        layoutParams.height = defaultHeight
        startFBtn.layoutParams = layoutParams
    }

    private fun goToNextActivity() {
        val connectItent = Intent(this, MainControl::class.java)
        startActivity(connectItent)
        finish()
    }


    private fun checkPermissions(context: Context, permissionArray: Array<String>): Boolean{
        var isAllGranted = true
        for (i in permissionArray.indices){
            if(checkCallingOrSelfPermission(permissionArray[i]) == PackageManager.PERMISSION_DENIED)
                isAllGranted = false
        }
        return isAllGranted
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISSION_REQUEST_CODE){
            var allGranted = true
            for (i in permissions.indices){
                if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                    allGranted = false
                    var requestAgainLater = shouldShowRequestPermissionRationale(permissions[i])
                    if(requestAgainLater){
                        Toast.makeText(this,"Quyền truy cập đã bị từ chối", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this,"Đi đến phần cài đặt để cấp quyền cho ứng dụng",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
            if(allGranted){
                Toast.makeText(this,"All permissions granted", Toast.LENGTH_SHORT).show()
            }
        }
    }
}