package xyz.savvamirzoyan.allaboutapps.base

import android.content.Intent
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import xyz.savvamirzoyan.allaaboutapps.core.Result

abstract class BaseViewModel : ViewModel() {

    private val _loadingFlow = MutableStateFlow<Boolean?>(null)
    val loadingFlow: Flow<Boolean> = _loadingFlow.filterNotNull()

    private val _finishActivityFlow = MutableSharedFlow<Unit>(replay = 0)
    val finishActivityFlow: Flow<Unit> = _finishActivityFlow

    private val _closeFragmentFlow = MutableSharedFlow<Unit>(replay = 0)
    val closeFragmentFlow: Flow<Unit> = _closeFragmentFlow

    private val _navigationIntentFlow = MutableSharedFlow<Intent>(replay = 0)
    val navigationIntentFlow: Flow<Intent> = _navigationIntentFlow

    suspend fun <T> whileLoading(function: suspend () -> T): T {
        _loadingFlow.emit(true)
        val result: T = function()
        _loadingFlow.emit(false)

        return result
    }

    protected suspend fun finishActivity() {
        _finishActivityFlow.emit(Unit)
    }

    protected suspend fun closeFragment() {
        _closeFragmentFlow.emit(Unit)
    }

    protected suspend fun navigateTo(intent: Intent) = _navigationIntentFlow.emit(intent)

    protected suspend fun <T : Any> Result<T>.handle(
        onError: (suspend (code: Int, message: String?) -> Unit)? = null,
        onException: (suspend (Throwable) -> Unit)? = null,
        onSuccess: (suspend (T) -> Unit)? = null,
    ) {
        when (this) {
            is Result.Error -> onError?.invoke(this.code, message)
            is Result.Exception -> onException?.invoke(throwable)
            is Result.Success -> onSuccess?.invoke(data)
        }
    }
}