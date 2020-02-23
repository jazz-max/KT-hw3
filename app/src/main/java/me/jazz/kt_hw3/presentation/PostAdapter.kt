package me.jazz.kt_hw3.presentation

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.posts_list_item.view.*
import me.jazz.kt_hw3.R
import me.jazz.kt_hw3.data.*
import java.util.*

class PostAdapter(
    var list: MutableList<Post>
) : RecyclerView.Adapter<PostViewHolder>() {

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_card, parent, false)
        return PostViewHolder(this, view)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class PostViewHolder(adapter: PostAdapter, view: View) : RecyclerView.ViewHolder(view) {
    init {
        with(itemView) {

            /*imgLikes.setOnClickListener {
                val post = adapter.list[adapterPosition]
                if (post.likes.selected) {
                    post.likes.count -= 1
                    post.likes.selected = false
                } else {
                    post.likes.count += 1
                    post.likes.selected = true
                }
                adapter.notifyItemChanged(adapterPosition)
            }

            imgShare.setOnClickListener {
                val post = adapter.list[adapterPosition]
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
                adapter.notifyItemChanged(adapterPosition)

                context.startActivity(intent)
            }

            imgClose.setOnClickListener {
                adapter.apply {
                    list.removeAt(adapterPosition)
                    notifyDataSetChanged()
                }
            }*/
        }
    }

    private fun postOptions2UI(opt: PostOptions, img: ImageView, txt: TextView) {
        // сначала все по дефолту
        txt.visibility = View.GONE


        val colorAccent = ContextCompat.getColor(itemView.context, R.color.colorAccent)
        val colorDefault = ContextCompat.getColor(itemView.context, R.color.colorData)
        img.drawable.mutate()
        img.drawable.setTint(colorDefault)

        // если опция есть - отображаем
        if (opt.selected) {
            img.drawable.setTint(colorAccent)
        }

        if (opt.count > 0) txt.visibility = View.VISIBLE

        txt.text = opt.count.toString()

    }

    private fun openGeoAction(lat: Double?, lng: Double?, address: String?, context: Context) {
        val intent = Intent().apply {
            action = Intent.ACTION_VIEW

            if (lat != null && lng != null)
                data = Uri.parse("geo:$lat,$lng")
            else if (address != null)
                data = Uri.parse("geo:0,0?q=$address")
        }
        context.startActivity(intent)
    }

    fun bind(post: Post) {
        val now: Long = Date().time / 1000

        with(itemView) {
            txtPostContent.text = post.content
            txtAuthor.text = post.authorName
            txtDateCreated.text = pluralTimeAgo(now - post.dateCreated)
            /*  postOptions2UI(post.likes, imgLikes, txtLikes)
              postOptions2UI(post.shares, imgShare, txtShare)
              postOptions2UI(post.comments, imgComments, txtComments)

              // событие с местом
              if (post.postType == PostType.EVENT && post.location != null) {
                  val (address, lat, lng) = post.location
                  imgLocation.visibility = View.VISIBLE
                  txtLocation.visibility = View.VISIBLE
                  txtLocation.text = address ?: ""

                  imgLocation.setOnClickListener {
                      openGeoAction(lat, lng, address, context)
                  }
                  txtLocation.setOnClickListener {
                      openGeoAction(lat, lng, address, context)
                  }
              } else {
                  imgLocation.visibility = View.GONE
                  txtLocation.visibility = View.GONE
              }*/
            // вложения
            val container = recyclerAttachements
            container.removeAllViews() // вычищаем все, что было до рендеринга

            post.attachments?.forEach {
                val (type, url) = it
                when (type) {
                    AttacheType.VIDEO -> {
                        val splash: View =
                            LayoutInflater.from(itemView.context)
                                .inflate(R.layout.video_dummy, container, false)

                        splash.setOnClickListener {
                            val intent = Intent().apply {
                                action = Intent.ACTION_VIEW
                                data = Uri.parse(url)
                            }
                            context.startActivity(intent)
                        }
                        container.addView(splash)
                    }
                    AttacheType.LINK -> {
                        val (text, uri) = url.split("|")
                        val link: TextView = TextView(container.context).apply {
                            this.text = text
                            setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                            setTypeface(typeface, Typeface.BOLD_ITALIC)
                        }

                        link.setOnClickListener {
                            val intent = Intent().apply {
                                action = Intent.ACTION_VIEW
                                data = Uri.parse(uri)
                            }
                            context.startActivity(intent)
                        }
                        container.addView(link)
                    }
                    AttacheType.IMG -> {
                        val img = ImageView(container.context).apply {

                        }
                        Picasso.get().load(url).into(img)
                        container.addView(img)
                        img.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
//                            img.layoutParams.height = 0
                        img.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
                        img.scaleType = ImageView.ScaleType.FIT_CENTER
                    }
                }
            }

            if (post.postType == PostType.ADS) {
                setBackgroundColor(ContextCompat.getColor(context, R.color.colorAdsBk))
            } else {
                setBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
            }
        }

    }
}