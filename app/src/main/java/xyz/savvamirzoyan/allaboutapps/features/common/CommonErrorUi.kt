package xyz.savvamirzoyan.allaboutapps.features.common

import xyz.savvamirzoyan.allaaboutapps.core.Model
import xyz.savvamirzoyan.allaboutapps.model.TextValue

data class CommonErrorUi(
    val title: TextValue,
    val message: TextValue,
) : Model.UI