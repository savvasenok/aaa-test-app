package xyz.savvamirzoyan.allaboutapps.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import xyz.savvamirzoyan.allaboutapps.storage.model.GenericClubInfoLocal

@Dao
interface GenericClubInfoDao : BaseDao<GenericClubInfoLocal> {

    @Query("SELECT * FROM generic_club_info")
    suspend fun selectAll(): List<GenericClubInfoLocal>

    @Query("SELECT * FROM generic_club_info WHERE id = :id")
    suspend fun select(id: String): GenericClubInfoLocal?

    @Query("SELECT * FROM generic_club_info")
    fun selectAllFlow(): Flow<List<GenericClubInfoLocal>>

    @Query("DELETE FROM generic_club_info")
    suspend fun nukeTable()
}