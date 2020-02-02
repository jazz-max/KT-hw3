package me.jazz.kt_hw3.data

class Post(
    val id : Int,
    val ownerId : Int,
    val fromId : Int,
    val date :Long,
    val text :String,
    val comments : Int,
    val likes : Int,
    val rposts : String,
    val postType : String
)