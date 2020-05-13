package com.mjdistillers.drinkthedrink.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.CompoundButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.mjdistillers.drinkthedrink.*
import com.mjdistillers.drinkthedrink.databinding.LayoutProfileBinding
import com.mjdistillers.drinkthedrink.databinding.LayoutProfileFromFollowersBinding
import com.mjdistillers.drinkthedrink.model.SearchFilterModel
import com.mjdistillers.drinkthedrink.model.request.GetUserVisibilityChange
import com.mjdistillers.drinkthedrink.model.response.get_profile.GetProfileResponse
import com.mjdistillers.drinkthedrink.model.response.update_cocktail.GetSaveCocktailImages
import com.mjdistillers.drinkthedrink.model.response.user_visibility.GetUserVisibilityResponse
import com.mjdistillers.drinkthedrink.utilities.*
import com.mjdistillers.drinkthedrink.utilities.interfaces.OneChoiceNoActionsCallbacks
import com.mjdistillers.drinkthedrink.utilities.interfaces.PermissionUtilsCallbacks
import com.mjdistillers.drinkthedrink.utilities.interfaces.PickImageCallbacks
import com.mjdistillers.drinkthedrink.utilities.models.ChoiceListModel
import java.io.File
import javax.inject.Inject

class ProfileDialog : DialogFragment(), PickImageCallbacks, PermissionUtilsCallbacks, View.OnClickListener {


    val PICK_IMAGE_FROM_CAMERA = 0
    val PICK_IMAGE_FROM_GALLARY = 1
    var image_number = 0

    var PROFILE_FROM: String? = ""
    var userId:Int? = 0
    var seachFilterModel:SearchFilterModel? = null

    lateinit var binding:LayoutProfileBinding
    lateinit var bindingFollowers:LayoutProfileFromFollowersBinding

    @Inject
    lateinit var dtdUtil: DtdUtils


    lateinit var pref:SharedPrefsUtils


    var country:String
    var state:String
    var city:String
    var works_at:String

    init {
        App.app.getComponent().inject(this)
        country = App.app.getString(R.string.country)
        state = App.app.getString(R.string.state)
        city = App.app.getString(R.string.city_)
        city = App.app.getString(R.string.city_)
        works_at = App.app.getString(R.string.works_at)
    }

