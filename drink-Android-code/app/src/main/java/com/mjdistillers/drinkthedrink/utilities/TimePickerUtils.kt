package com.mjdistillers.drinkthedrink.utilities

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.mjdistillers.drinkthedrink.utilities.constants.Extras
import com.mjdistillers.drinkthedrink.utilities.interfaces.TimePickerUtilsCallback
import java.util.*

/**
 * Just a simple time picker alert class.
 * By inflating the bundle containing this class.
 * Will prove more accurate use.
 * */
class TimePickerUtils : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    var viewId: Int = 0
    var callbacks: TimePickerUtilsCallback? = null


    companion object {
        fun getBundle(callbacks: TimePickerUtilsCallback, hours: Int, minutes: Int, viewId: Int): Bundle {
            var bundle = Bundle()
            bundle.putSerializable(Extras.TIME_UTILS_CALLBACKS, callbacks)
            bundle.putInt(Extras.HOUR, hours)
            bundle.putInt(Extras.MINUTES, minutes)
            bundle.putInt(Extras.VIEW_ID, viewId)
            return bundle
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var args = arguments
        var hour: Int = 0
        var minutes: Int = 0
        if (args != null) {
            if (args.containsKey(Extras.TIME_UTILS_CALLBACKS))
                callbacks = args.getSerializable(Extras.TIME_UTILS_CALLBACKS) as TimePickerUtilsCallback

            if (args.containsKey(Extras.HOUR))
                hour = args.getInt(Extras.HOUR)

            if (args.containsKey(Extras.MINUTES))
                minutes = args.getInt(Extras.MINUTES)

            if (args.containsKey(Extras.VIEW_ID))
                viewId = args.getInt(Extras.VIEW_ID)

        } else {
            var cal = Calendar.getInstance()
            hour = cal.get(Calendar.HOUR)
            minutes = cal.get(Calendar.MINUTE)
        }

        var timePicker = TimePickerDialog(activity, this, hour, minutes, false)

        return timePicker
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        var callback = callbacks
        callback?.let {
            callback.onTimeSet(view, hourOfDay, minute, viewId)
        }
    }


}