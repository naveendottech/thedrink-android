package com.mjdistillers.drinkthedrink.utilities.interfaces

/**
 * Callbacks for Agree Disagree Alert
 * Callbacks are hit as per the name is suggests
 *
 * */
interface AgreeDisagreeCancelAlertCallbacks {
    fun eventAgreeOfAgreeAlert()
    fun eventDisagreeOfAgreeAlert()
    fun eventCancelOfAgreeAlert()
}