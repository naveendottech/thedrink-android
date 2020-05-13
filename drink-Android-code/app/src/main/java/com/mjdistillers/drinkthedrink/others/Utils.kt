package com.mjdistillers.drinkthedrink.others

import android.graphics.Point
import android.os.Handler
import android.os.SystemClock
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.Projection
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class Utils {

    private var animationHandler = MarkerAnimationHandler("MarkAniH")
//    public var handler = Handler(animationHandler.looper)

    fun startDropMarkerAnimation(map: GoogleMap?,marker: Marker?) {
        if (map != null && marker != null) {
            val target = marker.position
            val start = SystemClock.uptimeMillis()
            val proj: Projection = map.projection

            val startPoint: Point = proj.toScreenLocation(marker.position)
            startPoint.y = 0
            val interpolator = LinearOutSlowInInterpolator()

            var handler = Handler()

            val startLatLng = proj.fromScreenLocation(startPoint)
            val targetPoint: Point = proj.toScreenLocation(target)

            val duration = (200 + targetPoint.y * 0.6).toFloat()

            handler.post(object : Runnable {
                override fun run() {
                    val elapsed = SystemClock.uptimeMillis() - start
                    val t: Float =
                        interpolator.getInterpolation(elapsed.toFloat() / duration)
                    val lng = t * target.longitude + (1 - t) * startLatLng.longitude
                    val lat = t * target.latitude + (1 - t) * startLatLng.latitude
                    marker.position = LatLng(lat,lng)
                    if (t < 1.0) { // Post again 16ms later == 60 frames per second
                        handler.postDelayed(this, 500)
                    }
                }
            })
        }
    }
}