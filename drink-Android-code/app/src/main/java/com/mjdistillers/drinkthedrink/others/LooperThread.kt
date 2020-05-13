package com.mjdistillers.drinkthedrink.others

import android.os.Handler
import android.os.Looper
import android.os.Message

class LooperThread : Thread() {

    lateinit var handler:Handler

    override fun run() {
        Looper.prepare()

        handler = Handler()

        Looper.loop()
    }

}