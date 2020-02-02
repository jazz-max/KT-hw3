package me.jazz.kt_hw3.data

class Post(
    val id : Int,
    val ownerId : Int,
    val fromId : Int,
    val date :Long,
    val text :String,
    val comments : PostOptions?,
    val likes : PostOptions?,
    val shares : PostOptions?,
    val postType : String?
)