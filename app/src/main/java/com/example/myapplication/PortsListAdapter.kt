package com.example.myapplication

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.net.URI

class PortsListAdapter(
    val context: Context,
    val db: DatabaseHelper,
    val portId: Int?
) : RecyclerView.Adapter<PortsListAdapter.PortViewHolder>() {
    var ports = if(portId == null) {
        db.getPorts()
    } else {
        val port = db.getPort(portId)!!
        db.getReachablePorts(port)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_layout, parent, false)

        return PortViewHolder(view)
    }


    override fun onBindViewHolder(holder: PortViewHolder, position: Int) {
        val port = ports[position]
        holder.textView.text = port?.name ?: ""

        holder.textView.setOnClickListener {
            var intent = Intent()

            intent.putExtra("port_id", port?.port_id ?: -1)
            val activity =context as PortsActivity
            activity.setResult(RESULT_OK, intent)
            activity.finish()
        }
    }

    override fun getItemCount(): Int {
        return ports.size
    }

    class PortViewHolder(
        val view: View
    ) : ViewHolder(view) {
        val textView = view.findViewById<TextView>(R.id.list_item_text_view)
    }
}