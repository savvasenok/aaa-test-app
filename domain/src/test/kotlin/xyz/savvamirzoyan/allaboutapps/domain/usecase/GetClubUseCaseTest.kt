package xyz.savvamirzoyan.allaboutapps.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import xyz.savvamirzoyan.allaaboutapps.core.Result
import xyz.savvamirzoyan.allaboutapps.domain.contract.IClubsRepository
import xyz.savvamirzoyan.allaboutapps.domain.model.GenericClubInfoDomain

class GetClubUseCaseTest {

    val club = GenericClubInfoDomain(
        "id",
        "country",
        42,
        "https://google.com/",
        "Test",
        100500,
    )

    private val repository = object : IClubsRepository {

        override val clubsFlow: Flow<Result<List<GenericClubInfoDomain>>> = flow {}
        override suspend fun refresh() = Unit

        override suspend fun getClub(clubId: String): Result<GenericClubInfoDomain> = when (clubId) {
            "success" -> Result.Success(club)
            "exception" -> Result.Exception(NoSuchElementException())
            "error" -> Result.Error(500, "error")
            else -> throw IllegalArgumentException()
        }
    }

    val usecase = GetClubUseCase.Base(repository)

    @Test
    fun `get club by id success`() = runBlocking {
        val actual = usecase(GetClubUseCaseRequest("success"))
        val expected = Result.Success(club)
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `get club by id error`() = runBlocking {
        val actual = usecase(GetClubUseCaseRequest("error"))
        val expected = Result.Error<GenericClubInfoDomain>(500, "error")
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `get club by id exception`() = runBlocking {
        val actual = usecase(GetClubUseCaseRequest("exception")) as Result.Exception
        val expected = Result.Exception<GenericClubInfoDomain>(NoSuchElementException())
        Assertions.assertEquals(expected.throwable::class, actual.throwable::class)
    }
}