package com.mjdistillers.drinkthedrink

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.mjdistillers.drinkthedrink.di.ApplicationModule
import com.mjdistillers.drinkthedrink.di.DaggerDtdComponent
import com.mjdistillers.drinkthedrink.di.DtdComponent


class App : MultiDexApplication() {

    companion object{
        lateinit var app:App
        var DTD_CHANNEL_ID = "dtdchannelId"
    }

    private lateinit var component: DtdComponent
    lateinit var dtdChannel:NotificationChannel

    private val ANDROID_CHANNEL_NAME = "dtdchannel"

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        app = this

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dtdChannel = NotificationChannel(
                DTD_CHANNEL_ID,
                ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)

            dtdChannel.description = getString(R.string.channel_description)

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(dtdChannel)
        }

        component = DaggerDtdComponent.builder()
            .applicationModule(ApplicationModule(app))
            .build()
    }

    fun isChannelInitialized():Boolean{
        return ::dtdChannel.isInitialized
    }

    fun getComponent(): DtdComponent{
        return component
    }
}