package xyz.savvamirzoyan.allaboutapps.features.clubslist

import xyz.savvamirzoyan.allaaboutapps.core.Model
import xyz.savvamirzoyan.allaaboutapps.core.PictureURL
import xyz.savvamirzoyan.allaboutapps.model.TextValue

data class GenericClubInfoListItemUi(
    val id: String,
    val pictureURL: PictureURL,
    val title: TextValue,
    val country: TextValue,
    val value: TextValue,
) : Model.UI