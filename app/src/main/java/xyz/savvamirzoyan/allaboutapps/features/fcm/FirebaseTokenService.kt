package xyz.savvamirzoyan.allaboutapps.features.fcm

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
