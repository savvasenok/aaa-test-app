package xyz.savvamirzoyan.allaboutapps.features.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject

class FcmService : FirebaseMessagingService() {
    @Inject
    lateinit var fcmHandler: FcmHandler

    @Inject
    lateinit var tokenHandler: FirebaseTokenHandler

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    /**
     * Called when message is received when the app is in the foreground.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.d("onMessageReceived")
        fcmHandler.handleFcmPushNotification(remoteMessage)
    }

    override fun onNewToken(token: String) {
        Timber.d("onNewToken: $token")
        tokenHandler.sendFirebaseTokenToServer(token)
    }
}
