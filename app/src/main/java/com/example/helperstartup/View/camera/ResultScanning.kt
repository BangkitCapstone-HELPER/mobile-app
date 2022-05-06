package com.example.helperstartup.View.camera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helperstartup.Model.Service.ApiConfig
import com.example.helperstartup.Model.Service.ResponseApi.ResponseMenu
import com.example.helperstartup.R
import com.example.helperstartup.View.Adapter.MenuAdapter
import com.example.helperstartup.View.Adapter.ScanningAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultScanning : AppCompatActivity() {

    private lateinit var listMenu : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_scanning)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        listMenu = findViewById(R.id.scanning_rv)
        fetchListStories()
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
                        listMenu.layoutManager = LinearLayoutManager(this@ResultScanning)
                        val listMenuAdapter = ScanningAdapter(responseBody)
                        listMenu.adapter = listMenuAdapter
                    }
                }
                else {
                    Toast.makeText(this@ResultScanning, "Error" , Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseMenu>, t: Throwable) {
                Toast.makeText(this@ResultScanning, "Error" , Toast.LENGTH_LONG).show()
            }
        })
    }
}