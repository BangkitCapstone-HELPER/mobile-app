package com.example.helperstartup.View.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.helperstartup.Model.service.ResponseApi.DataItem
import com.example.helperstartup.Model.service.ResponseApi.ResponseMenu
import com.example.helperstartup.R
import com.example.helperstartup.View.catering.home.DetailMenu
import com.squareup.picasso.Picasso

class MenuAdapter(private val listMenu: ResponseMenu?) : RecyclerView.Adapter<MenuAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.component_menu_home, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        listMenu?.data?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        if (listMenu?.data?.size == null) {
            return 0
        }
        else {
            return listMenu.data.size
        }
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var photoHome: ImageView = itemView.findViewById(R.id.photoHome)
        private var nameHome: TextView = itemView.findViewById(R.id.titleHome)
        private var descHome: TextView = itemView.findViewById(R.id.descHome)
        private var priceHome: TextView = itemView.findViewById(R.id.priceHome)

        fun bind(dataMenu: DataItem) {
            Picasso.get().load(dataMenu.dayMenus?.get(0)?.image).into(photoHome);
            nameHome.text = dataMenu.title
            descHome.text = dataMenu.description
            priceHome.text = "Rp " + dataMenu.price.toString()

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailMenu::class.java)
                intent.putExtra("DATA", dataMenu)
                itemView.context.startActivity(intent)
            }
        }
    }
}