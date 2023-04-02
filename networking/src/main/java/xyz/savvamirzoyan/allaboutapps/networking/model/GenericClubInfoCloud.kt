package xyz.savvamirzoyan.allaboutapps.networking.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import xyz.savvamirzoyan.allaaboutapps.core.Model

// Its said, that app would be reused for different markets, I suppose it still meant football
@JsonClass(generateAdapter = true)
data class GenericClubInfoCloud(
    val country: String, // Deutschland
    @Json(name = "european_titles")
    val europeanTitles: Int, // 3
    val image: String, // https://www.link.com
    val name: String, // Bayern München
    val value: Int, // 539
) : Model.Data.Cloud