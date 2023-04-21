package xyz.savvamirzoyan.allaboutapps.networking.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import xyz.savvamirzoyan.allaaboutapps.core.Model
import xyz.savvamirzoyan.allaaboutapps.core.PictureURL

// 1. Its said, that app would be reused for different markets, I suppose it still meant football
// 2. Its not shown in the project file, but API endpoint actually provides IDs for clubs, what you probably already knew
@JsonClass(generateAdapter = true)
data class GenericClubInfoCloud(
    val id: String,
    val country: String, // Deutschland
    @Json(name = "european_titles")
    val europeanTitles: Int, // 3
    @Json(name = "image")
    val pictureURL: PictureURL, // https://www.link.com
    val name: String, // Bayern München
    val value: Int, // 539
) : Model.Data.Cloud