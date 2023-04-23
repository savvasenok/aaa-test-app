package xyz.savvamirzoyan.allaboutapps.base

import android.widget.Toast.LENGTH_SHORT
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import xyz.savvamirzoyan.allaboutapps.di.Injectable
import xyz.savvamirzoyan.allaboutapps.di.viewmodel.ViewModelFactory
import javax.inject.Inject

/**
 * Base class to use for this application
 */
abstract class BaseFragment(@LayoutRes contentLayoutId: Int = 0) :
    Fragment(contentLayoutId), Injectable, HasAndroidInjector {

    @Inject
    lateinit var viewModelFactoryFactory: ViewModelFactory.Factory

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    /**
     * Request a ViewModel, scoped to this Fragment, from the injected factory.
     * @see ViewModelFactory
     */
    inline fun <reified T : BaseViewModel> fragmentViewModel() = ViewModelProvider(
        this,
        viewModelFactoryFactory.create(this, arguments),
    ).get(T::class.java)

    /**
     * Request a ViewModel, scoped to the parent Activity, from the injected factory.
     * @see ViewModelFactory
     */
    inline fun <reified T : BaseViewModel> activityViewModel() = ViewModelProvider(
        requireActivity(),
        viewModelFactoryFactory.create(this, arguments),
    ).get(T::class.java)

    /**
     * Request a ViewModel, scoped to the parent fragment, from the injected factory.
     * @see ViewModelFactory
     */
    inline fun <reified T : BaseViewModel> parentFragmentViewModel() = ViewModelProvider(
        requireParentFragment(),
        viewModelFactoryFactory.create(this, arguments),
    ).get(T::class.java)

    protected val baseActivity: BaseActivity
        get() = (requireActivity() as BaseActivity)

    @Suppress("SameParameterValue")
    private fun coroutine(lifecycle: Lifecycle.State, function: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launch { viewLifecycleOwner.repeatOnLifecycle(lifecycle, function) }
    }

    private fun launchWhenCreated(function: suspend CoroutineScope.() -> Unit) {
        coroutine(Lifecycle.State.CREATED, function)
    }

    protected fun <T> collect(flow: Flow<T>, function: suspend CoroutineScope.(T) -> Unit) {
        launchWhenCreated {
            flow.collect {
                function(it)
            }
        }
    }

    protected fun listenToAlerts(viewModel: BaseViewModel) {
        collect(viewModel.alertFlow) {
            Snackbar.make(requireContext(), requireView(), it.getString(requireContext()), LENGTH_SHORT).show()
        }
    }

    protected fun listenToIntent(viewModel: BaseViewModel) {
        collect(viewModel.navigationIntentFlow) {
            requireContext().startActivity(it)
        }
    }

    protected fun listenToCloseFragment(viewModel: BaseViewModel) {
        collect(viewModel.closeFragmentFlow) {
            findNavController().navigateUp()
        }
    }

    protected fun listenToCloseActivity(viewModel: BaseViewModel) {
        collect(viewModel.finishActivityFlow) {
            baseActivity.finish()
        }
    }

    /**
     * Usually tells [BaseActivity] to show global (activity-wide) progress bar, e.g. at the top of the screen.
     * No use in current app
     * */
    protected fun listenToLoading(viewModel: BaseViewModel) {
        collect(viewModel.loadingFlow) {}
    }
}
