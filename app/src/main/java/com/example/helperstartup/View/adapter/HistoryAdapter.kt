package com.example.helperstartup.View.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.helperstartup.Model.data.HistoryModel
import com.example.helperstartup.Model.helper.HistoryDiffCallback
import com.example.helperstartup.Model.helper.formatDate
import com.example.helperstartup.Model.helper.formatRupiah
import com.example.helperstartup.R
import com.example.helperstartup.View.adapter.HistoryAdapter.HistoryViewHolder
import com.example.helperstartup.databinding.ComponentsItemRowHistoryBinding
import com.squareup.picasso.Picasso

class HistoryAdapter(private val onItemClicked: (HistoryModel) -> Unit) :
    RecyclerView.Adapter<HistoryViewHolder>() {
    private val listHistory = ArrayList<HistoryModel>()
    private lateinit var context: Context

    fun setListHistories(listHistory: List<HistoryModel>) {
        val diffCallback = HistoryDiffCallback(this.listHistory, listHistory)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listHistory.clear()
        this.listHistory.addAll(listHistory)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ComponentsItemRowHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        context = parent.context
        return HistoryViewHolder(binding) {
            onItemClicked(listHistory[it])
        }
    }


    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(listHistory[position])
    }

    override fun getItemCount() = listHistory.size


    inner class HistoryViewHolder(
        private val binding: ComponentsItemRowHistoryBinding,
        onItemClicked: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        fun bind(historyModel: HistoryModel) {
            with(binding) {
                cardTitle.text = historyModel.title ?: ""
                historyDescription.text = historyModel.description ?: ""
                Picasso.get().load(historyModel.imageUrl)
                    .placeholder(R.drawable.loading_image)
                    .error(R.drawable.img_placeholder)
                    .resize(300, 300)
                    .centerCrop()
                    .into(historyImageView)
                historyDate.text = historyModel.date?.let { formatDate(it) } ?: ""

                historyPrice.text = formatRupiah(historyModel.price)

                historyExpiredText.text = historyModel.expiredTime ?: ""
            }
            changeStatus(historyModel.status, binding)
        }
    }

    fun changeStatus(status: String?, binding: ComponentsItemRowHistoryBinding) {
        when (status) {
            "pending" -> {
                binding.cardStatus.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.yellow
                    )
                )
                binding.cardTextName.text = "Pembayaran"
            }
            "completed" -> {
                binding.cardStatus.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.primary_green
                    )
                )
                binding.cardTextName.text = "Selesai"
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
                        R.color.young_green
                    )
                )
                binding.cardTextName.text = "Aktif"
            }
            "waiting" -> {
                binding.cardStatus.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.brown
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
                binding.cardTextName.setTextColor(ContextCompat.getColor(context, R.color.black))
            }
        }

        if (status != "pending") {
            binding.card.isClickable = false
        }
    }
}