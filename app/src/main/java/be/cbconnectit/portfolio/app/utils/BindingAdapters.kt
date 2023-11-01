package be.cbconnectit.portfolio.app.utils

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.AttrRes
import androidx.core.view.updateLayoutParams
import androidx.databinding.BindingAdapter
import com.google.android.material.color.MaterialColors

@BindingAdapter("tintAttr")
fun setTintAttr(view: ImageView, @AttrRes color: Int) {
    view.imageTintList = MaterialColors.getColorStateList(view.context, color, ColorStateList.valueOf(Color.BLACK))
}

@BindingAdapter("android:layout_marginStart")
fun setMarginStart(view: View, dimen: Float) {
    view.updateLayoutParams {
        (this as? ViewGroup.MarginLayoutParams)?.setMargins(dimen.toInt(), 0, 0, 0)
    }
}

@BindingAdapter("android:layout_marginEnd")
fun setMarginEnd(view: View, dimen: Float) {
    view.updateLayoutParams {
        (this as? ViewGroup.MarginLayoutParams)?.setMargins(0, 0, dimen.toInt(), 0)
    }
}

@BindingAdapter("android:layout_alignParentEnd")
fun setAlignParentEnd(view: View, alignParentEnd: Boolean) {
    val layoutParams = view.layoutParams as RelativeLayout.LayoutParams
    if (alignParentEnd) {
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END)
    }
    view.layoutParams = layoutParams
}