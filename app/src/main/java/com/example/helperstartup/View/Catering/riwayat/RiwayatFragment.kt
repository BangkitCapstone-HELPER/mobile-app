package com.example.helperstartup.View.Catering.riwayat

import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helperstartup.Model.Data.HistoryModel
import com.example.helperstartup.Model.Service.ApiConfig
import com.example.helperstartup.Model.Service.ResponseApi.TransactionResponse
import com.example.helperstartup.Model.User
import com.example.helperstartup.Model.UserPreference
import com.example.helperstartup.R
import com.example.helperstartup.View.Adapter.HistoryAdapter
import com.example.helperstartup.View.Catering.Menu.MenuCateringActivity
import com.example.helperstartup.View.activity.LoginActivity
import com.example.helperstartup.databinding.FragmentRiwayatBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupView() {
        (activity as MenuCateringActivity).supportActionBar?.title = "Riwayat Transaksi"

        historyAdapter = HistoryAdapter {
            if (it.status == "pending") {
                showBottomSheet(it)
            }

        }
        val recyclerView: RecyclerView = binding.rvHistory
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity().applicationContext)
            adapter = historyAdapter
            setHasFixedSize(true)
        }
    }

    private fun showBottomSheet(historyModel: HistoryModel) {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)

        val bottomSheetView =
            LayoutInflater.from(context).inflate(
                R.layout.component_bottom_sheet_transaction,
                view?.findViewById(R.id.bottomSheetTransaction) as ScrollView?
            )
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()

        val closeIcon =  bottomSheetView.findViewById<TextView>(R.id.closeDialogIcon)
        closeIcon?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
    }

    private fun showExistingPreference() {
        userModel = mUserPreference.getUser()
        if (!userModel.isLogin) {
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
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
            binding.noItemText.visibility = View.GONE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}