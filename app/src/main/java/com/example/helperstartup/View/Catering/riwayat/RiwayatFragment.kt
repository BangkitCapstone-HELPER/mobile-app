package com.example.helperstartup.View.Catering.riwayat

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helperstartup.Model.Data.BankAccount
import com.example.helperstartup.Model.Data.HistoryModel
import com.example.helperstartup.Model.Service.ApiConfig
import com.example.helperstartup.Model.Service.ResponseApi.TransactionResponse
import com.example.helperstartup.Model.User
import com.example.helperstartup.Model.UserPreference
import com.example.helperstartup.Model.helper.formatRupiah
import com.example.helperstartup.R
import com.example.helperstartup.View.Adapter.BankAdapter
import com.example.helperstartup.View.Adapter.HistoryAdapter
import com.example.helperstartup.View.Catering.Menu.MenuCateringActivity
import com.example.helperstartup.View.activity.LoginActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayatFragment : Fragment() {
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var mUserPreference: UserPreference
    private lateinit var userModel: User
    private lateinit var mDialog: Dialog
    private lateinit var tvItemText: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_riwayat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUserPreference = UserPreference(requireContext())
        showExistingPreference()
        tvItemText = view.findViewById(R.id.no_item_text)
        progressBar = view.findViewById(R.id.progress_bar)
        setupView()
        fetchTransactions()
        getBundleFromOrderConfirmation()

    }

    private fun setupView() {
        (activity as MenuCateringActivity).supportActionBar?.title = "Riwayat Transaksi"

        historyAdapter = HistoryAdapter {
            if (it.status == "pending") {
                showBottomSheet(it)
            }
        }
        val recyclerView: RecyclerView? = view?.findViewById(R.id.rv_history)
        recyclerView?.apply {
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

        val bankArraylist = arrayListOf<BankAccount>()
        bankArraylist.add(
            BankAccount(
                1,
                "https://rekreartive.com/wp-content/uploads/2019/04/Logo-BRI-Bank-Rakyat-Indonesia-PNG-Terbaru.png",
                "BRI",
                "034 101 000 743 303",
                "a.n Rahmat Wibowo"
            )
        )
        bankArraylist.add(
            BankAccount(
                2,
                "https://www.freepnglogos.com/uploads/logo-bca-png/bank-central-asia-logo-bank-central-asia-bca-format-cdr-png-gudril-1.png",
                "BCA",
                "123 456 789 102 123",
                "a.n Alamsyah"
            )
        )

        bottomSheetView.findViewById<TextView>(R.id.amount_price).text =
            "Jumlah ${formatRupiah(historyModel.price)}"

        val listView = bottomSheetView.findViewById<ListView>(R.id.bankAccountsList)
        listView.adapter = BankAdapter(requireActivity(), bankArraylist)

        val closeIcon = bottomSheetView.findViewById<TextView>(R.id.closeDialogIcon)
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
            tvItemText.visibility = View.VISIBLE
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
            tvItemText.visibility = View.GONE

        }
    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun getBundleFromOrderConfirmation() {
        val isSuccess = arguments?.getBoolean("isSuccess")
        if (isSuccess != null) {
            mDialog = Dialog(requireContext())
            if (isSuccess == true) {
                mDialog.setContentView(R.layout.component_popup)
                mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                mDialog.show()
            } else {
                mDialog.setContentView(R.layout.component_popup_fail)
                mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                mDialog.show()
            }
        }
    }
}