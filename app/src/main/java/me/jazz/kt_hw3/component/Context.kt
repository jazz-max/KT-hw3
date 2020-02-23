package me.jazz.kt_hw3.component

import android.content.Context
import androidx.annotation.DimenRes

@Suppress("NOTHING_TO_INLINE")
inline fun Context.getDimensionPixelSize(@DimenRes id: Int) = resources.getDimensionPixelSize(id)