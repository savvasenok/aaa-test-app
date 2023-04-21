package xyz.savvamirzoyan.allaboutapps.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import xyz.savvamirzoyan.allaboutapps.features.clubdetails.ClubDetailsFragment
import xyz.savvamirzoyan.allaboutapps.features.clubslist.ClubsListFragment

@Module
interface FragmentModule {

    @PerFragment
    @ContributesAndroidInjector
    fun provideClubsListFragment(): ClubsListFragment

    @PerFragment
    @ContributesAndroidInjector
    fun provideClubDetailsFragment(): ClubDetailsFragment
}
