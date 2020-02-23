package me.jazz.kt_hw3.data

import androidx.annotation.DrawableRes

data class PostOptions(
    @DrawableRes val drawableRes: Int,
    var count: Int = 0,
    var selected: Boolean = false,
    var isCanChange: Boolean = true
)

