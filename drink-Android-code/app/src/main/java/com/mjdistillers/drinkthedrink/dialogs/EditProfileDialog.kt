package com.mjdistillers.drinkthedrink.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.CompoundButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mjdistillers.drinkthedrink.*
import com.mjdistillers.drinkthedrink.adapters.CityArrayAdapter
import com.mjdistillers.drinkthedrink.adapters.ProvincesAndTerriteriesAdapter
import com.mjdistillers.drinkthedrink.adapters.RoleTypeSpinnerAdapter
import com.mjdistillers.drinkthedrink.adapters.StateSpinnerAdapter
import com.mjdistillers.drinkthedrink.databinding.LayoutEditProfileBinding
import com.mjdistillers.drinkthedrink.model.request.GetUserUpdateProfileRequest
import com.mjdistillers.drinkthedrink.model.response.edit_profile.GetEditProfile
import com.mjdistillers.drinkthedrink.model.response.edit_profile.ProvincesAndTerritory
import com.mjdistillers.drinkthedrink.model.response.edit_profile.State
import com.mjdistillers.drinkthedrink.model.response.get_cities.GetCitiesResponse
import com.mjdistillers.drinkthedrink.model.response.update_profile.GetUpdateProfileResponse
import com.mjdistillers.drinkthedrink.model.response.user_visibility.GetUserVisibilityResponse
import com.mjdistillers.drinkthedrink.utilities.*
import com.mjdistillers.drinkthedrink.utilities.interfaces.OneChoiceNoActionsCallbacks
import com.mjdistillers.drinkthedrink.utilities.interfaces.PermissionUtilsCallbacks
import com.mjdistillers.drinkthedrink.utilities.interfaces.PickImageCallbacks
import com.mjdistillers.drinkthedrink.utilities.models.ChoiceListModel
import java.io.File
import javax.inject.Inject

class EditProfileDialog : DialogFragment(), PickImageCallbacks, PermissionUtilsCallbacks {

    lateinit var pref: SharedPrefsUtils
    lateinit var updateProfile: GetUserUpdateProfileRequest
    lateinit var editProfile: GetEditProfile

    @Inject
    lateinit var alertUtils: AlertUtils

    @Inject
    lateinit var dtdUtil: DtdUtils

    @Inject
    lateinit var applicationUtils: ApplicationUtils

    var respEditProfile:GetEditProfile? = null


    val PICK_IMAGE_FROM_CAMERA = 0
    val PICK_IMAGE_FROM_GALLARY = 1
    var isFromMyStatus = false
    var justLoaded = 0


    lateinit var binding:LayoutEditProfileBinding

    init {
        App.app.getComponent().inject(this)
    }

