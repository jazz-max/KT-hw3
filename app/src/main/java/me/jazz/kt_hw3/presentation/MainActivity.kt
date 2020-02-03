package me.jazz.kt_hw3.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.posts_list_item.view.*
import me.jazz.kt_hw3.R
import me.jazz.kt_hw3.data.Post
import me.jazz.kt_hw3.data.PostOptions
import me.jazz.kt_hw3.data.pluralTimeAgo
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val root: LinearLayout = findViewById(R.id.container)

        val now: Long = Date().time / 1000

        val list = listOf(
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
            )
        )
        list.forEach {
            val view = layoutInflater.inflate(R.layout.posts_list_item, root, false).apply {
                tag = it
                txtPostContent.text = it.content
                val img = imgLikes
                val txt = txtLikes
                postOptions2UI(it.likes, imgLikes, txtLikes)

                imgLikes.setOnClickListener { v : View ->
                    if (it.likes.selected){
                        it.likes.count -=1
                        it.likes.selected = false
                    } else {
                        it.likes.count +=1
                        it.likes.selected = true
                    }
                    postOptions2UI(it.likes, img, txt)
                }

                postOptions2UI(it.comments, imgComments, txtComments)
                postOptions2UI(it.shares, imgShare, txtShare)

                txtAuthor.text = it.authorName
                txtDateCreated.text = pluralTimeAgo(now - it.dateCreated)

            }
            root.addView(view)
        }
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


