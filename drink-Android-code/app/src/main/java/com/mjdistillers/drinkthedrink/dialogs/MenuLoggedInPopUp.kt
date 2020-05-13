package com.mjdistillers.drinkthedrink.dialogs

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
import android.widget.PopupWindow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.mjdistillers.drinkthedrink.App
import com.mjdistillers.drinkthedrink.DtdConstants
import com.mjdistillers.drinkthedrink.MainActivity
import com.mjdistillers.drinkthedrink.R
import com.mjdistillers.drinkthedrink.databinding.LayoutDropDownLogInBinding
import com.mjdistillers.drinkthedrink.model.request.GetUserVisibilityChange
import com.mjdistillers.drinkthedrink.model.response.user_visibility.GetUserVisibilityResponse
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import com.mjdistillers.drinkthedrink.utilities.SharedPrefsUtils
import javax.inject.Inject


class MenuLoggedInPopUp(var context: Context, var mainHandler: Handler) {

    var prefs = SharedPrefsUtils(context)
    private var popUp: PopupWindow
    var dropDownBinding:LayoutDropDownLogInBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
        R.layout.layout_drop_down_log_in, null, false)

    @Inject
    lateinit var dtdUtils:DtdUtils

    init {
        var checkedChange = ClickEventsMenuPopUp()
        dropDownBinding?.clickEventsMenuDropDown = checkedChange
        dropDownBinding?.checkChanged = checkedChange.onCheckedChange()

        dropDownBinding?.tvName.text = prefs.getString(DtdConstants.NAME)
        dropDownBinding?.tvFollowers.text = prefs.getInt(DtdConstants.FOLLOW_BY).toString()
        dropDownBinding?.tvFollowing.text = prefs.getInt(DtdConstants.FOLLOW_TO).toString()
//        dropDownBinding?.tvStatus.text = prefs.getString(DtdConstants.MY_STATUS)

        dropDownBinding?.swOnOff.isChecked = prefs.getString(DtdConstants.VISIBILITY_STATUS) != "0"

        App.app.getComponent().inject(this)

        dtdUtils.loadImageWithCircleTransform(context, dropDownBinding?.ivProfileImage,
            DtdConstants.IMAGE_BASE_URL+DtdConstants.PROFILE_FOLDER_NAME+prefs.getString(DtdConstants.PROFILE_IMAGE),
            DtdConstants.PLACEHOLDER_NO_IMAGE)

        popUp = PopupWindow(
            dropDownBinding.root, ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT)


        popUp.isOutsideTouchable = true


//        var visStatus = prefs.getString(DtdConstants.VISIBILITY_STATUS)

        var observer = Observer<GetUserVisibilityResponse> {

            var changeVisi = if (it.checked){ "1"} else{ "0"}
            var messageStr = it?.data?.message?.get(0)

            if (messageStr == null) messageStr = ""
            if (it.status) {
                prefs.saveString(DtdConstants.VISIBILITY_STATUS,changeVisi)

                var message = mainHandler.obtainMessage()
                message.arg1 = DtdConstants.SHOW_SNACKBAR
                message.obj = messageStr
                message.sendToTarget()

            }else{

                var message = mainHandler.obtainMessage()
                message.arg1 = DtdConstants.SHOW_SNACKBAR
                message.arg2 = 1
                message.obj = messageStr
                message.sendToTarget()


                prefs.saveString(DtdConstants.VISIBILITY_STATUS,changeVisi)
            }

            var visibilit = prefs.getString(DtdConstants.VISIBILITY_STATUS)
            dropDownBinding.swOnOff.isChecked = visibilit == "1"
        }

        (context as MainActivity).viewModel.liveDataUpdateUserVisiblity()
            .observe(context as MainActivity, observer)

        applyFonts(dropDownBinding)
    }

    private fun applyFonts(binding: LayoutDropDownLogInBinding) {

        binding.tvInviteFrnds.typeface  = dtdUtils.fontNeutraMedium()
        binding.tvContacts.typeface = dtdUtils.fontNeutraMedium()
        binding.tvLogout.typeface = dtdUtils.fontNeutraMedium()
        binding.tvEditProfile.typeface = dtdUtils.fontNeutraMedium()
        binding.tvVisibleInvisible.typeface = dtdUtils.fontNeutraMedium()
        binding.tvStatus.typeface = dtdUtils.fontNeutraMedium()
        binding.tvMessage.typeface = dtdUtils.fontNeutraMedium()
        binding.tvInviteFrnds.typeface = dtdUtils.fontNeutraMedium()
        binding.tvContacts.typeface = dtdUtils.fontNeutraMedium()
        binding.tvLogout.typeface = dtdUtils.fontNeutraMedium()


        binding.tvName.typeface = dtdUtils.fontNeutraMedium()
        binding.tvFollowing.typeface = dtdUtils.fontNeutraMedium()
        binding.tvFollowers.typeface = dtdUtils.fontNeutraMedium()
    }


    fun getPopUp(): PopupWindow {
        return popUp
    }

    fun provideViewHeight():Int{
        return dropDownBinding.root.height
    }

    inner class ClickEventsMenuPopUp {

        fun onClickInviteFrnds(view: View) {

            var msg = Message()
            msg.arg1 = DtdConstants.SHOW_INVITE_PEOPLE
            mainHandler.sendMessage(msg)
            popUp.dismiss()
        }

        fun onClickFeedback(view: View) {
            var msg = Message()
            msg.arg1 = DtdConstants.SHOW_FEEDBACK
            mainHandler.sendMessage(msg)
            popUp.dismiss()
        }

        fun onClickPickLocation(view: View){

        }

        fun onClickMyStatus(view: View){
            var msg = Message()
            msg.arg1 = DtdConstants.SHOW_SEARCH_PEOPLE
//            msg.obj = DtdPrefsKeys.Values.FROM_MY_STATUS
            mainHandler.sendMessage(msg)

            popUp.dismiss()
        }

        fun onClickContacts(view: View) {
            var msg = Message()
            msg.arg1 = DtdConstants.SHOW_CONTACT
            mainHandler.sendMessage(msg)

//            ToastSnackUtils.toastShort(context,context.getString(R.string.in_progress))
            popUp.dismiss()
        }

        fun onClickLogout(view: View){
            var msg = Message()
            msg.arg1 = DtdConstants.LOGOUT_CLICKED
            mainHandler.sendMessage(msg)
            popUp.dismiss()
        }

        fun onClickNotifcations(view: View){
            var msg = Message()
            msg.arg1 = DtdConstants.SHOW_ALL_NOTIFICATIONS
            mainHandler.sendMessage(msg)
            popUp.dismiss()
        }

        fun onClickImageIcon(view: View) {
            var msg = Message()
            msg.arg1 = DtdConstants.SHOW_PROFILE
            mainHandler.sendMessage(msg)

            popUp.dismiss()
        }

        fun onClickEditProfile(view: View) {
            var msg = Message()
            msg.arg1 = DtdConstants.SHOW_EDIT_PROFILE
            mainHandler.sendMessage(msg)

            popUp.dismiss()
        }

        fun onClickVisible(view: View) {

        }

        fun onClickFollower(){
            var msg = Message()
            msg.arg1 = DtdConstants.SHOW_FOLLOW
            mainHandler.sendMessage(msg)

            popUp.dismiss()
        }

        fun onClickMessages(){
            var mes = Message.obtain(mainHandler)
            mes.arg1 = DtdConstants.SHOW_MESSAGE_ALL_USERS
            mes.sendToTarget()

            popUp.dismiss()
        }


        fun onCheckedChange(): CompoundButton.OnCheckedChangeListener {
            return CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                var visState = prefs.getString(DtdConstants.VISIBILITY_STATUS)
                dropDownBinding.swOnOff.isChecked = visState == "1"
                var visibility = GetUserVisibilityChange()
                visibility.id = prefs.getInt(DtdConstants.ID)
                var latlng = dtdUtils.getAppsLatLng()
                visibility.user_latitude = latlng[0]
                visibility.user_longitude = latlng[1]
                visibility.visibility_status = visState
                visibility.checked = isChecked
                (context as MainActivity).viewModel.getUpdateVisibility(visibility)
            }
        }
    }
}