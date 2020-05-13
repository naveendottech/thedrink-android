package com.mjdistillers.drinkthedrink.dialogs

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.mjdistillers.drinkthedrink.MainActivity
import com.mjdistillers.drinkthedrink.R

class ProgressDialogFragment: DialogFragment() {

    lateinit var viewDialog: View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dialogreg = super.onCreateDialog(savedInstanceState)
        dialogreg.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialogreg.window?.setGravity(Gravity.CENTER)
        dialogreg.setCanceledOnTouchOutside(false)
        dialogreg.setCancelable(false)
        viewDialog = LayoutInflater.from(activity as MainActivity)
            .inflate(R.layout.layout_progress_dialog,null,false)

        dialogreg.setContentView(viewDialog)

        return dialogreg
    }


    override fun onStart() {
        super.onStart()
        var dialogFreg = dialog
        dialogFreg?.let {
            it.window?.setWindowAnimations(R.style.dialog_snackbar_style)
            it.setCanceledOnTouchOutside(true)
            dialogFreg.window?.setLayout(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
//            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

}