package com.mjdistillers.drinkthedrink.others;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomNestedScrollView extends androidx.core.widget.NestedScrollView {


    public CustomNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        getParent().requestDisallowInterceptTouchEvent(true);

        return super.onTouchEvent(ev);
    }

}
