package com.mjdistillers.drinkthedrink.utilities.interfaces

import android.widget.DatePicker
import java.io.Serializable

/**
 * Callbacks for Date Set Utility
 * onDateSet method is called when date is set.
 * */
interface DatePickerUtilsCallbacks : Serializable {
    fun onDateSet(view: DatePicker?, viewId: Int, year: Int, month: Int, dayOfMonth: Int)
}