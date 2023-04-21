package xyz.savvamirzoyan.allaboutapps.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import xyz.savvamirzoyan.allaaboutapps.core.Result
import xyz.savvamirzoyan.allaboutapps.domain.contract.IClubsRepository
import xyz.savvamirzoyan.allaboutapps.domain.model.GenericClubInfoDomain

class GetAllClubsUseCaseTest {

    private val list = listOf(
        GenericClubInfoDomain(
            "id",
            "country",
            42,
            "https://google.com/",
            "Test",
            100500,
        ),
    )

    private val list2 = listOf(
        GenericClubInfoDomain(
            "id",
            "country",
            42,
            "https://google.com/",
            "Test",
            100500,
        ),
        GenericClubInfoDomain(
            "2id",
            "2country",
            242,
            "2https://google.com/",
            "2Test",
            2100500,
        )
    )

    private val repository = object : IClubsRepository {

        private val _flow = MutableStateFlow(list)

        override val clubsFlow: Flow<Result<List<GenericClubInfoDomain>>> = _flow
            .map { Result.Success(it) }

        override suspend fun refresh() {
            _flow.emit(list2)
        }

        override suspend fun getClub(clubId: String): Result<GenericClubInfoDomain> {
            TODO("Not yet implemented")
        }
    }

    @Test
    fun `get flow`() = runBlocking {
        val usecase = GetAllClubsUseCase.Base(repository)

        val actual: Result<GenericClubInfoListDomain> = usecase(NoParams).take(1).first()
        val expected: Result<GenericClubInfoListDomain> = Result.Success(GenericClubInfoListDomain(list))

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `rerun flow`() = runBlocking {
        val usecase = GetAllClubsUseCase.Base(repository)

        val actual: Result<GenericClubInfoListDomain> = usecase(NoParams).apply { usecase.rerun(NoParams) }
            .take(1).last()
        val expected: Result<GenericClubInfoListDomain> = Result.Success(GenericClubInfoListDomain(list2))

        Assertions.assertEquals(expected, actual)
    }
}