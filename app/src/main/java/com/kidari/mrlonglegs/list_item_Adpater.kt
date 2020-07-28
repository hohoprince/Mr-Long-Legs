package com.kidari.mrlonglegs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class listitemAdapter(val items: List<list_item_Member>) : RecyclerView.Adapter<listitemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.list_item_name.text = item.list_item_name
        holder.list_item_registrationdate.text = item.list_item_registrationdate
        holder.list_item_location.text = item.list_item_location
        holder.list_item_pay.text = item.list_item_pay
    }

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var list_item_name = itemview.findViewById<TextView>(R.id.list_item_name)
        var list_item_registrationdate = itemview.findViewById<TextView>(R.id.list_item_registrationdate)
        var list_item_location = itemview.findViewById<TextView>(R.id.list_item_location)
        var list_item_pay = itemview.findViewById<TextView>(R.id.list_item_pay)
    }
}
