package com.example.helperstartup.View.catering.keranjang

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import com.example.helperstartup.Model.service.ResponseApi.DataItem
import com.example.helperstartup.Model.service.ResponseApi.PostTransactionResponse
import com.example.helperstartup.Model.User
import com.example.helperstartup.Model.UserPreference
import com.example.helperstartup.Model.helper.formatRupiah
import com.example.helperstartup.Model.service.ApiConfig
import com.example.helperstartup.Model.service.request.PostTransaction
import com.example.helperstartup.R
import com.example.helperstartup.View.activity.MapsActivity
import com.example.helperstartup.View.catering.Menu.MenuCateringActivity
import com.example.helperstartup.ViewModel.OrderConfirmationViewModel
import com.example.helperstartup.databinding.ActivityOrderConfirmationBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class OrderConfirmationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderConfirmationBinding
    private lateinit var data: DataItem
    private lateinit var tempTimeSelectionList: ArrayList<Boolean>
    private val viewModel: OrderConfirmationViewModel by viewModels()
    private lateinit var mUserPreference: UserPreference
    private lateinit var userModel: User
    private var startDate: String? = null
    private var endDate: String? = null
    private var builder = MaterialDatePicker.Builder.dateRangePicker()
    private var calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mUserPreference = UserPreference(this)
        userModel = mUserPreference.getUser()
        setupActionBar()
        setupView()
        setupAction()
    }

    private fun setupActionBar() {
        supportActionBar?.title = "Konfirmasi Pesanan"
    }

    private fun setupView() {
        data = intent.getParcelableExtra("dataItem")!!
        with(binding) {
            Picasso.get().load(data.dayMenus?.get(0)?.image)
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.img_placeholder)
                .resize(300, 300)
                .centerCrop()
                .into(cardMenu.photoHome)
            cardMenu.titleHome.text = data.title
            cardMenu.descHome.text = data.description
        }

        // Create the observer which updates the UI.
