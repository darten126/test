package com.example.news_training

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.news_training.databinding.NewsDetailActivityBinding
import java.util.zip.Inflater

class NewsDetailActivity : AppCompatActivity() {
    private lateinit var binding: NewsDetailActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NewsDetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val urlToImage = intent.getStringExtra("UrlToImage")
        if (urlToImage!="Без изображения"){
            Glide.with(binding.imageView.context)
                .load(urlToImage)
                .into(binding.imageView)
        }
        binding.titleTextView.text = intent.getStringExtra("Title")
        binding.publishedAtTextView.text = intent.getStringExtra("PublishedAt")
        binding.descriptionTextView.text = intent.getStringExtra("Description")
    }
}