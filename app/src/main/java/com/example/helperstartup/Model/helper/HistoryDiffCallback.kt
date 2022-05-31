package com.example.helperstartup.Model.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.helperstartup.Model.data.HistoryModel

class HistoryDiffCallback(
    private val mOldHistory: List<HistoryModel>,
    private val mNewHistory: List<HistoryModel>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldHistory.size
    }

    override fun getNewListSize(): Int {
        return mNewHistory.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldHistory[oldItemPosition].title == mNewHistory[newItemPosition].title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldHistory = mOldHistory[oldItemPosition]
        val newHistory = mNewHistory[newItemPosition]
        return oldHistory.id == newHistory.id
        oldHistory.imageUrl == newHistory.imageUrl
                && oldHistory.expiredTime == newHistory.expiredTime
                && oldHistory.date == newHistory.date
                && oldHistory.price == newHistory.price
                && oldHistory.status == newHistory.status
                && oldHistory.title == newHistory.title
                && oldHistory.description == newHistory.description
    }
}