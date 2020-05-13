package com.mjdistillers.drinkthedrink.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Message
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.mjdistillers.drinkthedrink.*
import com.mjdistillers.drinkthedrink.databinding.LayoutLoginBinding
import com.mjdistillers.drinkthedrink.model.request.GetUserLoginRequest
import com.mjdistillers.drinkthedrink.model.response.login.GetUserLoginResponse
import com.mjdistillers.drinkthedrink.utilities.ApplicationUtils
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import com.mjdistillers.drinkthedrink.utilities.SharedPrefsUtils
import com.mjdistillers.drinkthedrink.utilities.ToastSnackUtils
import javax.inject.Inject

class LoginDialog : DialogFragment() {


    var requestModel = GetUserLoginRequest()
    lateinit var prefs:SharedPrefsUtils
    var binding:LayoutLoginBinding? = null

    @Inject
    lateinit var dtdUtils: DtdUtils

    @Inject
    lateinit var applicationUtils: ApplicationUtils

    init {
        App.app.getComponent().inject(this)
    }

    override fun onStart() {
        super.onStart()

        var dialogFreg = dialog
        dialogFreg?.let {
//            it.window?.setWindowAnimations(R.style.dialog_fragment_style)
            it.setCanceledOnTouchOutside(false)
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var dialogreg = super.onCreateDialog(savedInstanceState)
        dialogreg.window?.requestFeature(Window.FEATURE_NO_TITLE)
        prefs =  SharedPrefsUtils(activity as MainActivity)

         binding = DataBindingUtil.inflate(LayoutInflater.from(activity as MainActivity),
            R.layout.layout_login,null,false)

        binding?.etFirstName?.let {
            applicationUtils.showSoftKeyboard(activity as MainActivity,it)
        }


//        requestModel.email = "bartender@dtd.com"
//        requestModel.password = "123456"
        requestModel.device_token = prefs.getDeviceToken()

        binding?.loginClickEvents = LoginClickEvents()
        binding?.dataModel = requestModel

        binding?.let {
            dialogreg?.setContentView(it.root)
        }

        attachLoginObserver()

        applyFonts()

        return dialogreg
    }

    private fun applyFonts() {
        binding?.let {
            it.tvCreateAccount.typeface = dtdUtils.fontFutura()

            it.etFirstName.typeface = dtdUtils.fontNeutraMedium()
            it.etPassword.typeface = dtdUtils.fontNeutraMedium()
            it.tvCreateAccountEvent.typeface = dtdUtils.fontNeutraMedium()

            it.tvForgotPassword.typeface = dtdUtils.fontFuturaBook()

            it.etFirstName.filters = arrayOf(dtdUtils.obtainFilterFirstCharNoSpace(it.etFirstName))


        }
    }

    fun attachLoginObserver(){
        var observerLogin = Observer<GetUserLoginResponse> {

            if (it.status){
                var role = it.data?.role?.toInt()?:"0".toInt()
                if (role == DtdConstants.ROLE_CUSTOMER ||
                    role == DtdConstants.ROLE_RETAIL_LIQUORE ||
                    role == DtdConstants.ROLE_HOSPITALITY ||
                    role == DtdConstants.ROLE_EVENTS) {

                    dialog?.dismiss()

                    var msg = Message()
                    msg.arg1 = DtdConstants.SHOW_LOGIN_POP_UP
                    msg.arg2 = DtdConstants.FROM_LOGIN
                    (activity as MainActivity).handleMessages(msg)

                }else {
                    var message = App.app.getString(R.string.incorrect_role_received)
                    var mes = Message()
                    mes.arg1 = DtdConstants.SHOW_SNACKBAR
                    mes.obj = message
                    (activity as MainActivity).handleMessages(mes)
                }
            }else{
                var messageStr: String = it?.data?.message?.get(0) ?: getString(R.string.problem_occured)

                var message = Message()
                message.obj = messageStr
                (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)
            }
        }

        (activity as MainActivity).viewModel.liveDataLogin().observe(this,observerLogin)
    }


    inner class LoginClickEvents{

        fun onClickLogin(view: View){
            binding?.let {
                if(it.etPassword.isFocused){
                    applicationUtils.hideKeyboard(activity as MainActivity,it?.etPassword)
                }else{
                    applicationUtils.hideKeyboard(activity as MainActivity,it?.etFirstName)
                }
            }
            (activity as MainActivity).viewModel.getUserLogin(requestModel)
        }

        fun onClickClose(view: View){
            dialog?.dismiss()
        }


        fun onClickForgotPassword(view: View){
            var msg = Message()
            msg.arg1 = DtdConstants.SHOW_FORGOT_PASSWORD
            (activity as MainActivity).handleMessages(msg)
            dialog?.dismiss()
        }

        fun onClickProfileIcon(view: View){
        }

    }


}