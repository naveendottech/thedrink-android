package com.mjdistillers.drinkthedrink.utilities.interfaces


/**
 * Callback for Runtime Permissions System
 * Everything can be handled using this callback only. Because rationale has been handled automatically
 * */
interface PermissionUtilsCallbacks {
    fun permissionGranted(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
}