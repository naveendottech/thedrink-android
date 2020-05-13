package com.mjdistillers.drinkthedrink.adapters

import android.content.Context
import android.os.Handler
import android.os.Message
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.mjdistillers.drinkthedrink.App
import com.mjdistillers.drinkthedrink.DtdConstants
import com.mjdistillers.drinkthedrink.R
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import javax.inject.Inject

class ImageAdapter(var context:Context, var listImages: ArrayList<String>, var handler: Handler?) : PagerAdapter() {

    @Inject
    lateinit var dtdUtil: DtdUtils

    init {
        App.app.getComponent().inject(this)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var iv = ImageView(context)
            iv.scaleType = ImageView.ScaleType.FIT_XY

        dtdUtil.loadImageNoTransform(context,iv,
            DtdConstants.IMAGE_BASE_URL+listImages[position],
            DtdConstants.PLACEHOLDER_NO_IMAGE)

    if (handler != null) {
        iv.setOnClickListener {
            var message = Message.obtain(handler)
            message.arg1 = DtdConstants.SHOW_IMAGES_PAGER
            message.arg2 = position
            message.obj = listImages
            message.sendToTarget()
        }
    }
            container.addView(iv)

        return iv
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ImageView)
    }

    override fun getCount(): Int {
            return listImages.size
    }

}