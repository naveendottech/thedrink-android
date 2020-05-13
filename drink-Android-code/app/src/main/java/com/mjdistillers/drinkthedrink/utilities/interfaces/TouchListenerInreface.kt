package com.mjdistillers.drinkthedrink.utilities.interfaces

import android.view.View

/**
 * Callbacks for reyclerView item touch listener. Works as per the name suggests
 * **/
interface TouchListenerInreface {
    fun onSingleTap(view: View, position: Int)
    fun onDoubleTap(view: View, position: Int)
    fun onLongPressed(view: View, position: Int)
}
