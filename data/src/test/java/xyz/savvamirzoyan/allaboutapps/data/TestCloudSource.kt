package xyz.savvamirzoyan.allaboutapps.data

import kotlinx.coroutines.delay
import xyz.savvamirzoyan.allaaboutapps.core.Result
import xyz.savvamirzoyan.allaboutapps.networking.model.GenericClubInfoCloud
import xyz.savvamirzoyan.allaboutapps.networking.services.ApiService

internal abstract class TestCloudSource : ApiService {

    override suspend fun fetchData(): Result<List<GenericClubInfoCloud>> {
        TODO("Not yet implemented")
    }
}