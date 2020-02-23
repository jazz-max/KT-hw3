package me.jazz.kt_hw3.presentation.model

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import me.jazz.kt_hw3.R
import me.jazz.kt_hw3.data.*

data class PostUiModel(
    val id: Int,
    val authorName: String,
    val content: String,
    val dateCreated: Long,
    var comments: PostOptions = PostOptions(R.drawable.ic_mode_comment_24dp),
    var likes: PostOptions = PostOptions(R.drawable.ic_favorite_24dp),
    var shares: PostOptions = PostOptions(R.drawable.ic_share_24dp),
    val postType: PostType = PostType.POST,
    val source: Post? = null,
    val location: PostLocation? = null,
    val attachments: List<PostAttache>? = null
) : ViewModel {
    constructor(post: Post) : this(
        post.id,
        post.authorName,
        post.content,
        post.dateCreated,
        post.comments,
        post.likes,
        post.shares,
        post.postType,
        post.source,
        post.location,
        post.attachments
    )
}