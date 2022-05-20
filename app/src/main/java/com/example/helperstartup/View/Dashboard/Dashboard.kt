package com.example.helperstartup.View.Dashboard

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helperstartup.Model.Data.ArticleModel
import com.example.helperstartup.Model.Service.ApiConfig
import com.example.helperstartup.Model.Service.ResponseApi.ResponseArticle
import com.example.helperstartup.R
import com.example.helperstartup.Model.User
import com.example.helperstartup.Model.UserPreference
import com.example.helperstartup.View.activity.LoginActivity
import com.example.helperstartup.View.Adapter.ArticleAdapter
import com.example.helperstartup.View.Catering.Menu.MenuCateringActivity
import com.example.helperstartup.View.HandlingError.PageNotFound
import com.example.helperstartup.View.activity.ProfileActivity
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Dashboard : AppCompatActivity() {
    private lateinit var mUserPreference: UserPreference
    private lateinit var userModel: User
    private lateinit var listArticle : RecyclerView
    private var listData = ArrayList<ArticleModel>()
    private lateinit var buttonCatering : CardView
    private lateinit var buttonKost : CardView
    private lateinit var buttonShop : CardView
    private lateinit var buttonChatbot : CardView
    private lateinit var textViewProfile : TextView
    private lateinit var progressBar : ProgressBar
    private lateinit var textGreeting : TextView
    private lateinit var profileImage : CircleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        mUserPreference = UserPreference(this)
        showExistingPreference()
        setupView()


        listArticle = findViewById(R.id.article_rv)
        buttonCatering = findViewById(R.id.card_catering)
        buttonKost = findViewById(R.id.card_kost)
        buttonShop = findViewById(R.id.card_shop)
        buttonChatbot = findViewById(R.id.card_chatbot)
        textViewProfile = findViewById(R.id.textGreeting)
        progressBar = findViewById(R.id.progresbar)
        textGreeting = findViewById(R.id.textGreeting)
        profileImage = findViewById(R.id.profilPicture)

        progressBar.setVisibility(View.VISIBLE);


        setupView()
        setListenerButton()
        fetchListStories()
    }

    private fun setListenerButton() {
        buttonCatering.setOnClickListener {
            val intent = Intent(this, MenuCateringActivity::class.java)
            startActivity(intent)
        }
        buttonKost.setOnClickListener {
            val intent = Intent(this, PageNotFound::class.java)
            startActivity(intent)
        }
        buttonShop.setOnClickListener {
            val intent = Intent(this, PageNotFound::class.java)
            startActivity(intent)
        }
        buttonChatbot.setOnClickListener {
            val intent = Intent(this, PageNotFound::class.java)
            startActivity(intent)
        }
        textGreeting.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        profileImage.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun showExistingPreference() {
        userModel = mUserPreference.getUser()
        if (!userModel.isLogin) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun fetchListStories() {
        val client = ApiConfig.getApiService().getArticle()
        client.enqueue(object : Callback<ResponseArticle> {
            override fun onResponse(
                call: Call<ResponseArticle>,
                response: Response<ResponseArticle>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.i("data", responseBody.status.toString())
                        progressBar.setVisibility(View.GONE);
                        setListArticle(responseBody)
                    }
                }
                else {
                    Toast.makeText(this@Dashboard, "Error" , Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseArticle>, t: Throwable) {
                Toast.makeText(this@Dashboard, "Error" , Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setListArticle(listArticle: ResponseArticle?) {
        if (listArticle?.data == null) {
            Toast.makeText(this, "Data null", Toast.LENGTH_LONG).show()
        }
        else {
            listArticle.data.forEach { i ->
                val stories = ArticleModel(i?.title, i?.guid, i?.enclosure?._url)
                listData.add(stories)
            }
        }

        Log.i("data", listData.toString())
        showRecyclerList()
    }

    private fun showRecyclerList() {
        listArticle.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val listArticleAdapter = ArticleAdapter(listData)
        listArticle.adapter = listArticleAdapter
    }
}