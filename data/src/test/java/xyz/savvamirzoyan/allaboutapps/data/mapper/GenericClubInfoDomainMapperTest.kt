package xyz.savvamirzoyan.allaboutapps.data.mapper

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import xyz.savvamirzoyan.allaboutapps.domain.model.GenericClubInfoDomain
import xyz.savvamirzoyan.allaboutapps.networking.model.GenericClubInfoCloud
import xyz.savvamirzoyan.allaboutapps.storage.model.GenericClubInfoLocal

class GenericClubInfoDomainMapperTest {

    private val mapper = GenericClubInfoDomainMapper()

    @Test
    fun `local to domain`() {

        val local = GenericClubInfoLocal(
            id = "id",
            country = "Österreich",
            europeanTitles = 42,
            pictureURL = "https://google.com/",
            name = "Test name",
            value = 100500,
        )
        val expectedDomain = GenericClubInfoDomain(
            id = "id",
            country = "Österreich",
            europeanTitles = 42,
            pictureURL = "https://google.com/",
            name = "Test name",
            value = 100500,
        )

        Assertions.assertEquals(
            expectedDomain,
            mapper.map(local),
        )
    }

    @Test
    fun `cloud to domain`() {

        val cloud = GenericClubInfoCloud(
            id = "id",
            country = "Österreich",
            europeanTitles = 42,
            pictureURL = "https://google.com/",
            name = "Test name",
            value = 100500,
        )
        val expectedDomain = GenericClubInfoDomain(
            id = "id",
            country = "Österreich",
            europeanTitles = 42,
            pictureURL = "https://google.com/",
            name = "Test name",
            value = 100500,
        )

        Assertions.assertEquals(
            expectedDomain,
            mapper.map(cloud),
        )
    }

    @Test
    fun `cloud to local`() {

        val cloud = GenericClubInfoCloud(
            id = "id",
            country = "Österreich",
            europeanTitles = 42,
            pictureURL = "https://google.com/",
            name = "Test name",
            value = 100500,
        )
        val expectedLocal = GenericClubInfoLocal(
            id = "id",
            country = "Österreich",
            europeanTitles = 42,
            pictureURL = "https://google.com/",
            name = "Test name",
            value = 100500,
        )

        Assertions.assertEquals(
            expectedLocal,
            mapper.mapCloudToLocal(cloud),
        )
    }
}