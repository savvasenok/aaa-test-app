package xyz.savvamirzoyan.allaboutapps.utils.ext

import android.widget.ImageView
import androidx.core.view.isVisible
import com.google.android.material.textview.MaterialTextView
import xyz.savvamirzoyan.allaaboutapps.core.PictureURL
import xyz.savvamirzoyan.allaboutapps.glide.GlideApp
import xyz.savvamirzoyan.allaboutapps.model.TextValue

fun ImageView.load(pictureURL: PictureURL) = GlideApp.with(this)
    .load(pictureURL)
    .into(this)

fun MaterialTextView.setText(textValue: TextValue?) {
    if (textValue != null) {
        val value = textValue.getString(this.context)
        isVisible = value.isNotBlank()
        text = value
    } else isVisible = false
}