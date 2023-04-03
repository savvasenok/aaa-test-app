package xyz.savvamirzoyan.allaboutapps.storage.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import xyz.savvamirzoyan.allaaboutapps.core.Model

@Entity(tableName = "generic_club_info")
data class GenericClubInfoLocal(
    val country: String, // Deutschland
    @ColumnInfo(name = "european_titles")
    val europeanTitles: Int, // 3
    val image: String, // https://www.link.com
    val name: String, // Bayern München
    val value: Int, // 539
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
) : Model.Data.Local
