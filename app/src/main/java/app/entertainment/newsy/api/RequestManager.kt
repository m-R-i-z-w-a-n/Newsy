package app.entertainment.newsy.api

import app.entertainment.newsy.models.NewsResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

object RequestManager {
    // The base URL for the JSONPlaceholder API
    private const val BASE_URL = "https://newsapi.org/v2/"

    // OkHttpClient for making HTTP requests with timeouts
    private val client: OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(5, TimeUnit.SECONDS)
        readTimeout(10, TimeUnit.SECONDS)
    }.build()

    // Retrofit instance, lazily initialized when needed
    private var retrofit: Retrofit? = null

    // Property to access the Retrofit instance
    private val retrofitInstance: Retrofit?
        get() {
            if (retrofit == null) {
                // Create a Retrofit instance with the base URL, OkHttpClient, and Gson converter factory
                retrofit = Retrofit.Builder().apply {
                    baseUrl(BASE_URL)
                    client(client)
                    addConverterFactory(GsonConverterFactory.create())
                }.build()
            }
            //  Returns retrofit instance if not null
            return retrofit
        }

    // Property to access the ApiService
    val newsApiService: NewsApiService = retrofitInstance!!.create(NewsApiService::class.java)
}

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("q") query: String? = null
    ): Response<NewsResponse>
}