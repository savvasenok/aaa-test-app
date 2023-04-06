package xyz.savvamirzoyan.allaboutapps.features.clubslist

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import xyz.savvamirzoyan.allaaboutapps.core.Result
import xyz.savvamirzoyan.allaaboutapps.core.mapResult
import xyz.savvamirzoyan.allaboutapps.base.BaseViewModel
import xyz.savvamirzoyan.allaboutapps.domain.usecase.ClubSortingMethod
import xyz.savvamirzoyan.allaboutapps.domain.usecase.GenericClubInfoListDomain
import xyz.savvamirzoyan.allaboutapps.domain.usecase.GetAllClubsUseCase
import xyz.savvamirzoyan.allaboutapps.domain.usecase.NoParams
import xyz.savvamirzoyan.allaboutapps.domain.usecase.SortClubsRequestDomain
import xyz.savvamirzoyan.allaboutapps.domain.usecase.SortClubsUseCase
import javax.inject.Inject

class ClubsListViewModel @Inject constructor(
    private val genericClubInfoDomainToListUiMapper: GenericClubInfoDomainToListUiMapper,
    private val getAllClubsUseCase: GetAllClubsUseCase,
    sortClubsUserCase: SortClubsUseCase,
) : BaseViewModel() {

    private val _clubSortingMethodFlow = MutableStateFlow(ClubSortingMethod.NAME)
    private val _clubsFlow = getAllClubsUseCase.run(NoParams)

    @Suppress("UNCHECKED_CAST")
    val clubsFlow = combine(
        _clubsFlow.filter { it is Result.Success } as Flow<Result.Success<GenericClubInfoListDomain>>,
        _clubSortingMethodFlow,
    ) { clubsWrapper, sortingMethod ->
        sortClubsUserCase.run(SortClubsRequestDomain(clubsWrapper.data.clubs, sortingMethod))
    }
        .mapResult { clubs -> clubs.map { genericClubInfoDomainToListUiMapper.map(it) } }
        .map { it.getOrNull() ?: emptyList() }
        .flowOn(Dispatchers.Default)

    fun onClubClick(clubId: String) {
    }

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
