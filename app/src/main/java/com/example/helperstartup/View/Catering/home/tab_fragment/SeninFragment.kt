package com.example.helperstartup.View.Catering.home.tab_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helperstartup.Model.Service.ResponseApi.DataItem
import com.example.helperstartup.R
import com.example.helperstartup.View.Adapter.ListMenuAdapter
import com.example.helperstartup.View.Catering.home.DetailMenu

class SeninFragment : Fragment() {

    private lateinit var listMenu : RecyclerView
    private var getData : DataItem? = null
    private lateinit var priceText : TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_senin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity: DetailMenu? = activity as DetailMenu?
        getData = activity?.sendDataMenu()
        listMenu = view.findViewById(R.id.rvMenu)
        priceText = view.findViewById(R.id.priceText)
        priceText.text = "Rp " + getData?.price.toString()
        var menuText : TextView = view.findViewById(R.id.textMenu)
        menuText.text = "Menu hari " + getData?.dayMenus?.get(6)?.day + " :"
        showRecyclerList()
    }

    private fun showRecyclerList() {
        listMenu.layoutManager = LinearLayoutManager(context)
        val listMenuAdapter = ListMenuAdapter(getData?.dayMenus?.get(6)?.items)
        listMenu.adapter = listMenuAdapter
    }
}