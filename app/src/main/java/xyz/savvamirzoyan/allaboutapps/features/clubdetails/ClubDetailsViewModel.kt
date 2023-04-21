package xyz.savvamirzoyan.allaboutapps.features.clubdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import xyz.savvamirzoyan.allaboutapps.R
import xyz.savvamirzoyan.allaboutapps.base.BaseViewModel
import xyz.savvamirzoyan.allaboutapps.domain.usecase.GetClubUseCase
import xyz.savvamirzoyan.allaboutapps.domain.usecase.GetClubUseCaseRequest
import xyz.savvamirzoyan.allaboutapps.model.TextValue

class ClubDetailsViewModel @AssistedInject constructor(
    @Assisted private val clubId: String,
    private val getClubUseCase: GetClubUseCase,
) : BaseViewModel() {

    val clubDetailsFlow: Flow<ClubDetailsUi> = flow {

        // TODO: Fragment not opend when no data provided

        getClubUseCase(GetClubUseCaseRequest(clubId)).handle(
            onError = { a, b ->
                ClubDetailsUi.Failure(R.drawable.ic_error, TextValue(R.string.error_message_no_data))
                    .also { emit(it) }
            },
            onException = { t ->
                ClubDetailsUi.Failure(R.drawable.ic_error, TextValue(R.string.error_message_no_data))
                    .also { emit(it) }
            },
        ) {
            ClubDetailsUi.Success(
                pictureURL = it.pictureURL,
                country = TextValue(it.country),
                clubTitle = TextValue(it.name),
                description = TextValue(
                    R.string.club_details_description,
                    arrayOf(
                        it.name,
                        it.country,
                        TextValue(R.plurals.clubs_list_value_label, it.value, it.value),
                        it.name,
                        it.europeanTitles,
                    ),
                ),
                stringToMakeBoldInDescription = TextValue(it.name),
            ).also { modelUi -> emit(modelUi) }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(clubId: String): ClubDetailsViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory, clubId: String,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>) = assistedFactory.create(clubId) as T
        }
    }
}
