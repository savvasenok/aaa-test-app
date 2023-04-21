package xyz.savvamirzoyan.allaboutapps.features.clubslist

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import xyz.savvamirzoyan.allaaboutapps.core.Result
import xyz.savvamirzoyan.allaaboutapps.core.fold
import xyz.savvamirzoyan.allaaboutapps.core.mapResult
import xyz.savvamirzoyan.allaboutapps.R
import xyz.savvamirzoyan.allaboutapps.base.BaseViewModel
import xyz.savvamirzoyan.allaboutapps.domain.usecase.ClubSortingMethod
import xyz.savvamirzoyan.allaboutapps.domain.usecase.GetAllClubsUseCase
import xyz.savvamirzoyan.allaboutapps.domain.usecase.NoParams
import xyz.savvamirzoyan.allaboutapps.domain.usecase.SortClubsRequestDomain
import xyz.savvamirzoyan.allaboutapps.domain.usecase.SortClubsUseCase
import xyz.savvamirzoyan.allaboutapps.features.common.CommonErrorUi
import xyz.savvamirzoyan.allaboutapps.model.TextValue
import java.io.IOException
import javax.inject.Inject

class ClubsListViewModel @Inject constructor(
    private val genericClubInfoDomainToListUiMapper: GenericClubInfoDomainToListUiMapper,
    private val getAllClubsUseCase: GetAllClubsUseCase,
    sortClubsUserCase: SortClubsUseCase,
) : BaseViewModel() {

    private val _clubSortingMethodFlow = MutableStateFlow(ClubSortingMethod.NAME)
    private val _clubsFlow = getAllClubsUseCase(NoParams)

    @Suppress("UNCHECKED_CAST")
    val clubsFlow = combine(
        _clubsFlow,
        _clubSortingMethodFlow,
    ) { result, sortingMethod ->
        result.map { wrapper ->
            if (result is Result.Success)
                sortClubsUserCase(SortClubsRequestDomain(wrapper.clubs, sortingMethod)).getOrNull()!!.clubs
            else wrapper.clubs
        }
    }
        .mapResult { clubs -> clubs.map { genericClubInfoDomainToListUiMapper.map(it) } }
        .fold(
            onException = { throwable ->
                val error =
                    if (throwable is IOException) CommonErrorUi(
                        TextValue(R.string.error_title_no_internet), TextValue(R.string.error_message_no_internet),
                    )
                    else CommonErrorUi(
                        TextValue(R.string.error_title_unexpected), TextValue(throwable.message ?: ""),
                    )
                listOf(error)
            },
            onError = { code, message ->
                listOf(CommonErrorUi(TextValue(code.toString()), TextValue(message ?: "")))
            },
        ) {
            it
                .takeIf { it.isNotEmpty() }
                ?: listOf(
                    CommonErrorUi(TextValue(R.string.error_title_no_data), TextValue(R.string.error_message_no_data)),
                )
        }
        .map { it.getOrNull() ?: emptyList() }
        .flowOn(Dispatchers.Default)

    fun refresh() {
        viewModelScope.launch {
            getAllClubsUseCase.rerun(NoParams)
        }
    }

    fun onClubSortingMethodChange() {
        viewModelScope.launch {
            val toggledSortingMethod =
                if (_clubSortingMethodFlow.value == ClubSortingMethod.NAME) ClubSortingMethod.VALUE
                else ClubSortingMethod.NAME

            _clubSortingMethodFlow.emit(toggledSortingMethod)
        }
    }
}
