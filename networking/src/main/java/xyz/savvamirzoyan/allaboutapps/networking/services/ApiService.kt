package xyz.savvamirzoyan.allaboutapps.networking.services

import retrofit2.http.GET
import xyz.savvamirzoyan.allaaboutapps.core.Result
import xyz.savvamirzoyan.allaboutapps.networking.model.GenericClubInfoCloud

interface ApiService {

    @GET("clubs.json")
    suspend fun fetchData(): Result<List<GenericClubInfoCloud>>
}
