package com.example.helperstartup.View.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.helperstartup.Model.data.BankAccount
import com.example.helperstartup.R
import com.squareup.picasso.Picasso

class BankAdapter(private val context: Activity, private val arrayList: ArrayList<BankAccount>) :
    ArrayAdapter<BankAccount>(context, R.layout.components_list_item_bank_accounts, arrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.components_list_item_bank_accounts, null)

        val imgView : ImageView = view.findViewById(R.id.imageViewBank)
        val bankName : TextView = view.findViewById(R.id.tvBankName)
        val bankNumber : TextView = view.findViewById(R.id.tvBankNumber)
        val bankAccountName : TextView = view.findViewById(R.id.tvBankAccountName)

        val item = arrayList[position]
        Picasso.get().load(item.imageUrl)
            .placeholder(R.drawable.loading_image)
            .error(R.drawable.img_placeholder)
            .resize(100,100)
            .into(imgView)
        bankName.text = item.bankName
        bankNumber.text = item.bankNumber
        bankAccountName.text = item.bankNameAccount
        return view
    }
}