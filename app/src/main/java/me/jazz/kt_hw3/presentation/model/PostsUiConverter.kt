package me.jazz.kt_hw3.presentation.model

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import me.jazz.kt_hw3.data.Post
import me.jazz.kt_hw3.di.ResourceProvider
import javax.inject.Inject

class PostsUiConverter @Inject constructor(private val resourceProvider: ResourceProvider) {
    fun convert(posts: List<Post>): List<ViewModel> {

        val commentsUiModels = posts.map {
            PostUiModel(it)
        }

        return ArrayList<ViewModel>().apply {
            addAll(commentsUiModels)
        }
    }

    fun convert(comment: Post): ViewModel = PostUiModel(comment)
}