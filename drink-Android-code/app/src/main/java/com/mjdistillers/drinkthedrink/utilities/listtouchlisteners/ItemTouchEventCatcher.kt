package com.mjdistillers.drinkthedrink.utilities.listtouchlisteners

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.mjdistillers.drinkthedrink.utilities.interfaces.TouchListenerInreface


/**
 * Used for recyclerView Click events. Events are made using a GestureDetectorListener
 *
 * */
class ItemTouchEventCatcher(
    context: Context,
    recyclerView: RecyclerView,
    private val clickEventCatcher: TouchListenerInreface
) : RecyclerView.OnItemTouchListener {

    private val gestureDetector: GestureDetector

    init {

        /* This GestureDetector is calling our interface callbacks implemented in fragment.
        * Making calls to callbacks as the name suggests
        * */
        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                val child = recyclerView.findChildViewUnder(e.x, e.y)
                if (child != null && clickEventCatcher != null) {
                    clickEventCatcher.onSingleTap(child, recyclerView.getChildAdapterPosition(child))
                }
                return true
            }

            override fun onDoubleTap(e: MotionEvent?): Boolean {
                e?.let {
                    val child = recyclerView.findChildViewUnder(e.x, e.y)
                    if (child != null && clickEventCatcher != null) {
                        clickEventCatcher.onDoubleTap(child, recyclerView.getChildAdapterPosition(child))
                    }
                }

                return true
            }

            override fun onLongPress(e: MotionEvent) {
                val child = recyclerView.findChildViewUnder(e.x, e.y)
                if (child != null && clickEventCatcher != null) {
                    clickEventCatcher.onLongPressed(child, recyclerView.getChildAdapterPosition(child))
                }
            }
        })
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val child = rv.findChildViewUnder(e.x, e.y)
        if (child != null && clickEventCatcher != null && gestureDetector.onTouchEvent(e)) {
//            clickEventCatcher.onSingleTap(child, rv.getChildAdapterPosition(child))
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

}