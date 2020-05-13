package com.mjdistillers.drinkthedrink.utilities

import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class NotificationUtils {

    /**
     * Method can be used to show a simple notification by passing parameters as per name.
     * */
    fun showNotification(
        context: Context,
        channelId: String,
        notificationId: Int,
        title: String,
        message: String,
        iconSmall: Int,
        pendingIntent: PendingIntent? = null,
        priority: Int? = null,
        category: String? = null) {

            var notification  = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                     NotificationCompat.Builder(context, channelId)
                    .setContentTitle(title)
                    .setContentText(message)
            }else{
                NotificationCompat.Builder(context)
                    .setContentTitle(title)
                    .setContentText(message)
            }

        iconSmall?.let {
            notification.setSmallIcon(iconSmall)
        }

        priority?.let {
            notification.setPriority(priority)
        }

        category?.let {
            notification.setCategory(category)
        }

        pendingIntent?.let {
            notification.setContentIntent(pendingIntent)
        }


        NotificationManagerCompat.from(context).notify(notificationId, notification.build())
    }


/*
    // Action for clicking on notification. Clicking notification will hit this intent.
    Intent activityIntent = new Intent(this, MainActivity.class);

    PendingIntent contentIntent = PendingIntent.getActivity(this,
    0, activityIntent, 0);

    // Use Following for some Action we have defined a Receiver Do anyting onReceive.
    // Remember limits of Android O for Broadcasts Receivers.
    Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
    broadcastIntent.putExtra("toastMessage", message);

    // FLAG_UPDATE_CURRENT will just update the information on notification without reshowing it.
    PendingIntent actionIntent = PendingIntent.getBroadcast(this,
    0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);


    Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
    .setSmallIcon(R.drawable.ic_one)
    .setContentTitle(title)
    .setContentText(message)
    .setPriority(NotificationCompat.PRIORITY_HIGH)
    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
    .setColor(Color.BLUE)
    .setContentIntent(contentIntent)
    .setAutoCancel(true)
    .setOnlyAlertOnce(true)
    .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)  This will add a button in notification
    .build();
*/


}