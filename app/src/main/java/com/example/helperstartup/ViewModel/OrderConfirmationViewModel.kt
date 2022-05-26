package com.example.helperstartup.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderConfirmationViewModel : ViewModel() {

    var totalPrice = MutableLiveData(0)
    var counter = MutableLiveData(1)
    var timeSelectionList = MutableLiveData(arrayListOf(true, false, false))

    fun updateTotalPrice(basePrice: Int) {
        val predicate: (Boolean) -> Boolean = { it }
        val countTrue = timeSelectionList.value?.count(predicate)
        val tempPrice = basePrice * countTrue!!
        totalPrice.postValue(counter.value?.times(tempPrice) ?: 0)
    }
}