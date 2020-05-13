package com.mjdistillers.drinkthedrink.utilities

import android.content.Context
import android.widget.Toast
import com.mjdistillers.drinkthedrink.R
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.net.URL


class ValidationUtils {

    /**
     * Checks the uri is valid or not
     * */
    fun isValidURL(url: String): Boolean {
        return try {
            URL(url).toURI()
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * validates the password and username
     * */
    fun validateCredentials(context: Context, username: String, password: String): Boolean {
        return if (username.isEmpty() && password.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.txt_enter_username_and_password), Toast.LENGTH_SHORT)
                .show()
            false
        } else if (username.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.txt_enter_username), Toast.LENGTH_SHORT).show()
            false
        } else if (password.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.txt_enter_password), Toast.LENGTH_SHORT).show()
            false
        } else {
            if (validateUsername(username) && validatePassword(password)) true
            else {
                Toast.makeText(context, context.getString(R.string.txt_invalid_credentials), Toast.LENGTH_SHORT).show()
                false
            }
        }
    }

    /**
     * validates the username:
     * Username can be Mail or Contact Number.
     * */
    private fun validateUsername(username: String): Boolean {
        var isUsernameValid: Boolean
        var patternPhone: Pattern = Pattern.compile("(0|91)?[6-9][0-9]{9}")
        var patternEmail: Pattern = Pattern.compile("[\\w][\\w._]*@[\\w]+([.][a-zA-Z]+)+")
        var matcherPhone: Matcher = patternPhone.matcher(username)
        var matcherEmail: Matcher = patternEmail.matcher(username)

        return if ((matcherEmail.find() && matcherEmail.group() == username)) {
            true
        } else (matcherPhone.find() && matcherPhone.group() == username)
    }


    /**
     * Validates password
     * Logic can be implemented as per the requirement
     * */
    private fun validatePassword(password: String): Boolean {
        return true
    }

}