package com.mjdistillers.drinkthedrink.model

import com.google.android.gms.maps.model.MarkerOptions

data class MarkerModel(var markerOptions:MarkerOptions,
                       var isBlue:Boolean,
                       var bar_id:Int?,
                       var miles:Double?,
                       var dollar:Int?,
                       var dollarStr:String?,
                       var barName:String?,
                       var distance:String?,
                       var barImage:String?,
                       var index:Int,
                       var teamList:List<TeamsModel>)