package me.jazz.kt_hw3.di

import android.graphics.drawable.Drawable
import android.text.Spanned
import android.util.TypedValue
import androidx.annotation.*
import java.io.InputStream

interface ResourceProvider {
    fun getString(@StringRes stringId: Int, vararg args: Any): String
    fun getQuantityString(pluralsId: Int, quantity: Int, vararg args: Any): String
    fun getRawResInputStream(@RawRes rawResId: Int): InputStream
    fun getDimensionPixelSize(@DimenRes dimenId: Int): Int
    fun getColor(@ColorRes colorId: Int): Int
    fun getAttr(@AttrRes attrId: Int): TypedValue
    fun getDrawable(@DrawableRes drawableId: Int): Drawable
    fun getHtml(@StringRes stringId: Int, vararg args: Any): Spanned
}