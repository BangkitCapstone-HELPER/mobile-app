package com.example.helperstartup.View.catering.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helperstartup.Model.service.ApiConfig
import com.example.helperstartup.Model.service.ResponseApi.ResponseMenu
import com.example.helperstartup.R
import com.example.helperstartup.View.adapter.MenuAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var listMenu : RecyclerView
    private lateinit var textNoData : TextView
    private lateinit var progressBar: ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listMenu = view.findViewById(R.id.home_rv)
        textNoData = view.findViewById(R.id.textNoData)
        progressBar = view.findViewById(R.id.progresbar)
        fetchListStories()
        textNoData.setVisibility(View.INVISIBLE)
        progressBar.setVisibility(View.VISIBLE)

    }

    private fun fetchListStories() {
        val client = ApiConfig.getApiService().getMenu()
        client.enqueue(object : Callback<ResponseMenu> {
            override fun onResponse(
                call: Call<ResponseMenu>,
                response: Response<ResponseMenu>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.i("data", responseBody.toString())
                        if (responseBody.data?.size == null) {
                            textNoData.setVisibility(View.VISIBLE)
                            progressBar.setVisibility(View.GONE)
                        }
                        else {
                            progressBar.setVisibility(View.GONE)
                            listMenu.layoutManager = LinearLayoutManager(context)
                            val listMenuAdapter = MenuAdapter(responseBody)
                            listMenu.adapter = listMenuAdapter
                        }
                    }
                }
                else {
                    textNoData.setVisibility(View.VISIBLE)
                    Toast.makeText(context, "Error" , Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseMenu>, t: Throwable) {
                textNoData.setVisibility(View.VISIBLE)
                Toast.makeText(context, "Error" , Toast.LENGTH_LONG).show()
            }
        })
    }
}