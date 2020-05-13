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
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import javax.inject.Inject

class AdvertisementAdapter(var context: Context, var listImages: List<String>,var links: List<String>, var handler: Handler):PagerAdapter()
{

    @Inject
    lateinit var dtdUtils: DtdUtils
    init {
        App.app.getComponent().inject(this)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var iv = ImageView(context)
        iv.scaleType = ImageView.ScaleType.FIT_XY

        dtdUtils.loadImageNoTransform(context,iv,listImages[position],DtdConstants.PLACEHOLDER_NO_IMAGE)

        iv.setOnClickListener {
            if(links.isNotEmpty()){
                if (links.size-1 >= position){
                    var mes = Message.obtain(handler)
                    mes.arg1 = DtdConstants.SHOW_EXTERNAL_BROWSER
                    mes.obj = links[position]
                    mes.sendToTarget()
                }else{
                    var mes = Message.obtain(handler)
                    mes.arg1 = DtdConstants.SHOW_EXTERNAL_BROWSER
                    mes.obj = links[0]
                    mes.sendToTarget()
                }
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