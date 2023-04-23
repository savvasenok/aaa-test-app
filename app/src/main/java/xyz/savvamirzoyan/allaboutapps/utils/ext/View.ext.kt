package xyz.savvamirzoyan.allaboutapps.utils.ext

import android.widget.ImageView
import androidx.core.view.isVisible
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import xyz.savvamirzoyan.allaaboutapps.core.PictureURL
import xyz.savvamirzoyan.allaboutapps.glide.GlideApp
import xyz.savvamirzoyan.allaboutapps.model.ButtonState
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

fun MaterialToolbar.setTitle(textValue: TextValue?) {
    title = textValue?.getString(this.context) ?: ""
}

fun MaterialButton.setText(textValue: TextValue?) {
    text = textValue?.getString(context)
}

fun MaterialButton.setState(state: ButtonState) {
    isVisible = state.isVisible
    isEnabled = state.isEnabled
    setText(state.text)
    setIconResource(state.iconId ?: 0)
}