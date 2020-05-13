package com.mjdistillers.drinkthedrink.utilities.interfaces

import android.content.Intent


/**
 * Callback for Pick File
 * Methods called as per the name suggests
 * methods data object contains all the data information
 * */
interface PickFileCallbacks {
    fun resultPickFile(data: Intent?, id: Int)
}