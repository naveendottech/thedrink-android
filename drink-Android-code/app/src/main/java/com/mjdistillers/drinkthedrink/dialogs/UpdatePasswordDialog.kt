package com.mjdistillers.drinkthedrink.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.mjdistillers.drinkthedrink.App
import com.mjdistillers.drinkthedrink.DtdConstants
import com.mjdistillers.drinkthedrink.MainActivity
import com.mjdistillers.drinkthedrink.R
import com.mjdistillers.drinkthedrink.utilities.ApplicationUtils
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import com.mjdistillers.drinkthedrink.utilities.SharedPrefsUtils
import kotlinx.android.synthetic.main.layout_update_password.view.*
import javax.inject.Inject

class UpdatePasswordDialog:DialogFragment(), View.OnClickListener {

    @Inject
    lateinit var applicationUtils: ApplicationUtils

    @Inject
    lateinit var prefs:SharedPrefsUtils

    @Inject
    lateinit var dtdUtils:DtdUtils

    var idFromPrev = 0


    var viewUpdatePassword:View? = null

    init {
        App.app.getComponent().inject(this)
    }

    override fun onStart() {
        super.onStart()

        var dialogFreg = dialog
        dialogFreg?.let {
            it.window?.setWindowAnimations(R.style.dialog_fragment_style)
            it.setCanceledOnTouchOutside(false)
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dialogreg = super.onCreateDialog(savedInstanceState)
        dialogreg.window?.requestFeature(Window.FEATURE_NO_TITLE)

         viewUpdatePassword = LayoutInflater.from(activity as MainActivity).inflate(R.layout.layout_update_password,null,false)

        viewUpdatePassword?.ivClose?.setOnClickListener(this)
        viewUpdatePassword?.tvUpdate?.setOnClickListener(this)

        arguments?.let {
            if (it.containsKey(DtdConstants.ID)){
                idFromPrev = it.getInt(DtdConstants.ID)
            }
        }

        if (idFromPrev > 0){
            viewUpdatePassword?.etOldPassword?.visibility = View.GONE
        }else{
            viewUpdatePassword?.etOldPassword?.visibility = View.VISIBLE
        }

//        var editable = Editable.Factory.getInstance().newEditable(prefs.getString(DtdConstants.EMAIL))

//        viewUpdatePassword?.et?.text = editable
//        viewUpdatePassword?.etEmail?.isEnabled = false
//        viewUpdatePassword?.etEmail?.isCursorVisible = false

        viewUpdatePassword?.let{
            applicationUtils.showSoftKeyboard(activity as MainActivity, it.etOldPassword)
        }




        attachObserverUpdatePassword()

        viewUpdatePassword?.let {
            dialogreg.setContentView(it)
        }

        applyFonts()

        return dialogreg
    }

    private fun applyFonts() {
        viewUpdatePassword?.let {

            it.tvForgotPasswdHeading.typeface = dtdUtils.fontFutura()
            it.etPassword.typeface = dtdUtils.fontNeutraMedium()
            it.etOldPassword.typeface = dtdUtils.fontNeutraMedium()
            it.tvUpdate.typeface = dtdUtils.fontNeutraMedium()

        }
    }

    private fun attachObserverUpdatePassword() {
        (activity as MainActivity).viewModel.liveDataUpdatePassword().observe(activity as MainActivity,
            Observer {
                it?.let {
                    if (it.status){
                        var message = it.data.message[0]?:getString(R.string.updated)
                        var mes = Message()
                        mes.arg1 = DtdConstants.SHOW_SNACKBAR
                        mes.obj = message
                        (activity as MainActivity).handleMessages(mes)

                        dismiss()
                    }
                }
            })

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tvUpdate->{

                var oldPasswordTextLength = viewUpdatePassword?.etOldPassword?.text.toString()?:""
                var password = viewUpdatePassword?.etPassword?.text?.toString()?:""

                var userId = 0
                var key = ""
                if (idFromPrev > 0){
                    userId = idFromPrev
                    oldPasswordTextLength = "123456"
                    key = "forget"
                }else{
                    userId = prefs.getInt(DtdConstants.ID)
                    key = ""
                }


                when{
                    oldPasswordTextLength.length < 5 && key == "" ->{
                        var mes = Message()
                        mes.arg2 = DtdConstants.SHOW_SNACKBAR
                        mes.obj = getString(R.string.more_than_five)
                        (activity as MainActivity).handleMessages(mes)
                    }

                    password.length  < 5 ->{
                        var mes = Message()
                        mes.arg2 = DtdConstants.SHOW_SNACKBAR
                        mes.obj = getString(R.string.more_than_five)
                        (activity as MainActivity).handleMessages(mes)

                    }
                    else->{

                        (activity as MainActivity).viewModel.getUpdatePassword(
                            userId,
                            password,
                            oldPasswordTextLength,
                            key)
                    }
                }
            }

            R.id.ivClose->{
                dialog?.dismiss()
            }
        }
    }

}