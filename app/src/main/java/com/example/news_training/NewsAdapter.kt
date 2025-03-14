package com.example.news_training

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news_training.databinding.ActivityMainBinding
import com.example.news_training.databinding.ItemNewsBinding
import com.example.news_training.model.Article

class NewsAdapter(
    private val onItemClick: (String) -> Unit // Лямбда для обработки клика
): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val newsList = mutableListOf<Article>()

    fun submitList(list: List<Article>) {
        newsList.clear()
        newsList.addAll(list)
        notifyDataSetChanged()
    }

    class NewsViewHolder(
        private val binding: ItemNewsBinding,
        private val onItemClick: (String) -> Unit // Передаем лямбду в ViewHolder
        ): RecyclerView.ViewHolder(binding.root) {
                fun bind(article:Article) {
                    binding.titleTextView.text = article.title
                    binding.descriptionTextView.text = article.description?: "Без описания"
                    binding.publishedAtTextView.text = article.publishedAt?: "Без даты"
                    Glide.with(binding.imageView.context)
                        .load(article.urlToImage)
                        .into(binding.imageView)

                    itemView.setOnClickListener {
                        onItemClick(article.url)
                    }
                }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int = newsList.size
}