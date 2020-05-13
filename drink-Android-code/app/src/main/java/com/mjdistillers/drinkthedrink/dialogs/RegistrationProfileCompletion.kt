package com.mjdistillers.drinkthedrink.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.mjdistillers.drinkthedrink.*
import com.mjdistillers.drinkthedrink.adapters.RoleTypeSpinnerAdapter
import com.mjdistillers.drinkthedrink.databinding.LayoutRegistrationProfileCompletionBinding
import com.mjdistillers.drinkthedrink.model.request.GetRegistrationRequest
import com.mjdistillers.drinkthedrink.model.request.UpdateRoleRequest
import com.mjdistillers.drinkthedrink.model.response.states_provinces.GetStatesAndPrvincesResponse
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import com.mjdistillers.drinkthedrink.utilities.SharedPrefsUtils
import javax.inject.Inject

class RegistrationProfileCompletion: DialogFragment() {

    var requestModel = GetRegistrationRequest()
    var statesAndPrviResponse: GetStatesAndPrvincesResponse? = null
    var binding: LayoutRegistrationProfileCompletionBinding? = null

    @Inject
    lateinit var dtdUtil: DtdUtils

    @Inject
    lateinit var prefs:SharedPrefsUtils

    var updateRoleRequest = UpdateRoleRequest()

/*
    var onTocuhListenerSpinner =  View.OnTouchListener { v, event ->
        if (event.action == MotionEvent.ACTION_UP){
            var view = binding?.root?.findFocus()
            var viewFoc = binding?.root?.findFocus()
            if (viewFoc is AppCompatEditText){
                viewFoc.clearFocus()
            }
        }
        return@OnTouchListener false
    }
*/


    override fun onStart() {
        super.onStart()

        var dialogFreg = dialog
        dialogFreg?.let {
            it.setCanceledOnTouchOutside(false)
            it.window?.setWindowAnimations(R.style.dialog_fragment_style)

//            dialogFreg.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT,
//                ConstraintLayout.LayoutParams.WRAP_CONTENT)
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dialogreg = super.onCreateDialog(savedInstanceState)
        dialogreg.window?.requestFeature(Window.FEATURE_NO_TITLE)
//            dialogreg.window?.setGravity(Gravity.TOP)

        App.app.getComponent().inject(this)

        arguments?.let {
            statesAndPrviResponse = it.getParcelable(DtdPrefsKeys.Keys.RESPONSE_STATES_AND_PROVINCES)
        }

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity as MainActivity),
            R.layout.layout_registration_profile_completion,null,false)

        requestModel.role = DtdConstants.ROLE_CUSTOMER
        requestModel.device_token =  SharedPrefsUtils(activity as MainActivity).getDeviceToken()


        var registerClicks = RegisterProfileCompleionClicks()
        binding?.registerTouchEvents = registerClicks

        binding?.let {
            dialogreg?.setContentView(it.root)
        }

        setFavAlcoholValues()
        setDayOutValues()
        setOnlineAlcohol()
        setUpChooseRole()

        binding?.etSpeciality?.filters =
            arrayOf(dtdUtil.obtainInputFilterSpecialChars(binding?.etSpeciality),
                dtdUtil.obtainFilterFirstCharNoSpace(binding?.etSpeciality)
                )



        updateRoleRequest.user_id =  prefs.getInt(DtdConstants.ID)
        updateRoleRequest.role = 7

        applyFonts()

        return dialogreg
    }

    fun applyFonts(){
        binding?.let {

            it.tvCreateAccount.typeface = dtdUtil.fontFutura()

            it.tvWorkIndustry.typeface = dtdUtil.fontNeutraMedium()
            it.tvTellUsWhereWork.typeface = dtdUtil.fontNeutraMedium()
            it.tvTellUsFavAlcohol.typeface = dtdUtil.fontNeutraMedium()
            it.tvTelldDay.typeface = dtdUtil.fontNeutraMedium()
            it.tvAlcoholOnline.typeface = dtdUtil.fontNeutraMedium()
            it.tvSave.typeface = dtdUtil.fontNeutraMedium()
            it.tvUpdatePassword.typeface = dtdUtil.fontNeutraMedium()
            it.etSpeciality.typeface = dtdUtil.fontNeutraMedium()

            it.rbHospitality.typeface = dtdUtil.fontNeutraMedium()
            it.rbRetailLiquore.typeface = dtdUtil.fontNeutraMedium()
            it.rbEvents.typeface = dtdUtil.fontNeutraMedium()


        }
    }

    private fun setUpChooseRole() {
        binding?.rgPurchaseOnline?.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rbHospitality->{
                    updateRoleRequest.role = 7
                }
                R.id.rbRetailLiquore->{
                    updateRoleRequest.role = 8
                }
                R.id.rbEvents->{
                    updateRoleRequest.role = 4
                }
            }
        }
        binding?.rgPurchaseOnline?.check(R.id.rbHospitality)
    }

    private fun setOnlineAlcohol() {
        binding?.let {
            it.spOnlineAlcohol.adapter = RoleTypeSpinnerAdapter(context, DtdConstants.YES_NO_ARRAY)
            it.spOnlineAlcohol.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if (position > 0)
                        updateRoleRequest.alcohol_online = DtdConstants.YES_NO_ARRAY[position].toLowerCase()
                }
            }
        }
    }

    private fun setDayOutValues() {
        binding?.let {
            it.spDayOut.adapter = RoleTypeSpinnerAdapter(context, DtdConstants.DAYS_ARRAY)
            it.spDayOut.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if (position > 0)
                        updateRoleRequest.outing_day = DtdConstants.DAYS_ARRAY[position]
                }
            }
        }
    }

    private fun setFavAlcoholValues() {
        binding?.let {
            it.spFavAlcohol.adapter = RoleTypeSpinnerAdapter(context, DtdConstants.FAVOURITE_ALCOHOL_ARRAY)
            it.spFavAlcohol.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if (position > 0)
                        updateRoleRequest.fav_alcohol = DtdConstants.FAVOURITE_ALCOHOL_ARRAY[position]
                }
            }
        }
    }

    inner class RegisterProfileCompleionClicks{

        fun onClickClose(view: View){
            dialog?.dismiss()
        }

        fun onClickSave(view: View){
            prefs.saveInt(DtdPrefsKeys.Keys.USER_ROLE,requestModel.role)
            var message = Message.obtain((activity as MainActivity).handler)
            message.arg1 = DtdConstants.HIT_FOR_UPDATE_USER_PROFILE
            message.obj = updateRoleRequest
            message.sendToTarget()
            dismiss()
        }

        fun onClickContinue(view: View){
            var message = Message.obtain((activity as MainActivity).handler)
            message.arg1 = DtdConstants.SHOW_LOGIN_POP_UP
            message.arg2 = DtdConstants.FROM_LOGIN
            message.sendToTarget()
            dialog?.dismiss()
        }
    }

}