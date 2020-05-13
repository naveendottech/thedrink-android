package com.mjdistillers.drinkthedrink.utilities

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.mjdistillers.drinkthedrink.DtdConstants
import com.mjdistillers.drinkthedrink.R
import java.util.regex.Matcher
import java.util.regex.Pattern

class Utilities {

    fun saveIntPrefs(context: Context, key: String, value: Int) {
        var preferences: SharedPreferences = context.getSharedPreferences(
            DtdConstants.SHARED_PREF_FILE_NAME,
                Context.MODE_PRIVATE)
        preferences.edit().putInt(key, value).commit();
    }

    fun getIntPrefs(context: Context, key: String): Int {
        return context.getSharedPreferences(DtdConstants.SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE).getInt(key, 0)
    }

    fun validateCredentials(context: Context,username: String, password: String): Boolean {
        return if (username.isEmpty() && password.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.txt_enter_username_and_password), Toast.LENGTH_SHORT).show()
            false
        }else if (username.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.txt_enter_username), Toast.LENGTH_SHORT).show()
            false
        }else if (password.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.txt_enter_password), Toast.LENGTH_SHORT).show()
            false
        }else {
              if (validateUsername(username) && validatePassword(password)) true
            else {
                  Toast.makeText(context, context.getString(R.string.txt_invalid_credentials), Toast.LENGTH_SHORT).show()
                  false
              }
        }
    }

    private fun validateUsername(username: String): Boolean {
        var isUsernameValid: Boolean
        var patternPhone: Pattern = Pattern.compile("(0|91)?[6-9][0-9]{9}")
        var patternEmail: Pattern = Pattern.compile("[\\w][\\w._]*@[\\w]+([.][a-zA-Z]+)+")
        var matcherPhone: Matcher = patternPhone.matcher(username)
        var matcherEmail: Matcher = patternEmail.matcher(username)

        return true

//       if ((username.equals(StringConsts.VALID_PHONE_NUMBER)) || (matcherEmail.find() && matcherEmail.group().equals(username))) {
//            return true
//        }else
//           return false
//        isUsernameValid = (matcherPhone.find() && matcherPhone.group().equals(username))
    }

    fun getDisplayHeightAndWidthInDP(context: Context): List<Int>{
        val displayMetrics = context.resources.displayMetrics
        val dpHeight = displayMetrics.heightPixels / displayMetrics.density as Int
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density as Int
        var widthHeight = mutableListOf<Int>()

        widthHeight.add(dpWidth as Int)
        widthHeight.add(dpHeight as Int)

        return widthHeight
    }

    fun getHeightInDp(context: Context, dp: Int): Int{
        val metrics = context.resources.displayMetrics
        val dp = dp
        val fpixels = metrics.density * dp
        val pixels = (fpixels + 0.5f).toInt()

        return pixels
    }

    private fun validatePassword(password: String): Boolean {
        return password == DtdConstants.VALID_PASSWORD
    }

}