package com.mjdistillers.drinkthedrink.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.mjdistillers.drinkthedrink.App
import com.mjdistillers.drinkthedrink.DtdConstants
import com.mjdistillers.drinkthedrink.R
import com.mjdistillers.drinkthedrink.model.MarkerModel
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import javax.inject.Inject


class CustomInfoWindowMaps(var context: Context) : GoogleMap.InfoWindowAdapter {


    @Inject
    lateinit var dtdUtils: DtdUtils

    lateinit var view:View

    init {
        App.app.getComponent().inject(this)
    }

    override fun getInfoContents(p0: Marker?): View? {
        return null
    }

    override fun getInfoWindow(p0: Marker?): View {
        view = LayoutInflater.from(context).inflate(R.layout.layout_maps_info_window,null)
        var tvName = view.findViewById<TextView>(R.id.tvBarName)
        var tvDistance = view.findViewById<TextView>(R.id.tvDistance)
        var ivCross = view.findViewById<ImageView>(R.id.ivCross)
        var tvDistanceTitle = view.findViewById<TextView>(R.id.tvDistanceTitle)

        applyFonts(tvName,tvDistance,tvDistanceTitle)

        if (p0?.tag == DtdConstants.TAG_NONE_TRANS) {
            return View(context)
        }
            var markerModel = p0?.tag as MarkerModel

            tvName.text = markerModel.barName
            tvDistance.text = markerModel.distance + " Miles"


        return view
    }


    private fun applyFonts(tvOne:TextView, tvTwo: TextView,tvThree: TextView){
        tvOne.typeface = dtdUtils.fontNeutraMedium()
        tvTwo.typeface = dtdUtils.fontNeutraMedium()
        tvThree.typeface = dtdUtils.fontFuturaBook()
    }


}