package com.example.helperstartup.View.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.helperstartup.R

class ListMenuAdapter(private val listMenu: List<String?>?) : RecyclerView.Adapter<ListMenuAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.component_menu, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.textMenu.text = "- " + listMenu?.get(position)
    }

    override fun getItemCount(): Int = listMenu!!.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textMenu: TextView = itemView.findViewById(R.id.textMenu)
    }
}
