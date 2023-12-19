package app.entertainment.newsy

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import app.entertainment.newsy.adapters.NewsArticleAdapter
import app.entertainment.newsy.api.RequestManager.newsApiService
import app.entertainment.newsy.databinding.ActivityMainBinding
import app.entertainment.newsy.models.Article
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val articleList = mutableListOf<Article>()

    private lateinit var newsArticleAdapter: NewsArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initRecyclerView()

        val job = CoroutineScope(Dispatchers.IO).launch {
            val response = newsApiService.getTopHeadlines(resources.getString(R.string.api_key),"in", "sports", "cricket")
            if(response.isSuccessful){
                val newsResponse = response.body()
                withContext(Dispatchers.Main){
                    newsArticleAdapter.updateArticles(newsResponse!!.articles)
                }
            } else
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "An error occurred: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
        }

        job.cancel()

    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            setHasFixedSize(true)
            newsArticleAdapter = NewsArticleAdapter(applicationContext, articleList)
            adapter = newsArticleAdapter
        }
    }
}