    override fun onStart() {
        super.onStart()
        var dialogFreg = dialog
        dialogFreg?.let {
            if (!isFromMyStatus)
            it.window?.setWindowAnimations(R.style.dialog_fragment_style)
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        respEditProfile?.let {
            setUpDataAfterResponse(it)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        App.app.getComponent().inject(this)
        arguments?.let {
            if (it.containsKey(DtdPrefsKeys.Keys.FROM) && it.getString(DtdPrefsKeys.Keys.FROM) ==
                DtdPrefsKeys.Values.FROM_MY_STATUS){
                isFromMyStatus = true
            }

            if (it.containsKey(DtdPrefsKeys.Keys.RESPONSE_EDIT_PROFILE)) {
                respEditProfile = it.getParcelable(DtdPrefsKeys.Keys.RESPONSE_EDIT_PROFILE)
            }
        }

        respEditProfile?.let {
            editProfile = it
        }

        updateProfile = GetUserUpdateProfileRequest()
        pref = SharedPrefsUtils(activity as MainActivity)
        var dialogreg = super.onCreateDialog(savedInstanceState)
        updateProfile.token = pref.getString(DtdConstants.SERVER_TOKEN)
        updateProfile.id = pref.getInt(DtdConstants.ID)
        updateProfile.role = editProfile.data.role.toInt()



        dialogreg.window?.requestFeature(Window.FEATURE_NO_TITLE)
        binding = DataBindingUtil.inflate(LayoutInflater.from(activity as MainActivity),
            R.layout.layout_edit_profile,null,false)

            var clickEvents = ClickEvents()
            binding.clickEvents = clickEvents
            binding.dataModel = updateProfile
            binding.checkChanged = clickEvents.onCheckedChange()

            var barName = editProfile.data.barName
            var storeName = editProfile.data.storeName

        var barNameETValue = ""


                if (barName == null && storeName == null){
                    barNameETValue = ""
                }else if (barName == null)
                    barNameETValue = storeName
                else if(storeName == null){
                    barNameETValue = barName
                }

        barNameETValue?.let {
            binding.etBarName.setText(it)
            binding.etBarName.isEnabled = false
        }

        dialogreg.setContentView(binding.root)

        dtdUtil.loadImageWithCircleTransform(activity as MainActivity,binding.ivUserImage,
            DtdConstants.IMAGE_BASE_URL+DtdConstants.PROFILE_FOLDER_NAME+pref.getString(DtdConstants.PROFILE_IMAGE),
            DtdConstants.PLACEHOLDER_PROFILE_IMAGE)


//        Glide.with(activity as MainActivity).applyDefaultRequestOptions(RequestOptions().circleCrop())
//            .load()
//            .placeholder(R.drawable.profile_placeholder)
//            .into()

        binding.tvFollowersCount.text = pref.getInt(DtdConstants.FOLLOW_BY).toString()
        binding.tvFollowingCount.text = pref.getInt(DtdConstants.FOLLOW_TO).toString()

        binding.etUserName.clearFocus()

//        binding.etStreetAddress.parent.requestDisallowInterceptTouchEvent(true)

        binding.etStreetAddress.setOnTouchListener { v, event ->
            event?.let {
                if (event.action == MotionEvent.ACTION_DOWN) {
                    if (binding.etStreetAddress.hasFocus()) {
                        v?.parent?.requestDisallowInterceptTouchEvent(true)
                    }
                }
            }
            false
        }

        binding.ivClose.setOnClickListener { dismiss() }

        binding?.toggleVisibility.isChecked = pref.getString(DtdConstants.VISIBILITY_STATUS) != "0"

        attachObservers()
        addOnTouchToASpinner()
        attachObserverUpdateProfile()
        attachObserverCitites()

        setUpFavouriteAlcoholSpinner(binding)
        setUpDaysSpinner(binding)
        setUpPurchaseOnlineQuestion(binding)

        setUpCountrySpinner(binding,editProfile)

        var role = editProfile?.data?.role?.toInt()


        if(role != null &&  role == DtdConstants.ROLE_CUSTOMER)
                binding?.groupRole.visibility = View.VISIBLE
        else
                binding?.groupRole.visibility = View.GONE

        var country = editProfile.data.country
        var state =  editProfile.data.state

        if (country != null && state != null) {
            if (country.isNotEmpty() && state.isNotEmpty()) {
               (activity as MainActivity).viewModel.getCities(country,state)
            }
        }

        applyFonts()

        return dialogreg
    }

    private fun applyFonts() {

        binding.tvProfile.typeface = dtdUtil.fontFutura()

        binding.tvName.typeface = dtdUtil.fontNeutraMedium()
        binding.tvOnline.typeface = dtdUtil.fontNeutraMedium()
        binding.etEmail.typeface = dtdUtil.fontNeutraMedium()
        binding.etUserName.typeface = dtdUtil.fontNeutraMedium()
        binding.etPhoneNumber.typeface = dtdUtil.fontNeutraMedium()
        binding.tvIAmA.typeface = dtdUtil.fontNeutraMedium()
        binding.tvCity.typeface = dtdUtil.fontNeutraMedium()
        binding.tvState.typeface = dtdUtil.fontNeutraMedium()
        binding.tvCountry.typeface = dtdUtil.fontNeutraMedium()
        binding.tvPurchanseOnline.typeface = dtdUtil.fontNeutraMedium()
        binding.rbyes.typeface = dtdUtil.fontNeutraMedium()
        binding.rbno.typeface = dtdUtil.fontNeutraMedium()
        binding.etCity.typeface = dtdUtil.fontNeutraMedium()
        binding.tvStreetAddress.typeface = dtdUtil.fontNeutraMedium()
        binding.etStreetAddress.typeface = dtdUtil.fontNeutraMedium()
        binding.tvWorksAt.typeface = dtdUtil.fontNeutraMedium()
        binding.etBarName.typeface = dtdUtil.fontNeutraMedium()
        binding.etFavoriteDrink.typeface = dtdUtil.fontNeutraMedium()
        binding.etFavoriteCocktail.typeface = dtdUtil.fontNeutraMedium()
        binding.etStatus.typeface = dtdUtil.fontNeutraMedium()
        binding.etFavoriteLiquor.typeface = dtdUtil.fontNeutraMedium()
        binding.etSpeciality.typeface = dtdUtil.fontNeutraMedium()
        binding.tvSave.typeface = dtdUtil.fontNeutraMedium()
        binding.tvUpdatePassword.typeface = dtdUtil.fontNeutraMedium()

        binding.etUserName.filters = arrayOf(dtdUtil.obtainFilterFirstCharNoSpace(binding.etUserName))
        binding.etPhoneNumber.filters = arrayOf(dtdUtil.obtainFilterFirstCharNoSpace(binding.etPhoneNumber))
        binding.etCity.filters = arrayOf(
            dtdUtil.obtainFilterFirstCharNoSpace(binding.etCity),
            dtdUtil.obtainInputFilterSpecialChars(binding.etCity))

        binding.etBarName.filters = arrayOf(
            dtdUtil.obtainFilterFirstCharNoSpace(binding.etBarName),
            dtdUtil.obtainInputFilterSpecialChars(binding.etBarName))

        binding.etFavoriteDrink.filters = arrayOf(
            dtdUtil.obtainFilterFirstCharNoSpace(binding.etFavoriteDrink),
            dtdUtil.obtainInputFilterSpecialChars(binding.etFavoriteDrink))


        binding.etFavoriteCocktail.filters = arrayOf(
            dtdUtil.obtainFilterFirstCharNoSpace(binding.etFavoriteCocktail),
            dtdUtil.obtainInputFilterSpecialChars(binding.etFavoriteCocktail))

        binding.etStatus.filters = arrayOf(
            dtdUtil.obtainFilterFirstCharNoSpace(binding.etStatus),
            dtdUtil.obtainInputFilterSpecialChars(binding.etStatus))

        binding.etFavoriteLiquor.filters = arrayOf(
            dtdUtil.obtainFilterFirstCharNoSpace(binding.etFavoriteLiquor),
            dtdUtil.obtainInputFilterSpecialChars(binding.etFavoriteLiquor))

        binding.etSpeciality.filters = arrayOf(
            dtdUtil.obtainFilterFirstCharNoSpace(binding.etSpeciality),
            dtdUtil.obtainInputFilterSpecialChars(binding.etSpeciality))
    }

    private fun setUpPurchaseOnlineQuestion(binding: LayoutEditProfileBinding) {
        editProfile?.data?.alcohol_online?.let {
            updateProfile.alcohol_online = editProfile?.data?.alcohol_online
            when (it) {
                App.app.resources.getString(R.string.yes).toLowerCase() -> binding.rgPurchaseOnline.check(binding.rbyes.id)
                App.app.resources.getString(R.string.no).toLowerCase() -> binding.rgPurchaseOnline.check(binding.rbno.id)
                else -> {
                    binding.rgPurchaseOnline.check(binding.rbyes.id)
                }
            }
        }

            binding.rgPurchaseOnline.setOnCheckedChangeListener { group, checkedId ->
                when(checkedId){
                    binding.rbyes.id->{
                        updateProfile.alcohol_online = APIs.V_YES.toLowerCase()
                    }

                    binding.rbno.id->{
                        updateProfile.alcohol_online = APIs.V_NO.toLowerCase()
                    }
                }
            }
    }

    private fun setUpDaysSpinner(binding: LayoutEditProfileBinding) {

        binding.spWeekDays.adapter = RoleTypeSpinnerAdapter(context, DtdConstants.DAYS_ARRAY)
            binding.spWeekDays.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if(position > 0)  updateProfile.outing_day = DtdConstants.DAYS_ARRAY[position]
                }

            }

        binding.spWeekDays.setSelection(DtdConstants.DAYS_ARRAY.indexOf(editProfile.data.outing_day))
        updateProfile.outing_day = editProfile.data.outing_day
    }

