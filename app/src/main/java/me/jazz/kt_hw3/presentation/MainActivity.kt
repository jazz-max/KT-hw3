package me.jazz.kt_hw3.presentation

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.DaggerActivity
import kotlinx.android.synthetic.main.activity_main.*
import me.jazz.kt_hw3.R
import me.jazz.kt_hw3.data.*
import me.jazz.kt_hw3.di.ResourceProvider
import me.jazz.kt_hw3.presentation.model.PostsUiConverter
import me.jazz.kt_hw3.presentation.ui.PostsAdapter
import java.util.*
import javax.inject.Inject


class MainActivity : DaggerActivity() {
    @Inject
    lateinit var resourceProvider: ResourceProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val container: RecyclerView = findViewById(R.id.container)
//        val postAdapter = PostAdapter(createListPosts())
        val postAdapter =
            PostsAdapter(PostsUiConverter(resourceProvider).convert(createListPosts()))

        recycler?.apply {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)

        }
        swipe.setOnRefreshListener {
            with(postAdapter) {
                postItems = PostsUiConverter(resourceProvider).convert(createListPosts())
                notifyDataSetChanged()
            }
            swipe.isRefreshing = false
        }
    }


    private fun createListPosts(): MutableList<Post> {
        val now: Long = Date().time / 1000

        return mutableListOf(
            Post(
                3, "Netology", "Welcome to Kotlin Course!", now - 10,
                comments = PostOptions(R.drawable.ic_mode_comment_24dp, 1, true, isCanChange = true)
            ),
            Post(
                1, "Netology", "First it in our network!", now - 3600,
                shares = PostOptions(R.drawable.ic_share_24dp, 1, true, isCanChange = true)
            ),
            Post(
                2, "Netology", "Our network is growing!", now - 180,
                likes = PostOptions(R.drawable.ic_favorite_24dp, 1, true, isCanChange = true)
            ),
            Post(
                4, "Netology", "Приглашаем на первую встречу!", now - 180,
                postType = PostType.EVENT,
                location = PostLocation("Дворцовая пл., Санкт-Петербург, 191186", null, null)
            ),
            Post(
                5, "Netology", "Запись вебинара по теме Коллекции", now - 7200,
                attachments = listOf(
                    PostAttache(
                        AttacheType.VIDEO,
                        "https://videos-bb5ddb7a.cdn.integros.com/videos/9p3CK2QYDhac5wPRmFhepQ/mp4/720.mp4"
                    )
                )
            ),
            Post(
                6, "Netology", "Весна – открытый фильм Blender", now - 3600 * 5,
                attachments = listOf(
                    PostAttache(
                        AttacheType.VIDEO,
                        "https://www.youtube.com/watch?v=WhWc3b3KhnY"
                    )
                )
            ),
            Post(
                7, "Netology", "Анонсы событий и промокоды в декабре", now - 3600 * 50,
                postType = PostType.ADS,
                attachments = listOf(
                    PostAttache(
                        AttacheType.IMG,
                        "https://netology.ru/content/i6/6896.jpg"
                    ),
                    PostAttache(
                        AttacheType.LINK,
                        "Поробнее ...|https://netology.ru/blog/12-2019-sobytya-i-promokody-v-dekabre"
                    )
                )
            )
        )
    }
}


