package com.mjdistillers.drinkthedrink

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.telephony.SmsManager
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import com.mjdistillers.drinkthedrink.utilities.*
import com.mjdistillers.drinkthedrink.utilities.constants.IntConstants
import com.mjdistillers.drinkthedrink.utilities.interfaces.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


open class BaseActivity : AppCompatActivity(),
    View.OnClickListener
{

    private var permissionUtilsCallbacks: PermissionUtilsCallbacks? = null
    private var pickImageCallbacks: PickImageCallbacks? = null
    private var pickFileCallbacks: PickFileCallbacks? = null
    private var viewId: Int = 0
    var outputUri: Uri? = null
    private var requestCode: Int = -1

    override fun onClick(v: View?) {

    }

    /**
     * Method is used to disable screenshots
     * */
    fun disableScreenShots() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }

  /*  fun addFragment(
        fragment: Fragment,
        tagFragment: String,
        tagBackstack: String = "",
        tagTransition: String = DtdConstants.TAG_NONE_TRANS
    ) {
        var transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (!tagFragment.isEmpty()) {
            if (tagTransition != DtdConstants.TAG_NONE_TRANS) {
                var transition = getTransitionToUse(tagTransition)
                fragment.reenterTransition = transition
//                fragment.exitTransition = transition
            }
            transaction.add(R.id.flContainer, fragment, tagFragment)
            if (!tagBackstack.isEmpty())
                transaction.addToBackStack(StringConsts.TAG_ADD_PREFIX + tagFragment)
            transaction.commit()
        } else {
            LogUtils.loge(resources.getString(R.string.fragment_transaction_was_not_committed))
        }
    }
*/
    /*fun removeFragment(fragment: Fragment?, tagBackstack: String, tagTransition: String = DtdConstants.TAG_NONE_TRANS) {
        var transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (fragment != null) {
            if (tagTransition != StringConsts.TAG_NONE_TRANS) {
                var transition = getTransitionToUse(tagTransition)
                fragment.enterTransition = transition
                fragment.exitTransition = transition
            }
            transaction.remove(fragment)
            if (!tagBackstack.isEmpty())
                transaction.addToBackStack(StringConsts.TAG_REMOVE_PREFIX + tagBackstack)
            transaction.commit()
        } else {
            LogUtils.loge(resources.getString(R.string.fragment_transaction_was_not_committed))
        }
    }
*/
   /* fun replaceFragment(
        fragment: Fragment?,
        tagFragment: String,
        tagBackstack: String,
        tagTransition: String = DtdConstants.TAG_NONE_TRANS) {
        var transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (fragment != null && !tagFragment.isEmpty()) {
            if (tagTransition != DtdConstants.TAG_NONE_TRANS) {
                var transition = getTransitionToUse(tagTransition)
                fragment.enterTransition = transition
                fragment.exitTransition = transition
            }
            transaction.replace(R.id.flContainer, fragment, tagFragment)
            if (!tagBackstack.isEmpty())
                transaction.addToBackStack(DtdConstants.TAG_REPLACE_PREFIX + tagBackstack)
            transaction.commit()
        } else {
            LogUtils.loge(resources.getString(R.string.fragment_transaction_was_not_committed))
        }
    }*/

