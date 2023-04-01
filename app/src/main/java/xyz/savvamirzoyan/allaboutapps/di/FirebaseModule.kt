package xyz.savvamirzoyan.allaboutapps.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import xyz.savvamirzoyan.allaboutapps.features.fcm.FcmService

@Module
interface FirebaseModule {

    @ContributesAndroidInjector
    fun provideFcmService(): FcmService
}
