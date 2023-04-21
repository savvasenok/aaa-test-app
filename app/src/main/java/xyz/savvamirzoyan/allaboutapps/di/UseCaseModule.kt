package xyz.savvamirzoyan.allaboutapps.di

import dagger.Binds
import dagger.Module
import xyz.savvamirzoyan.allaboutapps.domain.usecase.GetAllClubsUseCase
import xyz.savvamirzoyan.allaboutapps.domain.usecase.GetClubUseCase
import xyz.savvamirzoyan.allaboutapps.domain.usecase.SortClubsUseCase
import javax.inject.Singleton

@Module
abstract class UseCaseModule {

    @Singleton
    @Binds
    abstract fun bindGetAllClubsUseCase(base: GetAllClubsUseCase.Base): GetAllClubsUseCase

    @Singleton
    @Binds
    abstract fun bindSortClubsUseCase(base: SortClubsUseCase.Base): SortClubsUseCase

    @Singleton
    @Binds
    abstract fun bindGetClubUseCase(base: GetClubUseCase.Base): GetClubUseCase
}