package com.mjdistillers.drinkthedrink.utilities

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.os.Environment
import android.provider.MediaStore
import java.io.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.content.Intent

/**
 * Class is used to perform operations on content URI
 * */
class ProviderUtils {


    /**
     * Will return the name of file linked to URI with extension
     * */
    fun getNameOfFile(uri: Uri, context: Context): String {
        var uriString = uri.toString()
        var myFile: File? = File(uriString)
        var path = myFile?.absolutePath
        var displayName = ""
        var cursor: Cursor? = null

        if (uriString.startsWith("content://")) {
            try {
                cursor = context.contentResolver.query(
                    uri,
                    null, null, null, null)
                cursor?.let {
                    if (cursor.moveToFirst()) {
                        displayName = cursor.getString(
                            cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        )
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            } finally {
                cursor?.let {
                    cursor.close()
                }
            }
        } else if (uriString.startsWith("file://")) {
            myFile?.let {
                displayName = myFile.name
            }
        }

        return displayName
    }

    /**
     * To Paste file linked to Uri to specified location
     *
     * @rootUri : file path
     * @toPath: path of file where to paste the file.
     *
     * */
    fun pasteFileTo(context: Context, rootUri: Uri, toPath: File?): Boolean {
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null
        var hasCopied = false
        try {
            val content = context.contentResolver
            inputStream = content.openInputStream(rootUri)

            val saveDirectory =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath

//            var outputStream = FileOutputStream(saveDirectory+File.separator+ProviderUtils().getNameOfFile(rootUri,context))
            var outputStream = FileOutputStream(
                saveDirectory.toString() + File.separator + ProviderUtils().getNameOfFile(
                    rootUri,
                    context
                )
            )
            if (outputStream != null) {
                LogUtils.logd("Output Stream Opened successfully")
            }

            if (inputStream != null) {
                val buffer = ByteArray(1000)
                while ((inputStream.read(buffer, 0, buffer.size)) >= 0) {
                    outputStream.write(buffer, 0, buffer.size)
                }
            }
            hasCopied = true
        } catch (e: Exception) {
            hasCopied = false
            e.printStackTrace()
        }
        return hasCopied
    }

    /**
     * get bytes array from the file linked to Uri
     * */
    fun getBytesArray(context: Context, uri: Uri): ByteArray {
        val inputStream = context.contentResolver.openInputStream(uri)
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)

        if (inputStream != null)
        while ((inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, buffer.size)
        }

        return byteBuffer.toByteArray()
    }

    /**
     * UNDER DEVELOPMENT
     * get file path from content URI.
     * Not completly fixed
     * */
    fun getPath(context: Context, uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null) ?: return null
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val s = cursor.getString(column_index)
        cursor.close()
        return s
    }



    fun getRealPathFromURI(contentURI: Uri, context: Context) : String?{
        var result:String?
        var cursor = context.contentResolver.query(contentURI,null, null, null, null)
        if (cursor == null) {
            result = contentURI.path
        } else {
            try {
                cursor.moveToFirst()
                var idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                result = cursor.getString(idx)
                cursor.close()
            }catch (e: java.lang.Exception){
                e.printStackTrace()
                return contentURI.path
            }
        }
        return result
    }


    /**
     * As the name suggests returns bitmap from byte array
    * */
    fun getBitmapFromByteArray(byteArray: ByteArray): Bitmap {
        val arrayInputStream = ByteArrayInputStream(byteArray)
        return BitmapFactory.decodeStream(arrayInputStream)
    }

    /**
     * returns the status of external storage for Read Only check
     * */
    private fun isExternalStorageReadOnly(): Boolean {
        val extStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED_READ_ONLY == extStorageState
    }

    /**
     * returns the status of external storage for Availablity check
     * */
    private fun isExternalStorageAvailable(): Boolean {
        val extStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == extStorageState
    }

    /**
     * Writes in file.
     * File location can be changed as per the requirement
     * */
    fun writeInFile(fileName: String, textBody: String): Boolean {
        var isSaved = false
        try {
            if (isExternalStorageAvailable() && !isExternalStorageReadOnly()) {
                val root = File(Environment.getExternalStorageDirectory(), "Notes")
                if (!root.exists()) {
                    root.mkdirs()
                }

                if (root.exists()) {
                    val myFile = File(root, fileName)
                    val fos = FileOutputStream(myFile)
                    fos.write(textBody.toByteArray())
                    fos.close()
                }
                isSaved = true
            }
        } catch (e: IOException) {
            isSaved = false
            e.printStackTrace()
        }
        return isSaved
    }

    /**
     * Reads from file.
     * File location can be changed as per the requirement
     * */
    fun readFromFile(fileName: String): String {
        var readText = ""
        try {
            if (isExternalStorageAvailable() && !isExternalStorageReadOnly()) {
                var dataString = ""
                val root = File(Environment.getExternalStorageDirectory(), "Notes")
                val myFile = File(root, fileName)
                if (!root.exists()) {
                    dataString = "File do not exists"
                } else {
                    val fis = FileInputStream(myFile)
                    val dateInut = DataInputStream(fis)
                    val br = BufferedReader(InputStreamReader(dateInut))
                    for (line in br.readLine()) {
                        if (line != null)
                            dataString += line
                    }
                    dateInut.close()
                    readText = dataString
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return readText
    }

    /**
     * Scans media for a particular URI
     * */
    fun scanMediaForFile(context: Context, uri: Uri) {
        val scanFileIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri)
        context.sendBroadcast(scanFileIntent)
    }


}