package xyz.savvamirzoyan.allaboutapps.model

import androidx.annotation.DrawableRes

data class ButtonState(
    val text: TextValue,
    @DrawableRes val iconId: Int? = null,
    val isVisible: Boolean = true,
    val isEnabled: Boolean = true
)