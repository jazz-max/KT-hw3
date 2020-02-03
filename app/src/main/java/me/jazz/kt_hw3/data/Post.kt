package me.jazz.kt_hw3.data

data class Post(
    val id : Int,
    val authorName : String,
    val content :String,
    val dateCreated :Long,
    var comments : PostOptions = PostOptions(),
    var likes : PostOptions = PostOptions(),
    var shares : PostOptions = PostOptions(),
    val postType : PostType = PostType.POST,
    val source : Post? = null
)