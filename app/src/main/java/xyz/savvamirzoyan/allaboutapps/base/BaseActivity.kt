package xyz.savvamirzoyan.allaboutapps.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import xyz.savvamirzoyan.allaboutapps.di.Injectable
import xyz.savvamirzoyan.allaboutapps.di.viewmodel.ViewModelFactory
import javax.inject.Inject

/**
 * Base class to use for this application
 */
abstract class BaseActivity : AppCompatActivity(), Injectable, HasAndroidInjector {

    @Inject
    lateinit var viewModelFactoryFactory: ViewModelFactory.Factory

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    /**
     * Request a ViewModel, scoped to this Activity, from the injected factory.
     * @see ViewModelFactory
     */
    inline fun <reified T : ViewModel> viewModel() = ViewModelProvider(
        this,
        viewModelFactoryFactory.create(this, intent.extras),
    ).get(T::class.java)
}
