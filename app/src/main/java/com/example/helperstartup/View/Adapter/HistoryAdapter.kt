package com.example.helperstartup.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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

class HistoryAdapter : RecyclerView.Adapter<HistoryViewHolder>() {
    private val listHistory = ArrayList<HistoryModel>()
    private lateinit var context : Context

    fun setListHistories(listHistory: List<HistoryModel>) {
        val diffCallback = HistoryDiffCallback(this.listHistory, listHistory)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listHistory.clear()
        this.listHistory.addAll(listHistory)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ComponentsItemRowHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
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
                    .placeholder(com.denzcoskun.imageslider.R.drawable.placeholder)
                    .error(com.denzcoskun.imageslider.R.drawable.placeholder)
                    .resize(300,300 )
                    .centerCrop()
                    .into(historyImageView)
                historyDate.text = historyModel.date?.let { formatDate(it) } ?: ""
                if (historyModel.price != null) {
                    historyPrice.text = formatRupiah(historyModel.price)
                } else {
                    historyPrice.text = ""
                }
                historyExpiredText.text = historyModel.expiredTime ?: ""
            }
            changeStatus(historyModel.status, binding)
        }
    }

    fun changeStatus(status : String?, binding : ComponentsItemRowHistoryBinding) {
        when(status) {
            "pending" -> {
                binding.cardStatus.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.young_pink
                    )
                )
                binding.cardTextName.text = "Ditunda"
            }
            "completed" -> {
                binding.cardStatus.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.primary_green
                    )
                )
                binding.cardTextName.text = "Berhasil"
            }
            "cancelled" -> {
                binding.cardStatus.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.red_button
                    )
                )
                binding.cardTextName.text = "Gagal"
            }
            "ongoing" -> {
                binding.cardStatus.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.yellow
                    )
                )
                binding.cardTextName.text = "Diproses"
            }
                else -> {
                    binding.cardStatus.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.gray_soft
                        )
                    )
                    binding.cardTextName.text = status ?: ""
                }
        }

    }

}