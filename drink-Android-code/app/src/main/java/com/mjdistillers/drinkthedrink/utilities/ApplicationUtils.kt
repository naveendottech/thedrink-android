package com.mjdistillers.drinkthedrink.utilities

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText


class ApplicationUtils {


    /**
     * Returns the version code of app.
     * return empty string if something wrong happened
     * */
    fun getVersionCode(context: Context): String {
        return try {
            var packInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            var version = packInfo.versionName
            version
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    /**
     * Returns the device id
     * */
    fun getDeviceId(context: Context): String {
        return Settings.Secure
            .getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    /**
     * hide the keyboard after getting focused view
     * */
    fun hideKeyboard(context: Context,editText : AppCompatEditText) {
        try {
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editText?.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * show soft keyboard on specified view
     * */
    fun showSoftKeyboard(context: Context, editText: AppCompatEditText) {
        try {
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            editText.postDelayed(
                {
                    editText.requestFocus()
                    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
                },100)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    /**
     * Just captures the layout object which is passed as an parameter.
     * return its bitmap to be used for image views or any other purposes.
     * */
    fun captureLayout(layoutObject: View): Bitmap {
        val bitmap = Bitmap.createBitmap(
            layoutObject.width,
            layoutObject.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        layoutObject.draw(canvas)
        return bitmap
    }



    /**
     * Returns true if media is mounted
     * */
    fun isMediaMounted(): Boolean {
        return TextUtils.equals(
            Environment.getExternalStorageState(),
            Environment.MEDIA_MOUNTED
        )
    }

    /**
     * This method gives you height and width of the current screen in dp.
     * @return
     * Float[0] = width
     * Float[1] = height
     * */
    fun getDisplayHeightAndWidthInDP(context: Context): List<Float> {
        val displayMetrics = context.resources.displayMetrics
        val dpHeight = displayMetrics.heightPixels / displayMetrics.density
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        var widthHeight = mutableListOf<Float>()

        widthHeight.add(dpWidth)
        widthHeight.add(dpHeight)

        return widthHeight
    }

    /**
     * This method will give you dp from pixel
     * */
    fun convertPixelsToDp(px: Float): Float {
        val metrics = Resources.getSystem().displayMetrics
        val dp = px / (metrics.densityDpi / 160f)
        return Math.round(dp).toFloat()
    }

    /**
     * This method will give you pixel from dp
     * */
    fun convertDpToPixel(dp: Float): Float {
        val metrics = Resources.getSystem().displayMetrics
        val px = dp * (metrics.densityDpi / 160f)
        return Math.round(px).toFloat()
    }

    fun hideKeyboardWithoutView(activity: Activity) {
        val imm =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}