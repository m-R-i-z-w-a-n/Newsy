package app.entertainment.newsy.utils

import androidx.recyclerview.widget.DiffUtil
import app.entertainment.newsy.models.Article

class MyDiffUtil(
    private val oldArticleList: List<Article>,
    private val newArticleList: List<Article>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldArticleList.size
    }

    override fun getNewListSize(): Int {
        return newArticleList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldArticleList[oldItemPosition].url == newArticleList[newItemPosition].url
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldArticleList[oldItemPosition].url != newArticleList[newItemPosition].url -> {
                false
            }

            oldArticleList[oldItemPosition].author != newArticleList[newItemPosition].author -> {
                false
            }

            oldArticleList[oldItemPosition].title != newArticleList[newItemPosition].title -> {
                false
            }

            oldArticleList[oldItemPosition].content != newArticleList[newItemPosition].content -> {
                false
            }

            oldArticleList[oldItemPosition].description != newArticleList[newItemPosition].description -> {
                false
            }

            oldArticleList[oldItemPosition].source != newArticleList[newItemPosition].source -> {
                false
            }

            oldArticleList[oldItemPosition].publishedAt != newArticleList[newItemPosition].publishedAt -> {
                false
            }

            oldArticleList[oldItemPosition].urlToImage != newArticleList[newItemPosition].urlToImage -> {
                false
            }

            else -> true
        }
    }
}