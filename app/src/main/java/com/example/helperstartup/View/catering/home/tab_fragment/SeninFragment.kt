package com.example.helperstartup.View.catering.home.tab_fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helperstartup.Model.service.ResponseApi.DataItem
import com.example.helperstartup.R
import com.example.helperstartup.View.adapter.ListMenuAdapter
import com.example.helperstartup.View.catering.home.DetailMenu

class SeninFragment : Fragment() {

    private lateinit var listMenu : RecyclerView
    private var getData : DataItem? = null
    private lateinit var priceText : TextView
    private lateinit var namaPaket : TextView
    private lateinit var descPaket : TextView

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
        namaPaket = view.findViewById(R.id.judulPaket)
        descPaket = view.findViewById(R.id.descPaket)
        namaPaket.text = getData?.title
        descPaket.text = getData?.description
        priceText.text = "Rp " + getData?.price.toString()
        var menuText : TextView = view.findViewById(R.id.textMenu)
        menuText.text = "Menu hari " + getData?.dayMenus?.get(0)?.day + " :"
        showRecyclerList()
    }

    private fun showRecyclerList() {
        listMenu.layoutManager = LinearLayoutManager(context)
        val listMenuAdapter = ListMenuAdapter(getData?.dayMenus?.get(0)?.items)
        listMenu.adapter = listMenuAdapter
    }
}