    private fun setUpFavouriteAlcoholSpinner(binding: LayoutEditProfileBinding) {
        binding.spFavAlcohal.adapter = RoleTypeSpinnerAdapter(context, DtdConstants.FAVOURITE_ALCOHOL_ARRAY)
        binding.spFavAlcohal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateProfile.fav_alcohol = DtdConstants.FAVOURITE_ALCOHOL_ARRAY[position]
                updateProfile.fav_spirit = DtdConstants.FAVOURITE_ALCOHOL_ARRAY[position]
            }
        }
    }

    fun attachObserverCitites(){
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
                            updateProfile.city = textToUse
                            binding?.etCity?.setText(textToUse.toUpperCase())
                        }
                    })

                    if (justLoaded > 1){
                        binding.etCity.text.clear()
                    }

                    ++justLoaded

/*
                    binding?.etCity?.setOnItemClickListener { parent, view, position, id ->
                        Log.i("VVV","postion : "+position+"  id : "+id)
                    }
*/
                }
            })
    }

    fun attachObserverUpdateProfile(){
        var observer = Observer<GetUpdateProfileResponse> {
            if(activity != null) {
                it.toString()
                if (it.status) {

                    var changeVisi = if (binding.toggleVisibility.isChecked){"1"} else{"0"}
                    pref.saveString(DtdConstants.VISIBILITY_STATUS,changeVisi)
                    pref.saveInt(DtdPrefsKeys.Keys.USER_ROLE,updateProfile.role)

                    var message = Message()
                    message.obj = getString(R.string.updated_successfully)
                    (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)

//                    ToastSnackUtils.toastShort(
//                        (context as MainActivity),
//                        )

                    dialog?.dismiss()
                } else {
                    var message = Message()
                    message.obj = DtdConstants.PROBLEM_OCCURED
                    (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)

//                    ToastSnackUtils.toastShort(
//                        (context as MainActivity),
//                        DtdConstants.PROBLEM_OCCURED)
                }
            }
        }

        (activity as MainActivity).viewModel.liveDataUpdateProfile().observe(this,observer)
    }


    private fun addOnTouchToASpinner() {
        var dialog = alertUtils.showInformationAlert(activity as MainActivity,App.app.getString(R.string.alert),
            App.app.getString(R.string.choose_country),App.app.getString(R.string.ok))

        var touchListener = object : View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (event?.action == MotionEvent.ACTION_UP){
                    return if (updateProfile.country.isEmpty()) {

                        if (!dialog.isShowing) dialog.show()
                        true
                    }else{
                        false
                    }
                }
                return false
            }
        }

        binding.spState.setOnTouchListener(touchListener)
        binding.etCity.setOnTouchListener(touchListener)
    }

    private fun attachObservers() {

        var observer = Observer<GetUserVisibilityResponse> {

            var changeVisi = if (it.checked){ "1"} else{ "0"}
            var messageStr = it?.data?.message?.get(0)

            if (messageStr == null) messageStr = ""
            if (it.status) {

                var message = Message()
                message.arg1 = DtdConstants.SHOW_SNACKBAR
                message.obj = messageStr

                (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)


//                ToastSnackUtils.snackBar(binding.root,
//                    messageStr,
//                    Snackbar.LENGTH_SHORT,
//                    false)

                pref.saveString(DtdConstants.VISIBILITY_STATUS,changeVisi)
            }else{


                var message = Message()
                message.obj = messageStr
                (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)


//                ToastSnackUtils.toastShort(activity as MainActivity, messageStr)

                pref.saveString(DtdConstants.VISIBILITY_STATUS,changeVisi)
            }

            var visibilit = pref.getString(DtdConstants.VISIBILITY_STATUS)
            binding.toggleVisibility.isChecked = visibilit == "1"

        }

        (context as MainActivity).viewModel.liveDataUpdateUserVisiblity()
            .observe(context as MainActivity, observer)
    }

    fun setUpDataAfterResponse(editProfile: GetEditProfile){
        if(activity != null) {

            var favAlchol = editProfile.data.fav_alcohol

            DtdConstants.FAVOURITE_ALCOHOL_ARRAY.iterator().forEach{
                if (it == favAlchol){
                    binding.spFavAlcohal.setSelection(DtdConstants.FAVOURITE_ALCOHOL_ARRAY.indexOf(favAlchol))
                }
            }



            setUpRoleSpinner(binding)
            setUpdData(editProfile)

//            when {
//                editProfile.data.role.toInt() == DtdConstants.ROLE_USER -> {
//                    binding.spRole.setSelection(0)
//                }
//                editProfile.data.role.toInt() == DtdConstants.ROLE_BAR_TENDER -> {
//                    binding.spRole.setSelection(1)
//                }
//                editProfile.data.role.toInt() == DtdConstants.ROLE_PROMOTOR -> {
//                    binding.spRole.setSelection(2)
//                }
//                editProfile.data.role.toInt() == DtdConstants.ROLE_OWNER -> {
//                    binding.spRole.setSelection(3)
//                }
//            }

            if (isFromMyStatus) {
                applicationUtils.showSoftKeyboard(
                    activity as MainActivity,
                    binding.etStatus
                )
            }
        }
    }

    private fun setUpdData(it: GetEditProfile?) {

        var obj = it?.data

        updateProfile.name = obj?.name?.toUpperCase()
        updateProfile.email = obj?.email
        updateProfile.phone = obj?.phone
        updateProfile.work_at =  obj?.workAt
        updateProfile.city = obj?.city?.toUpperCase()
        updateProfile?.address = obj?.address
        updateProfile?.favouriteDrink = obj?.favDrink
        updateProfile?.fav_spirit = obj?.favSpirit

        updateProfile?.fav_cocktail = obj?.favCocktail
        updateProfile.username = obj?.username
        updateProfile.my_status = obj?.myStatus
        updateProfile.fav_liquore = obj?.fav_liquor
        updateProfile.special = obj?.speciality
    }

    private fun setUpCountrySpinner(binding: LayoutEditProfileBinding, editResponse: GetEditProfile) {

        binding.spCountry.adapter = RoleTypeSpinnerAdapter(context, DtdConstants.COUNTRY_ARRAY)
        binding.spCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateProfile.country = DtdConstants.COUNTRY_ARRAY[position]

                when(position){
                    0->{
                        binding.spState.setSelection(0)
                    }
                    1->{
                        binding.tvState.text = resources.getString(R.string.states)
                        setUpStatesSpinner(binding,1,editProfile)
                    }
                    2->{
                        binding.tvState.text = resources.getString(R.string.provinces)
                        setUpStatesSpinner(binding,2,editProfile)
                    }
                }
            }
        }


        if (!editProfile.data.country.isNullOrEmpty()){
            var country = editProfile.data.country
            DtdConstants.COUNTRY_ARRAY.iterator().forEach {
                if (it == country){
                    binding.spCountry.setSelection(DtdConstants.COUNTRY_ARRAY.indexOf(it))
                }
            }
        }

    }


    private fun setUpStatesSpinner(binding: LayoutEditProfileBinding,flag:Int, editProfile: GetEditProfile) {

        if (flag == 1){
            var listStates = mutableListOf<State>()

            var states = State()
            states.fullName = ""
            states.abbreviation = ""

            listStates.add(states)
            listStates.addAll(editProfile.data.states)

//            addOnTouchToASpinner()
            binding.spState.adapter =  StateSpinnerAdapter(activity as MainActivity,listStates)
        }else{
            var listProvinces = mutableListOf<ProvincesAndTerritory>()

            var provinces = ProvincesAndTerritory()
            provinces.fullName = ""
            provinces.abbreviation = ""

            listProvinces.add(provinces)
            listProvinces.addAll(editProfile.data.provincesAndTerritories)

            binding.spState.adapter =  ProvincesAndTerriteriesAdapter(activity as MainActivity,listProvinces)
        }

        binding.spState.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(flag == 1){
                    updateProfile.state = editProfile.data.states[position].abbreviation
                    if(position > 1) {
                        (activity as MainActivity).viewModel.getCities(
                            updateProfile.country,
                            updateProfile.state
                        )
                    }

                }else{
                    updateProfile.state = editProfile.data.provincesAndTerritories[position].abbreviation


                    (activity as MainActivity).viewModel.getCities(updateProfile.country,updateProfile.state)
                }
            }
        }

        if (flag == 1){
            if (!editProfile.data.state.isNullOrEmpty()){
                var st = editProfile.data.state
                editProfile.data.states.iterator().forEach {
                    if (it.abbreviation == st){
                        binding.spState.setSelection(editProfile.data.states.indexOf(it))
                    }
                }
            }
        }
    }

    private fun setUpRoleSpinner(binding: LayoutEditProfileBinding) {
        binding.spRole.adapter = RoleTypeSpinnerAdapter(context, DtdConstants.NEW_NEW_ROLE_ARRAY)
        binding.spRole.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // TO DO
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position){
                    1->{
                        updateProfile.role = DtdConstants.ROLE_HOSPITALITY
                    }
                        2->{
                        updateProfile.role = DtdConstants.ROLE_RETAIL_LIQUORE
                    }
                    3->{
                        updateProfile.role = DtdConstants.ROLE_EVENTS
                    }
                    else->{
                        updateProfile.role = DtdConstants.ROLE_CUSTOMER
                    }

                  /*  0->{
                        updateProfile.role = DtdConstants.ROLE_USER
                        updateProfile.role = 0
                        binding.toggleVisibility.isChecked = true
                       }
                    1->{
                        updateProfile.role = DtdConstants.ROLE_BAR_TENDER
                        binding.toggleVisibility.isChecked = true
                    }
                    2->{
                        updateProfile.role = DtdConstants.ROLE_STORE_MANAGER
                        binding.toggleVisibility.isChecked = false
                    }
                    3->{
                        updateProfile.role = DtdConstants.ROLE_PROMOTOR
                        binding.toggleVisibility.isChecked = false
                    }
                    4->{
                        updateProfile.role = DtdConstants.ROLE_USER
                        binding.toggleVisibility.isChecked = true
                    }*/
                }

