package app.entertainment.newsy.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.entertainment.newsy.NewsArticleDetailsActivity
import app.entertainment.newsy.R
import app.entertainment.newsy.databinding.ItemNewsArticleBinding
import app.entertainment.newsy.models.Article
import app.entertainment.newsy.utils.MyDiffUtil
import com.bumptech.glide.Glide

class NewsArticleAdapter(
    private val context: Context, private var currentArticleList: List<Article>
) : RecyclerView.Adapter<NewsArticleAdapter.NewsArticleViewHolder>() {

    inner class NewsArticleViewHolder(val binding: ItemNewsArticleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsArticleViewHolder {
        return NewsArticleViewHolder(
            ItemNewsArticleBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return currentArticleList.size
    }

    override fun onBindViewHolder(holder: NewsArticleViewHolder, position: Int) {
        val currentArticle = currentArticleList[position]

        holder.binding.apply {
            newsArticleTitle.text = currentArticle.title
            newsArticleSource.text = currentArticle.source.name

            // Load image into imageview using Glide library
            Glide.with(context).load(currentArticle.urlToImage)
                .placeholder(R.drawable.not_available)
                .into(newsArticleImageView)

            articleContainer.setOnClickListener {
                Intent(context, NewsArticleDetailsActivity::class.java).also {
                    it.putExtra("URL", currentArticle.url)
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(it)
                }
            }
        }
    }

    fun updateArticles(newArticleList: List<Article>) {
        val diffUtil = MyDiffUtil(currentArticleList, newArticleList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        currentArticleList = newArticleList
        diffResults.dispatchUpdatesTo(this)
    }
}