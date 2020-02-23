package me.jazz.kt_hw3.presentation.ui

import android.widget.TextView
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import me.jazz.kt_hw3.R
import me.jazz.kt_hw3.data.PostOptions
import me.jazz.kt_hw3.data.pluralTimeAgo
import me.jazz.kt_hw3.di.ResourceProvider
import me.jazz.kt_hw3.presentation.model.PostUiModel
import java.util.*
import javax.inject.Inject

data class PostsAdapter(var postItems: List<ViewModel>) : RendererRecyclerViewAdapter(),
    HasAndroidInjector {

    @Inject
    lateinit var resourceProvider: ResourceProvider

    lateinit var androidInjector: DispatchingAndroidInjector<Any>


    init {
        setItems(postItems)

        registerRenderer( // POST
            ViewBinder(
                R.layout.posts_list_item,
                PostUiModel::class.java,
                ViewBinder.Binder<PostUiModel> { post, finder, payloads ->
                    val now: Long = Date().time / 1000
                    finder.apply {
                        find<TextView>(R.id.txtPostContent).text = post.content
                        find<TextView>(R.id.txtAuthor).text = post.authorName
                        find<TextView>(R.id.txtDateCreated).text =
                            pluralTimeAgo(now - post.dateCreated)
                        find<TextView>(R.id.txtDateCreated).text =
                            pluralTimeAgo(now - post.dateCreated)
                        find<TextView>(R.id.txtLikes).also {
                            postOptions2UI(post.likes, it)
                            it.setOnClickListener {
                                post.likes.count += if (post.likes.selected) -1 else 1
                                post.likes.selected = !post.likes.selected
                            }
                        }
                        find<TextView>(R.id.txtShare).also {
                            postOptions2UI(post.likes, it)
                            it.setOnClickListener {
                                post.shares.count += if (post.shares.selected) -1 else 1
                                post.shares.selected = !post.shares.selected
                            }
                        }
                        find<TextView>(R.id.txtComments).also {
                            postOptions2UI(post.likes, it)
                            it.setOnClickListener {
                                post.comments.count += if (post.comments.selected) -1 else 1
                                post.comments.selected = !post.comments.selected
                            }
                        }
                    }
                })
        )
    }

    private fun postOptions2UI(opt: PostOptions, targetView: TextView) {

        val colorAccent = resourceProvider.getColor(R.color.colorAccent)
        val colorDefault = resourceProvider.getColor(R.color.colorData)
        val drawable = resourceProvider.getDrawable(opt.drawableRes)

        drawable.setTint(if (opt.selected) colorAccent else colorDefault)
        targetView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
        targetView.text = if (opt.count > 0) opt.count.toString() else ""

    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
}