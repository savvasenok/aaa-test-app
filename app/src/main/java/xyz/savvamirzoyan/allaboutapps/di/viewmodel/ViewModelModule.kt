package xyz.savvamirzoyan.allaboutapps.di.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoMap
import dagger.multibindings.Multibinds
import xyz.savvamirzoyan.allaboutapps.features.clubslist.ClubsListViewModel
import javax.inject.Provider

@Module(subcomponents = [ViewModelComponent::class])
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ClubsListViewModel::class)
    abstract fun bindMainViewModel(viewModel: ClubsListViewModel): ViewModel

    @Multibinds
    abstract fun viewModels(): Map<Class<out ViewModel>, @JvmSuppressWildcards ViewModel>
}

@Subcomponent
interface ViewModelComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance savedStateHandle: SavedStateHandle): ViewModelComponent
    }

    fun viewModelProviders(): Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
}
