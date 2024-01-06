import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException


suspend fun getNewsData(apiKey: String): List<NewsArticle> {
    return withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.worldnewsapi.com/search-news?source-countries=fr&language=fr&text=actualités&api-key=$apiKey")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val body = response.body?.string()
            body?.let {
                val gson = Gson()
                val jsonObject = gson.fromJson(it, JsonObject::class.java)
                val newsArray = jsonObject.getAsJsonArray("news")
                val newsType = object : TypeToken<List<NewsArticle>>() {}.type
                gson.fromJson(newsArray, newsType)
            } ?: emptyList()
        }
    }
}

data class NewsArticle(
    val title: String,
    val url: String,
    val image: String,
    val author: String
)