package com.example.helperstartup.View.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.helperstartup.Model.Data.BankAccount
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
            .placeholder(R.drawable.bri)
            .error(com.denzcoskun.imageslider.R.drawable.placeholder)
            .resize(150, 150)
            .centerCrop()
            .into(imgView)
        bankName.text = item.bankName
        bankNumber.text = item.bankNumber
        bankAccountName.text = item.bankNameAccount
        return view
    }
}