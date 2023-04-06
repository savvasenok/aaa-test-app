package xyz.savvamirzoyan.allaboutapps.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import xyz.savvamirzoyan.allaboutapps.storage.model.GenericClubInfoLocal

@Dao
interface GenericClubInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg items: GenericClubInfoLocal)

    @Query("SELECT * FROM generic_club_info")
    suspend fun selectAll(): List<GenericClubInfoLocal>

    @Query("SELECT * FROM generic_club_info WHERE name = :name")
    suspend fun select(name: String): GenericClubInfoLocal?

    @Delete
    suspend fun delete(item: GenericClubInfoLocal)
}