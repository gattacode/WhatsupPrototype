package com.example.whatsupprototype.ui.screens.component.News

import NewsArticle
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import coil.compose.AsyncImage
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.whatsupprototype.R
import getNewsData
import kotlinx.coroutines.launch


@Composable
fun NewsSection (){
    NewsSlideMenu(apiKey = "d5bf7b73735f4450bcf9868fd1be17ec")
}
@Composable
fun NewsSlideMenu(apiKey: String) {
    var newsArticles by remember { mutableStateOf<List<NewsArticle>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch {
            newsArticles = getNewsData(apiKey)
        }
    }

    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        newsArticles.take(3).forEach { article ->
            NewsItem(article)
        }
    }
}


@Composable
fun NewsItem(article: NewsArticle) {
    val context = LocalContext.current
    val defaultImage = "https://media4.s-nbcnews.com/i/newscms/2019_01/2705191/nbc-social-default_b6fa4fef0d31ca7e8bc7ff6d117ca9f4.png"
    val imageUrl = if (article.image.isNotEmpty()) article.image else defaultImage


    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(article.url)))
        }
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "News Image",
            modifier = Modifier.height(200.dp).fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Text(
            text = article.title,
            textDecoration = TextDecoration.Underline,
            fontSize = 16.sp
        )
        Text(text = "Écrit par ${article.author}")
    }
}
