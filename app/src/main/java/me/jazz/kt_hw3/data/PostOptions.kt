package me.jazz.kt_hw3.data

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.posts_list_item.*

data class PostOptions(
    var count: Int = 0,
    var selected: Boolean = false,
    var isCanChange: Boolean = true
)

