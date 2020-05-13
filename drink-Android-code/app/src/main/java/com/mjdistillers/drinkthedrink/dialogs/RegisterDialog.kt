package com.mjdistillers.drinkthedrink.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Message
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.widget.AdapterView
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.mjdistillers.drinkthedrink.*
import com.mjdistillers.drinkthedrink.adapters.CityArrayAdapter
import com.mjdistillers.drinkthedrink.adapters.ProvincesAndTerriteriesAdapter
import com.mjdistillers.drinkthedrink.adapters.RoleTypeSpinnerAdapter
import com.mjdistillers.drinkthedrink.adapters.StateSpinnerAdapter
import com.mjdistillers.drinkthedrink.databinding.LayoutRegisterBinding
import com.mjdistillers.drinkthedrink.model.request.GetRegistrationRequest
import com.mjdistillers.drinkthedrink.model.response.edit_profile.ProvincesAndTerritory
import com.mjdistillers.drinkthedrink.model.response.edit_profile.State
import com.mjdistillers.drinkthedrink.model.response.get_cities.GetCitiesResponse
import com.mjdistillers.drinkthedrink.model.response.registration.GetUserRegisterationResponse
import com.mjdistillers.drinkthedrink.model.response.states_provinces.GetStatesAndPrvincesResponse
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import com.mjdistillers.drinkthedrink.utilities.SharedPrefsUtils
import javax.inject.Inject

class RegisterDialog: DialogFragment() {


    var requestModel = GetRegistrationRequest()
    var statesAndPrviResponse: GetStatesAndPrvincesResponse? = null
    var binding: LayoutRegisterBinding? = null

    @Inject
    lateinit var dtdUtil: DtdUtils

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

        binding = DataBindingUtil.inflate(LayoutInflater.from(activity as MainActivity),
            R.layout.layout_register,null,false)

        binding?.registerClickEvents = RegisterClickEvents()
        requestModel.role = DtdConstants.ROLE_CUSTOMER
        requestModel.device_token =  SharedPrefsUtils(activity as MainActivity).getDeviceToken()

        binding?.etFirstName?.filters = arrayOf(
            dtdUtil.obtainInputFilterSpecialChars(binding?.etFirstName),
            dtdUtil.obtainFilterFirstCharNoSpace(binding?.etFirstName))

        binding?.etEmail?.filters = arrayOf(dtdUtil.obtainFilterFirstCharNoSpace(binding?.etEmail))

        binding?.etPhoneNumber?.filters = arrayOf(dtdUtil.obtainFilterFirstCharNoSpace(binding?.etPhoneNumber))

        binding?.etUserName?.filters = arrayOf(dtdUtil.obtainFilterFirstCharNoSpace(binding?.etUserName))
        binding?.etFavoriteLiquor?.filters = arrayOf(dtdUtil.obtainFilterFirstCharNoSpace(binding?.etFavoriteLiquor),
            dtdUtil.obtainInputFilterSpecialChars(binding?.etFavoriteLiquor))
        binding?.etCity?.filters = arrayOf(dtdUtil.obtainFilterFirstCharNoSpace(binding?.etCity),
            dtdUtil.obtainInputFilterSpecialChars(binding?.etCity))


//        requestModel.email = "jamjam@gmail.com"
//        requestModel.name = "jam"
//        requestModel.password = "12345678"
//        requestModel.username = "jam11"

        binding?.dataModel = requestModel

        binding?.let {
            setUpRoleSpinner(it)
            setUpFavouriteAlcoholSpinner(it)
            setUpWrokingAtSpinner(it)
            setUpCountrySpinner(it)
            setUpSpannableForTermsAndPolicy(it)
            setUpCheckEventWithIndustryChecbox(it)
            dialogreg?.setContentView(it.root)
        }


//        var provinces = ProvincesAndTerritory()
//        provinces.fullName = "Countries or States"
//        provinces.abbreviation = ""
//        var mutableList = mutableListOf<ProvincesAndTerritory>()
//        mutableList.add(provinces)
//        binding?.spState?.adapter = ProvincesAndTerriteriesAdapter(activity as MainActivity, mutableList)

        attachRegistrationObserver()
        applyFonts()
        attachObserverCitites()

