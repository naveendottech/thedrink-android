package com.mjdistillers.drinkthedrink.utilities

import android.util.Base64
import java.lang.StringBuilder
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.charset.Charset
import java.security.MessageDigest

/**
 * Contains all the encryption methods as per the name suggests
 * */
class CryptoUtils {

    var byteBuffer8: ByteBuffer? = null
    var byteBuffer16: ByteBuffer? = null

    fun encryptUTF8(string: String): String {
        var charset = Charset.forName("UTF-8")
        byteBuffer8 = charset.newEncoder().encode(CharBuffer.wrap(string))
        return "Encrypted Byte buffer object : $byteBuffer8"
    }

    fun encryptMD5(string: String): String {
        var md = MessageDigest.getInstance("MD5")
        md.update(string.toByteArray())
        var bytes = md.digest()
        var sb = StringBuilder()
        for (byte in bytes) {
            sb.append(String.format("%02x", byte))
        }
        return sb.toString()
    }

    fun encryptSHA256(string: String): String {
        var md = MessageDigest.getInstance("SHA-256")
        md.update(string.toByteArray())
        var bytes = md.digest()
        var sb = StringBuilder()
        for (byte in bytes) {
            sb.append(String.format("%02x", byte))
        }
        return sb.toString()
    }




    fun decryptUTF8(string: String): String {
        var combinedStringOfChars:String
        combinedStringOfChars = if (byteBuffer8 != null) {
            var charset = Charset.forName("UTF-8")
            var stringChars = charset.newDecoder().decode(byteBuffer8).array()
            String(stringChars)
        }else{
            "Nothing to decode"
        }
        return combinedStringOfChars
    }

    fun encryptBase64(string: String): String {
        var bytes = Base64.encodeToString(string.toByteArray(), Base64.DEFAULT)
        return bytes
    }


    fun encryptUTF16(string: String): String {
        var charset = Charset.forName("UTF_16")
        byteBuffer16 = charset.newEncoder().encode(CharBuffer.wrap(string))
        return "Encrypted Byte buffer object : $byteBuffer16"
    }

    fun decryptUTF16(string: String): String {
        var combinedStringOfChars:String
        combinedStringOfChars = if (byteBuffer16 != null) {
            var charset = Charset.forName("UTF_16")
            var stringChars = charset.newDecoder().decode(byteBuffer16).array()
            String(stringChars)
        }else{
            "Nothing to decode"
        }
        return combinedStringOfChars
    }

    fun decryptBase64(string: String): String {
        var bytes = Base64.decode(string.toByteArray(), Base64.DEFAULT)
        var string = String(bytes, Charset.defaultCharset())
        return string
    }


}