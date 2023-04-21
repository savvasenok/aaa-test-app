package xyz.savvamirzoyan.allaboutapps.domain.usecase

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import xyz.savvamirzoyan.allaboutapps.domain.model.GenericClubInfoDomain

class SortClubsUseCaseTest {

    private val usecase = SortClubsUseCase.Base()

    private val unsorted = listOf(
        GenericClubInfoDomain("id1", "country1", 41, "https://google.com/", "A name", 2),
        GenericClubInfoDomain("id2", "country2", 42, "https://google.com/", "B name", 1),
        GenericClubInfoDomain("id3", "country3", 43, "https://google.com/", "C name", 0),
    )

    private val sortedName = unsorted.sortedBy { it.name }
    private val sortedValue = unsorted.sortedBy { it.value }

    @Test
    fun `sort by name`() = runBlocking {
        val actual = usecase(SortClubsRequestDomain(unsorted, ClubSortingMethod.NAME)).getOrNull()!!.clubs
        Assertions.assertEquals(sortedName, actual)
    }

    @Test
    fun `sort by value`() = runBlocking {
        val actual = usecase(SortClubsRequestDomain(unsorted, ClubSortingMethod.VALUE)).getOrNull()!!.clubs
        Assertions.assertEquals(sortedValue, actual)
    }
}