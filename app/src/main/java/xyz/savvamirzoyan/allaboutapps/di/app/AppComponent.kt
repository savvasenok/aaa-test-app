package xyz.savvamirzoyan.allaboutapps.di.app

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import xyz.savvamirzoyan.allaboutapps.application.App
import xyz.savvamirzoyan.allaboutapps.di.ActivityModule
import xyz.savvamirzoyan.allaboutapps.di.FirebaseModule
import xyz.savvamirzoyan.allaboutapps.di.FragmentModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        FragmentModule::class,

        FirebaseModule::class,

    ],
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Factory<App> {

        /**
         * Create an AppComponent with a bound Application.
         */
        override fun create(app: App): AppComponent {
            seedInstance(app)
            application(app)
            return build()
        }

        @BindsInstance
        abstract fun application(application: Application): Builder

        @BindsInstance
        abstract fun seedInstance(application: App): Builder

        abstract fun build(): AppComponent
    }
}
