package com.example.news_training

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.inputmethod.InputBinding
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news_training.databinding.ActivityMainBinding
import com.example.news_training.model.Article
import com.example.news_training.utils.Constants

class MainActivity : AppCompatActivity(), MainView {
    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: NewsAdapter
    private var currentQuery: String = "bitcoin"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        presenter = MainPresenter(this)
        adapter = NewsAdapter{ url ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            /*val intent = Intent(this, NewsDetailActivity::class.java).apply{
                putExtra("Title",article.title)
                putExtra("Description",article.description?:"Без описания")
                putExtra("UrlToImage",article.urlToImage?:"Без изображения")
                putExtra("PublishedAt",article.publishedAt?:"Без даты")
            }*/
            startActivity(intent)
        }

        binding.recyclerView.layoutManager =LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.swipeRefresh.setOnRefreshListener { presenter.loadNewsFromToDate(Constants.API_KEY, currentQuery,"2025-03-11","2025-03-11") }
    }

    override fun showLoading() {
        binding.swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        binding.swipeRefresh.isRefreshing = false
    }

    override fun showNews(newsList: List<Article>) {
        val formattedTimeNews = newsList.map { article ->
            article.copy(publishedAt = presenter.formatDate(article.publishedAt))
        }
        adapter.submitList(formattedTimeNews)
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    currentQuery = query
                    presenter.loadNews(Constants.API_KEY, currentQuery)
                    searchView.clearFocus()
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }
}
