package xyz.savvamirzoyan.allaboutapps.storage

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import xyz.savvamirzoyan.allaboutapps.storage.dao.GenericClubInfoDao
import javax.inject.Singleton

@Module
abstract class StorageModule {

    companion object {
        @Singleton
        @Provides
        fun provideAppDatabase(context: Context): AppDatabase = Room
            .databaseBuilder(context, AppDatabase::class.java, "main")
            .fallbackToDestructiveMigration()
            .build()

        @Singleton
        @Provides
        fun provideGenericClubInfoDao(db: AppDatabase): GenericClubInfoDao = db.genericClubInfoDao
    }
}