package com.example.helperstartup.View.Adapter


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.helperstartup.Model.Data.ArticleModel
import com.example.helperstartup.Model.Service.ResponseApi.DataItem
import com.example.helperstartup.Model.Service.ResponseApi.ResponseMenu
import com.example.helperstartup.R
import com.example.helperstartup.View.Catering.home.DetailMenu
import com.example.helperstartup.View.Dashboard.ArticleActivity
import com.squareup.picasso.Picasso

class ScanningAdapter(private val listMenu: ResponseMenu) : RecyclerView.Adapter<ScanningAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.result_scanning_component, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        listMenu.data?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = listMenu.data!!.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var photoScan: ImageView = itemView.findViewById(R.id.photoScan)
        private var nutrisiScan: TextView = itemView.findViewById(R.id.nutrisiScan)
        private var kaloriScan: TextView = itemView.findViewById(R.id.kaloriScan)
        private var vitaminScan: TextView = itemView.findViewById(R.id.vitaminScan)

        fun bind(dataMenu: DataItem) {
            Picasso.get().load(dataMenu.dayMenus?.get(0)?.image).into(photoScan);
            nutrisiScan.text = "Nutrisi : 500 gram"
            kaloriScan.text = "Kalori : 500 gram"
            vitaminScan.text = "Vitamin : 500 gram"

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailMenu::class.java)
                intent.putExtra("DATA", dataMenu)
                itemView.context.startActivity(intent)
            }
        }
    }
}