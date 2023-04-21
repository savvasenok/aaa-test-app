package xyz.savvamirzoyan.allaboutapps.data.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import xyz.savvamirzoyan.allaaboutapps.core.Result
import xyz.savvamirzoyan.allaboutapps.data.TestCloudSource
import xyz.savvamirzoyan.allaboutapps.data.TestGenericClubInfoDao
import xyz.savvamirzoyan.allaboutapps.data.mapper.GenericClubInfoDomainMapper
import xyz.savvamirzoyan.allaboutapps.networking.model.GenericClubInfoCloud
import xyz.savvamirzoyan.allaboutapps.storage.model.GenericClubInfoLocal
import java.net.UnknownHostException

internal class ClubsRepositoryImplTest {

    private val mapper = GenericClubInfoDomainMapper()

    @Test
    fun `no local error cloud`() = runBlocking {
        val localSource = object : TestGenericClubInfoDao() {
            override fun selectAllFlow(): Flow<List<GenericClubInfoLocal>> = flow { emit(emptyList()) }
        }

        val cloudSource = object : TestCloudSource() {
            override suspend fun fetchData(): Result<List<GenericClubInfoCloud>> =
                Result.Exception(UnknownHostException())
        }

        val repository = ClubsRepositoryImpl(cloudSource, localSource, mapper)

        launch {
            delay(50)
            repository.refresh()
        }

        val result = repository.clubsFlow.take(1).toList().first()

        Assertions.assertTrue(result is Result.Exception && result.throwable is UnknownHostException)
    }

    @Test
    fun `no local success cloud`() = runBlocking {
        val localSource = object : TestGenericClubInfoDao() {
            override fun selectAllFlow(): Flow<List<GenericClubInfoLocal>> = flow { emit(emptyList()) }
            override suspend fun nukeTable() = Unit
            override suspend fun insert(vararg items: GenericClubInfoLocal) = Unit
        }

        val cloud = listOf(GenericClubInfoCloud("id-cloud", "name-cloud", 42, "url-cloud", "name-cloud", 100500))
        val cloudSource = object : TestCloudSource() {
            override suspend fun fetchData(): Result<List<GenericClubInfoCloud>> = Result.Success(cloud)
        }

        val expected = listOf(Result.Success(cloud.map { mapper.map(it) }))

        val repository = ClubsRepositoryImpl(cloudSource, localSource, mapper)

        launch {
            delay(50)
            repository.refresh()
        }

        val resultList = repository.clubsFlow.take(1).toList()

        Assertions.assertEquals(
            expected,
            resultList,
        )
    }

    @Test
    fun `has local error cloud`() = runBlocking {
        val local = listOf(GenericClubInfoLocal("id-local", "name-local", 42, "url-local", "name-local", 100500))
        val localSource = object : TestGenericClubInfoDao() {
            override fun selectAllFlow(): Flow<List<GenericClubInfoLocal>> = flow { emit(local) }
        }

        val cloudSource = object : TestCloudSource() {
            override suspend fun fetchData(): Result<List<GenericClubInfoCloud>> =
                Result.Exception(UnknownHostException())
        }

        val expected = listOf(Result.Success(local.map { mapper.map(it) }))

        val repository = ClubsRepositoryImpl(cloudSource, localSource, mapper)

        launch {
            delay(50)
            repository.refresh()
        }

        val resultList = repository.clubsFlow.take(1).toList()

        Assertions.assertEquals(
            expected,
            resultList,
        )
    }

    @Test
    fun `has local and success cloud`(): Unit = runBlocking {

        val local = listOf(GenericClubInfoLocal("id-local", "name-local", 42, "url-local", "name-local", 100500))
        val localSource = object : TestGenericClubInfoDao() {
            override fun selectAllFlow(): Flow<List<GenericClubInfoLocal>> = flow { emit(local) }
            override suspend fun nukeTable() = Unit
            override suspend fun insert(vararg items: GenericClubInfoLocal) = Unit
        }

        val cloud = listOf(GenericClubInfoCloud("id-cloud", "name-cloud", 42, "url-cloud", "name-cloud", 100500))
        val cloudSource = object : TestCloudSource() {
            override suspend fun fetchData(): Result<List<GenericClubInfoCloud>> = Result.Success(cloud)
        }

        val expected = listOf(Result.Success(cloud.map { mapper.map(it) }))

        val repository = ClubsRepositoryImpl(cloudSource, localSource, mapper)

        launch {
            delay(50)
            repository.refresh()
        }

        val resultList = repository.clubsFlow.take(1).toList()

        Assertions.assertEquals(
            expected,
            resultList,
        )
    }
}
