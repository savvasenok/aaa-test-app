package xyz.savvamirzoyan.allaboutapps.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import xyz.savvamirzoyan.allaboutapps.features.start.MainActivity

@Module
interface ActivityModule {

    @PerActivity
    @ContributesAndroidInjector
    fun provideMainActivity(): MainActivity
}
