package xyz.savvamirzoyan.allaboutapps.data

import dagger.Binds
import dagger.Module
import xyz.savvamirzoyan.allaboutapps.data.repository.ClubsRepositoryImpl
import xyz.savvamirzoyan.allaboutapps.domain.contract.IClubsRepository
import javax.inject.Singleton

@Module
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun bindsIClubsRepository(impl: ClubsRepositoryImpl): IClubsRepository
}