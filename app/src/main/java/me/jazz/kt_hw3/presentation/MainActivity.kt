package me.jazz.kt_hw3.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import me.jazz.kt_hw3.R
import me.jazz.kt_hw3.data.*
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val container: RecyclerView = findViewById(R.id.container)
        val postAdapter = PostAdapter(createListPosts())

        with(container) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = postAdapter
        }
        swipe.setOnRefreshListener {
            with(postAdapter) {
                list = createListPosts()
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
                comments = PostOptions(1, true, isCanChange = true)
            ),
            Post(
                1, "Netology", "First it in our network!", now - 3600,
                shares = PostOptions(1, true, isCanChange = true)
            ),
            Post(
                2, "Netology", "Our network is growing!", now - 180,
                likes = PostOptions(1, true, isCanChange = true)
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


