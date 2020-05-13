package com.mjdistillers.drinkthedrink.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.PopupWindow
import androidx.fragment.app.DialogFragment
import com.mjdistillers.drinkthedrink.R

class ProgressPopUp: DialogFragment(){

    var progressView: View
    var popUp: PopupWindow

    init {
        progressView = LayoutInflater.from(context).inflate(R.layout.layout_progress_bar,null,false)

        popUp = PopupWindow(
            progressView, FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT)
    }


}