package com.example.helperstartup.View.catering.riwayat

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helperstartup.Model.data.BankAccount
import com.example.helperstartup.Model.data.HistoryModel
import com.example.helperstartup.Model.service.ApiConfig
import com.example.helperstartup.Model.service.ResponseApi.PatchTransactionResponse
import com.example.helperstartup.Model.service.ResponseApi.TransactionResponse
import com.example.helperstartup.Model.service.ResponseApi.UploadFileToStorageResponse
import com.example.helperstartup.Model.service.request.PatchTransaction
import com.example.helperstartup.Model.User
import com.example.helperstartup.Model.UserPreference
import com.example.helperstartup.Model.helper.formatRupiah
import com.example.helperstartup.Model.reduceFileImage
import com.example.helperstartup.Model.uriToFile
import com.example.helperstartup.R
import com.example.helperstartup.View.adapter.BankAdapter
import com.example.helperstartup.View.adapter.HistoryAdapter
import com.example.helperstartup.View.catering.Menu.MenuCateringActivity
import com.example.helperstartup.View.activity.LoginActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class RiwayatFragment : Fragment() {
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var mUserPreference: UserPreference
    private lateinit var userModel: User
    private lateinit var mDialog: Dialog
    private lateinit var tvItemText: TextView
    private lateinit var progressBar: ProgressBar
    private var getFile: File? = null
    private var clickedTransaction: HistoryModel? = null

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

    private fun showExistingPreference() {
        userModel = mUserPreference.getUser()
        if (!userModel.isLogin) {
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }
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
//        val swipeRefreshLayout = view?.findViewById<SwipeRefreshLayout>(R.id.swiperefresh)
//        swipeRefreshLayout?.setOnRefreshListener {
//            fetchTransactions()
//            swipeRefreshLayout.isRefreshing = false
//        }
    }

    private fun showBottomSheet(historyModel: HistoryModel) {
        clickedTransaction = historyModel
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

        val uploadBtn = bottomSheetView.findViewById<MaterialButton>(R.id.unggahBukti)
        uploadBtn.setOnClickListener {
            startGallery()
            bottomSheetDialog.dismiss()
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Pilih gambar")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, requireContext())
            getFile = myFile
            uploadImage()
        }
    }

    private fun uploadImage() {
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)
            val folder =
                "transactions/${userModel.id}".toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file",
                file.name,
                requestImageFile
            )

            val client = ApiConfig.getApiService().uploadFile(imageMultipart, folder)
            showLoading(true)
            client.enqueue(object : Callback<UploadFileToStorageResponse> {
                override fun onResponse(
                    call: Call<UploadFileToStorageResponse>,
                    response: Response<UploadFileToStorageResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            val url = responseBody.data.url
                            patchTransaction(url)
                            showLoading(false)
                        }
                    } else {
                        Toast.makeText(context, response.message().toString(), Toast.LENGTH_SHORT)
                            .show()
                        showLoading(false)
                    }
                }

                override fun onFailure(call: Call<UploadFileToStorageResponse>, t: Throwable) {
                    Toast.makeText(context, "Gagal mengunggah gambar", Toast.LENGTH_SHORT).show()
                    showLoading(false)
                }
            })

        } else {
            Toast.makeText(
                context,
                "Silakan masukkan berkas gambar terlebih dahulu.",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    private fun patchTransaction(url: String) {
        if (clickedTransaction != null) {
            val service = ApiConfig.getApiService()
                .updateTransaction(
                    auth = "bearer ${userModel.token}",
                    PatchTransaction(clickedTransaction!!.id, STATUS, url)
                )
            service.enqueue(object : Callback<PatchTransactionResponse> {
                override fun onResponse(
                    call: Call<PatchTransactionResponse>,
                    response: Response<PatchTransactionResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            Toast.makeText(context, "Berhasil mengunggah bukti, dalam verifikasi", Toast.LENGTH_SHORT).show()
                        }
                        showLoading(false)
                    } else {
                        Toast.makeText(
                            context,
                            "Terjadi kesalahan dalam mengunggah bukti",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        showLoading(false)
                    }
                }

                override fun onFailure(call: Call<PatchTransactionResponse>, t: Throwable) {
                    Toast.makeText(context, "Terdapat kesalahan jaringan", Toast.LENGTH_SHORT)
                        .show()
                    showLoading(false)
                }

            })
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
                showLoading(false)
                Log.d("riwayatfragment", t.message.toString())
            }

        })
    }

    private fun setListTransaction(transactionResponse: TransactionResponse) {
        if (transactionResponse.data == null || transactionResponse.data.isEmpty()) {
            tvItemText.visibility = View.VISIBLE
        } else {
            val listHistory = arrayListOf<HistoryModel>()
            transactionResponse.data.forEach {
                listHistory.add(
                    HistoryModel(
                        it.id,
                        it.menu.dayMenus[0].image,
                        it.menu.title,
                        it.status,
                        it.menu.description,
                        it.remaining,
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

    companion object {
        private const val STATUS = "waiting"
    }
}