package com.mjdistillers.drinkthedrink.utilities.interfaces

import android.widget.TimePicker
import java.io.Serializable


/**
 * Callback for ok TimePicker set up
 * Methods called as per the name suggests
 * */
interface TimePickerUtilsCallback : Serializable {
    fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int, viewId: Int)
}