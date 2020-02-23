package me.jazz.kt_hw3.component

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Html
import android.text.Spanned
import android.util.TypedValue
import me.jazz.kt_hw3.di.ResourceProvider
import java.io.InputStream
import javax.inject.Inject

class ResourceProviderImpl @Inject constructor(
    private val context: Context
) : ResourceProvider {
    override fun getDimensionPixelSize(dimenId: Int): Int =
        context.getDimensionPixelSize(dimenId)

    override fun getColor(colorId: Int): Int =
        context.getColor(colorId)

    override fun getAttr(attrId: Int): TypedValue {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(attrId, typedValue, true)
        return typedValue
    }

    override fun getDrawable(drawableId: Int): Drawable =
        context.resources.getDrawable(drawableId, context.theme)

    override fun getHtml(stringId: Int, vararg args: Any): Spanned {
        return Html.fromHtml(context.getString(stringId, *args), Html.FROM_HTML_MODE_LEGACY)
    }

    override fun getString(stringId: Int, vararg args: Any): String =
        context.getString(stringId, *args)

    override fun getQuantityString(pluralsId: Int, quantity: Int, vararg args: Any): String =
        context.resources.getQuantityString(pluralsId, quantity, *args)

    override fun getRawResInputStream(rawResId: Int): InputStream =
        context.resources.openRawResource(rawResId)
}