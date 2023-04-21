package xyz.savvamirzoyan.allaboutapps.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import xyz.savvamirzoyan.allaaboutapps.core.Model

@Dao
interface BaseDao<T : Model.Data.Local> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg items: T)

    @Update
    suspend fun update(item: T)

    @Delete
    suspend fun delete(item: T)
}