/*

    fun attachFragment(fragment: Fragment?, tagBackstack: String, tagTransition: String = DtdConstants.TAG_NONE_TRANS) {
        var transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (fragment != null) {
            if (tagTransition != StringConsts.TAG_NONE_TRANS) {
                var transition = getTransitionToUse(tagTransition)
                fragment.enterTransition = transition
                fragment.exitTransition = transition
            }
            transaction.attach(fragment)
            if (!tagBackstack.isEmpty())
                transaction.addToBackStack(StringConsts.TAG_ATTACH_PREFIX + tagBackstack)
            transaction.commit()
        } else {
            LogUtils.loge(resources.getString(R.string.fragment_transaction_was_not_committed))
        }
    }

    fun detachFragment(fragment: Fragment?, tagBackstack: String, tagTransition: String = DtdConstants.TAG_NONE_TRANS) {
        var transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (fragment != null) {
            if (tagTransition != StringConsts.TAG_NONE_TRANS) {
                var transition = getTransitionToUse(tagTransition)
                fragment.enterTransition = transition
                fragment.exitTransition = transition
            }
            transaction.detach(fragment)
            if (!tagBackstack.isEmpty())
                transaction.addToBackStack(StringConsts.TAG_DETACH_PREFIX + tagBackstack)
            transaction.commit()
        } else {
            LogUtils.loge(resources.getString(R.string.fragment_transaction_was_not_committed))
        }
    }


    fun hideFragment(fragment: Fragment?, tagBackstack: String, tagTransition: String = DtdConstants.TAG_NONE_TRANS) {
        var transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (fragment != null) {
            if (tagTransition != StringConsts.TAG_NONE_TRANS) {
                var transition = getTransitionToUse(tagTransition)
                fragment.enterTransition = transition
                fragment.exitTransition = transition
            }
            transaction.hide(fragment)
            if (!tagBackstack.isEmpty())
                transaction.addToBackStack(StringConsts.TAG_HIDE_PREFIX + tagBackstack)
            transaction.commit()
        } else {
            LogUtils.loge(resources.getString(R.string.fragment_transaction_was_not_committed))
        }
    }

    fun showFragment(fragment: Fragment?, tagBackstack: String, tagTransition: String = DtdConstants.TAG_NONE_TRANS) {
        var transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (fragment != null) {
            if (tagTransition != StringConsts.TAG_NONE_TRANS) {
                var transition = getTransitionToUse(tagTransition)
                fragment.enterTransition = transition
                fragment.exitTransition = transition
            }
            transaction.show(fragment)
            if (!tagBackstack.isEmpty())
                transaction.addToBackStack(StringConsts.TAG_SHOW_PREFIX + tagBackstack)
            transaction.commit()
        } else {
            LogUtils.loge(resources.getString(R.string.fragment_transaction_was_not_committed))
        }
    }

    fun popBackStack() {
        supportFragmentManager.popBackStack()
    }

    fun popBackStackInclusive(backStackTag: String) {
        supportFragmentManager.popBackStack(backStackTag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    fun popBackStackExclusive(backStackTag: String) {
        supportFragmentManager.popBackStack(backStackTag, 0)
    }
*/

    fun getSlideTransition(): Transition {
        var transition: Transition = TransitionInflater.from(this).inflateTransition(R.transition.slide)
        transition.duration = resources.getInteger(R.integer.anim_duration_medium).toLong()
        return transition
    }

    fun getExplodeTransition(): Transition {
        var transition: Transition = TransitionInflater.from(this).inflateTransition(R.transition.explode)
        transition.duration = resources.getInteger(R.integer.anim_duration_medium).toLong()
        return transition
    }

    fun getFadeTransition(): Transition {
        var transition: Transition = TransitionInflater.from(this).inflateTransition(R.transition.fade)
        transition.duration = resources.getInteger(R.integer.anim_duration_medium).toLong()

        return transition
    }

    fun getTransitionToUse(transitionTag: String): Transition {
        if (transitionTag == DtdConstants.TAG_SLIDE_TRANS) {
            return getSlideTransition()
        } else if (transitionTag == DtdConstants.TAG_EXPLODE_TRANS) {
            return getExplodeTransition()
        } else
            return getFadeTransition()
    }

   /* override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
*/


    /**
     * Method for Requesting runtime permissions
     * */
    fun requestPermission(callbacks: PermissionUtilsCallbacks,
                          permissions: Array<out String>,
                          rationale: Array<String>,
                          requestCode: Int){

        this.requestCode = requestCode
        this.permissionUtilsCallbacks = callbacks

        var grantResults = IntArray(permissions.size)
        var shouldShowRationale = IntArray(permissions.size)


        for ((count, permission) in permissions.withIndex()){
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                grantResults[count] = 1
            }else{
                grantResults[count] = 0
            }

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                shouldShowRationale[count] = 1
            }else{
                shouldShowRationale[count] = 0
            }
        }

        when {
            shouldShowRationale.contains(1) -> {
                AlertUtils().showInformationAlert(this, "Permission Need",
                    rationale[shouldShowRationale.indexOf(1)],
                    "Ok",
                    object : InformationAlertCallbacks {
                        override fun eventOkOfInformationAlert() {
                            ActivityCompat.requestPermissions(this@BaseActivity, permissions, requestCode)
                        }
                    }).show()
            }
            grantResults.contains(0) -> {
                ActivityCompat.requestPermissions(this, permissions, requestCode)
            }
            else -> {
                callbacks.permissionGranted(requestCode,permissions,grantResults)
            }
        }
    }


    fun pickImageFromMedia(pickImageCallbacks: PickImageCallbacks) {
        this.pickImageCallbacks = pickImageCallbacks
        var pickPhoto = Intent(
            Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickPhoto, IntConstants.PICK_IMAGE_MEDIA_REQUEST_CODE)
    }

    /**
     * Create an image file uri for the image Captured from camera.
     * */
    fun createImageFileUri(): Uri {
        var timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var imageFileName = "JPEG_" + timeStamp + "_"
//        var storageDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),"Camera")
        var dir =  File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "dtdFiles")
        dir.mkdirs()

          var file =   File(dir,File.separator+imageFileName+".jpg")
            file.createNewFile()
        var uritoreturn = FileProvider.getUriForFile(
            this,
            applicationContext.packageName + ".utilities.providers",
            file)



        return uritoreturn
    }

    fun pickImageFromCamera(pickImageCallbacks: PickImageCallbacks) {
        this.pickImageCallbacks = pickImageCallbacks
        pickImageFromCameraOpen()
    }

    fun sendSMS(phoneNumber: String, message: String) {
        var sms = SmsManager.getDefault()
        sms.sendTextMessage(phoneNumber, null, message, null, null)
    }

