package com.example.bluetoothcontrol;

import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class ConnectBluetooth: AppCompatActivity() {
    val PROFILE_ACTIVITY_CODE = 200
    private var bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private val REQUEST_ENABLE_BLUETOOTH = 1
    var value = 0;

    val listDevices = ArrayList<infoBlt>()

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scan_devices_main)

        //hide status bar
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Thiet bi khong ho tro BLUETOORH", Toast.LENGTH_LONG).show()
        }

        // bật blt
        if (bluetoothAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BLUETOOTH)
        }

        //scan devices old (connected devices)
        val connBtn = findViewById<ImageButton>(R.id.connBtn)
        connBtn.setOnClickListener {
            val listInfo = ArrayList<infoBlt>()
            val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
            pairedDevices?.forEach { device ->
                listInfo.add(infoBlt(device.name, device.address))
            }
            val bluetoothRV2 = findViewById<RecyclerView>(R.id.recyclerView2)
            val bluetoothAdapterRV = BluetoothAdapterRV(this, listInfo)

            bluetoothRV2.layoutManager = GridLayoutManager(this,1)
            bluetoothRV2.adapter = bluetoothAdapterRV

            bluetoothAdapterRV.onItemClick={ list->
//                addressDevice = list.address
//                connectBLT(addressDevice)
                val controlIntent = Intent(this, MainControl::class.java)
                controlIntent.putExtra("nameDevices", list.name)
                controlIntent.putExtra("addressDevices", list.address)
                startActivityForResult(controlIntent,PROFILE_ACTIVITY_CODE)
                finish()
            }
        }

        //scan new devices
        val scanBtn = findViewById<ImageButton>(R.id.scanBtn)
        scanBtn.setOnClickListener{
            val requestCode = 1;
            val discoverableIntent: Intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
                putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
            }
            startActivityForResult(discoverableIntent, requestCode)

//            val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
//            registerReceiver(receiver, filter)

            bluetoothAdapter?.startDiscovery()
            val bluetoothRV1 = findViewById<RecyclerView>(R.id.recyclerView1)
            val bluetoothAdapterRV = BluetoothAdapterRV(this, listDevices)

            bluetoothRV1.layoutManager = GridLayoutManager(this,1)
            bluetoothRV1.adapter = bluetoothAdapterRV

            bluetoothAdapterRV.onItemClick={ list->
//                addressDevice = list.address
//                connectBLT(addressDevice)
                val controlIntent = Intent(this, MainControl::class.java)
                controlIntent.putExtra("nameDevices", list.name)
                controlIntent.putExtra("addressDevices", list.address)
                startActivityForResult(controlIntent,PROFILE_ACTIVITY_CODE)
                finish()
            }
        }
//        finish()

    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action: String? = intent.action
            when (action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device: BluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    if (device != null) {
                        listDevices.add(infoBlt(device.name, device.address))
                        Log.d("name", device.name)
                    }
                }
            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val ctrIt = Intent(this, MainControl::class.java)
        startActivity(ctrIt)
        finish()
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver, filter)
    }
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}


