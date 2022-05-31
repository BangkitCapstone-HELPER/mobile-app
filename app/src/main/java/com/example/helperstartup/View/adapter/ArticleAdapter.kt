package com.example.helperstartup.View.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.helperstartup.Model.data.ArticleModel
import com.example.helperstartup.R
import com.example.helperstartup.View.dashboard.ArticleActivity
import com.squareup.picasso.Picasso

class ArticleAdapter(private val listArticle: ArrayList<ArticleModel>) : RecyclerView.Adapter<ArticleAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.component_article, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listArticle[position])
    }

    override fun getItemCount(): Int = listArticle.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var photoPost: ImageView = itemView.findViewById(R.id.photoPost)
        private var namePost: TextView = itemView.findViewById(R.id.titleArticle)

        fun bind(articleModel: ArticleModel) {
            Picasso.get().load(articleModel.photo).into(photoPost);
            namePost.text = articleModel.title

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ArticleActivity::class.java)
                intent.putExtra("DATA", articleModel)
                itemView.context.startActivity(intent)
            }
        }
    }
}