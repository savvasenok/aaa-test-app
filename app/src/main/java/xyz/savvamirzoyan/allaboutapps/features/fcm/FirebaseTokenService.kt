package xyz.savvamirzoyan.allaboutapps.features.fcm

// import io.reactivex.rxjava3.core.Single
import xyz.savvamirzoyan.allaboutapps.networking.services.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseTokenService @Inject constructor(
    // private val apiService: SomeService // reserved for future use
) {

    fun registerFirebaseToken(token: String): Unit /*Single<Any>*/ {
        // TODO: send FCM Token to backend
        // return Single.never<Any>()
    }
}
