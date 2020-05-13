package com.mjdistillers.drinkthedrink

import android.app.PendingIntent
import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mjdistillers.drinkthedrink.utilities.NotificationUtils
import com.mjdistillers.drinkthedrink.utilities.SharedPrefsUtils

class DtdFirebaseMessaging: FirebaseMessagingService(){

    var prefs = SharedPrefsUtils(App.app)

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        prefs.saveDeviceToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
//        var type = remoteMessage.data[DtdPrefsKeys.Keys.NOTIFICATION_TYPE]?:""
        var message = remoteMessage.data[DtdPrefsKeys.Keys.NOTIFICATION_MESSAGE]?:""

        var type = DtdPrefsKeys.Values.NOTIFICATION_TYPE_MESSAGE

        var notificatioID = 0
        when(message.toLowerCase().contains("new Message".toLowerCase())){
            true->{
                type = DtdPrefsKeys.Values.NOTIFICATION_TYPE_MESSAGE
            }
            false->{
                type = DtdPrefsKeys.Values.NOTIFICATION_TYPE_REQUEST
            }
        }

        var intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.putExtra(DtdPrefsKeys.Keys.NOTIFICATION_TYPE,type)
        var pendingIntent = PendingIntent.getActivity(this,1,
            intent,PendingIntent.FLAG_UPDATE_CURRENT)

        NotificationUtils().showNotification(
            App.app,
            App.DTD_CHANNEL_ID,
            notificatioID,
            getString(R.string.app_name),
            message,
            R.drawable.ic_notificaion,
            pendingIntent)
    }
}