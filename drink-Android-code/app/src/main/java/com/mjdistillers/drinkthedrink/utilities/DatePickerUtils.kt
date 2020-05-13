package com.mjdistillers.drinkthedrink.utilities

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.mjdistillers.drinkthedrink.MainActivity
import com.mjdistillers.drinkthedrink.utilities.constants.Extras
import com.mjdistillers.drinkthedrink.utilities.interfaces.DatePickerUtilsCallbacks
import java.util.*
/**
 * This is a simple date picker utility
 * it handles from an to date logic in between it.
 * Just inflate the bundle containing this class using getBundle
 * before you show date alert.
 * */
class DatePickerUtils : DialogFragment(), DatePickerDialog.OnDateSetListener {

    var viewId: Int = 0
    var callbacks: DatePickerUtilsCallbacks? = null

    companion object {
        fun getBundle(
            callbacks: DatePickerUtilsCallbacks, isFrom: Boolean, selectedDate: Long,
            fromMillis: Long, toMillis: Long, viewId: Int
        ): Bundle {
            var bundle = Bundle()
            bundle.putSerializable(Extras.DATE_UTILS_CALLBACKS, callbacks)
            if (isFrom) {
                bundle.putLong(Extras.MIN_DATE_PICKER, 0L)
                bundle.putLong(Extras.MAX_DATE_PICKER, toMillis)
            } else {
                bundle.putLong(Extras.MIN_DATE_PICKER, fromMillis)
                bundle.putLong(Extras.MAX_DATE_PICKER, 0L)
            }

            if (selectedDate > 0)
                bundle.putLong(Extras.SELECTED_DATE, selectedDate)
            else
                bundle.putLong(Extras.SELECTED_DATE, Date().time)

            bundle.putInt(Extras.VIEW_ID, viewId)
            return bundle
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var maxDate = 0L
        var minDate = 0L
        var selectedDate = 0L
        var pickerDialog: DatePickerDialog

        var args = arguments

        args?.let {
            if (args.containsKey(Extras.MAX_DATE_PICKER)) {
                maxDate = args.getLong(Extras.MAX_DATE_PICKER)
            }

            if (args.containsKey(Extras.MIN_DATE_PICKER)) {
                minDate = args.getLong(Extras.MIN_DATE_PICKER)
            }
            if (args.containsKey(Extras.VIEW_ID)) {
                viewId = args.getInt(Extras.VIEW_ID)
            }
            if (args.containsKey(Extras.SELECTED_DATE)) {
                selectedDate = args.getLong(Extras.SELECTED_DATE)
            }
            if (args.containsKey(Extras.DATE_UTILS_CALLBACKS)) {
                callbacks = args.getSerializable(Extras.DATE_UTILS_CALLBACKS) as DatePickerUtilsCallbacks
            }
        }

        var year = 0
        var month = 0
        var day = 0

        if (selectedDate > 0) {
            var selectedDateCal = Calendar.getInstance()
            var selectedDateDateObject = Date()
            selectedDateDateObject.time = selectedDate
            selectedDateCal.time = selectedDateDateObject

            year = selectedDateCal.get(Calendar.YEAR)
            month = selectedDateCal.get(Calendar.MONTH)
            day = selectedDateCal.get(Calendar.DAY_OF_MONTH)

            pickerDialog = DatePickerDialog(activity as MainActivity, this, year, month, day)
        } else {
            var selectedDateCal = Calendar.getInstance()
            year = selectedDateCal.get(Calendar.YEAR)
            month = selectedDateCal.get(Calendar.MONTH)
            day = selectedDateCal.get(Calendar.DAY_OF_MONTH)

            pickerDialog = DatePickerDialog(activity as MainActivity, this, year, month, day)
        }

        if (minDate > 0)
            if(minDate > 0) pickerDialog.datePicker.minDate = minDate

        if (maxDate > 0)
            if(maxDate > 0) pickerDialog.datePicker.maxDate = maxDate
        else
            if(minDate > 0) pickerDialog.datePicker.minDate = minDate

        return pickerDialog
    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        var callbacksDate = callbacks
        callbacksDate?.let {
            callbacksDate.onDateSet(view, viewId, year, month+1 , dayOfMonth)
        }
    }

}