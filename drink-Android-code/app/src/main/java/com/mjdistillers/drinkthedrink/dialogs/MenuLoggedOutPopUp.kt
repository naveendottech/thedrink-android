package com.mjdistillers.drinkthedrink.dialogs

import android.content.Context
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.widget.PopupWindow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.mjdistillers.drinkthedrink.App
import com.mjdistillers.drinkthedrink.DtdConstants
import com.mjdistillers.drinkthedrink.R
import com.mjdistillers.drinkthedrink.databinding.LayoutDropDownLogOutBinding
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import com.mjdistillers.drinkthedrink.utilities.ToastSnackUtils
import javax.inject.Inject

class MenuLoggedOutPopUp(var context: Context, var mainHandler: Handler) {

    private var popUp: PopupWindow

    @Inject
    lateinit var dtdUtils: DtdUtils

    init {

        App.app.getComponent().inject(this)

        var dropDownBinding: LayoutDropDownLogOutBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.layout_drop_down_log_out,null,false)
        dropDownBinding.clickEvents = ClickEventsMenuPopUp()
        popUp = PopupWindow(
            dropDownBinding.root, ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT)

        setUpLayoutObserver()
        popUp.isOutsideTouchable = true

        applyFonts(dropDownBinding)
    }

    private fun applyFonts(dropDownLogOutBinding: LayoutDropDownLogOutBinding) {
        dropDownLogOutBinding?.tvRegisterLogin.typeface = dtdUtils.fontNeutraMedium()
        dropDownLogOutBinding?.tvContact.typeface = dtdUtils.fontNeutraMedium()

    }

    private fun setUpLayoutObserver() {


    }


    fun getPopUp(): PopupWindow {
        return popUp
    }

    inner class ClickEventsMenuPopUp{

        fun onClickRegisterLogin(view: View){
            var msg = Message.obtain(mainHandler)
            msg.arg1 = DtdConstants.SHOW_REGISTER_FORM
            msg.sendToTarget()

            popUp.dismiss()
        }

        fun onClickFeedback(view: View){

            var message = mainHandler.obtainMessage()
            message.arg1 = DtdConstants.SHOW_SNACKBAR
            message.obj = App.app.getString(R.string.in_progress)
            message.sendToTarget()

            popUp.dismiss()
        }


        fun onClickContacts(view: View){

            var msg = Message()
            msg.arg1 = DtdConstants.SHOW_FEEDBACK
            mainHandler.sendMessage(msg)

            popUp.dismiss()
        }
    }
}