//                if (position == 1){
//                    binding.etBarName.visibility = View.VISIBLE

//                    binding.groupState.visibility = View.VISIBLE
//                    binding.groupCountry.visibility = View.VISIBLE
//
//                    setUpCountrySpinner(binding,editProfile)


//                }else if(position > 1){
//                    binding.etBarName.visibility = View.GONE

//                    binding.groupState.visibility = View.GONE
//                    binding.groupCountry.visibility = View.GONE

//                }
//                binding.nsFeilds.invalidate()
            }
        }
    }


    inner class ClickEvents{

        fun onClickSave(view: View) {
            updateProfile.visibiltiy_status = if(binding.toggleVisibility.isChecked) 1 else 0

            when {

                updateProfile.name.isEmpty()->{
                    var message = Message()
                    message.obj = getString(R.string.enter_name)
                    (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)
                }

                updateProfile.username.isEmpty() -> {

                    var message = Message()
                    message.obj = getString(R.string.enter_username)
                    (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)

//                    ToastSnackUtils.toastShort(context as MainActivity,
//                        "Please Enter Username")
                }
                updateProfile.phone.isEmpty() -> {
                    var message = Message()
                    message.obj = getString(R.string.enter_phone)
                    (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)

//                    ToastSnackUtils.toastShort((context as MainActivity),
//                        "Please Enter Phone Number")
                }
                updateProfile.country.isEmpty()->{
                var message = Message()
                message.obj = getString(R.string.enter_country)
                (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)
            }
             updateProfile.state.isEmpty()->{
                    var message = Message()
                    message.obj = getString(R.string.enter_country)
                    (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)
                }

                updateProfile.city.isEmpty()->{
                var message = Message()
                message.obj = getString(R.string.enter_city)
                (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)
            }

                updateProfile.city.isEmpty()->{
                    var message = Message()
                    message.obj = getString(R.string.enter_city)
                    (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)
                }

                updateProfile.phone.length > 10->{
                    var message = Message()
                    message.obj = getString(R.string.enter_valid_phone)
                    (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)

                }

                (updateProfile.role == 0) ->{

                    var message = Message()
                    message.obj = getString(R.string.enter_role)
                    (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)


//                    if (updateProfile.country.isEmpty()){
//
//                        var message = Message()
//                        message.obj = getString(R.string.choose_country_)
//                        (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)
//
//
////                        ToastSnackUtils.toastShort((context as MainActivity),
////                            "Choose country")
//                    }else if (updateProfile.state.isEmpty()){
//
//                        var message = Message()
//                        message.obj = getString(R.string.choose_state)
//                        (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)
//
////                        ToastSnackUtils.toastShort(context as MainActivity,
////                            "Choose state")
//                    }
                }
                else -> {
                    (activity as MainActivity).viewModel.getUpdateProfile(updateProfile)
                    dismiss()
                }
            }
        }

        fun onClickUpdatePassword(view: View){
                var message = Message()
                message.arg1 = DtdConstants.SHOW_UPDATE_PASSWORD
                (activity as MainActivity).handleMessages(message)
                dismiss()
        }

        fun onClicFollowGroup(view: View){
            var msg = Message()
            msg.arg1 = DtdConstants.SHOW_FOLLOW
            (activity as MainActivity).handleMessages(msg)

            dismiss()
        }

        fun onClickClose(view:View){
            dialog?.dismiss()
        }

        fun onImageClick(view: View){

            var list = mutableListOf<ChoiceListModel>()
            var ch1 = ChoiceListModel()
            ch1.text = "From Camera"
            var ch2 = ChoiceListModel()
            ch2.text = "From Media"

            list.add(ch1)
            list.add(ch2)



            AlertUtils().showChooseOneFromListAlertNoActions(activity as MainActivity,
                "Pick Image",object : OneChoiceNoActionsCallbacks{
                    override fun eventListItemSelected(position: Int) {
                        when(position){
                            0->{
                                    val permissions = arrayOf(android.Manifest.permission.CAMERA,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    val rationale = arrayOf("This permission is required to capture image from camera")
                                    (activity as MainActivity).requestPermission(this@EditProfileDialog,permissions,rationale,PICK_IMAGE_FROM_CAMERA)
                            }
                            1->{
                                val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                                val rationale = arrayOf("We need this Permission to read image")
                                (activity as MainActivity).requestPermission(this@EditProfileDialog,permissions,rationale,PICK_IMAGE_FROM_GALLARY)
                            }
                        }
                    }
                }, list)
        }

        fun onCheckedChange(): CompoundButton.OnCheckedChangeListener {
            return CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->

//                var visState = pref.getString(DtdConstants.VISIBILITY_STATUS)
//                binding.toggleVisibility.isChecked = visState == "1"
                updateProfile.visibiltiy_status = if(isChecked) 1 else 0

//                var visibility = GetUserVisibilityChange()
//                visibility.id = pref.getInt(DtdConstants.ID)
//                var latlng = dtdUtil.getAppsLatLng()
//                visibility.user_latitude = latlng[0]
//                visibility.user_longitude = latlng[1]
//                visibility.visibility_status = visState
//                visibility.checked = isChecked
//                (context as MainActivity).viewModel.getUpdateVisibility(visibility)
            }
        }

    }

    override fun resultPickFromCamera(file: File?) {
        file?.let {
                updateProfile.fileToUpload = file.absolutePath

                Glide.with(activity as MainActivity)
                    .asBitmap().load(Uri.fromFile(file))
                    .apply(RequestOptions.noAnimation())
                    .apply(RequestOptions.circleCropTransform())
                    .into(binding.ivUserImage)
            }
    }


    override fun resultPickFromMedia(data: File?) {
        data?.let {
            updateProfile.fileToUpload = data.absolutePath

            Glide.with(activity as MainActivity)
                .asBitmap().load(Uri.fromFile(data))
                .apply(RequestOptions.noAnimation())
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivUserImage)

//            binding.ivUserImage.setImageURI(data?.data)
        }
    }


    override fun permissionGranted(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {

        when(requestCode){
            PICK_IMAGE_FROM_GALLARY->{
                if (!grantResults.contains(0)){
                    (activity as MainActivity).pickImageFromMedia(this)
                }else{
                    var message = Message()
                    message.obj = getString(R.string.allow_permissions)
                    (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)

//                    ToastSnackUtils.toastShort(activity as MainActivity,"Please allow Permissions")
                }
            }
            PICK_IMAGE_FROM_CAMERA->{
                if (!grantResults.contains(0)){
                    (activity as MainActivity).pickImageFromCamera(this)
                }else{
                    var message = Message()
                    message.obj = getString(R.string.allow_permissions)
                    (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)

//                    ToastSnackUtils.toastShort(activity as MainActivity,"Please allow Permissions")
                }
            }
        }
    }

}