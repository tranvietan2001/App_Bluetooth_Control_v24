package com.example.bluetoothcontrol;

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.io.OutputStream
import java.util.*


class MainControl : AppCompatActivity() {
    @SuppressLint("ResourceAsColor")
    private var isDeviceConnected:Boolean = false
    private var statusBtn:Button?=null
    private var guidBtn: ImageButton?=null
    private var connectBtn: ImageButton?=null
    private var startBtn: ImageButton?=null
    private var controlBtn: ImageButton?=null
    private var infoBtn: ImageButton?=null
    private var nameDeviceTV: TextView?=null
    private var resetBtn:ImageButton?=null

    private var statusBtnStart:Boolean= false

    companion object {
        var myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var bluetoothSocket: BluetoothSocket? = null
        lateinit var bluetoothAdapter: BluetoothAdapter
        var isConnected: Boolean = false
    }

    @SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.control_main)

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN and WindowManager.LayoutParams.FLAG_FULLSCREEN)


        guidBtn = findViewById(R.id.guidBtn)
        connectBtn = findViewById(R.id.connectBtn)
        startBtn = findViewById(R.id.startBtn)
        controlBtn= findViewById(R.id.controlBtn)
        infoBtn = findViewById(R.id.infoBtn)
        nameDeviceTV = findViewById(R.id.nameDeviceTVC)
        statusBtn = findViewById(R.id.statusBtnC)
        resetBtn = findViewById(R.id.resetBtn)
        statusBtn?.setBackgroundColor(Color.RED)

        if(intent != null){
            val nameDevice = intent.getStringExtra("nameDevices")
            val addressDevice = intent.getStringExtra("addressDevices")
            if (addressDevice != null && nameDevice!=null) {
                connectDevice(addressDevice, nameDevice)
            }
        }

        connectBtn?.setOnClickListener {
            if (isDeviceConnected) {
                disconnectDevice()
            }
            else {
                val connectItent = Intent(this, ConnectBluetooth::class.java)
                startActivity(connectItent)
                finish()
            }
        }

        if (isDeviceConnected) {
            connectBtn!!.setImageResource(R.drawable.btn_disconnect)
        }
        else connectBtn!!.setImageResource(R.drawable.btn_connect)

        infoBtn?.setOnClickListener{
//            val infoIt = Intent(this, MainInfoApp::class.java)
//            startActivity(infoIt)
//            if(isDeviceConnected)
//                disconnectDevice()
//            finish()
            Toast.makeText(this, "Chuc nang nay tam thoi bi an", Toast.LENGTH_LONG).show()
        }

        controlBtn?.setOnClickListener{
            val controlHandIt = Intent(this, MainControlHand::class.java)
            val nameDevice = nameDeviceTV?.text
            controlHandIt.putExtra("isDeviceConnected", isDeviceConnected)
            controlHandIt.putExtra("nameDevice", nameDevice)
            startActivity(controlHandIt)
            finish()
        }

        startBtn?.setOnClickListener {
//            statusBtnStart = !statusBtnStart
//            if(statusBtnStart and isDeviceConnected) {
//                sendCommand("s\b")
//                startBtn?.setImageResource(R.drawable.btn_stop)
//            }
//            else {
//                sendCommand("e\b")
//                startBtn?.setImageResource(R.drawable.btn_start)
//            }
            Toast.makeText(this, "Chuc nang nay tam thoi bi an", Toast.LENGTH_LONG).show()
        }

        guidBtn?.setOnClickListener {
            Toast.makeText(this, "Chuc nang nay tam thoi bi an", Toast.LENGTH_LONG).show()
//            val guidIt = Intent(this, MainGuid::class.java)
//            startActivity(guidIt)
//            if(isDeviceConnected)
//                disconnectDevice()
//            finish()
        }

        resetBtn?.setOnClickListener{
            sendCommand("r\b")
        }

    }

    @SuppressLint("SetTextI18n")
    private fun connectDevice(addressDevice : String, nameDevice: String) {
    try {
        if (bluetoothSocket == null || !isConnected) {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            val device: BluetoothDevice = bluetoothAdapter.getRemoteDevice(addressDevice)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(myUUID)
            BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
            bluetoothSocket!!.connect()
            statusBtn?.setBackgroundColor(Color.GREEN)
            nameDeviceTV?.text = nameDevice
            isDeviceConnected = true
            sendCommand("C\b")
        }
    }
    catch (e: IOException) {
        nameDeviceTV?.text = "not device"
        e.printStackTrace()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun disconnectDevice(){
        try {
            sendCommand("D\b")
            bluetoothSocket!!.close()
            bluetoothSocket = null
            statusBtn?.setBackgroundColor(Color.RED)
            nameDeviceTV?.text = "Device"
            isDeviceConnected = false
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun sendCommand(output: String) {

        if (bluetoothSocket != null){
            val outputStream: OutputStream? = bluetoothSocket?.outputStream
//            val data: ByteArray = output.toByteArray()
//            outputStream?.write(output.toByteArray(Charsets.UTF_8))
            outputStream?.write(output.toByteArray())
            outputStream?.flush()
        }
        else {
//            Toast.makeText(this, "No connect device so not send command", Toast.LENGTH_SHORT).show()
        }
    }

    private val bluetoothStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            when (intent.action) {
                BluetoothDevice.ACTION_ACL_CONNECTED -> {
                    isDeviceConnected = true
                    Toast.makeText(this@MainControl, "Connect with device", Toast.LENGTH_LONG).show()
                    statusBtn!!.setBackgroundColor(Color.GREEN)
                    connectBtn!!.setImageResource(R.drawable.btn_disconnect)
                }
                BluetoothDevice.ACTION_ACL_DISCONNECTED -> {
                    isDeviceConnected = false
                    Toast.makeText(this@MainControl, "Disconnect with device", Toast.LENGTH_LONG).show()
                    statusBtn!!.setBackgroundColor(Color.RED)
                    connectBtn!!.setImageResource(R.drawable.btn_connect)
                    nameDeviceTV!!.text = "Device"
                }
                else -> println("Default")
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isDeviceConnected", isDeviceConnected)

    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter()
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        registerReceiver(bluetoothStateReceiver, filter)
    }
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(bluetoothStateReceiver)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val ctrIt = Intent(this, MainActivity::class.java)
        startActivity(ctrIt)
        finish()
    }
}

