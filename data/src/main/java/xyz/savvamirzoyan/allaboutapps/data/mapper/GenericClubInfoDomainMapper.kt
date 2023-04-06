package xyz.savvamirzoyan.allaboutapps.data.mapper

import xyz.savvamirzoyan.allaboutapps.domain.model.GenericClubInfoDomain
import xyz.savvamirzoyan.allaboutapps.networking.model.GenericClubInfoCloud
import xyz.savvamirzoyan.allaboutapps.storage.model.GenericClubInfoLocal
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenericClubInfoDomainMapper @Inject constructor() {

    fun map(model: GenericClubInfoLocal) = GenericClubInfoDomain(
        id = model.id,
        country = model.country,
        europeanTitles = model.europeanTitles,
        pictureURL = model.pictureURL,
        name = model.name,
        value = model.value,
    )

    fun map(model: GenericClubInfoCloud) = GenericClubInfoDomain(
        id = model.id,
        country = model.country,
        europeanTitles = model.europeanTitles,
        pictureURL = model.pictureURL,
        name = model.name,
        value = model.value,
    )

    fun mapCloudToLocal(model: GenericClubInfoCloud) = GenericClubInfoLocal(
        id = model.id,
        country = model.country,
        europeanTitles = model.europeanTitles,
        pictureURL = model.pictureURL,
        name = model.name,
        value = model.value,
    )
}