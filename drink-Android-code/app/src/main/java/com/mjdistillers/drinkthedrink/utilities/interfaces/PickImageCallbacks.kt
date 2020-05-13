package com.mjdistillers.drinkthedrink.utilities.interfaces

import android.content.Intent
import android.net.Uri
import java.io.File


/**
 * Callback for Pick Image
 * Methods called as per the name suggests
 * */
interface PickImageCallbacks {
    fun resultPickFromCamera(data: File?)
    fun resultPickFromMedia(data: File?)
}