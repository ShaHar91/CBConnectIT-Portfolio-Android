package be.christiano.portfolio.app.utils

import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.ImageView
import androidx.annotation.AttrRes
import androidx.databinding.BindingAdapter
import com.google.android.material.color.MaterialColors

@BindingAdapter("tintAttr")
fun setTintAttr(view: ImageView, @AttrRes color: Int) {
    view.imageTintList = MaterialColors.getColorStateList(view.context, color, ColorStateList.valueOf(Color.BLACK))
}