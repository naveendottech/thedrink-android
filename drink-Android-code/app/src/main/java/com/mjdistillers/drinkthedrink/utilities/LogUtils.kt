package com.mjdistillers.drinkthedrink.utilities

import android.util.Log
import com.mjdistillers.drinkthedrink.DtdConstants

/**
 * Just log utils as the name suggests
 *
 * */
object LogUtils {



    open fun logi(message: String, TAG: String = DtdConstants.TAG_LOG_DEFAULT) {
        Log.i(TAG, message)
    }

    open fun loge(message: String, TAG: String = DtdConstants.TAG_LOG_DEFAULT) {
        Log.e(TAG, message)
    }

    open fun logd(message: String, TAG: String = DtdConstants.TAG_LOG_DEFAULT) {
        Log.d(TAG, message)
    }

    open fun logv(message: String, TAG: String = DtdConstants.TAG_LOG_DEFAULT) {
        Log.v(TAG, message)
    }


    open fun logw(message: String, TAG: String = DtdConstants.TAG_LOG_DEFAULT) {
        Log.w(TAG, message)
    }


    open fun logwtf(message: String, TAG: String = DtdConstants.TAG_LOG_DEFAULT) {
        Log.wtf(TAG, message)
    }


}