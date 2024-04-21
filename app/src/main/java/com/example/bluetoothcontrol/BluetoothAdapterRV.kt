package com.example.bluetoothcontrol;

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

@ExperimentalStdlibApi
class BluetoothAdapterRV(val context: Context, val listInfo: ArrayList<infoBlt>)
    : RecyclerView.Adapter<BluetoothAdapterRV.ViewHolder>() {

    var onItemClick: ((infoBlt) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.info_main, parent, false
        ))
    }

    override fun getItemCount(): Int {
        return listInfo.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = listInfo[position].name
        holder.address.text = listInfo[position].address

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)  {
        val name = view.findViewById<TextView>(R.id.nameInfoTV)!!
        val address = view.findViewById<TextView>(R.id.addressInfoTV)!!
        init{
            view.setOnClickListener{
                onItemClick?.invoke(listInfo[adapterPosition])
            }
        }

    }
}