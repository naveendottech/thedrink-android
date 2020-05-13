package com.mjdistillers.drinkthedrink.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.mjdistillers.drinkthedrink.App
import com.mjdistillers.drinkthedrink.MainActivity
import com.mjdistillers.drinkthedrink.R
import com.mjdistillers.drinkthedrink.utilities.ApplicationUtils
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import com.mjdistillers.drinkthedrink.utilities.SharedPrefsUtils
import kotlinx.android.synthetic.main.layout_invite_people.view.*
import kotlinx.android.synthetic.main.layout_web_view_dialog.view.ivClose
import javax.inject.Inject

class InvitePeopleDialog : DialogFragment(), View.OnClickListener {

    @Inject
    lateinit var pref: SharedPrefsUtils

    @Inject
    lateinit var dtdUtils: DtdUtils

    @Inject
    lateinit var applicationUtils: ApplicationUtils

    lateinit var etEmail:AppCompatEditText

    init {
        App.app.getComponent().inject(this)
    }


    override fun onStart() {
        super.onStart()
        var dialogFreg = dialog
        dialogFreg?.let {
            it.window?.setWindowAnimations(R.style.dialog_fragment_style)
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dialogdd = super.onCreateDialog(savedInstanceState)
        dialogdd.window?.requestFeature(Window.FEATURE_NO_TITLE)

        var viewInvitePeople = LayoutInflater.from(activity as MainActivity)
            .inflate(R.layout.layout_invite_people, null, false)

        etEmail = viewInvitePeople.findViewById(R.id.etEmail)
        viewInvitePeople.ivClose.setOnClickListener(this)
        viewInvitePeople.tvSend.setOnClickListener(this)

        viewInvitePeople.ivEmail.setColorFilter(
            ContextCompat.getColor(activity as MainActivity,R.color.text_color),
            PorterDuff.Mode.SRC_ATOP)

        dialogdd.setContentView(viewInvitePeople)

        applicationUtils.showSoftKeyboard(activity as MainActivity,viewInvitePeople.etEmail)

        applyFonts(viewInvitePeople)

        return dialogdd
    }

    private fun applyFonts(view: View) {
        view.tvInvitePeopleHeading.typeface = dtdUtils.fontFutura()
        view.etEmail.typeface = dtdUtils.fontNeutraMedium()
        view.tvSend.typeface = dtdUtils.fontNeutraMedium()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ivClose->{
                dialog?.dismiss()
            }
            R.id.tvSend->{
                (activity as MainActivity)
                    .sendEmail(arrayOf(etEmail.text.toString()),
                    App.app.getString(R.string.subject_invitaion),
                    App.app.getString(R.string.invite_text))
                dialog?.dismiss()
            }
        }
    }
}