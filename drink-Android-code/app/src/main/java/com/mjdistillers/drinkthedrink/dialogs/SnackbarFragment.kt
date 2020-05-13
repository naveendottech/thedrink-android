package com.mjdistillers.drinkthedrink.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.mjdistillers.drinkthedrink.*
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import kotlinx.android.synthetic.main.layout_snack_bar_dialog.view.*
import javax.inject.Inject

class SnackbarFragment : DialogFragment() {

    @Inject
    lateinit var dtdUtils: DtdUtils

    lateinit var viewDialog:View

    init {
        App.app.getComponent().inject(this)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dialogreg = super.onCreateDialog(savedInstanceState)
        dialogreg.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialogreg.window?.setGravity(Gravity.TOP)

        viewDialog = LayoutInflater.from(activity as MainActivity).inflate(R.layout.layout_snack_bar_dialog,null,false)

        arguments?.let {
            viewDialog.tvMessage.text = it.getString(DtdPrefsKeys.Keys.SNACKBAR_MESSAGE)
            viewDialog.tvButton.text = it.getString(DtdPrefsKeys.Keys.SNACKBAR_BUTTON)
        }


        viewDialog.tvMessage.typeface = dtdUtils.fontNeutraMedium()
        viewDialog.tvButton.typeface = dtdUtils.fontNeutraMedium()

        dialogreg.setContentView(viewDialog)

        return dialogreg
    }


    override fun onStart() {
        super.onStart()
        var dialogFreg = dialog
        dialogFreg?.let {
            it.window?.setWindowAnimations(R.style.dialog_snackbar_style)
            it.setCanceledOnTouchOutside(true)
            dialogFreg.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
//            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        Handler().postDelayed({
            dialog?.dismiss()
        },DtdConstants.SNACKBAR_DURATION)
    }


    fun updateMessage(message: String, btnText: String) {
        if (::viewDialog.isInitialized){
            viewDialog.tvMessage.text = message
            viewDialog.tvButton.text = btnText
        }
    }

}