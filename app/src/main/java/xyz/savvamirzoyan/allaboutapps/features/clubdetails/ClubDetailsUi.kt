package xyz.savvamirzoyan.allaboutapps.features.clubdetails

import androidx.annotation.DrawableRes
import xyz.savvamirzoyan.allaaboutapps.core.Model
import xyz.savvamirzoyan.allaaboutapps.core.PictureURL
import xyz.savvamirzoyan.allaboutapps.model.TextValue

sealed interface ClubDetailsUi : Model.UI {

    data class Success(
        val pictureURL: PictureURL,
        val clubTitle: TextValue,
        val country: TextValue,
        val description: TextValue,
        val stringToMakeBoldInDescription: TextValue,
    ) : ClubDetailsUi

    data class Failure(
        @DrawableRes val iconId: Int,
        val errorText: TextValue,
    ) : ClubDetailsUi
}