package me.jazz.kt_hw3.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.posts_list_item.view.*
import me.jazz.kt_hw3.R
import me.jazz.kt_hw3.data.Post
import me.jazz.kt_hw3.data.PostOptions
import me.jazz.kt_hw3.data.PostType
import me.jazz.kt_hw3.data.pluralTimeAgo
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
                location = Triple("Дворцовая пл., Санкт-Петербург, 191186", null, null)
            ),
            Post(
                5, "Netology", "Запись вебинара по теме Коллекции", now - 7200,
                attachments = listOf(
                    Pair(
                        "video",
                        "https://videos-bb5ddb7a.cdn.integros.com/videos/9p3CK2QYDhac5wPRmFhepQ/mp4/720.mp4"
                    )
                )
            ),
            Post(
                6, "Netology", "Весна – открытый фильм Blender", now - 3600 * 5,
                attachments = listOf(
                    Pair(
                        "video",
                        "https://www.youtube.com/watch?v=WhWc3b3KhnY"
                    )
                )
            ),
            Post(
                7, "Netology", "Анонсы событий и промокоды в декабре", now - 3600 * 50,
                postType = PostType.ADS,
                attachments = listOf(
                    Pair(
                        "img",
                        "https://netology.ru/content/i6/6896.jpg"
                    ),
                    Pair(
                        "link",
                        "Поробнее ...|https://netology.ru/blog/12-2019-sobytya-i-promokody-v-dekabre"
                    )
                )
            )
        )
    }

    private fun initList(
        list: List<Post>,
        root: LinearLayout
    ) {
        val now: Long = Date().time / 1000
        list.forEach { post ->
            val view = layoutInflater.inflate(R.layout.posts_list_item, root, false).apply {
                txtPostContent.text = post.content
                txtAuthor.text = post.authorName
                txtDateCreated.text = pluralTimeAgo(now - post.dateCreated)


                val imgLike = imgLikes
                val txtLike = txtLikes
                postOptions2UI(post.likes, imgLikes, txtLikes)

                imgLikes.setOnClickListener { _: View ->
                    if (post.likes.selected) {
                        post.likes.count -= 1
                        post.likes.selected = false
                    } else {
                        post.likes.count += 1
                        post.likes.selected = true
                    }
                    postOptions2UI(post.likes, imgLike, txtLike)
                }
                val imgS = imgShare
                val txtS = txtShare
                postOptions2UI(post.shares, imgS, txtS)
                imgShare.setOnClickListener { _: View ->
                    post.shares.count++
                    post.shares.selected = true

                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT, """
                                     ${post.authorName} (${txtDateCreated.text})
                                     ${post.content} """.trimIndent()
                        )
                        type = "text/plain"
                    }
                    startActivity(intent)
                    postOptions2UI(post.shares, imgS, txtS)

                }

                postOptions2UI(post.comments, imgComments, txtComments)

                if (post.postType == PostType.EVENT && post.location != null) {
                    val (address, lat, lng) = post.location
                    imgLocation.visibility = View.VISIBLE
                    txtLocation.visibility = View.VISIBLE
                    txtLocation.text = address ?: ""

                    imgLocation.setOnClickListener {
                        openGeoAction(lat, lng, address)
                    }
                    txtLocation.setOnClickListener {
                        openGeoAction(lat, lng, address)
                    }
                } else {
                    imgLocation.visibility = View.GONE
                    txtLocation.visibility = View.GONE
                }

                if (post.attachments != null) {
                    val container = containerAttachements
                    post.attachments.forEach {
                        val (type, url) = it
                        if (type == "video") {
                            val splash: View =
                                layoutInflater.inflate(R.layout.video_dummy, container, false)

                            splash.setOnClickListener {
                                val intent = Intent().apply {
                                    action = Intent.ACTION_VIEW
                                    data = Uri.parse(url)
                                }
                                startActivity(intent)
                            }

                            container.addView(splash)
                        }
                    }
                }
            }
            root.addView(view)
        }
    }

    private fun openGeoAction(lat: Double?, lng: Double?, address: String?) {
        val intent = Intent().apply {
            action = Intent.ACTION_VIEW

            if (lat != null && lng != null)
                data = Uri.parse("geo:$lat,$lng")
            else if (address != null)
                data = Uri.parse("geo:0,0?q=$address")
        }
        startActivity(intent)
    }

    fun postOptions2UI(opt: PostOptions, img: ImageView, txt: TextView) {
        // сначала все по дефолту
        txt.visibility = View.GONE


        val colorAccent = ContextCompat.getColor(this, R.color.colorAccent)
        val colorDefault = ContextCompat.getColor(this, R.color.colorData)
        img.drawable.mutate()
        img.drawable.setTint(colorDefault)

        // если опция есть - отображаем
        if (opt.selected) {
            img.drawable.setTint(colorAccent)
        }

        if (opt.count > 0) txt.visibility = View.VISIBLE

        txt.text = opt.count.toString()

    }
}


