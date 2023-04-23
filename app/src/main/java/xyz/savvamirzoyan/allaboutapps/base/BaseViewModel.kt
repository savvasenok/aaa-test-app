package xyz.savvamirzoyan.allaboutapps.base

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import xyz.savvamirzoyan.allaaboutapps.core.Result
import xyz.savvamirzoyan.allaaboutapps.core.emit
import xyz.savvamirzoyan.allaboutapps.model.TextValue

abstract class BaseViewModel : ViewModel() {

    private val _loadingFlow = MutableStateFlow(false)
    val loadingFlow: Flow<Boolean> = _loadingFlow

    private val _finishActivityFlow = MutableSharedFlow<Unit>(replay = 0)
    val finishActivityFlow: Flow<Unit> = _finishActivityFlow

    private val _closeFragmentFlow = MutableSharedFlow<Unit>(replay = 0)
    val closeFragmentFlow: Flow<Unit> = _closeFragmentFlow

    private val _navigationIntentFlow = MutableSharedFlow<Intent>(replay = 0)
    val navigationIntentFlow: Flow<Intent> = _navigationIntentFlow

    private val _alertFlow = MutableSharedFlow<TextValue>(replay = 0)
    val alertFlow: Flow<TextValue> = _alertFlow

    protected suspend fun <T> whileLoading(function: suspend () -> T): T {
        _loadingFlow.emit(true)
        val result: T = function()
        _loadingFlow.emit(false)

        return result
    }

    protected suspend fun startLoading() = _loadingFlow.emit(true)
    protected suspend fun stopLoading() = _loadingFlow.emit(false)

    protected suspend fun finishActivity() = _finishActivityFlow.emit()
    protected suspend fun closeFragment() = _closeFragmentFlow.emit()
    protected suspend fun showAlert(text: TextValue) = _alertFlow.emit(text)
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