    override fun onStart() {
        super.onStart()
        var dialogFreg = dialog
        dialogFreg?.let {
            it.window?.setWindowAnimations(R.style.dialog_fragment_style)
            //            dialogFreg.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT,
//                ConstraintLayout.LayoutParams.WRAP_CONTENT)
            dialogFreg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dialogreg = super.onCreateDialog(savedInstanceState)
        pref = SharedPrefsUtils(activity as MainActivity)

        PROFILE_FROM = arguments?.getString(DtdPrefsKeys.Keys.PROFILE_FROM)?:""
        userId = arguments?.getInt(DtdPrefsKeys.Keys.USER_ID,0)

        if (PROFILE_FROM != DtdPrefsKeys.Values.PROFILE_FROM_LOGIN_MENU) {
            bindingFollowers = DataBindingUtil.inflate(LayoutInflater.from(activity as MainActivity),
                R.layout.layout_profile_from_followers,null,false)

            applyFontsForProfileFromFollow(bindingFollowers)
        }else{
            binding = DataBindingUtil.inflate(LayoutInflater.from(activity as MainActivity),
                R.layout.layout_profile,null,false)

            applyFontsForProfile(binding)
        }

        pref =  SharedPrefsUtils(activity as MainActivity)

        dialogreg.window?.requestFeature(Window.FEATURE_NO_TITLE)

        if (PROFILE_FROM == DtdPrefsKeys.Values.PROFILE_FROM_LOGIN_MENU){
            binding.llCocktailImages.visibility = View.VISIBLE
            binding.llCameraTitle.visibility = View.VISIBLE
            binding.tvEditProfile.visibility = View.VISIBLE

            binding.ivClose.setOnClickListener { dismiss() }
            var clickEvent = ClickEvents()
            binding.clickEvents = clickEvent
            binding.checkChanged = clickEvent.onCheckedChange()

            setDataForOurProfile()

            dialogreg?.setContentView(binding.root)

        }else{
            bindingFollowers.llCocktailImages.visibility = View.GONE
            bindingFollowers.llCameraTitle.visibility = View.GONE
            bindingFollowers.tvEditProfile.visibility = View.INVISIBLE


            bindingFollowers.ivClose.setOnClickListener { dismiss() }
            var clickEvent = ClickEvents()
            bindingFollowers.clickEvents = clickEvent
//            bindingFollowers.checkChanged = clickEvent.onCheckedChange()

//            attachObserverForProfile()
            var id:Long = userId?.toLong()?:0
            if (id > 0){
                if (arguments?.containsKey(DtdPrefsKeys.Keys.SEARCH_FILTER_MODEL) == true){
                    seachFilterModel = arguments?.getParcelable(DtdPrefsKeys.Keys.SEARCH_FILTER_MODEL)
                    var profileResp = arguments?.getParcelable<GetProfileResponse>(DtdPrefsKeys.Keys.RESPONSE_PROFILE)
                    profileResp?.let {
                        setDataForProfileWithID(it)
                    }
                }
            }
            else{
                var message = Message()
                message.obj = getString(R.string.incorrect_user)
                (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)

//                ToastSnackUtils.toastShort(activity as MainActivity,getString(R.string.incorrect_user))
                dialogreg.dismiss()
            }

            dialogreg?.setContentView(bindingFollowers.root)
        }

        attachObservers()
        attachUpdateDataObservers()

        return dialogreg
    }

    private fun applyFontsForProfile(bind: LayoutProfileBinding) {
        bind?.let {
            it.tvProfile.typeface = dtdUtil.fontFutura()

            it.tvEditProfile.typeface = dtdUtil.fontNeutraMedium()
            it.tvOnline.typeface = dtdUtil.fontNeutraMedium()

            it.tvEmail.typeface = dtdUtil.fontNeutraMedium()
            it.tvPhoneNumber.typeface = dtdUtil.fontNeutraMedium()

            it.tvCountry.typeface = dtdUtil.fontNeutraMedium()
            it.tvState.typeface = dtdUtil.fontNeutraMedium()
            it.tvCity.typeface = dtdUtil.fontNeutraMedium()
            it.tvWorksAt.typeface = dtdUtil.fontNeutraMedium()
            it.tvFavoriteDrink.typeface = dtdUtil.fontNeutraMedium()
            it.tvPhotos.typeface = dtdUtil.fontNeutraMedium()

            it.tvName.typeface = dtdUtil.fontNeutraMedium()
            it.tvFollowersCount.typeface = dtdUtil.fontNeutraMedium()
            it.tvFollowingCount.typeface = dtdUtil.fontNeutraMedium()
        }

    }

    private fun applyFontsForProfileFromFollow(bind: LayoutProfileFromFollowersBinding){
        bind?.let {
            it.tvProfile.typeface = dtdUtil.fontFutura()

            it.tvEditProfile.typeface = dtdUtil.fontNeutraMedium()
            it.tvOnline.typeface = dtdUtil.fontNeutraMedium()

            it.tvEmail.typeface = dtdUtil.fontNeutraMedium()
            it.tvPhoneNumber.typeface = dtdUtil.fontNeutraMedium()
            it.tvCountry.typeface = dtdUtil.fontNeutraMedium()
            it.tvState.typeface = dtdUtil.fontNeutraMedium()
            it.tvCity.typeface = dtdUtil.fontNeutraMedium()
            it.tvWorksAt.typeface = dtdUtil.fontNeutraMedium()
            it.tvFavoriteDrink.typeface = dtdUtil.fontNeutraMedium()
            it.tvPhotos.typeface = dtdUtil.fontNeutraMedium()
            it.tvMessages.typeface = dtdUtil.fontNeutraMedium()

            it.tvName.typeface = dtdUtil.fontNeutraMedium()
            it.tvFollowersCount.typeface = dtdUtil.fontNeutraMedium()
            it.tvFollowingCount.typeface = dtdUtil.fontNeutraMedium()
        }
    }

//    private fun attachObserverForProfile() {
//        var observer = Observer<GetProfileResponse> {
//                setDataForProfileWithID(it)
//        }
//
//        (activity as MainActivity)
//            .viewModel
//            .liveDataGetProfile()
//            .observe(activity as MainActivity,observer)
//    }

    private fun setDataForOurProfile() {
        if (PROFILE_FROM == DtdPrefsKeys.Values.PROFILE_FROM_LOGIN_MENU) {

            dtdUtil.loadImageWithCircleTransform(
                activity as MainActivity, binding.ivUserImage,
                DtdConstants.IMAGE_BASE_URL + DtdConstants.PROFILE_FOLDER_NAME + pref.getString(
                    DtdConstants.PROFILE_IMAGE
                ), DtdConstants.PLACEHOLDER_PROFILE_IMAGE)

            binding.tvMessage.isChecked = pref.getString(DtdConstants.VISIBILITY_STATUS) == "1"

            binding.tvEmail.text = pref.getString(DtdConstants.EMAIL)
            binding.tvPhoneNumber.text = pref.getString(DtdConstants.PHONE)
//            binding.tvCountry.text = pref.getString(DtdConstants.)
//            binding.tvState.text = pref.getString(DtdConstants.)
//            binding.tvCity.text = pref.getString(DtdConstants.)
//            binding.tvWorksAt.text = resources.getString(R.string.works_at) + " : " + pref.getString(DtdConstants.WORKS_AT)
            binding.tvFavoriteDrink.text =
                resources.getString(R.string.favorite_drink) + " : " + pref.getString(DtdConstants.FAVORITE_DRINK)
            binding.tvName.text = pref.getString(DtdConstants.NAME)
            binding.tvFollowersCount.text = pref.getInt(DtdConstants.FOLLOW_BY).toString()
            binding.tvFollowingCount.text = pref.getInt(DtdConstants.FOLLOW_TO).toString()

            binding.tvCountry.text = App.app.getString(R.string.country)+" : "+pref.getString(DtdConstants.COUNTRY)
            binding.tvState.text = App.app.getString(R.string.state)+" : "+pref.getString(DtdConstants.STATE)
            binding.tvCity.text = App.app.getString(R.string.city_)+" : "+pref.getString(DtdConstants.CITY)
            binding.tvWorksAt.text = App.app.getString(R.string.works_at)+" : "+pref.getString(DtdConstants.WORKS_AT_STR)

            binding.ivImageOneCancel.setOnClickListener(this)
            binding.ivImageTwoCancel.setOnClickListener(this)
            binding.ivImageThreeCancel.setOnClickListener(this)

            binding.ivImageOne.setOnClickListener(this)
            binding.ivImageTwo.setOnClickListener(this)
            binding.ivImageThree.setOnClickListener(this)


            binding.tvEditProfile.setOnClickListener {
                var msg = Message()
                msg.arg1 = DtdConstants.SHOW_EDIT_PROFILE
                (activity as MainActivity).handleMessages(msg)
                dialog?.dismiss()
            }

            loadImages()
        }else{

            dtdUtil.loadImageWithCircleTransform(
                activity as MainActivity, bindingFollowers.ivUserImage,
                DtdConstants.IMAGE_BASE_URL + DtdConstants.PROFILE_FOLDER_NAME + pref.getString(
                    DtdConstants.PROFILE_IMAGE
                ),
                DtdConstants.PLACEHOLDER_PROFILE_IMAGE
            )

            bindingFollowers.tvEmail.text = pref.getString(DtdConstants.EMAIL)
            bindingFollowers.tvPhoneNumber.text = pref.getString(DtdConstants.PHONE)
//            bindingFollowers.tvAddress.text = pref.getString(DtdConstants.ADDRESS)
//            bindingFollowers.tvWorksAt.text =
//                resources.getString(R.string.works_at) + " : " + pref.getString(DtdConstants.WORKS_AT)
            bindingFollowers.tvFavoriteDrink.text =
                resources.getString(R.string.favorite_drink) + " : " + pref.getString(DtdConstants.FAVORITE_DRINK)
            bindingFollowers.tvName.text = pref.getString(DtdConstants.NAME)
            bindingFollowers.tvFollowersCount.text = pref.getInt(DtdConstants.FOLLOW_BY).toString()
            bindingFollowers.tvFollowingCount.text = pref.getInt(DtdConstants.FOLLOW_TO).toString()

            bindingFollowers.tvCountry.text = App.app.getString(R.string.country)+" : "+pref.getString(DtdConstants.COUNTRY)
            bindingFollowers.tvState.text = App.app.getString(R.string.state)+" : "+pref.getString(DtdConstants.STATE)
            bindingFollowers.tvCity.text = App.app.getString(R.string.city_)+" : "+pref.getString(DtdConstants.CITY)
            bindingFollowers.tvWorksAt.text = App.app.getString(R.string.works_at)+" : "+pref.getString(DtdConstants.WORKS_AT_STR)


            bindingFollowers.ivImageOne.setOnClickListener(this)
            bindingFollowers.ivImageTwo.setOnClickListener(this)
            bindingFollowers.ivImageThree.setOnClickListener(this)


            bindingFollowers.tvEditProfile.setOnClickListener {
                var msg = Message()
                msg.arg1 = DtdConstants.SHOW_EDIT_PROFILE
                (activity as MainActivity).handleMessages(msg)
                dialog?.dismiss()
            }

            loadImages()
        }
    }

    fun setDataForProfileWithID(profileData: GetProfileResponse) {

        var data = profileData.data

        if (PROFILE_FROM == DtdPrefsKeys.Values.PROFILE_FROM_LOGIN_MENU) {
            dtdUtil.loadImageWithCircleTransform(
                activity as MainActivity, binding.ivUserImage,
                DtdConstants.IMAGE_BASE_URL + DtdConstants.PROFILE_FOLDER_NAME + data.profileImage,
                DtdConstants.PLACEHOLDER_PROFILE_IMAGE
            )

            bindingFollowers.tvEmail.text = data.email
            bindingFollowers.tvPhoneNumber.text = data.phone
//            binding.tvAddress.text = data.address
//        binding.tvWorksAt.text = data.
            bindingFollowers.tvFavoriteDrink.text = data.favDrink
            bindingFollowers.tvName.text = data.name
            bindingFollowers.tvFollowersCount.text = data.followBy.toString()
            bindingFollowers.tvFollowingCount.text = data.followTo.toString()


            bindingFollowers.tvCountry.text = country+" : "+pref.getString(DtdConstants.COUNTRY)
            bindingFollowers.tvState.text = state+" : "+pref.getString(DtdConstants.STATE)
            bindingFollowers.tvCity.text = city+" : "+pref.getString(DtdConstants.CITY)
            bindingFollowers.tvWorksAt.text = works_at+" : "+pref.getString(DtdConstants.WORKS_AT_STR)


            bindingFollowers.ivImageOne.setOnClickListener(this)
            bindingFollowers.ivImageTwo.setOnClickListener(this)
            bindingFollowers.ivImageThree.setOnClickListener(this)

            bindingFollowers.ivClose.setOnClickListener { dismiss() }

            bindingFollowers.tvEditProfile.setOnClickListener {
                var msg = Message()
                msg.arg1 = DtdConstants.SHOW_EDIT_PROFILE
                (activity as MainActivity).handleMessages(msg)
                dialog?.dismiss()
            }

            dtdUtil.loadImageNoTransform(
                activity as MainActivity, bindingFollowers.ivImageOne,
                DtdConstants.IMAGE_BASE_URL + DtdConstants.PROFILE_BAR_IMAGES_FOLDER_NAME + data.drinkImage1,
                DtdConstants.PLACEHOLDER_NO_IMAGE
            )

            dtdUtil.loadImageNoTransform(
                activity as MainActivity, bindingFollowers.ivImageTwo,
                DtdConstants.IMAGE_BASE_URL + DtdConstants.PROFILE_BAR_IMAGES_FOLDER_NAME + data.drinkImage2,
                DtdConstants.PLACEHOLDER_NO_IMAGE
            )

            dtdUtil.loadImageNoTransform(
                activity as MainActivity, bindingFollowers.ivImageThree,
                DtdConstants.IMAGE_BASE_URL + DtdConstants.PROFILE_BAR_IMAGES_FOLDER_NAME + data.drinkImage3,
                DtdConstants.PLACEHOLDER_NO_IMAGE)
        }else{

            dtdUtil.loadImageWithCircleTransform(
                activity as MainActivity, bindingFollowers.ivUserImage,
                DtdConstants.IMAGE_BASE_URL + DtdConstants.PROFILE_FOLDER_NAME + data.profileImage,
                DtdConstants.PLACEHOLDER_PROFILE_IMAGE)

            bindingFollowers.tvEmail.text = data.email
            bindingFollowers.tvPhoneNumber.text = data.phone
//            bindingFollowers.tvAddress.text = data.address
//        binding.tvWorksAt.text = data.
            bindingFollowers.tvFavoriteDrink.text = data.favDrink
            bindingFollowers.tvName.text = data.name
            bindingFollowers.tvFollowersCount.text = data.followBy.toString()
            bindingFollowers.tvFollowingCount.text = data.followTo.toString()

            bindingFollowers.tvCountry.text =country+" : "+data.country
            bindingFollowers.tvState.text = state+" : "+data.state
            bindingFollowers.tvCity.text = city+" : "+data.city
            bindingFollowers.tvWorksAt.text = works_at+" : "

            bindingFollowers.ivImageOne.setOnClickListener(this)
            bindingFollowers.ivImageTwo.setOnClickListener(this)
            bindingFollowers.ivImageThree.setOnClickListener(this)

            bindingFollowers.ivClose.setOnClickListener { dismiss() }

            bindingFollowers.tvEditProfile.setOnClickListener {
                var msg = Message()
                msg.arg1 = DtdConstants.SHOW_EDIT_PROFILE
                (activity as MainActivity).handleMessages(msg)
                dialog?.dismiss()
            }

            dtdUtil.loadImageNoTransform(
                activity as MainActivity, bindingFollowers.ivImageOne,
                DtdConstants.IMAGE_BASE_URL + DtdConstants.PROFILE_BAR_IMAGES_FOLDER_NAME + data.drinkImage1,
                DtdConstants.PLACEHOLDER_NO_IMAGE
            )

            dtdUtil.loadImageNoTransform(
                activity as MainActivity, bindingFollowers.ivImageTwo,
                DtdConstants.IMAGE_BASE_URL + DtdConstants.PROFILE_BAR_IMAGES_FOLDER_NAME + data.drinkImage2,
                DtdConstants.PLACEHOLDER_NO_IMAGE
            )

            dtdUtil.loadImageNoTransform(
                activity as MainActivity, bindingFollowers.ivImageThree,
                DtdConstants.IMAGE_BASE_URL + DtdConstants.PROFILE_BAR_IMAGES_FOLDER_NAME + data.drinkImage3,
                DtdConstants.PLACEHOLDER_NO_IMAGE)
        }
    }



    private fun attachUpdateDataObservers() {
        var observer  = Observer<GetSaveCocktailImages> {
            if (it.status){
                loadImages()

                var message = Message()
                message.obj = resources.getString(R.string.cocktail_image_updated)
                (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)


//                ToastSnackUtils.toastShort(activity as MainActivity,
//                resources.getString(R.string.cocktail_image_updated))
            }
        }

        (activity as MainActivity).viewModel.liveDataCocktailImage().observe(this,observer)
    }


    fun loadImages(){

        var imageOne  = pref.getString(DtdConstants.DRINK_IMAGE_1)
        var imageTwo = pref.getString(DtdConstants.DRINK_IMAGE_2)
        var imageThree = pref.getString(DtdConstants.DRINK_IMAGE_3)

        if (imageOne ==  "") binding.ivImageOneCancel.visibility = View.GONE else binding.ivImageOneCancel.visibility = View.VISIBLE
        if (imageTwo ==  "") binding.ivImageTwoCancel.visibility = View.GONE else binding.ivImageTwoCancel.visibility = View.VISIBLE
        if (imageThree ==  "") binding.ivImageThreeCancel.visibility = View.GONE else binding.ivImageThreeCancel.visibility = View.VISIBLE

        dtdUtil.loadImageNoTransform(activity as MainActivity,binding.ivImageOne,
            DtdConstants.IMAGE_BASE_URL+DtdConstants.PROFILE_BAR_IMAGES_FOLDER_NAME+imageOne,
            DtdConstants.PLACEHOLDER_NO_IMAGE)

        dtdUtil.loadImageNoTransform(activity as MainActivity,binding.ivImageTwo,
            DtdConstants.IMAGE_BASE_URL+DtdConstants.PROFILE_BAR_IMAGES_FOLDER_NAME+imageTwo,
            DtdConstants.PLACEHOLDER_NO_IMAGE)

        dtdUtil.loadImageNoTransform(activity as MainActivity,binding.ivImageThree,
            DtdConstants.IMAGE_BASE_URL+
                    DtdConstants.PROFILE_BAR_IMAGES_FOLDER_NAME+imageThree,
            DtdConstants.PLACEHOLDER_NO_IMAGE)

    }

    private fun attachObservers() {

        var observer = Observer<GetUserVisibilityResponse> {
            var changeVisi = if (it.checked){ "1"} else{ "0"}
            var messageStr = it?.data?.message?.get(0)

            if (messageStr == null) messageStr = ""
            if (it.status) {
                var message = Message()
                message.obj = messageStr
                (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)

//                ToastSnackUtils.toastShort(activity as MainActivity, messageStr)

                pref.saveString(DtdConstants.VISIBILITY_STATUS,changeVisi)
            }else{


                var message = Message()
                message.obj = messageStr
                (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)


//                ToastSnackUtils.toastShort(activity as MainActivity, messageStr)

                pref.saveString(DtdConstants.VISIBILITY_STATUS,changeVisi)
            }

            var visibilit = pref.getString(DtdConstants.VISIBILITY_STATUS)
            binding.tvMessage.isChecked = visibilit == "1"
        }

        (context as MainActivity).viewModel.liveDataUpdateUserVisiblity()
            .observe(context as MainActivity, observer)
    }

    override fun resultPickFromCamera(data: File?) {
        data?.let {
            (activity as MainActivity).viewModel.getUpdateCocktailImage(image_number,data.absolutePath)
        }
    }

    override fun resultPickFromMedia(data: File?) {
//        var uri:Uri? = data?.data
        data?.let {
            (activity as MainActivity).viewModel.getUpdateCocktailImage(image_number, data.absolutePath)
        }
    }

    inner class ClickEvents{

        fun onClicFollowGroup(view: View){

            var msg = Message()
            msg.arg1 = DtdConstants.SHOW_FOLLOW
            (activity as MainActivity).handleMessages(msg)

            dismiss()
        }

        fun onCheckedChange(): CompoundButton.OnCheckedChangeListener {
            return CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                var visState = pref.getString(DtdConstants.VISIBILITY_STATUS)
                binding.tvMessage.isChecked = visState == "1"

                var visibility = GetUserVisibilityChange()
                var latlng = dtdUtil.getAppsLatLng()
                visibility.user_latitude = latlng[0]
                visibility.user_longitude = latlng[1]
                visibility.id = pref.getInt(DtdConstants.ID)
                visibility.visibility_status = visState
                visibility.checked = isChecked
                (context as MainActivity).viewModel.getUpdateVisibility(visibility)
            }
        }

        fun onClickMessages(view: View){
            var message = Message()
            message.arg1 = DtdConstants.SHOW_CHAT_HISTORY
            message.what = userId?:0
            message.arg2 = 3
            message.obj = seachFilterModel

            (activity as MainActivity).handleMessages(message)
            dialog?.dismiss()
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

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ivImageOne->{
                image_number = 1
                onImageClick()
            }
            R.id.ivImageTwo->{
                image_number = 2
                onImageClick()
            }
            R.id.ivImageThree->{
                image_number =3
                onImageClick()
            }
            R.id.ivImageOneCancel->{
                image_number = 1
                (activity as MainActivity).viewModel.getDeleteCocktailImage(image_number, "")
            }
            R.id.ivImageTwoCancel->{
                image_number = 2
                (activity as MainActivity).viewModel.getDeleteCocktailImage(image_number, "")
            }
            R.id.ivImageThreeCancel->{
                image_number = 3
                (activity as MainActivity).viewModel.getDeleteCocktailImage(image_number, "")
            }
        }


    }

    fun onImageClick(){

        var list = mutableListOf<ChoiceListModel>()
        var ch1 = ChoiceListModel()
        ch1.text = getString(R.string.from_camera)
        var ch2 = ChoiceListModel()
        ch2.text = getString(R.string.from_media)

        list.add(ch1)
        val add = list.add(ch2)

        AlertUtils().showChooseOneFromListAlertNoActions(activity as MainActivity,
            getString(R.string.pick_image),object : OneChoiceNoActionsCallbacks {
                override fun eventListItemSelected(position: Int) {
                    when(position){
                        0->{
                            val permissions = arrayOf(android.Manifest.permission.CAMERA,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            val rationale = arrayOf("This permission is required to capture image from camera")
                            (activity as MainActivity).requestPermission(this@ProfileDialog,permissions,rationale,PICK_IMAGE_FROM_CAMERA)
                        }
                        1->{
                            val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                            val rationale = arrayOf("We need this Permission to read image")
                            (activity as MainActivity).requestPermission(this@ProfileDialog,permissions,rationale,PICK_IMAGE_FROM_GALLARY)
                        }
                    }
                }
            }, list)
    }



}