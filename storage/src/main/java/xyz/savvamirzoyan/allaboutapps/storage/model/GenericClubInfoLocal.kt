package xyz.savvamirzoyan.allaboutapps.storage.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import xyz.savvamirzoyan.allaaboutapps.core.Model
import xyz.savvamirzoyan.allaaboutapps.core.PictureURL

@Entity(tableName = "generic_club_info")
data class GenericClubInfoLocal(
    @PrimaryKey(autoGenerate = false) val id: String,
    val country: String, // Deutschland
    @ColumnInfo(name = "european_titles")
    val europeanTitles: Int, // 3
    val pictureURL: PictureURL, // https://www.link.com
    val name: String, // Bayern München
    val value: Int, // 539
) : Model.Data.Local