        return dialogreg
    }

    private fun applyFonts() {
        binding?.let {
            it.tvCreateAccount.typeface = dtdUtil.fontFutura()
            it.tvAlreadyRegistered.typeface = dtdUtil.fontNeutraMedium()
            it.tvLogIn.typeface = dtdUtil.fontNeutraMedium()

            it.etFirstName.typeface = dtdUtil.fontNeutraMedium()
            it.etLastName.typeface = dtdUtil.fontNeutraMedium()
            it.etEmail.typeface = dtdUtil.fontNeutraMedium()
            it.etPhoneNumber.typeface = dtdUtil.fontNeutraMedium()
            it.etPassword.typeface = dtdUtil.fontNeutraMedium()
            it.etUserName.typeface = dtdUtil.fontNeutraMedium()
            it.etFavoriteLiquor.typeface = dtdUtil.fontNeutraMedium()
            it.cbIndustry.typeface = dtdUtil.fontNeutraMedium()
            it.tvHeadingOnSPWorkingATSelection.typeface = dtdUtil.fontNeutraMedium()
            it.etWorksAt.typeface = dtdUtil.fontNeutraMedium()
            it.etBarAddressNew.typeface = dtdUtil.fontNeutraMedium()
            it.etCity.typeface = dtdUtil.fontNeutraMedium()
            it.etSpeciality.typeface = dtdUtil.fontNeutraMedium()
            it.tvTermsAndPolicy.typeface = dtdUtil.fontNeutraMedium()
            it.tvCreateAccountEvent.typeface = dtdUtil.fontNeutraMedium()


            it.tvTellUsWhereWork.typeface = dtdUtil.fontFutura()
        }

    }

    private fun setUpCheckEventWithIndustryChecbox(binding: LayoutRegisterBinding) {

        binding.cbIndustry.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                binding.groupIsIndustry.visibility = View.VISIBLE
//                binding.tvHeadingOnSPWorkingATSelection.visibility = View.VISIBLE

//                etCity,ivDownArrowWorkingAt,spWorkingAt,tvDividerWorkingAt,tvHeadingOnSPWorkingATSelection,
//                tvTellUsWhereWork,etWorksAt,etBarAddressNew,spCountry,tvUnderlineCountry,ivDownArrowCountry,
//                spState,tvUnderlineState,ivDownArrowStatesPro,
//                etSpeciality,tvSupportFavAlcohol,tvSupportWorkingAtSpin,tvSupportCountrySpin,tvSupportState

                binding.nsFeilds.invalidate()
            }else{
                binding.etCity.text?.clear()
                binding.spWorkingAt.setSelection(0)
                binding.etWorksAt.text?.clear()
                binding.etBarAddressNew.text?.clear()
                binding.spCountry.setSelection(0)
                binding.etSpeciality.text?.clear()
                binding.spState.adapter =  ProvincesAndTerriteriesAdapter(activity as MainActivity, listOf())
                requestModel.work_at = 0
                binding.groupIsIndustry.visibility = View.GONE
                binding.tvHeadingOnSPWorkingATSelection.visibility = View.GONE
                binding.nsFeilds.invalidate()
            }
        }
    }

    private fun setUpCountrySpinner(binding: LayoutRegisterBinding) {
        binding.spCountry.adapter = RoleTypeSpinnerAdapter(context, DtdConstants.COUNTRY_ARRAY)
        binding.spCountry.setOnTouchListener(onTocuhListenerSpinner)

        binding.spCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position > 0)
                requestModel.country = DtdConstants.COUNTRY_ARRAY[position]

                when(position){
                    0->{
                        binding.spState.setSelection(0)
                    }
                    1->{
                        setUpStatesSpinner(binding,1)
                    }
                    2->{
                        setUpStatesSpinner(binding,2)
                    }
                }
            }
        }

    }

    private fun setUpStatesSpinner(binding: LayoutRegisterBinding,flag:Int) {

        if (flag == 1) {
            var listStates = mutableListOf<State>()

            var states = State()
            states.fullName = "Choose States"
            states.abbreviation = ""

            listStates.add(states)
            statesAndPrviResponse?.data?.states?.let {
                listStates.addAll(it)
            }


//            addOnTouchToASpinner()
            binding.spState.adapter = StateSpinnerAdapter(activity as MainActivity, listStates)
        } else {
            var listProvinces = mutableListOf<ProvincesAndTerritory>()

            var provinces = ProvincesAndTerritory()
            provinces.fullName = "Choose Provinces"
            provinces.abbreviation = ""

            listProvinces.add(provinces)

            statesAndPrviResponse?.data?.provincesAndTerritories?.let {
                listProvinces.addAll(it)

                    binding.spState.adapter =
                        ProvincesAndTerriteriesAdapter(activity as MainActivity, listProvinces)
            }
        }

            binding.spState.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                  /*  if (position == 0){

                        var provinces = ProvincesAndTerritory()
                        provinces.fullName = "Countries or States"
                        provinces.abbreviation = ""

                        var mutableList = mutableListOf<ProvincesAndTerritory>()
                        mutableList.add(provinces)

                        binding.spState.adapter = ProvincesAndTerriteriesAdapter(activity as MainActivity, mutableList)
                    }
*/
                    if (flag == 1) {
                        statesAndPrviResponse?.data?.states?.let {
                            requestModel.state = it[position].abbreviation

                            if(position > 0)
                            (activity as MainActivity).viewModel.getCities(requestModel.country,requestModel.state)

                        }
                    } else {
                        statesAndPrviResponse?.data?.provincesAndTerritories?.let {
                            requestModel.state = it[position].abbreviation

                            (activity as MainActivity).viewModel.getCities(requestModel.country,requestModel.state)
                        }
                    }
                } // End of onItemSelected
            }
    }

    private fun attachObserverCitites(){
        (activity as MainActivity).viewModel.liveDataCities().observe((activity as MainActivity),
            androidx.lifecycle.Observer<GetCitiesResponse> {
                var listOfCities = it?.data?.cities
                listOfCities?.let {
                    var arrayCities = mutableListOf<String>()
                    for (obj in it) {
                        arrayCities.add(obj.city)
                    }
//                    var arrayOfCities = arrayCities.toTypedArray()

                    var arrayAdapter = CityArrayAdapter(activity as MainActivity, arrayCities)

                    binding?.etCity?.threshold = 2
                    binding?.etCity?.setAdapter(arrayAdapter)

                    binding?.etCity?.setOnItemClickListener(object  : AdapterView.OnItemClickListener{
                        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            var textToUse = view?.tag?.toString()?:""
                            requestModel.city = textToUse
                            binding?.etCity?.setText(textToUse.toUpperCase())
                        }
                    })
/*
                    binding?.etCity?.setOnItemClickListener { parent, view, position, id ->
                        Log.i("VVV","postion : "+position+"  id : "+id)
                    }
*/
                }
            })
    }

    private fun setUpWrokingAtSpinner(binding: LayoutRegisterBinding) {
        binding.spWorkingAt.adapter = RoleTypeSpinnerAdapter(context, DtdConstants.NEW_NEW_ROLE_ARRAY)
        binding.spWorkingAt.setOnTouchListener(onTocuhListenerSpinner)
        binding.spWorkingAt.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

//                when(position){
//                    0->{
//                        requestModel.role = 0
//                    }
//                    1->{
//                        requestModel.role = DtdConstants.ROLE_BAR_TENDER
//                    }
//                    2->{
//                        requestModel.role = DtdConstants.ROLE_STORE_MANAGER
//                    }
//                    3->{
//                        requestModel.role = DtdConstants.ROLE_PROMOTOR
//                    }
//                    4->{
//                        requestModel.role = DtdConstants.ROLE_USER
//                    }
//                }

//                if(position > 0){
//                    binding.tvHeadingOnSPWorkingATSelection.visibility =View.VISIBLE
//                }else{
//                    binding.tvHeadingOnSPWorkingATSelection.visibility =View.GONE
//                }

            }
        }
    }

    private fun setUpFavouriteAlcoholSpinner(binding: LayoutRegisterBinding) {
        binding.spFavAlcohal.adapter = RoleTypeSpinnerAdapter(context, DtdConstants.FAVOURITE_ALCOHOL_ARRAY)
        binding.spFavAlcohal.setOnTouchListener(onTocuhListenerSpinner)
        binding.spFavAlcohal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position > 0)
                requestModel.favoriteAlcohol = DtdConstants.FAVOURITE_ALCOHOL_ARRAY[position]

            }
        }
    }

    fun attachRegistrationObserver(){
        var observer = Observer<GetUserRegisterationResponse> {
            if (it.status){
                var msg = Message()
                msg.arg1 = DtdConstants.SHOW_LOGIN_POP_UP
                (activity as MainActivity).handleMessages(msg)
                dialog?.dismiss()
            }else{
                var messagest: String = it?.registrationData?.message?.get(0) ?: getString(R.string.problem_occured)
                var message = Message()
                message.obj = messagest
                (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)
//                ToastSnackUtils.toastShort((context as MainActivity), message)
            }
        }

        (activity as MainActivity).viewModel.liveDataRegistration().observe(this,observer)
    }

    private fun setUpSpannableForTermsAndPolicy(binding: LayoutRegisterBinding) {

        binding.tvTermsAndPolicy.movementMethod = LinkMovementMethod.getInstance()

        var termStart = 41
        var termEnd = 58

        var privacyStart = 92
        var privacyEnd = resources.getString(R.string.terms_and_policy).length

        var spannable = SpannableString(resources.getString(R.string.terms_and_policy))

        spannable.setSpan(object : ClickableSpan(){
            override fun onClick(widget: View) {
                var msg =Message()
                msg.arg1 = DtdConstants.SHOW_WEBVIEW_DIALOG
                msg.obj= App.app.getString(R.string.terms_and_conditions)
                msg.arg2 = APIs.P_ONE
                (activity as MainActivity).handleMessages(msg)

                dismiss()
            }
        },termStart,termEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


        spannable.setSpan(object : ClickableSpan(){
            override fun onClick(widget: View) {
                var msg =Message()
                msg.arg1 = DtdConstants.SHOW_WEBVIEW_DIALOG
                msg.obj= App.app.getString(R.string.privacy_policy)
                msg.arg2 = APIs.P_ZERO
                (activity as MainActivity).handleMessages(msg)

                dismiss()
            }
        },privacyStart,privacyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        var forgroundColor1 = ForegroundColorSpan(ContextCompat.getColor(activity as MainActivity,R.color.text_color))
        var forgroundColor2 = ForegroundColorSpan(ContextCompat.getColor(activity as MainActivity,R.color.text_color))

        var boldSpan1 = StyleSpan(Typeface.BOLD)
        var boldSpan2 = StyleSpan(Typeface.BOLD)

        spannable.setSpan(forgroundColor1,termStart,termEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(forgroundColor2,privacyStart,privacyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        spannable.setSpan(boldSpan1,termStart,termEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(boldSpan2,privacyStart,privacyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.tvTermsAndPolicy.text = spannable
    }

    fun openBrowserDialog(isRegistration : Boolean){

    }

    private fun setUpRoleSpinner(binding: LayoutRegisterBinding) {
        binding.spRole.adapter = RoleTypeSpinnerAdapter(context,DtdConstants.ROLES_ARRAY)
        binding.spRole.setOnTouchListener(onTocuhListenerSpinner)
        binding.spRole.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                    // TO DO
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0){
                    binding.etBarName.visibility = View.VISIBLE
                    binding.etBarAddress.visibility = View.VISIBLE
                }else{
                    binding.etBarName.visibility = View.GONE
                    binding.etBarAddress.visibility = View.GONE
                }

                binding.nsFeilds.invalidate()
            }
        }
    }


    inner class RegisterClickEvents{

        fun onClickCreateAccount(view: View){

            binding?.let {
                var passwordLength =  it.etPassword.text?.length?:0

                when{
                    it.etFirstName.text?.isEmpty()?:true->{
                        showSnackBarWithMessage(getString(R.string.enter_frist_name))
                    }

                    it.etEmail.text?.isEmpty()?:true->{
                        showSnackBarWithMessage(getString(R.string.enter_email_id))
                    }

                    it.etPhoneNumber.text?.isEmpty()?:true->{
                        showSnackBarWithMessage(getString(R.string.enter_phone_number))
                    }

                    it.etPassword.text?.isEmpty()?:true->{
                    showSnackBarWithMessage(getString(R.string.enter_password))
                    }
                   passwordLength < 5 ->{
                        showSnackBarWithMessage(getString(R.string.more_than_five))
                    }

                    it.etUserName.text?.isEmpty()?:true->{
                        showSnackBarWithMessage(getString(R.string.enter_user_name))
                    }
                    else->{
                        (activity as MainActivity).viewModel.getUserRegistration(requestModel)
                    }
                }
            }
        }

        fun onClickClose(view: View){
                dialog?.dismiss()
        }

        fun onClickLogin(view: View){
            var msg = Message()
            msg.arg1 = DtdConstants.SHOW_LOGIN_FORM
            (activity as MainActivity).handleMessages(msg)
            dialog?.dismiss()
        }
        }

    fun isValid():Boolean{
        return false
    }

    fun showSnackBarWithMessage(messageStr: String){
        var message = Message()
        message.arg1 = DtdConstants.SHOW_SNACKBAR
        message.obj = messageStr
        (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)
    }
}
