package com.mjdistillers.drinkthedrink.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Message
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.mjdistillers.drinkthedrink.App
import com.mjdistillers.drinkthedrink.DtdConstants
import com.mjdistillers.drinkthedrink.MainActivity
import com.mjdistillers.drinkthedrink.R
import com.mjdistillers.drinkthedrink.utilities.ApplicationUtils
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import com.mjdistillers.drinkthedrink.utilities.SharedPrefsUtils
import kotlinx.android.synthetic.main.forgot_password.view.*
import kotlinx.android.synthetic.main.layout_web_view_dialog.view.ivClose
import javax.inject.Inject

class ForgotPasswordDialog : DialogFragment(), View.OnClickListener {

    @Inject
    lateinit var pref: SharedPrefsUtils

    @Inject
    lateinit var dtdUtils: DtdUtils

    @Inject
    lateinit var applicationUtils: ApplicationUtils

    var viewInvitePeople : View? = null

    init {
        App.app.getComponent().inject(this)
    }

    override fun onStart() {
        super.onStart()
        var dialogFreg = dialog
        dialogFreg?.let {
//            it.window?.setWindowAnimations(R.style.dialog_fragment_style)
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        attachObserverForgotPassword()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dialogdd = super.onCreateDialog(savedInstanceState)
        dialogdd.window?.requestFeature(Window.FEATURE_NO_TITLE)

        viewInvitePeople = LayoutInflater.from(activity as MainActivity)
            .inflate(R.layout.forgot_password, null, false)

        viewInvitePeople?.ivClose?.setOnClickListener(this)
        viewInvitePeople?.tvRecover?.setOnClickListener(this)

        viewInvitePeople?.let {
            dialogdd.setContentView(it)
        }

        viewInvitePeople?.ivEmail?.setColorFilter(ContextCompat.getColor(activity as MainActivity,R.color.text_color), PorterDuff.Mode.SRC_ATOP)

        viewInvitePeople?.etEmail?.text = Editable.Factory.getInstance().newEditable(pref.getString(DtdConstants.EMAIL))

        viewInvitePeople?.let {
            applicationUtils.showSoftKeyboard(activity as MainActivity,it.etEmail)
        }

        viewInvitePeople?.etEmail?.filters =
            arrayOf(
            dtdUtils.obtainFilterFirstCharNoSpace(viewInvitePeople?.etEmail))

        applyFonts()

        return dialogdd
    }

    private fun applyFonts() {
        viewInvitePeople?.tvForgotPasswdHeading?.typeface = dtdUtils.fontFutura()
        viewInvitePeople?.etEmail?.typeface = dtdUtils.fontNeutraMedium()
        viewInvitePeople?.tvRecover?.typeface = dtdUtils.fontNeutraMedium()
    }

    private fun attachObserverForgotPassword() {
        (activity as MainActivity).viewModel.liveDataForgotPassword().observe(activity as MainActivity,
            Observer {
                it?.let {
                    if (it.status){
//                        var message = " Mail have been sent to your email id"
//                        var mes = Message()
//                        mes.arg2 = DtdConstants.SHOW_SNACKBAR
//                        mes.obj = message
                        var latest_id = it.data.userId
                        activity?.let {

                            var message = Message()
                            message.arg1 = DtdConstants.SHOW_UPDATE_PASSWORD
                            message.arg2 = latest_id
                            (activity as MainActivity).handleMessages(message)

//                            (activity as MainActivity).handleMessages(mes)
                            dismiss()
                        }
                    }

                }
            })
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ivClose->{
                dialog?.dismiss()
            }
            R.id.tvRecover->{
                var password = viewInvitePeople?.etEmail?.text.toString()?:""
                if(password.isEmpty()){
                    var mes = Message()
                    mes.arg1 = DtdConstants.SHOW_SNACKBAR
                    mes.obj = getString(R.string.enter_email_id)
                    (activity as MainActivity).handleMessages(mes)
                }else {
                    (activity as MainActivity).viewModel.getForgotPassword(password)
                }
            }
        }
    }
}