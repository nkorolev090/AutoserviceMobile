package com.project.autoservicemobile.common.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.project.deviceToken.DeviceTokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationService : FirebaseMessagingService() {

    @Inject
    lateinit var deviceTokenManager: DeviceTokenManager

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if(task.isSuccessful.not()){
                return@addOnCompleteListener
            }

            val token = task.result
            deviceTokenManager.saveToken(token)
            Log.e("NotificationService", "Token: $token")
        }
    }
}