package com.example.helperstartup.View.Catering.riwayat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helperstartup.Model.Data.HistoryModel
import com.example.helperstartup.Model.Service.ApiConfig
import com.example.helperstartup.Model.Service.ResponseApi.TransactionResponse
import com.example.helperstartup.Model.User
import com.example.helperstartup.Model.UserPreference
import com.example.helperstartup.View.Adapter.HistoryAdapter
import com.example.helperstartup.View.activity.LoginActivity
import com.example.helperstartup.databinding.FragmentRiwayatBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayatFragment : Fragment() {
    private var _binding: FragmentRiwayatBinding? = null
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var mUserPreference: UserPreference
    private lateinit var userModel: User

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRiwayatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUserPreference = UserPreference(requireContext())
        showExistingPreference()
        setupView()
        fetchTransactions()
//        showData(
//            listOf(
//                HistoryModel(
//                    1,
//                    "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png",
//                    "Paket A",
//                    "completed",
//                    "Lorem ipsum",
//                    null,
//                    "2022-12-31T09:55:00",
//                    30000
//                ),
//                HistoryModel(
//                    2,
//                    null,
//                    "Paket B",
//                    "cancelled",
//                    "Lorem ipsum",
//                    null,
//                    "2022-10-20T09:55:00",
//                    40000
//                )
//            )
//        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showExistingPreference() {
        userModel = mUserPreference.getUser()
        if (!userModel.isLogin) {
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }
    }

    private fun setupView() {
        historyAdapter = HistoryAdapter()
        val recyclerView: RecyclerView = binding.rvHistory
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity().applicationContext)
            adapter = historyAdapter
            setHasFixedSize(true)
        }
    }

    private fun fetchTransactions() {
        showLoading(true)
        val client = ApiConfig.getApiService().getTransactions(auth = "bearer " + userModel.token)
        client.enqueue(object : Callback<TransactionResponse> {
            override fun onResponse(
                call: Call<TransactionResponse>,
                response: Response<TransactionResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("RESPOSNE BODY", responseBody.toString())
                    if (responseBody != null) {
                        setListTransaction(responseBody)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Terjadi kesalahan dalam memuat data",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Terjadi kesalahan dalam memuat data",
                        Toast.LENGTH_LONG
                    ).show()
                }
                showLoading(false)
            }

            override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Terjadi kesalahan pada jaringan",
                    Toast.LENGTH_LONG
                ).show()
            }

        })
    }

    private fun setListTransaction(transactionResponse: TransactionResponse) {
        if (transactionResponse.data == null || transactionResponse.data.isEmpty()) {
            binding.noItemText.visibility = View.VISIBLE
            Log.d("HISTORYADAPTER", "MASUK situ")
        } else {
            val listHistory = arrayListOf<HistoryModel>()
            transactionResponse.data.forEach {
                val remaining =
                    if (it.remaining == 0) "Sudah selesai" else "${it.remaining} hari tersisa"
                listHistory.add(
                    HistoryModel(
                        it.id,
                        it.menu.dayMenus[0].image,
                        it.menu.title,
                        it.status,
                        it.menu.description,
                        remaining,
                        it.createdAt,
                        it.amount
                    )
                )
            }
            historyAdapter.setListHistories(listHistory)
            Log.d("HISTORYADAPTER", "MASUK SINI")
            binding.noItemText.visibility = View.GONE
        }
    }

    private fun showData(listHistory: List<HistoryModel>?) {
        Log.d("riwayatList", listHistory.toString())
        if (listHistory != null && listHistory.isNotEmpty()) {
            historyAdapter.setListHistories(listHistory)
            binding.noItemText.visibility = View.GONE
        } else {
            binding.noItemText.visibility = View.VISIBLE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}