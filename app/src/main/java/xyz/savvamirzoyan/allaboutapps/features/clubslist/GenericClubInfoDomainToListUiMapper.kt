package xyz.savvamirzoyan.allaboutapps.features.clubslist

import xyz.savvamirzoyan.allaboutapps.R
import xyz.savvamirzoyan.allaboutapps.domain.model.GenericClubInfoDomain
import xyz.savvamirzoyan.allaboutapps.model.TextValue
import javax.inject.Inject

interface GenericClubInfoDomainToListUiMapper {

    fun map(model: GenericClubInfoDomain): GenericClubInfoListItemUi

    class Base @Inject constructor() : GenericClubInfoDomainToListUiMapper {
        override fun map(model: GenericClubInfoDomain) = GenericClubInfoListItemUi(
            id = model.id,
            pictureURL = model.pictureURL,
            title = TextValue(model.name),
            country = TextValue(model.country),
            value = TextValue(R.plurals.clubs_list_value_label, model.value, model.value)
        )
    }
}
