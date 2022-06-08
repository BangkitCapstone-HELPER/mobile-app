package com.example.helperstartup.View.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.helperstartup.Model.service.ResponseApi.DataItems
import com.example.helperstartup.Model.service.ResponseApi.ResponseUploadScanner
import com.example.helperstartup.R
import com.squareup.picasso.Picasso

class ScanningAdapter(private val listMenu: ResponseUploadScanner) : RecyclerView.Adapter<ScanningAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.component_result_scanning, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listMenu.data?.data?.get(position))
    }

    override fun getItemCount(): Int = listMenu.data?.data!!.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var photoScan: ImageView = itemView.findViewById(R.id.photoScan)
        private var titleScan : TextView = itemView.findViewById(R.id.namaScan)
        private var karbohidratScan : TextView = itemView.findViewById(R.id.karbohidratScan)
        private var proteinScan : TextView = itemView.findViewById(R.id.proteinScan)
        private var fatScan : TextView = itemView.findViewById(R.id.fatScan)
        private var kaloriScan: TextView = itemView.findViewById(R.id.kaloriScan)

        fun bind(dataMenu: DataItems?) {
            Picasso.get().load(dataMenu?.image).into(photoScan);
            titleScan.text = dataMenu?.name
            karbohidratScan.text = "Karbohidrat : " + dataMenu?.carbohydrate
            proteinScan.text = "Protein : " + dataMenu?.protein
            fatScan.text = "Lemak : " + dataMenu?.fat
            kaloriScan.text = "Kalori : " + dataMenu?.calorie
        }
    }
}