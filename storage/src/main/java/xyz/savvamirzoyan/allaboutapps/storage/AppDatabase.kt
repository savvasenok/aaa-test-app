package xyz.savvamirzoyan.allaboutapps.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import xyz.savvamirzoyan.allaboutapps.storage.dao.GenericClubInfoDao
import xyz.savvamirzoyan.allaboutapps.storage.model.GenericClubInfoLocal

@Database(
    entities = [GenericClubInfoLocal::class],
    exportSchema = true,
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract val genericClubInfoDao: GenericClubInfoDao
}