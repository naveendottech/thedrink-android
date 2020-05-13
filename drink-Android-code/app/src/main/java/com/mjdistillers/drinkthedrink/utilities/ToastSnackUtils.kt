package com.mjdistillers.drinkthedrink.utilities

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import com.mjdistillers.drinkthedrink.MainActivity


/**
 * Toast and Snackbar Utilites are there as per the name suggested.
 * */
object ToastSnackUtils {
    open fun toastShort(context: Context, message: String) {

//        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    open fun toastLong(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    open fun toastMillis(context: Context, message: String, millis: Int) {
        Toast.makeText(context, message, millis).show()
    }

    open fun snackBar(
        view: View,
        message: String,
        duration: Int,
        isError: Boolean,
        actionText: String? = null,
        clickListener: View.OnClickListener? = null,
        actionTextColor: Int? = null) {

        var snackbar: Snackbar = Snackbar.make(view, message, duration)
        if (isError)
            snackbar.view.setBackgroundColor(Color.RED)
        else
            snackbar.view.setBackgroundColor(Color.parseColor("#176b95"))

        var isActionTextEmpty = true
        actionText?.let {
            isActionTextEmpty = actionText.isEmpty()
        }

        if (!isActionTextEmpty && clickListener != null)
            snackbar.setAction(actionText, clickListener)
        actionTextColor?.let {
            snackbar.setActionTextColor(actionTextColor)
        }

        snackbar.show()
    }

}