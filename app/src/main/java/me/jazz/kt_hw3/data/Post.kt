package me.jazz.kt_hw3.data

import me.jazz.kt_hw3.R


data class Post(
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
)