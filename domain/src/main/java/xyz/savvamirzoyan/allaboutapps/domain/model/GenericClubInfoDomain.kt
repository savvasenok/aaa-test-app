package xyz.savvamirzoyan.allaboutapps.domain.model

import xyz.savvamirzoyan.allaaboutapps.core.Model
import xyz.savvamirzoyan.allaaboutapps.core.PictureURL

data class GenericClubInfoDomain(
    val id: String,
    val country: String,
    val europeanTitles: Int,
    val pictureURL: PictureURL,
    val name: String,
    val value: Int,
) : Model.Domain