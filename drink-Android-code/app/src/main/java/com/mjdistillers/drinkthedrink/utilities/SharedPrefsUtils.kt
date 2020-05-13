package com.mjdistillers.drinkthedrink.utilities

import android.content.Context
import com.mjdistillers.drinkthedrink.DtdConstants
import com.mjdistillers.drinkthedrink.DtdPrefsKeys

class SharedPrefsUtils(var context: Context) {

    var prefs = context.getSharedPreferences(DtdConstants.SHARED_PREF_FILE_NAME,Context.MODE_PRIVATE)
    private var deviceTokenPrefs = context.getSharedPreferences(DtdConstants.SHARED_PREF_DEVICE_TOKEN_FILE_NAME,
        Context.MODE_PRIVATE)



    fun saveString(key:String, value:String?){
        var str:String = value ?: ""
        prefs.edit().putString(key,str).commit()
    }

    fun saveInt(key:String, value:Int?){
        var inte:Int = value ?: 0
        prefs.edit().putInt(key,inte).commit()
    }

    fun saveFloat(key:String, value:Float?){
        var floatee:Float = value ?: 0.0f
        prefs.edit().putFloat(key,floatee).commit()
    }

    fun saveBoolean(key:String, value:Boolean?){
        var boolle:Boolean = value ?: false
        prefs.edit().putBoolean(key,boolle).commit()
    }


    fun getString(key:String): String {
        var str = prefs.getString(key,"")
        if (str == null) str=""
        return str
    }

    fun getInt(key:String): Int{
        return prefs.getInt(key,0)
    }

    fun getFloat(key:String):Float{
        return prefs.getFloat(key,0.0f)
    }

    fun getBoolean(key:String):Boolean{
        return prefs.getBoolean(key,false)
    }

    fun saveDeviceToken(deviceToken: String){
        deviceTokenPrefs.edit().putString(DtdPrefsKeys.Keys.DEVICE_TOKEN,deviceToken).commit()
    }

    fun getDeviceToken():String{
        var token = deviceTokenPrefs.getString(DtdPrefsKeys.Keys.DEVICE_TOKEN,"")
        return if (token != null && token.isNotEmpty()) token
        else ""
    }

    fun clearPrefrences() : Boolean{
        return prefs.edit().clear().commit()
    }


}