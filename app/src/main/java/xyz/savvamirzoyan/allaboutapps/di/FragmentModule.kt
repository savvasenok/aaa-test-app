package xyz.savvamirzoyan.allaboutapps.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import xyz.savvamirzoyan.allaboutapps.features.main.MainFragment

@Module
interface FragmentModule {

    @PerFragment
    @ContributesAndroidInjector
    fun provideMainFragment(): MainFragment
}
