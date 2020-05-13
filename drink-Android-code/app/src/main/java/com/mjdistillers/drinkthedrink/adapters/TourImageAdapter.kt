package com.mjdistillers.drinkthedrink.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.mjdistillers.drinkthedrink.R

class TourImageAdapter(var context: Context) : PagerAdapter()  {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var iv = ImageView(context)
        iv.scaleType = ImageView.ScaleType.FIT_XY

        when(position){
            0->{
                iv.setImageResource(R.drawable.tour_1)
            }
            1->{
                iv.setImageResource(R.drawable.tour_2)
            }
            2->{
                iv.setImageResource(R.drawable.tour_3)
            }
        }

        container.addView(iv)

        return iv
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ImageView)
    }

    override fun getCount(): Int {
        return 3
    }


}