/*    fun makeCall(phoneNumber: String) {
        var intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$phoneNumber")
        startActivity(intent)
    }*/

    fun sendEmail(recipientsArray: Array<String>, subject: String, body: String) {
        var i = Intent(Intent.ACTION_SEND)
        i.type = "message/rfc822"
        i.putExtra(Intent.EXTRA_EMAIL, recipientsArray)
        i.setPackage("com.google.android.gm")
        i.putExtra(Intent.EXTRA_SUBJECT, subject)
        i.putExtra(Intent.EXTRA_TEXT, body)
        try {
            startActivity(Intent.createChooser(i, getString(R.string.send_mail)))
        } catch (ex: Exception) {
            ex.printStackTrace()
            Toast.makeText(
                this,
                getString(R.string.no_email_clients),
                Toast.LENGTH_SHORT).show()
        }
    }

    fun openInBrowser(url: String) {
        if (ValidationUtils().isValidURL(url)) {
            var browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        } else {
            ToastSnackUtils.toastShort(this, getString(R.string.invalid_url))
        }
    }

    fun shareText(message: String, appFlag: Int) {
        var intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"

        when (appFlag) {
            IntConstants.APP_FLAG_WHATS_APP -> {
                intent.setPackage("com.whatsapp")
            }
            IntConstants.APP_FLAG_TWITTER -> {
                intent.setPackage("com.twitter.android")
            }
            IntConstants.APP_FLAG_FACEBOOK -> {
                intent.setPackage("com.facebook.katana")
            }
            IntConstants.APP_FLAG_LINKED_IN_ -> {
                intent.setPackage("com.linkedin.android")
            }
            IntConstants.APP_FLAG_GOOGLE_PLUS -> {
                intent.setPackage("com.google.android.apps.plus")
            }
            IntConstants.APP_FLAG_ANY -> {
                // Nothing to do
            }
        }

        if (intent != null) {
            intent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(Intent.createChooser(intent, getString(R.string.send_text)))
        } else {
            ToastSnackUtils.toastShort(this, getString(R.string.no_app_found))
        }
    }

     private fun pickImageFromCameraOpen() {
        var takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
//            var photoUri: Uri? = null
//            try {
//                photoUri = createImageFileUri()
//            } catch (ex: IOException) {
//                ex.printStackTrace()
//            }
//            if (photoUri != null) {
//                outputUri = photoUri
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
//                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivityForResult(takePictureIntent, IntConstants.PICK_IMAGE_CAMERA_REQUEST_CODE)
            }
        }


    fun getResizedBitmap(bm: Bitmap, newHeight: Int, newWidth: Int): Bitmap? {
        val width = bm.width
        val height = bm.height
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false)
    }


    fun saveImageFromBitmap(bitmap: Bitmap): File?{

        var timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val bytes = ByteArrayOutputStream()
        try {
//            var resizedBitmap =  getResizedBitmap(bitmap,300,300)
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 80, bytes)

//            var dir = File(
//                Environment.getExternalStoragePublicDirectory(
//                    Environment.DIRECTORY_DCIM
//                ), "dtdFiles")

            var dir = App.app.getDir("imgupload", Context.MODE_PRIVATE)

            dir.mkdirs()

            var file = File(dir.absolutePath+File.separator+timeStamp+".jpg")
            file.createNewFile()

            val fo = FileOutputStream(file)
            fo.write(bytes.toByteArray())

            fo.close()

            return file
        }catch (e: java.lang.Exception){
            return  null
        }
        return  null
    }



    /**
     * Picking a file device storage
     * */
    fun pickFile(pickFileCallbacks: PickFileCallbacks, viewId: Int) {
        this.pickFileCallbacks = pickFileCallbacks
        this.viewId = viewId
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
//           intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.addCategory(Intent.CATEGORY_OPENABLE)

        try {
            startActivityForResult(
                Intent.createChooser(intent, ""),
                IntConstants.PICK_FILE_REQUEST_CODE
            )
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(
                this, getString(R.string.PLZ_INSTALL_FILE_MANAGER),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * Method is used to pick folder. It returns a tree URI
     * we cann't use that URI to create an image.
     * */
    fun pickFolder(pickFileCallbacks: PickFileCallbacks, viewId: Int) {
        this.pickFileCallbacks = pickFileCallbacks
        this.viewId = viewId
        var i = Intent(Intent.ACTION_OPEN_DOCUMENT)
        i.addCategory(Intent.CATEGORY_DEFAULT)
        startActivityForResult(
            Intent.createChooser(i, getString(R.string.choose_directory)),
            IntConstants.PICK_DIRECTORY_REQUEST_CODE)
    }

    /**
     * OnRequestPermissionResultCallback
     * Please don't make any change here for any kind of permission or from anywhere.
     * */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        var arrayPermissions = IntArray(grantResults.size)
        for ((index,permission) in grantResults.withIndex()){
            if (permission == 0)
                arrayPermissions[index] = 1
            else
                arrayPermissions[index] = 0
        }
         permissionUtilsCallbacks?.permissionGranted(requestCode,permissions,arrayPermissions)
         super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    /**
     * This Method handles returned intents from any startActivityForResult we are using in this app.
     * */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IntConstants.PICK_IMAGE_CAMERA_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.let {
                        var bitmap = data.extras?.get("data") as Bitmap
                        var file = saveImageFromBitmap(bitmap)
                        var bitmapp =  ScalingUtilities.compressImage(file,40)
                        pickImageCallbacks?.resultPickFromCamera(saveImageFromBitmap(bitmapp))
                    }
                }
            }

            IntConstants.PICK_IMAGE_MEDIA_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    var uri:Uri? = data?.data
                    uri?.let {
                        var path  = ProviderUtils().getRealPathFromURI(uri,this)

//                        var bitmapp = ScalingUtilities.decodeFile(path,DtdConstants.UPLOAD_IMAGE_SIZE,
//                            DtdConstants.UPLOAD_IMAGE_SIZE, ScalingUtilities.ScalingLogic.FIT)

                        try {
                            var file = File(path)
                            var bitmapp = ScalingUtilities.compressImage(file, 40)
                            pickImageCallbacks?.resultPickFromMedia(saveImageFromBitmap(bitmapp))
                        }catch (e: java.lang.Exception){
                            e.printStackTrace()
                            ToastSnackUtils.toastShort(this,resources.getString(R.string.choose_valid_image))
                        }
                    }
                }
            }

            IntConstants.PICK_FILE_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    pickFileCallbacks?.resultPickFile(data, viewId)
                }
            }

            IntConstants.PICK_DIRECTORY_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    pickFileCallbacks?.resultPickFile(data, viewId)
                }
            }
        }
    }

}
