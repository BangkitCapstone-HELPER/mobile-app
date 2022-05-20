package com.example.helperstartup.View.Adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.helperstartup.Model.Data.HistoryModel
import com.example.helperstartup.Model.helper.HistoryDiffCallback
import com.example.helperstartup.Model.helper.formatDate
import com.example.helperstartup.Model.helper.formatRupiah
import com.example.helperstartup.R
import com.example.helperstartup.View.Adapter.HistoryAdapter.HistoryViewHolder
import com.example.helperstartup.databinding.ComponentsItemRowHistoryBinding
import com.squareup.picasso.Picasso

@RequiresApi(Build.VERSION_CODES.N)
class HistoryAdapter : RecyclerView.Adapter<HistoryViewHolder>() {
    private val listHistory = ArrayList<HistoryModel>()

    fun setListHistories(listHistory: List<HistoryModel>) {
        val diffCallback = HistoryDiffCallback(this.listHistory, listHistory)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listHistory.clear()
        this.listHistory.addAll(listHistory)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ComponentsItemRowHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }


    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(listHistory[position])
    }

    override fun getItemCount() = listHistory.size


    inner class HistoryViewHolder(private val binding: ComponentsItemRowHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(historyModel: HistoryModel) {
            with(binding) {
                cardTitle.text = historyModel.title ?: ""
                historyDescription.text = historyModel.description ?: ""
                Picasso.get().load(historyModel.imageUrl)
                    .placeholder(R.drawable.img_placeholder)
                    .error(R.drawable.img_placeholder)
                    .centerCrop()
                    .into(historyImageView)
                cardTextName.text = historyModel.status ?: "Diproses"
                historyDate.text = historyModel.date?.let { formatDate(it) } ?: ""
                if (historyModel.price != null) {
                    historyPrice.text = formatRupiah(historyModel.price)
                } else {
                    historyPrice.text = ""
                }
            }
        }
    }
}