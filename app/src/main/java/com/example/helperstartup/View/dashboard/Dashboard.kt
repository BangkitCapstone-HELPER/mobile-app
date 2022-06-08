package com.example.helperstartup.View.dashboard

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helperstartup.Model.data.ArticleModel
import com.example.helperstartup.Model.service.ApiConfig
import com.example.helperstartup.Model.service.ResponseApi.ResponseArticle
import com.example.helperstartup.R
import com.example.helperstartup.Model.User
import com.example.helperstartup.Model.UserPreference
import com.example.helperstartup.Model.service.ResponseApi.ResponseArticle2
import com.example.helperstartup.View.activity.LoginActivity
import com.example.helperstartup.View.adapter.ArticleAdapter
import com.example.helperstartup.View.catering.Menu.MenuCateringActivity
import com.example.helperstartup.View.handlingError.PageNotFound
import com.example.helperstartup.View.activity.ProfileActivity
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Dashboard : AppCompatActivity() {
    private lateinit var mUserPreference: UserPreference
    private lateinit var userModel: User
    private lateinit var listArticle : RecyclerView
    private lateinit var listArticle2 : RecyclerView
    private var listData = ArrayList<ArticleModel>()
    private var listData2 = ArrayList<ArticleModel>()
    private lateinit var buttonCatering : CardView
    private lateinit var buttonKost : CardView
    private lateinit var buttonShop : CardView
    private lateinit var buttonChatbot : CardView
    private lateinit var textViewProfile : TextView
    private lateinit var progressBar : ProgressBar
    private lateinit var progressBar2 : ProgressBar
    private lateinit var textGreeting : TextView
    private lateinit var profileImage : CircleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        mUserPreference = UserPreference(this)
        showExistingPreference()
        setupView()

        listArticle = findViewById(R.id.article_rv)
        listArticle2 = findViewById(R.id.article_rv2)
        buttonCatering = findViewById(R.id.card_catering)
        buttonKost = findViewById(R.id.card_kost)
        buttonShop = findViewById(R.id.card_shop)
        buttonChatbot = findViewById(R.id.card_chatbot)
        textViewProfile = findViewById(R.id.textGreeting)
        progressBar = findViewById(R.id.progresbar)
        progressBar2 = findViewById(R.id.progresbar2)
        textGreeting = findViewById(R.id.textGreeting)
        profileImage = findViewById(R.id.profilPicture)

        if ( !userModel.image.isEmpty()) {
            Picasso.get().load(userModel.image)
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_placeholder)
                .centerCrop()
                .resize(150, 150)
                .into(profileImage)
        } else {
            profileImage.setImageResource(R.drawable.img_placeholder)
        }
        textGreeting.text = "Hi, " + userModel.name

        progressBar.setVisibility(View.VISIBLE);
        progressBar2.setVisibility(View.VISIBLE);
        setListenerButton()
        fetchListStories()
        fetchListStories2()
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
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this@Dashboard, "Tidak dapat memuat artikel" , Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseArticle>, t: Throwable) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this@Dashboard, "Tidak dapat memuat artikel" , Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun fetchListStories2() {
        val client = ApiConfig.getApiService().getArticle2()
        client.enqueue(object : Callback<ResponseArticle2> {
            override fun onResponse(
                call: Call<ResponseArticle2>,
                response: Response<ResponseArticle2>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.i("data", responseBody.status.toString())
                        progressBar2.setVisibility(View.GONE);
                        setListArticle2(responseBody)
                    }
                }
                else {
                    progressBar2.setVisibility(View.GONE);
                    Toast.makeText(this@Dashboard, "Tidak dapat memuat artikel" , Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseArticle2>, t: Throwable) {
                progressBar2.setVisibility(View.GONE);
                Toast.makeText(this@Dashboard, "Tidak dapat memuat artikel" , Toast.LENGTH_LONG).show()
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

    private fun setListArticle2(listArticle: ResponseArticle2?) {
        if (listArticle?.data == null) {
            Toast.makeText(this, "Data null", Toast.LENGTH_LONG).show()
        }
        else {
            listArticle.data.forEach { i ->
                val stories = ArticleModel(i?.title, i?.guid, i?.enclosure?._url)
                listData2.add(stories)
            }
        }

        Log.i("data", listData2.toString())
        showRecyclerList2()
    }

    private fun showRecyclerList() {
        listArticle.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val listArticleAdapter = ArticleAdapter(listData)
        listArticle.adapter = listArticleAdapter
    }

    private fun showRecyclerList2() {
        listArticle2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val listArticleAdapter = ArticleAdapter(listData2)
        listArticle2.adapter = listArticleAdapter
    }
}