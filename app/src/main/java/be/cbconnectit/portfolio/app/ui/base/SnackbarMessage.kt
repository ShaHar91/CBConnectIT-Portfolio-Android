package be.cbconnectit.portfolio.app.ui.base

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import java.io.Serializable

open class SnackbarMessage(
    open val message: String? = null,
    @StringRes open val messageRes: Int? = null,
    @DrawableRes open val iconRes: Int? = null,
    @ColorRes open val containerColorRes: Int? = null,
    open vararg val formatParams: Any?
) : Serializable {

    fun getMessage(context: Context) = when {
        message != null -> message
        messageRes != null -> context.getString(messageRes!!, *formatParams)
        else -> null
    }
}

object Empty : SnackbarMessage()
