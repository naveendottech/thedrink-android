package com.mjdistillers.drinkthedrink;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.mjdistillers.drinkthedrink.model.MarkerModel;

public class MapMarkerBounce implements GoogleMap.OnMarkerClickListener {

//    private final Handler mHandler;
    private Runnable mAnimation;
    private Handler handlerMarker;

    public MapMarkerBounce(Handler markerHandler) {
        handlerMarker = markerHandler;
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        final long start = SystemClock.uptimeMillis();
        final long duration = 1500L;

//        mHandler.removeCallbacks(mAnimation);

//            mAnimation = new BounceAnimation(start, duration, marker, mHandler);

//            mHandler.post(mAnimation);

        if (marker.getTag() instanceof MarkerModel){
            MarkerModel model = (MarkerModel) marker.getTag();

            Message message = new Message();
            message.obj = model;

            handlerMarker.sendMessage(message);
        }

        return false;
    }

    /**
     * Performs a bounce animation on a {@link Marker}.
     */
    private static class BounceAnimation implements Runnable {

        private final long mStart, mDuration;
        private final Interpolator mInterpolator;
        private final Marker mMarker;
        private final Handler mHandler;

        private BounceAnimation(long start,
                                long duration,
                                Marker marker,
                                Handler handler) {
            mStart = start;
            mDuration = duration;
            mMarker = marker;
            mHandler = handler;
            mInterpolator = new BounceInterpolator();
        }

        @Override
        public void run() {
            long elapsed = SystemClock.uptimeMillis() - mStart;
            float t = Math.max(1 - mInterpolator.getInterpolation(
                    (float) elapsed / mDuration), 0f);
            mMarker.setAnchor(0.5f, 1.0f + 1.2f * t);

            if (t > 0.0) {
                // Post again 16ms later.
                mHandler.postDelayed(this, 16l);
            }
        }
    }
}