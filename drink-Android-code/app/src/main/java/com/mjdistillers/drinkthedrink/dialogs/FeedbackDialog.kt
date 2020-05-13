package com.mjdistillers.drinkthedrink.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.mjdistillers.drinkthedrink.App
import com.mjdistillers.drinkthedrink.DtdConstants
import com.mjdistillers.drinkthedrink.MainActivity
import com.mjdistillers.drinkthedrink.R
import com.mjdistillers.drinkthedrink.databinding.LayoutFeedbackBinding
import com.mjdistillers.drinkthedrink.model.response.feedback.GetPostFeedbackResponse
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import com.mjdistillers.drinkthedrink.utilities.SharedPrefsUtils
import javax.inject.Inject

class FeedbackDialog: DialogFragment() {

    lateinit var prefs: SharedPrefsUtils

    var binding:LayoutFeedbackBinding? = null

    @Inject
    lateinit var dtdUtils:DtdUtils

    init {
        App.app.getComponent().inject(this)
    }

    override fun onStart() {
        super.onStart()

        prefs =  SharedPrefsUtils(activity as MainActivity)
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

        binding =  DataBindingUtil.inflate(
            LayoutInflater.from(activity as MainActivity),
            R.layout.layout_feedback,null,false)

        binding?.clickEvents = FeedbackClickEvents()

        binding?.let {
            dialogreg.setContentView(it.root)
        }

        attachObserverFeedback()

        applyFonts()

        return dialogreg
    }

    private fun applyFonts() {
        binding?.tvWanttohear?.typeface = dtdUtils.fontNeutraMedium()
        binding?.tvSubHeading?.typeface = dtdUtils.fontNeutraMedium()
        binding?.etComments?.typeface = dtdUtils.fontNeutraMedium()
        binding?.tvSubmitFeedback?.typeface = dtdUtils.fontNeutraMedium()
    }

    private fun attachObserverFeedback() {
        (activity as MainActivity).viewModel.liveDataFeedback().observe(activity as MainActivity,
            Observer<GetPostFeedbackResponse>{
            var messages = Message()
            messages.arg1 = DtdConstants.SHOW_SNACKBAR
            it.data.message?.let {
                if (it.size > 0){
                    messages.obj = it[0]
                }else{
                    messages.obj = getString(R.string.success)
                }
            }?: kotlin.run {
                messages.obj = getString(R.string.success)
            }

            (activity as MainActivity).handleMessages(messages)

            dialog?.dismiss()

        })
    }

    inner class FeedbackClickEvents{

        fun onClickFeedback(view: View) {
            if (binding?.etComments?.text.toString().isNotEmpty()){
                (activity as MainActivity).viewModel.postFeedback(binding?.etComments?.text.toString())
            }else{
                binding?.let {
                    var message = Message()
                    message.obj = getString(R.string.enter_feedback_first)
                    (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)
                }
            }

        }

        fun onClickClose(view: View){
            dialog?.dismiss()
        }


    }
}