//        viewModel.counter.observe(this) {
//            binding.tvCounter.text = it.toString()
//        }

        viewModel.totalPrice.observe(this) {
            binding.tvTotalPrice.text = "Total ${formatRupiah(it)}"
        }

        viewModel.timeSelectionList.observe(this) {
            toggleButton(binding.buttonPagi, it[0])
            toggleButton(binding.buttonSiang, it[1])
            toggleButton(binding.buttonMalam, it[2])
        }

        data.price?.let { viewModel.updateTotalPrice(it) }
    }

    private fun toggleButton(btn: MaterialButton, state: Boolean) {
        if (state) {
            btn.setTextColor(ContextCompat.getColor(this, R.color.white))
            btn.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary_green))
        } else {
            btn.setTextColor(ContextCompat.getColor(this, R.color.primary_green))
            btn.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
        }
    }

    private fun setupAction() {
        with(binding) {
//            minusitemBtn.setOnClickListener {
//                if (viewModel.counter.value!! > 1) {
//                    viewModel.counter.value = viewModel.counter.value?.minus(1)
//                    data.price?.let { it1 -> viewModel.updateTotalPrice(it1) }
//                }
//            }
//            additemBtn.setOnClickListener {
//                viewModel.counter.value = viewModel.counter.value?.plus(1)
//                data.price?.let { it1 -> viewModel.updateTotalPrice(it1) }
//            }

            buttonPagi.setOnClickListener {
                tempTimeSelectionList = viewModel.timeSelectionList.value!!

                tempTimeSelectionList[0] = tempTimeSelectionList[0].not()

                viewModel.timeSelectionList.value = tempTimeSelectionList

                data.price?.let { it1 -> viewModel.updateTotalPrice(it1) }
            }

            buttonSiang.setOnClickListener {
                tempTimeSelectionList = viewModel.timeSelectionList.value!!

                tempTimeSelectionList[1] = tempTimeSelectionList[1].not()

                viewModel.timeSelectionList.value = tempTimeSelectionList

                data.price?.let { it1 -> viewModel.updateTotalPrice(it1) }
            }

            buttonMalam.setOnClickListener {
                tempTimeSelectionList = viewModel.timeSelectionList.value!!

                tempTimeSelectionList[2] = tempTimeSelectionList[2].not()

                viewModel.timeSelectionList.value = tempTimeSelectionList

                data.price?.let { it1 -> viewModel.updateTotalPrice(it1) }
            }

            checkoutButton.setOnClickListener {
                val dateValid = startDate != null && endDate != null
                if (!dateValid) {
                    Toast.makeText(this@OrderConfirmationActivity, "Pilih tanggal mulai dan berakhir terlebih dahulu", Toast.LENGTH_LONG).show()
                }

                tempTimeSelectionList = viewModel.timeSelectionList.value!!
                val predicate: (Boolean) -> Boolean = { it }
                val countTrue = tempTimeSelectionList.count(predicate)
                if (countTrue > 0 && viewModel.counter.value!! > 0 && dateValid) {
                    val client = ApiConfig.getApiService().postTransaction(
                        auth = "bearer ${userModel.token}",
                        PostTransaction(
                            data.id,
                            viewModel.counter.value,
                            viewModel.totalPrice.value,
                            binding.editTextCatatan.text.toString(),
                            tempTimeSelectionList[0],
                            tempTimeSelectionList[1],
                            tempTimeSelectionList[2],
                            null,
                            null,
                            startDate,
                            endDate
                        )
                    )

                    client.enqueue(object : Callback<PostTransactionResponse> {
                        override fun onResponse(
                            call: Call<PostTransactionResponse>,
                            response: Response<PostTransactionResponse>
                        ) {
                            if (response.isSuccessful) {
                                startHistoryFragment(true)
                            } else {
                                // pesanan gagal
                                startHistoryFragment(false)
                            }
                        }

                        override fun onFailure(call: Call<PostTransactionResponse>, t: Throwable) {
                            Toast.makeText(
                                this@OrderConfirmationActivity,
                                "Terjadi kesalahan pada jaringan",
                                Toast.LENGTH_SHORT
                            ).show()
                            startHistoryFragment(false)
                        }
                    })
                } else {
                    Toast.makeText(
                        this@OrderConfirmationActivity,
                        "Mohon pilih waktu antar terlebih dahulu",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            selectLocationBtn.setOnClickListener {
                Log.d("Bukalokasi", "Masuk sini")
                val intent = Intent(this@OrderConfirmationActivity, MapsActivity::class.java)
                startActivity(intent)
            }


            // date picker
            val today = MaterialDatePicker.todayInUtcMilliseconds()
            calendar.clear()
            calendar.timeInMillis = today
            val constraintBuilder = CalendarConstraints.Builder()
            constraintBuilder.setValidator(DateValidatorPointForward.now())
            constraintBuilder.setStart(today)
            builder.setTitleText("Pilih tanggal mulai dan berakhir")
            builder.setSelection(
                Pair(
                    MaterialDatePicker.thisMonthInUtcMilliseconds(),
                    MaterialDatePicker.todayInUtcMilliseconds()
                )
            )

            builder.setCalendarConstraints(constraintBuilder.build())
            val materialDatePicker = builder.build()

            chooseDateBtn.setOnClickListener {
                materialDatePicker.show(supportFragmentManager, "DATERANGE_PICKER")
            }

            materialDatePicker.addOnPositiveButtonClickListener {
                val formatter = SimpleDateFormat("dd/MM/yyyy")
                startDate = formatter.format(Date(materialDatePicker.selection?.first!!))
                endDate = formatter.format(Date(materialDatePicker.selection?.second!!))
                dateTextView.text = "${startDate} - ${endDate}"
                if (materialDatePicker.selection != null) {
                    val msDiff =
                        materialDatePicker.selection?.second!! - materialDatePicker.selection?.first!!
                    val daysDiff = 1 + TimeUnit.MILLISECONDS.toDays(msDiff).toInt()
                    viewModel.counter.value = daysDiff
                    data.price?.let { it1 -> viewModel.updateTotalPrice(it1) }
                }
            }
        }
    }

    private fun startHistoryFragment(isSuccess: Boolean) {
        val intent = Intent(this@OrderConfirmationActivity, MenuCateringActivity::class.java)
        intent.putExtra("isSuccess", isSuccess)
        startActivity(intent)
    }
}