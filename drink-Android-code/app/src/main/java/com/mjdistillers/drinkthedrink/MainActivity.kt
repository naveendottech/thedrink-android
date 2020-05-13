package com.mjdistillers.drinkthedrink

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.MediaStore
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.StyleSpan
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.*
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.snackbar.Snackbar
import com.mjdistillers.drinkthedrink.adapters.*
import com.mjdistillers.drinkthedrink.databinding.ActivityMainBinding
import com.mjdistillers.drinkthedrink.databinding.LayoutBottomSheetBinding
import com.mjdistillers.drinkthedrink.dialogs.*
import com.mjdistillers.drinkthedrink.model.MarkerModel
import com.mjdistillers.drinkthedrink.model.SearchFilterModel
import com.mjdistillers.drinkthedrink.model.request.*
import com.mjdistillers.drinkthedrink.model.response.BarTeam
import com.mjdistillers.drinkthedrink.model.response._all_notifications.Datum
import com.mjdistillers.drinkthedrink.model.response._all_notifications.GetAllNotificationsResponse
import com.mjdistillers.drinkthedrink.model.response.bar_details.Bardetilsdata
import com.mjdistillers.drinkthedrink.model.response.bar_details.GetBarsDetailsResponse
import com.mjdistillers.drinkthedrink.model.response.chat_all_user.GetAllUsesrsChatResponse
import com.mjdistillers.drinkthedrink.model.response.edit_profile.GetEditProfile
import com.mjdistillers.drinkthedrink.model.response.follow_unfollow_to_back_decline_logout.GetFollowUnfollowTO_BackResponse_Decline_Logout
import com.mjdistillers.drinkthedrink.model.response.fu_bar_store_team.FUBarStoreTeamResponse
import com.mjdistillers.drinkthedrink.model.response.get_profile.GetProfileResponse
import com.mjdistillers.drinkthedrink.model.response.request_push_notification.GetPushNotificationResponse
import com.mjdistillers.drinkthedrink.model.response.states_provinces.GetStatesAndPrvincesResponse
import com.mjdistillers.drinkthedrink.model.response.update_user.GetUpdateUserResponse
import com.mjdistillers.drinkthedrink.model.response.user_block.GetBlockUserResp
import com.mjdistillers.drinkthedrink.model.response.user_chat_history.GetUserChatHistoryResponse
import com.mjdistillers.drinkthedrink.others.OnSwipeTouchListener
import com.mjdistillers.drinkthedrink.others.RecyclerItemClickListener
import com.mjdistillers.drinkthedrink.utilities.*
import com.mjdistillers.drinkthedrink.utilities.constants.IntConstants
import com.mjdistillers.drinkthedrink.utilities.interfaces.OkCancelAlertCallbacks
import com.mjdistillers.drinkthedrink.utilities.interfaces.OneChoiceNoActionsCallbacks
import com.mjdistillers.drinkthedrink.utilities.interfaces.PermissionUtilsCallbacks
import com.mjdistillers.drinkthedrink.utilities.models.ChoiceListModel
import com.mjdistillers.drinkthedrink.viewmodel.MainActivityViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_bottom_sheet.*
import kotlinx.android.synthetic.main.layout_bottom_sheet.view.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : BaseActivity(), OnMapReadyCallback, PermissionUtilsCallbacks{

    lateinit var behaviour : BottomSheetBehavior<RelativeLayout>
    var googleMap:GoogleMap? = null
    lateinit var rvAdapter:StoresRVAdapter
    var markers: ArrayList<MarkerModel> = ArrayList()
    lateinit var binding:ActivityMainBinding
    lateinit var layoutBS:LayoutBottomSheetBinding
    lateinit var viewModel: MainActivityViewModel
    private lateinit var menuLogedInPopUp:MenuLoggedInPopUp
    private lateinit var menuLogedOutPopUp:MenuLoggedOutPopUp
    private lateinit var filterPopUp:FilterSearchPopUp
    lateinit var  prefs: SharedPrefsUtils
    private var prevPosition:Int = 0
    var isFromBars:Boolean = true
    var selectedOption:String = DtdConstants.SEL_OPTION_BARS
    var isFromSearchFilterCrossIcon = false
    lateinit var clickEvent:ClickEvents
    //    lateinit var progresDialog:ProgressDialog
    lateinit var listPeoplePretending:List<SearchFilterModel>
    private lateinit var placesClient:PlacesClient
    lateinit var followUnFollowClicks:FollowUnFollowClickHandling
    lateinit var autoComplete:AutocompleteSupportFragment
    var notificationType = ""
    var followFollowerMessage = Message()
    var snackbarFragment = SnackbarFragment()
    var progressLayout = ProgressDialogFragment()
    lateinit var teamsAdapter : TeamsAdapter
    var id_follow_btn = 0
    var user_id_follow_btn = 0

    var bar_latitude = 0.0
    var bar_longitute = 0.0

    @Inject
    lateinit var alertUtils:AlertUtils

    @Inject
    lateinit var networkUtils: NetworkUtils

    @Inject
    lateinit var dtdUtils: DtdUtils

    var handler: Handler = Handler{
        handleMessages(it)
        true
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            notificationType = intent.getStringExtra(DtdPrefsKeys.Keys.NOTIFICATION_TYPE)
        }

        openNotificationPopUpWhenFromNotification()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent?.extras?.let {
            if (it.containsKey(DtdPrefsKeys.Keys.NOTIFICATION_TYPE))
                notificationType = it.getString(DtdPrefsKeys.Keys.NOTIFICATION_TYPE)?:""
        }

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main) as ActivityMainBinding
        layoutBS = binding.layoutBottomSheet

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        progressLayout.dialog?.setCanceledOnTouchOutside(false)
        progressLayout.dialog?.setCancelable(false)

        viewModel = ViewModelProviders.of(MainActivity@this).get(MainActivityViewModel::class.java)
        App.app.getComponent().inject(this)
        prefs = SharedPrefsUtils(this)

        Places.initialize(MainActivity@this,resources.getString(R.string.place_api))
        placesClient = Places.createClient(MainActivity@this)
        LogUtils.logi(prefs.getDeviceToken())

        clickEvent = ClickEvents()

        layoutBS.llDelivery.visibility = View.GONE

        behaviour = BottomSheetBehavior.from(binding.bottomSheet)

        var supportMapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        supportMapFragment.getMapAsync(this)

//        behaviour.peekHeight = ApplicationUtils().convertPixelsToDp(App.app.resources.getDimension(R.dimen.bottom_sheet_peek_height)).toInt()

        prefs.saveString(DtdPrefsKeys.Keys.BARS_SELECTED_OPTION,DtdPrefsKeys.Values.OPTION_ONE)

        binding.clickEvents = clickEvent

        layoutBS.vpBarImages.requestDisallowInterceptTouchEvent(true)

        makeFilterSelectedForBars()

        handleProgressBar()
        handleAlerts()
        setUpAds()

        attachObservers()
        attachBarsDetailObserver()
        attachObserverEditProfileNoUI()
        attachObserverStores()
        attachObserverPeople()
        attachObserverPeoplePretendingSearch()
        attachObserverForSearchFilter()
        attachObserverLogout()
        attachObserverAllNotifications()
        attachObserversMessages()
        attachObserverFUBar()
        attachObserverFUStore()
        attachObserverFUBarTeam()
        attachObserverFUStoreTeam()
        attachObserverSnackbar()
        attachObserverUpdateRole()

        clickEvent.swipeGesture()
        clickEvent.bottomSheetCallBack()

        layoutBS?.scview.setOnClickListener {  }


/*
        var vto = binding.root.viewTreeObserver
        vto.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    var ldrawable = binding.layoutBottomSheet.et
//                    ldrawable.setLayerInset(1, 0, loginpopUp.height / 2, 0, 0)
                    var viewObserver = loginpopUp.viewTreeObserver

                    ToastSnackUtils.toastLong(context,loginpopUp.height.toString())
                    viewObserver.removeOnGlobalLayoutListener(this)
                }
            })
*/
        applyFonts()
        setBottomSheetPeekHeight()
//        requestLocationPermission()
    }



    fun setBottomSheetPeekHeight(){

        var ivExpang = layoutBS.clBottomLayout.getViewById(R.id.ivExpand)
        var autCompTV = layoutBS.flMain.clBottomLayout.getViewById(R.id.autoCompleteTextView)
        var clFilt = layoutBS.flMain.clBottomLayout.coordiLayout.appbarLayout.clFilterOuter.clFilter.getViewById(R.id.clFilter)

        clFilt.apply {
            viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    behaviour.peekHeight = autCompTV.bottom + clFilt.height + ivExpang.height + ivExpang.height
                    clFilt.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }

/*        clFilterBG.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                clFilt.viewTreeObserver.removeOnGlobalLayoutListener(this)
//                var dpi = ApplicationUtils().convertPixelsToDp(clFilterBG.bottom.toFloat()).toInt()
                behaviour.peekHeight = clFilt.bottom+100
                LogUtils.logd("Botom : "+ clFilt.bottom)
            }
        })*/
    }


    private fun makeFilterSelectedForBars() {
        layoutBS.ivFilter.setImageResource(R.drawable.ic_filter_icon)
        layoutBS.llFilter.tag = DtdConstants.RB_SEL_TAG
        layoutBS.groupFilterOptions.visibility = View.VISIBLE

        layoutBS.ivSpecialssInFilter.setImageResource(R.drawable.btn_sel_radio)
        layoutBS.rbSpecialssNoFilter.tag = DtdConstants.RB_SEL_TAG
        layoutBS.rbSpecialss.tag = DtdConstants.RB_SEL_TAG

//        clickEvent.onClickSpecialsInFilter(layoutBS.rbSpecialss)
    }

    private fun attachObserverFUBar() {
        var observerBar =  Observer<FUBarStoreTeamResponse> {
            if (it.status){
                if(layoutBS.tvFollowBarStore.visibility == View.VISIBLE){
                    layoutBS.tvFollowBarStore.visibility = View.GONE
                    layoutBS.tvUnFollowBarStore.visibility = View.VISIBLE
                }else{
                    layoutBS.tvFollowBarStore.visibility = View.VISIBLE
                    layoutBS.tvUnFollowBarStore.visibility = View.GONE
                }

                var snackMess = Message()
                snackMess.arg1 = DtdConstants.SHOW_SNACKBAR

                it?.data?.message?.let {
                    if (it.size > 0){
                        snackMess.obj = it[0]
                        handleMessages(snackMess)
                    }
                }
            }
        }

        viewModel.liveDataFUBar().observe(this,observerBar)
    }

    private fun attachObserverFUStore() {
        var observerStore =  Observer<FUBarStoreTeamResponse> {
            if (it.status){
                if(layoutBS.tvFollowBarStore.visibility == View.VISIBLE){
                    layoutBS.tvFollowBarStore.visibility = View.GONE
                    layoutBS.tvUnFollowBarStore.visibility = View.VISIBLE
                }else{
                    layoutBS.tvFollowBarStore.visibility = View.VISIBLE
                    layoutBS.tvUnFollowBarStore.visibility = View.GONE
                }

                var snackMess = Message()
                snackMess.arg1 = DtdConstants.SHOW_SNACKBAR

                it?.data?.message?.let {
                    if (it.size > 0){
                        snackMess.obj = it[0]
                        handleMessages(snackMess)
                    }
                }
            }
        }
        viewModel.liveDataFUStore().observe(this,observerStore)
    }

    private fun attachObserverFUBarTeam() {
        var observerBarTeam =  Observer<FUBarStoreTeamResponse> {
            if (it.status){
                if (::teamsAdapter.isInitialized) {
                    when(teamsAdapter.currBarTeam.isFollowVisible){
                        true->{
                            teamsAdapter.currBarTeam.justRevertButton = DtdConstants.UNFOLLOW
                        }
                        false->{
                            teamsAdapter.currBarTeam.justRevertButton = DtdConstants.FOLLOW
                        }
                    }
                    teamsAdapter.notifyDataSetChanged()
                }

                teamsAdapter.prevPositio = teamsAdapter.currBarTeam.position

                var snackMess = Message()
                snackMess.arg1 = DtdConstants.SHOW_SNACKBAR

                it?.data?.message?.let {
                    if (it.size > 0){
                        snackMess.obj = it[0]
                        handleMessages(snackMess)
                    }
                }
            }
        }
        viewModel.liveDataFUBarTeam().observe(this,observerBarTeam)
    }

    private fun attachObserverFUStoreTeam() {
        var observerStoreTeam =  Observer<FUBarStoreTeamResponse> {
            if (it.status){

                if (::teamsAdapter.isInitialized) {
                    when(teamsAdapter.currBarTeam.isFollowVisible){
                        true->{
                            teamsAdapter.currBarTeam.justRevertButton = DtdConstants.UNFOLLOW
                        }
                        false->{
                            teamsAdapter.currBarTeam.justRevertButton = DtdConstants.FOLLOW
                        }
                    }
                    teamsAdapter.notifyDataSetChanged()
                }

                teamsAdapter.prevPositio = teamsAdapter.currBarTeam.position

                var snackMess = Message()
                snackMess.arg1 = DtdConstants.SHOW_SNACKBAR

                it?.data?.message?.let {
                    if (it.size > 0){
                        snackMess.obj = it[0]
                        handleMessages(snackMess)
                    }
                }
            }
        }

        viewModel.liveDataFUStoreTeam().observe(this,observerStoreTeam)

    }

    private fun applyFonts() {

        layoutBS.autoCompleteTextView.typeface = dtdUtils.fontFuturaBook()


        layoutBS.tvBarStreetAddress.typeface = dtdUtils.fontNeutraMedium()

        layoutBS.tvDollor.typeface = dtdUtils.fontFuturaBook()
        layoutBS.tvmiles.typeface = dtdUtils.fontFuturaBook()
        layoutBS.tvBarName.typeface = dtdUtils.fontFutura()
        layoutBS.tvBarStreetAddress.typeface = dtdUtils.fontFuturaBook()

        layoutBS.tvFeaturesHeading.typeface = dtdUtils.fontFutura()
        layoutBS.tvEventHeading.typeface = dtdUtils.fontFutura()
        layoutBS.tvUpcommingHeading.typeface = dtdUtils.fontFutura()
        layoutBS.tvTeamsHeading.typeface = dtdUtils.fontFutura()
        layoutBS.tvHours.typeface = dtdUtils.fontFutura()
        layoutBS.tvPhone.typeface = dtdUtils.fontFutura()

        layoutBS.tvDesctiption.typeface = dtdUtils.fontFuturaBook()

        layoutBS.tvPeople.typeface = dtdUtils.fontNeutraMedium()
        layoutBS.tvStores.typeface = dtdUtils.fontNeutraMedium()
        layoutBS.tvBars.typeface = dtdUtils.fontNeutraMedium()
        layoutBS.tvFilter.typeface = dtdUtils.fontNeutraMedium()
        layoutBS.tvOneDolllor.typeface = dtdUtils.fontNeutraMedium()
        layoutBS.tvTwoDolllor.typeface = dtdUtils.fontNeutraMedium()
        layoutBS.tvThreeDolllor.typeface = dtdUtils.fontNeutraMedium()
        layoutBS.tvSpecials.typeface  = dtdUtils.fontNeutraMedium()
        layoutBS.tvDrinksOnly.typeface = dtdUtils.fontNeutraMedium()
        layoutBS.tvRestaurants.typeface  = dtdUtils.fontNeutraMedium()
        layoutBS.tvTheMeBar.typeface = dtdUtils.fontNeutraMedium()
        layoutBS.tvNightClub.typeface = dtdUtils.fontNeutraMedium()
        layoutBS.tvEvents.typeface = dtdUtils.fontNeutraMedium()
        layoutBS.tvPatio.typeface = dtdUtils.fontNeutraMedium()
//        layoutBS.tvSpecialEventsText.typeface = dtdUtils.fontNeutraMedium()
        layoutBS.tvPhoneValue.typeface = dtdUtils.fontNeutraMedium()
        layoutBS.tvHoursValue.typeface = dtdUtils.fontNeutraMedium()

        layoutBS.tvSpecialsAndDelivery.typeface = dtdUtils.fontNeutraMedium()
        layoutBS.tvEventSpecialTitle.typeface = dtdUtils.fontFutura()
        layoutBS.tvEventsAndTasting.typeface = dtdUtils.fontNeutraMedium()

    }

    private fun attachObserverSnackbar() {
        var observer = Observer<Message> {
            it.arg1 = DtdConstants.SHOW_SNACKBAR
            handleMessages(it)
        }

        viewModel.liveDataSnackabr().observe(this,observer)
    }

    private fun attachObserverStateAndProvinces() {
        var observerStates = Observer<GetStatesAndPrvincesResponse>{

            if (it.status) {
                if (!isDialogFragShowing(DtdConstants.SHOW_REGISTER_FORM.toString())) {
                    var dialogFrag = RegisterDialog()
                    var bundle = Bundle()
                    bundle.putParcelable(DtdPrefsKeys.Keys.RESPONSE_STATES_AND_PROVINCES, it)
                    dialogFrag.arguments = bundle
                    dialogFrag.setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_fragment_style)
                    dialogFrag.show(
                        supportFragmentManager,
                        DtdConstants.SHOW_REGISTER_FORM.toString()
                    )
                }
            }else{
                var messSnack = Message()
                messSnack.arg1 = DtdConstants.SHOW_SNACKBAR
                messSnack.obj = getString(R.string.unable_get_states_provin)
                handleMessages(messSnack)
            }
        }

        viewModel.liveDataStatesAndProvinces().observe(this,observerStates)
    }

    private fun attachObserverUpdateRole() {
        var observerUpdateUser = Observer<GetUpdateUserResponse>{
            if (it.status) {
                var messList = it?.data?.message
                messList?.let{
                    if (it.isNotEmpty()){
                        var messSnack = Message()
                        messSnack.arg1 = DtdConstants.SHOW_SNACKBAR
                        messSnack.obj = it[0]
                        handleMessages(messSnack)


                        // To show logged in menu
                        var message = Message.obtain(handler)
                        message.arg1 = DtdConstants.SHOW_LOGIN_POP_UP
                        message.arg2 = DtdConstants.FROM_LOGIN
                        message.sendToTarget()

                    }
                }
            }
        }

        viewModel.liveDataUpdateUser().observe(this,observerUpdateUser)

    }


    private fun attachObserverAllNotifications() {
        var observerNotifications = Observer<GetAllNotificationsResponse> {

            it?.data?.data?.let {
                if (it.isNotEmpty()) {
                    var userId = prefs.getInt(DtdConstants.ID)
                    var list = ArrayList<Datum>()
                    it.forEach {
                        if (it.notifications != DtdConstants.NOTIFICATION_DECLINED && it.followId != userId){
                            list.add(it)
                        }
                    }

                    var dialogFrag = AllNotificationsDialog()
                    var bundle = Bundle()
                    bundle.putParcelableArrayList(DtdPrefsKeys.Keys.NOTIFICATIONS_DATA, list)

                    dialogFrag.arguments = bundle
                    dialogFrag.setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_fragment_style)
                    dialogFrag.show(supportFragmentManager,
                        DtdConstants.SHOW_ALL_NOTIFICATIONS.toString())
                }else{

                    var messageSnack = Message()
                    messageSnack.obj = getString(R.string.no_notifications_found)
                    viewModel.getAlertLiveData().postValue(messageSnack)


//                    ToastSnackUtils.snackBar(binding.root,getString(R.string.no_notifications_found),Snackbar.LENGTH_LONG,false,getString(
//                                            R.string.retry),
//
//
//
//                        View.OnClickListener {
//                            var msg = Message()
//                            msg.arg1 = DtdConstants.SHOW_ALL_NOTIFICATIONS
//                            handleMessages(msg)
//                        })
                }
            }
        }
        viewModel.liveDataAllNotifications().observe(this,observerNotifications)
    }

    private fun attachObserversMessages() {
        var observer = androidx.lifecycle.Observer<GetAllUsesrsChatResponse>
        {
            it.data?.data?.let {
                if (it.size > 0) {
                    var dialogFrag = AllUsersMessagesDialog()
                    var bundle = Bundle()
                    bundle.putParcelableArrayList(DtdPrefsKeys.Keys.ALL_USER_MESSAGES_LIST, it)
                    dialogFrag.arguments = bundle
                    dialogFrag.setStyle(
                        DialogFragment.STYLE_NORMAL,
                        R.style.dialog_fragment_style
                    )
                    dialogFrag.show(
                        supportFragmentManager,
                        DtdConstants.SHOW_MESSAGE_ALL_USERS.toString()
                    )
                }else{
                    var mess = Message()
                    mess.obj = getString(R.string.no_data_found)
                    viewModel.getAlertLiveData().postValue(mess)


                }
            }?: kotlin.run {
                var mess = Message()
                mess.obj = getString(R.string.no_data_found)
                viewModel.getAlertLiveData().postValue(mess)
            }
        }
        viewModel.liveDataAlluser().observe(this@MainActivity,observer)
    }

    private fun attachObserverForProfile(dialogProfile: ProfileDialog, bundle: Bundle) {
        var observer = Observer<GetProfileResponse> {
            var resp = it

            if (resp != null){
                bundle.putParcelable(DtdPrefsKeys.Keys.RESPONSE_PROFILE,resp)
                dialogProfile.arguments = bundle
                dialogProfile.show(supportFragmentManager,DtdConstants.SHOW_PROFILE.toString())
            }else{
                var mesSnackbar = Message()
                mesSnackbar.arg1 = DtdConstants.SHOW_SNACKBAR
                mesSnackbar.obj = getString(R.string.profile_data_not_found)
                handleMessages(mesSnackbar)
            }

        }

        viewModel.liveDataGetProfile().observe(this,observer)
    }

    private fun hitEditProfileApi(obj: Any?,dialogFrag: EditProfileDialog,isHitNoUI: Boolean) {

        viewModel.getEditProfile(isHitNoUI).observe(this,
            Observer {

                if (!isDialogFragShowing(DtdConstants.SHOW_EDIT_PROFILE.toString())) {

                    var bundle = Bundle()

                    bundle.putParcelable(DtdPrefsKeys.Keys.RESPONSE_EDIT_PROFILE, it)

                    if (obj != null && (obj as String) == DtdPrefsKeys.Values.FROM_MY_STATUS)
                        bundle.putString(DtdPrefsKeys.Keys.FROM, DtdPrefsKeys.Values.FROM_MY_STATUS)

                    dialogFrag.arguments = bundle
                    dialogFrag.setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_fragment_style)
                    dialogFrag.show(
                        supportFragmentManager,
                        DtdConstants.SHOW_EDIT_PROFILE.toString()
                    )
                }

            })
    }


    private fun isDialogFragShowing(fragtag: String): Boolean{
        var fragment = supportFragmentManager.findFragmentByTag(fragtag)
        return if (fragment == null){
            false
        }else{
            fragment.activity == this

        }
    }

    private fun initAutoCompletePlaces() {

        /* autoComplete = supportFragmentManager
            .findFragmentById(R.id.autoCompleteSupportFragment) as AutocompleteSupportFragment

        autoComplete.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG))
        autoComplete.setOnPlaceSelectedListener(object : PlaceSelectionListener{
            override fun onPlaceSelected(place: Place) {

                var latlng = place.latLng

                prefs.saveString(DtdPrefsKeys.Keys.LATITUDE,latlng?.latitude.toString())
                prefs.saveString(DtdPrefsKeys.Keys.LONGITUDE,latlng?.longitude.toString())

                layoutBS.rbBars.tag = DtdConstants.RB_UNSEL_TAG
                clickEvent.onClickrbBars(layoutBS.rbBars)
            }


            override fun onError(status: Status) {
                ToastSnackUtils.toastLong(this@MainActivity,status.statusMessage.toString())
            }
        })*/
    }


    private fun attachObserverPeoplePretendingSearch() {

        var observer = Observer<List<SearchFilterModel>> {
            filterPopUp = FilterSearchPopUp(this,handler,DtdConstants.SEL_OPTION_PEOPLE,null)
            // filterPopUp.updateViewForListSize(it.size)
            listPeoplePretending = it
            if (it.isEmpty()){
                var mes = Message()
                mes.arg1 = DtdConstants.BAR_FILTER_FLAG

                var message = App.app.getString(R.string.no_data_found_for_filter,layoutBS.etSearch.text.toString())
                if (layoutBS.etSearch.text.toString().isNotEmpty()) {
                    mes.obj = message
                    viewModel.getAlertLiveData().postValue(mes)
                    handleMessages(mes)
                }
            }else{
                if (::filterPopUp.isInitialized){
                    if (!filterPopUp.popUp.isShowing) filterPopUp.popUp.showAsDropDown(layoutBS.clFilter)
                    filterPopUp.adapter.updateList(it)
                }
            }
        }

        viewModel.liveDataPretendingLikeSearch().observe(this,observer)
    }

    private fun attachObserverForSearchFilter() {
        var observer = Observer<List<SearchFilterModel>> {
//            filterPopUp.updateViewForListSize(it.size)
            if (it.isEmpty()){
                var mes = Message()
                mes.arg1 = DtdConstants.BAR_FILTER_FLAG
                mes.obj = App.app.getString(R.string.no_data_found_for_filter,layoutBS.etSearch.text.toString())
                viewModel.getAlertLiveData().postValue(mes)
                handleMessages(mes)
            }else{
                if (::filterPopUp.isInitialized){
                    if (!filterPopUp.popUp.isShowing) filterPopUp.popUp.showAsDropDown(layoutBS.clFilter)
                    filterPopUp.adapter.updateList(it)
                }
            }
        }
        viewModel.liveDataSearchBars().observe(this,observer)
    }

    private fun attachObserverLogout(){
        var observerLogout = Observer<GetFollowUnfollowTO_BackResponse_Decline_Logout> {

            if (::menuLogedInPopUp.isInitialized) {

                if (it.status) {
                    prefs.clearPrefrences()
                    clickEvent.onClickrbBars(rbBars)
                }


                var messageString = App.app.getString(R.string.problem_occured)

                it?.data?.message?.let {
                    messageString = it[0]
                }

                var messageSnack = Message()
                messageSnack.obj = messageString
                viewModel.getAlertLiveData().postValue(messageSnack)

//                ToastSnackUtils.snackBar(binding.root,
//                    messageString, Snackbar.LENGTH_LONG, false)
            }
        }
        viewModel.liveDataLogout().observe(this@MainActivity,observerLogout)
    }

    private fun attachObserverPeople() {

        var observer = Observer<ArrayList<MarkerModel>> {
            googleMap?.clear()
            markers = it

            var appsLatlng = dtdUtils.getAppsLatLng()
            var latlng = LatLng(appsLatlng[0],appsLatlng[1])

            dtdUtils.addCircleToMap(googleMap,latlng)

            /*  layoutBS.rvStores.layoutManager = LinearLayoutManager(
                  this@MainActivity,
                  LinearLayoutManager.HORIZONTAL, false)

              rvAdapter = StoresRVAdapter(it, this@MainActivity)

              layoutBS.rvStores.adapter = rvAdapter

              layoutBS.rvStores.addOnItemTouchListener(
                  RecyclerItemClickListener(
                      this@MainActivity,
                      layoutBS.rvStores,
                      object : RecyclerItemClickListener.OnItemClickListener {
                          override fun onLongItemClick(view: View?, position: Int) {

                          }

                          override fun onItemClick(view: View?, position: Int) {
                              selectItem(rvAdapter, position, markers[position])
                          }
                      }))
  */
            for(it in markers){
                var marker = googleMap?.addMarker(it.markerOptions)
                marker?.tag = it
            }

            clearDetail()
        }

        viewModel.liveDataGetPeople().observe(this,observer)
    }

    private fun attachObserverStores() {

        var observer = Observer<ArrayList<MarkerModel>> {
            googleMap?.clear()
            markers = it

            layoutBS.rvStores.layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.HORIZONTAL, false)

            var appsLatLng = dtdUtils.getAppsLatLng()
            var latlng = LatLng(appsLatLng[0],appsLatLng[1])

            dtdUtils.addCircleToMap(googleMap,latlng)

            rvAdapter = StoresRVAdapter(it, this@MainActivity)

            layoutBS.rvStores.adapter = rvAdapter

            layoutBS.rvStores.addOnItemTouchListener(
                RecyclerItemClickListener(
                    this@MainActivity,
                    layoutBS.rvStores,
                    object : RecyclerItemClickListener.OnItemClickListener {
                        override fun onLongItemClick(view: View?, position: Int) {

                        }

                        override fun onItemClick(view: View?, position: Int) {
                            selectItem(rvAdapter, position, markers[position])
                        }
                    }))

            for(it in markers){
                var marker = googleMap?.addMarker(it.markerOptions)
                marker?.tag = it
            }

            if (markers.size > 0) {
                layoutBS.rvStores.scrollToPosition(0)
                selectItem(rvAdapter,0, markers[0])
//                hitStoreDetail(markers[0])
            }else{
                clearDetail()
            }
        }

        viewModel.liveDataStores().observe(this,observer)
    }

    private fun attachObservers() {
        var observer = Observer<ArrayList<MarkerModel>> {

            var isfromDollorFilter = false

            it?.let {
                if(it.size > 0){
                    if(it[it.size-1].distance == DtdConstants.DUMMY_DISTANCE_SIGNATURE){
                        isfromDollorFilter = true
                        it.removeAt(it.size-1)
                    }
                }
            }

            if((layoutBS.rbOneDollor.tag == DtdConstants.RB_SEL_TAG ||
                        layoutBS.rbTwoDollor.tag == DtdConstants.RB_SEL_TAG ||
                        layoutBS.rbThreeDollor.tag == DtdConstants.RB_SEL_TAG ) && !isfromDollorFilter){

                viewModel.repository.getListFilteredFromDollors(
                    rvAdapter.getCurruntList(),
                    layoutBS.rbOneDollor,
                    layoutBS.rbTwoDollor,
                    layoutBS.rbThreeDollor)

            }

            markers = it
            if(it.isEmpty()){
                clearDetail()
                layoutBS.rvStores.adapter = StoresRVAdapter(ArrayList<MarkerModel>(),this)
            }

            if(!isfromDollorFilter && it.isNotEmpty()) {


                when(selectedOption){
                    DtdConstants.SEL_OPTION_BARS->{
                        if (selectedOption == DtdConstants.SEL_OPTION_BARS) {
                            if (it.size > 0) {

//                            layoutBS.tvEventSpecialTitle.visibility = View.VISIBLE
                                layoutBS.tvEventSpecialTitle.text =
                                    App.app.getString(R.string.specials_)
                            }
                        }else{
                            layoutBS.tvEventSpecialTitle.visibility = View.GONE
                        }
                    }

                    DtdConstants.SEL_OPTION_BARS->{
                        if (selectedOption == DtdConstants.SEL_OPTION_BARS) {
                            if (it.size > 0) {
//                            layoutBS.tvEventSpecialTitle.visibility = View.VISIBLE
                                layoutBS.tvEventSpecialTitle.text = App.app.getString(R.string.events_)
                            }
                        }else{
                            layoutBS.tvEventSpecialTitle.visibility = View.GONE
                        }
                    }
                }

                googleMap?.clear()

                it?.let {
                    if(it.isEmpty()) clearDetail()
                }

                markers = it

                var appsLatlng = dtdUtils.getAppsLatLng()
                var latlng = LatLng(appsLatlng[0], appsLatlng[1])

                dtdUtils.addCircleToMap(googleMap, latlng)

                layoutBS.rvStores.visibility = View.VISIBLE

                layoutBS.rvStores.layoutManager = LinearLayoutManager(
                    this@MainActivity,
                    LinearLayoutManager.HORIZONTAL, false
                )

                rvAdapter = StoresRVAdapter(it, this@MainActivity)

                layoutBS.rvStores.adapter = rvAdapter

                layoutBS.rvStores.addOnItemTouchListener(
                    RecyclerItemClickListener(
                        this@MainActivity,
                        layoutBS.rvStores,
                        object : RecyclerItemClickListener.OnItemClickListener {
                            override fun onLongItemClick(view: View?, position: Int) {

                            }

                            override fun onItemClick(view: View?, position: Int) {
                                selectItem(rvAdapter, position, markers[position])
                            }
                        })
                )

                for (it in markers) {
                    var marker = googleMap?.addMarker(it.markerOptions)
                    marker?.tag = it
                }

                if (markers.size > 0) {
                    layoutBS.rvStores.scrollToPosition(0)
                    selectItem(rvAdapter, 0, markers[0])

//                    hitDetailWebService(markers[0])
                } else {
                    clearDetail()
                }
            }

            if(markers.size > 2) {
                layoutBS.ivArrowLeft.visibility = View.VISIBLE
                layoutBS.ivArrowRight.visibility = View.VISIBLE
            }else{
                layoutBS.ivArrowLeft.visibility = View.GONE
                layoutBS.ivArrowRight.visibility = View.GONE
            }

        }

        viewModel.liveDataBars().observe(this,observer)
    }

    fun attachObserverEditProfileNoUI(){
        viewModel.getEditProfileNoUI().observe(this,
            Observer<GetEditProfile> {
                if (prefs.getBoolean(DtdConstants.IS_LOGGED_IN)){
                    viewInLoggedPopUp()
                }else{
                    viewOutLoggedPopUp()
                }
            })
    }

    fun attachBarsDetailObserver(){
        var barsDetail =  Observer<GetBarsDetailsResponse?> {
            if(it != null){
//                setUpMap(googleMap)
                setDetail(it.isFromBars,it.bardetilsdata)
            }
        }

        viewModel.liveDataBarsDetails().observe(this,barsDetail)
    }

    private fun attachObserverMessageHistory(bundle: Bundle) {

        var observer = Observer<GetUserChatHistoryResponse> {

            if (!isDialogFragShowing(DtdConstants.SHOW_CHAT_HISTORY.toString())) {
                bundle.putParcelable(DtdPrefsKeys.Keys.RESPONSE_MESSAGES_HISTORY, it)

                var dialogFrag = ChatHistoryDialog()
                dialogFrag.arguments = bundle
                dialogFrag.setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_fragment_style)
                dialogFrag.show(supportFragmentManager, DtdConstants.SHOW_CHAT_HISTORY.toString())
            }
        }

        viewModel.liveDataChatHistory().removeObservers(this)

        viewModel.liveDataChatHistory().observe(this,observer)
    }

    private fun setUpAds() {
        var imges = mutableListOf<String>()

        viewModel.getFrontAds().observe(this, Observer {

            var links = it.data.link?: mutableListOf<String>()

            if (it.status) {
                var imgeArray = it.data.images
                imgeArray?.let {

                    for (i in 1..50) imges.addAll(imgeArray)

                    var adsAdapter = AdvertisementAdapter(this,imges,links,handler)
                    vpAdvertisement.adapter = adsAdapter
                }

                Observable.intervalRange(10, 3000, 4, 4, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError(Consumer {

                    })
                    .subscribe(Consumer {
                        vpAdvertisement.currentItem = vpAdvertisement.currentItem + 1
                    })


                vpAdvertisement.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) {

                    }

                    override fun onPageSelected(position: Int) {
                    }

                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int) {

                        if (position == imges.size - 1) {
                            vpAdvertisement.currentItem = 0
                        }
                    }
                })

            }else{
                Toast.makeText(this,"Unable to load Ads",Toast.LENGTH_SHORT).show()
            }
        })
    }

    inner class ClickEvents{

        fun onClickrbBars(view: View) {
            clearDetail()

            layoutBS.llFilter.visibility = View.VISIBLE
            prefs.saveString(DtdPrefsKeys.Keys.SEL_OPTION, DtdConstants.SEL_OPTION_BARS)
//            layoutBS.autoCompleteTextView.text = prefs.getString(DtdPrefsKeys.Keys.PICKED_LOCATION)

            if (layoutBS.llFilter.tag == DtdConstants.RB_SEL_TAG){
                layoutBS.groupFilterOptions.visibility = View.VISIBLE
                layoutBS.groupFilterItemsNotRequredInPeople.visibility = View.GONE
            }else{
                layoutBS.groupFilterOptions.visibility = View.GONE
                layoutBS.groupFilterItemsNotRequredInPeople.visibility = View.GONE
            }

            layoutBS.rvStores.visibility = View.VISIBLE
            layoutBS.vpAdvertisement.visibility = View.VISIBLE
//            layoutBS.groupBarsAndStores.visibility = View.VISIBLE
            layoutBS.tvDivider.visibility = View.VISIBLE

            layoutBS.tvFollowBarStore.text = resources.getString(R.string.follow_bar_cc)
            layoutBS.tvUnFollowBarStore.text = resources.getString(R.string.unfollow_bar_cc)

            if (::filterPopUp.isInitialized && filterPopUp.popUp.isShowing){
                onClickFilterETSearchIcon(layoutBS.ivFileterETSearch)
            }

            layoutBS.tvEventSpecialTitle.visibility = View.GONE
            selectedOption = DtdConstants.SEL_OPTION_BARS

            if (networkUtils.isInternetAvailable(this@MainActivity)) {
                if (view.tag == DtdConstants.RB_UNSEL_TAG) {
                    layoutBS.ivBars.setImageResource(R.drawable.btn_sel_radio)
                    layoutBS.rbBars.tag = DtdConstants.RB_SEL_TAG

                    layoutBS.ivStoress.setImageResource(R.drawable.btn_unsel_radio)
                    layoutBS.rbStoress.tag = DtdConstants.RB_UNSEL_TAG

                    layoutBS.ivPeopless.setImageResource(R.drawable.btn_unsel_radio)
                    layoutBS.rbPeopless.tag = DtdConstants.RB_UNSEL_TAG

                    isFromBars = true

                    layoutBS.etSearch.text?.clear()

                    setRadioButtonForBars()
                }
            }else{
                var mess = Message()
                mess.obj = resources.getString(R.string.connect_internet)
                viewModel.getAlertLiveData().postValue(mess)
            }

            if(::filterPopUp.isInitialized) {
                filterPopUp.dismissIfShowing()
            }
            setBottomSheetPeekHeight()
        }

        fun onClickrbStores(view: View){
            clearDetail()

            layoutBS.llFilter.visibility = View.VISIBLE
            layoutBS.clFilterBG.visibility = View.GONE

            prefs.saveString(DtdPrefsKeys.Keys.SEL_OPTION,DtdConstants.SEL_OPTION_STORES)
            if (layoutBS.llFilter.tag == DtdConstants.RB_SEL_TAG) {
                layoutBS.groupFilterOptions.visibility = View.GONE
                layoutBS.groupFilterItemsNotRequredInPeople.visibility = View.VISIBLE
            }else{
                layoutBS.groupFilterOptions.visibility = View.GONE
                layoutBS.groupFilterItemsNotRequredInPeople.visibility = View.GONE
            }

//            layoutBS.groupBarsAndStores.visibility = View.VISIBLE
            layoutBS.rvStores.visibility = View.VISIBLE
            layoutBS.vpAdvertisement.visibility = View.VISIBLE

            layoutBS.tvDivider.visibility = View.VISIBLE

            layoutBS.tvFollowBarStore.text = resources.getString(R.string.follow_store_cc)
            layoutBS.tvUnFollowBarStore.text = resources.getString(R.string.unfollow_store_cc)

            if (::filterPopUp.isInitialized && filterPopUp.popUp.isShowing){
                onClickFilterETSearchIcon(layoutBS.ivFileterETSearch)
            }

            layoutBS.tvEventSpecialTitle.visibility = View.GONE

            if (networkUtils.isInternetAvailable(this@MainActivity)) {

                selectedOption = DtdConstants.SEL_OPTION_STORES

                if (view.tag == DtdConstants.RB_UNSEL_TAG) {
                    layoutBS.ivStoress.setImageResource(R.drawable.btn_sel_radio)
                    layoutBS.rbStoress.tag = DtdConstants.RB_SEL_TAG
                    isFromBars = false

                    layoutBS.ivBars.setImageResource(R.drawable.btn_unsel_radio)
                    layoutBS.rbBars.tag = DtdConstants.RB_UNSEL_TAG

                    layoutBS.ivPeopless.setImageResource(R.drawable.btn_unsel_radio)
                    layoutBS.rbPeopless.tag = DtdConstants.RB_UNSEL_TAG

                    layoutBS.etSearch.text?.clear()

                    setRadioButtonForStores()
                }
            }else{
                var mess = Message()
                mess.obj = resources.getString(R.string.connect_internet)
                viewModel.getAlertLiveData().postValue(mess)
            }

            if(::filterPopUp.isInitialized) {
                filterPopUp.dismissIfShowing()
            }

            setBottomSheetPeekHeight()

        }

        fun onClickrbPeople(view: View){

            if(prefs.getBoolean(DtdConstants.IS_LOGGED_IN)) {
                layoutBS.llFilter.visibility = View.GONE

                layoutBS.ivArrowLeft.visibility = View.GONE
                layoutBS.ivArrowRight.visibility = View.GONE

                clearDetail()
                layoutBS.groupFilterOptions.visibility = View.GONE
                layoutBS.groupFilterItemsNotRequredInPeople.visibility = View.GONE

                prefs.saveString(DtdPrefsKeys.Keys.SEL_OPTION, DtdConstants.SEL_OPTION_PEOPLE)

                if (::filterPopUp.isInitialized && filterPopUp.popUp.isShowing) {
                    onClickFilterETSearchIcon(layoutBS.ivFileterETSearch)
                }

                layoutBS.tvEventSpecialTitle.visibility = View.GONE
                selectedOption = DtdConstants.SEL_OPTION_PEOPLE


                if (networkUtils.isInternetAvailable(this@MainActivity)) {
                    if (view.tag == DtdConstants.RB_UNSEL_TAG) {
                        layoutBS.ivPeopless.setImageResource(R.drawable.btn_sel_radio)
                        layoutBS.rbPeopless.tag = DtdConstants.RB_SEL_TAG

                        layoutBS.ivBars.setImageResource(R.drawable.btn_unsel_radio)
                        layoutBS.rbBars.tag = DtdConstants.RB_UNSEL_TAG

                        layoutBS.ivStoress.setImageResource(R.drawable.btn_unsel_radio)
                        layoutBS.rbStoress.tag = DtdConstants.RB_UNSEL_TAG

//                layoutBS.etSearch.text?.clear()

                        var getPeople = GetPeopleRequest()

                        var appsLatLng = dtdUtils.getAppsLatLng()

                        var latitude = appsLatLng[0]
                        var longitude = appsLatLng[1]

                        getPeople.latitude = latitude
                        getPeople.longitude = longitude
                        getPeople.user_id = prefs.getInt(DtdConstants.ID)
                        getPeople.privacy =
                            if (prefs.getString(DtdConstants.VISIBILITY_STATUS) == "1") {
                                APIs.V_PRIVATE
                            } else {
                                APIs.V_PUBLIC
                            }
                        viewModel.getPeople(getPeople)
                    }
                }
//            else{
//                var mess = Message()
//                mess.obj = resources.getString(R.string.connect_internet)
//                viewModel.getAlertLiveData().postValue(mess)
//            }

//            if(::filterPopUp.isInitialized) {
//                filterPopUp.dismissIfShowing()
//            }

                clearDetail()

                layoutBS.groupFilterOptions.visibility = View.GONE
                layoutBS.groupFilterItemsNotRequredInPeople.visibility = View.GONE
                layoutBS.rvStores.layoutManager = null
                layoutBS.rvStores.adapter = null

                layoutBS.rvStores.visibility = View.GONE
                layoutBS.vpAdvertisement.visibility = View.GONE
//                layoutBS.groupBarsAndStores.visibility = View.GONE
                layoutBS.tvDivider.visibility = View.INVISIBLE

            }else{

                var msg = Message()
                msg.arg1 = DtdConstants.SHOW_REGISTER_FORM
                handleMessages(msg)

//                var message = Message()
//                message.obj = App.app.getString(R.string.message_follow_when_no_login)
//                viewModel.getAlertLiveData().postValue(message)
            }


            setBottomSheetPeekHeight()


        }

        fun onClickrbSpecials(view: View){

            if (::filterPopUp.isInitialized && filterPopUp.popUp.isShowing){
                onClickFilterETSearchIcon(layoutBS.ivFileterETSearch)
            }

            if (networkUtils.isInternetAvailable(this@MainActivity)) {
                if (selectedOption == DtdConstants.SEL_OPTION_STORES){
                    prefs.saveString(DtdPrefsKeys.Keys.STORES_SELECTED_OPTION,DtdPrefsKeys.Values.OPTION_ONE)
                }

                if (view.tag == DtdConstants.RB_UNSEL_TAG) {
                    layoutBS.ivSpecialss.setImageResource(R.drawable.btn_sel_radio)
                    layoutBS.rbSpecialssNoFilter.tag = DtdConstants.RB_SEL_TAG

                    layoutBS.ivEvents.setImageResource(R.drawable.btn_unsel_radio)
                    layoutBS.rbEventsNoFilter.tag = DtdConstants.RB_UNSEL_TAG

                }else{
                    layoutBS.ivSpecialss.setImageResource(R.drawable.btn_unsel_radio)
                    layoutBS.rbSpecialssNoFilter.tag = DtdConstants.RB_UNSEL_TAG

                    layoutBS.tvEventSpecialTitle.visibility = View.GONE
                }


                hitStoresApi()

            }else{
                var mess = Message()
                mess.obj = resources.getString(R.string.connect_internet)
                viewModel.getAlertLiveData().postValue(mess)
            }
        }

        fun onClickrbEvents(view: View){
            if (::filterPopUp.isInitialized && filterPopUp.popUp.isShowing){
                onClickFilterETSearchIcon(layoutBS.ivFileterETSearch)
            }

            if (networkUtils.isInternetAvailable(this@MainActivity)) {

                if (selectedOption == DtdConstants.SEL_OPTION_STORES){
                    prefs.saveString(DtdPrefsKeys.Keys.STORES_SELECTED_OPTION,DtdPrefsKeys.Values.OPTION_TWO)
                }else if (selectedOption == DtdConstants.SEL_OPTION_BARS){
                    prefs.saveString(DtdPrefsKeys.Keys.BARS_SELECTED_OPTION,DtdPrefsKeys.Values.OPTION_TWO)
                }

                if (view.tag == DtdConstants.RB_UNSEL_TAG) {
                    layoutBS.ivEvents.setImageResource(R.drawable.btn_sel_radio)
                    layoutBS.rbEventsNoFilter.tag = DtdConstants.RB_SEL_TAG

                    layoutBS.ivSpecialss.setImageResource(R.drawable.btn_unsel_radio)
                    layoutBS.rbSpecialssNoFilter.tag = DtdConstants.RB_UNSEL_TAG
                }else{
                    layoutBS.ivEvents.setImageResource(R.drawable.btn_unsel_radio)
                    layoutBS.rbEventsNoFilter.tag = DtdConstants.RB_UNSEL_TAG

                    layoutBS.tvEventSpecialTitle.visibility = View.GONE

                }

                if(isFromBars){
                    hitApi()
                }else{
                    hitStoresApi()
                }
            }else{
                var mess = Message()
                mess.obj = resources.getString(R.string.connect_internet)
                viewModel.getAlertLiveData().postValue(mess)
            }
        }

        fun onClickOneDollor(view: View){

            when(view.tag){
                DtdConstants.RB_UNSEL_TAG->{
                    layoutBS.ivOneDollor.setImageResource(R.drawable.btn_sel_radio)
                    layoutBS.rbOneDollor.tag = DtdConstants.RB_SEL_TAG
                }

                DtdConstants.RB_SEL_TAG->{
                    layoutBS.ivOneDollor.setImageResource(R.drawable.btn_unsel_radio)
                    layoutBS.rbOneDollor.tag = DtdConstants.RB_UNSEL_TAG

                }
            }
            when(selectedOption){
                DtdConstants.SEL_OPTION_BARS->{
                    hitApi()
                }

                DtdConstants.SEL_OPTION_STORES->{
                    hitStoresApi()
                }
            }

//            viewModel.repository.getListFilteredFromDollors(rvAdapter.getCurruntList(),layoutBS.rbOneDollor,layoutBS.rbTwoDollor,layoutBS.rbThreeDollor)
        }

        fun onClickTwoDollor(view: View){
            when(view.tag){
                DtdConstants.RB_UNSEL_TAG->{
                    layoutBS.ivTwoDollor.setImageResource(R.drawable.btn_sel_radio)
                    layoutBS.rbTwoDollor.tag = DtdConstants.RB_SEL_TAG
                }

                DtdConstants.RB_SEL_TAG->{
                    layoutBS.ivTwoDollor.setImageResource(R.drawable.btn_unsel_radio)
                    layoutBS.rbTwoDollor.tag = DtdConstants.RB_UNSEL_TAG
                }
            }
            when(selectedOption){
                DtdConstants.SEL_OPTION_BARS->{
                    hitApi()
                }

                DtdConstants.SEL_OPTION_STORES->{
                    hitStoresApi()
                }
            }
//            viewModel.repository.getListFilteredFromDollors(rvAdapter.getCurruntList(),layoutBS.rbOneDollor,layoutBS.rbTwoDollor,layoutBS.rbThreeDollor)
        }

        fun onClickThreeDollor(view: View){
            when(view.tag){
                DtdConstants.RB_UNSEL_TAG->{
                    layoutBS.ivThreeDollor.setImageResource(R.drawable.btn_sel_radio)
                    layoutBS.rbThreeDollor.tag = DtdConstants.RB_SEL_TAG
                }

                DtdConstants.RB_SEL_TAG->{
                    layoutBS.ivThreeDollor.setImageResource(R.drawable.btn_unsel_radio)
                    layoutBS.rbThreeDollor.tag = DtdConstants.RB_UNSEL_TAG

                }
            }

            when(selectedOption){
                DtdConstants.SEL_OPTION_BARS->{
                    hitApi()
                }

                DtdConstants.SEL_OPTION_STORES->{
                    hitStoresApi()
                }
            }

//            viewModel.repository.getListFilteredFromDollors(rvAdapter.getCurruntList(),layoutBS.rbOneDollor,layoutBS.rbTwoDollor,layoutBS.rbThreeDollor)
        }

        fun onClickSpecialsInFilter(view: View){

            if (::filterPopUp.isInitialized && filterPopUp.popUp.isShowing){
                onClickFilterETSearchIcon(layoutBS.ivFileterETSearch)
            }

            if (networkUtils.isInternetAvailable(this@MainActivity)) {
                if (selectedOption == DtdConstants.SEL_OPTION_BARS){
                    prefs.saveString(DtdPrefsKeys.Keys.BARS_SELECTED_OPTION,DtdPrefsKeys.Values.OPTION_ONE)
                }

                if (view.tag == DtdConstants.RB_UNSEL_TAG) {
                    layoutBS.ivSpecialssInFilter.setImageResource(R.drawable.btn_sel_radio)
                    layoutBS.rbSpecialss.tag = DtdConstants.RB_SEL_TAG

//                    layoutBS.ivEvents.setImageResource(R.drawable.btn_unsel_radio)
//                    layoutBS.rbEventsNoFilter.tag = DtdConstants.RB_UNSEL_TAG

                }else{
                    layoutBS.ivSpecialssInFilter.setImageResource(R.drawable.btn_unsel_radio)
                    layoutBS.rbSpecialss.tag = DtdConstants.RB_UNSEL_TAG

//                    layoutBS.tvEventSpecialTitle.visibility = View.GONE
                }

                hitApi()

            }else{
                var mess = Message()
                mess.obj = resources.getString(R.string.connect_internet)
                viewModel.getAlertLiveData().postValue(mess)
            }

//            when(view.tag){
//                DtdConstants.RB_UNSEL_TAG->{
//                    layoutBS.ivSpecialssInFilter.setImageResource(R.drawable.btn_sel_radio)
//                    layoutBS.rbSpecialss.tag = DtdConstants.RB_SEL_TAG
//                }
//
//                DtdConstants.RB_SEL_TAG->{
//                    layoutBS.ivSpecialssInFilter.setImageResource(R.drawable.btn_unsel_radio)
//                    layoutBS.rbSpecialss.tag = DtdConstants.RB_UNSEL_TAG
//
//                }
//            }
        }

        fun onClickDrinksOnly(view: View){
            when(view.tag){
                DtdConstants.RB_UNSEL_TAG->{
                    layoutBS.ivDrinksOnly.setImageResource(R.drawable.btn_sel_radio)
                    layoutBS.rbDrinksOnly.tag = DtdConstants.RB_SEL_TAG
                }

                DtdConstants.RB_SEL_TAG->{
                    layoutBS.ivDrinksOnly.setImageResource(R.drawable.btn_unsel_radio)
                    layoutBS.rbDrinksOnly.tag = DtdConstants.RB_UNSEL_TAG

                }
            }
            hitApi()
        }

        fun onClickRestaurants(view: View){
            if (networkUtils.isInternetAvailable(this@MainActivity)) {
                when (view.tag) {
                    DtdConstants.RB_UNSEL_TAG -> {
                        layoutBS.ivRestaurants.setImageResource(R.drawable.btn_sel_radio)
                        layoutBS.rbRestaurants.tag = DtdConstants.RB_SEL_TAG
                    }

                    DtdConstants.RB_SEL_TAG -> {
                        layoutBS.ivRestaurants.setImageResource(R.drawable.btn_unsel_radio)
                        layoutBS.rbRestaurants.tag = DtdConstants.RB_UNSEL_TAG

                    }
                }
                hitApi()
            }else{
                var mess = Message()
                mess.obj = resources.getString(R.string.connect_internet)
                viewModel.getAlertLiveData().postValue(mess)
            }
        }

        fun onClickTheMeBar(view: View){
            if(networkUtils.isInternetAvailable(this@MainActivity)) {
                when (view.tag) {
                    DtdConstants.RB_UNSEL_TAG -> {
                        layoutBS.ivTheMeBar.setImageResource(R.drawable.btn_sel_radio)
                        layoutBS.rbTheMeBar.tag = DtdConstants.RB_SEL_TAG
                    }

                    DtdConstants.RB_SEL_TAG -> {
                        layoutBS.ivTheMeBar.setImageResource(R.drawable.btn_unsel_radio)
                        layoutBS.rbTheMeBar.tag = DtdConstants.RB_UNSEL_TAG

                    }
                }
                hitApi()
            }else{
                var mess = Message()
                mess.obj = resources.getString(R.string.connect_internet)
                viewModel.getAlertLiveData().postValue(mess)
            }

        }

        fun onClickNightClub(view: View){
            if(networkUtils.isInternetAvailable(this@MainActivity)) {
                when (view.tag) {
                    DtdConstants.RB_UNSEL_TAG -> {
                        layoutBS.ivNightClub.setImageResource(R.drawable.btn_sel_radio)
                        layoutBS.rbNightClub.tag = DtdConstants.RB_SEL_TAG
                    }

                    DtdConstants.RB_SEL_TAG -> {
                        layoutBS.ivNightClub.setImageResource(R.drawable.btn_unsel_radio)
                        layoutBS.rbNightClub.tag = DtdConstants.RB_UNSEL_TAG

                    }
                }
                hitApi()
            }else{
                var mess = Message()
                mess.obj = resources.getString(R.string.connect_internet)
                viewModel.getAlertLiveData().postValue(mess)
            }
        }

        fun onClickEventsInFilter(view: View){
            if (networkUtils.isInternetAvailable(this@MainActivity)) {
                when (view.tag) {
                    DtdConstants.RB_UNSEL_TAG -> {
                        layoutBS.ivEventsNoFilter.setImageResource(R.drawable.btn_sel_radio)
                        layoutBS.rbEvents.tag = DtdConstants.RB_SEL_TAG
                    }

                    DtdConstants.RB_SEL_TAG -> {
                        layoutBS.ivEventsNoFilter.setImageResource(R.drawable.btn_unsel_radio)
                        layoutBS.rbEvents.tag = DtdConstants.RB_UNSEL_TAG
                    }
                }
                hitApi()
            }else{
                var mess = Message()
                mess.obj = resources.getString(R.string.connect_internet)
                viewModel.getAlertLiveData().postValue(mess)
            }
        }

        fun onClickPatio(view: View){
            if (networkUtils.isInternetAvailable(this@MainActivity)) {
                when (view.tag) {
                    DtdConstants.RB_UNSEL_TAG -> {
                        layoutBS.ivPatio.setImageResource(R.drawable.btn_sel_radio)
                        layoutBS.rbPatio.tag = DtdConstants.RB_SEL_TAG
                    }
                    DtdConstants.RB_SEL_TAG -> {
                        layoutBS.ivPatio.setImageResource(R.drawable.btn_unsel_radio)
                        layoutBS.rbPatio.tag = DtdConstants.RB_UNSEL_TAG
                    }
                }
                hitApi()
            }else{
                var mess = Message()
                mess.obj = resources.getString(R.string.connect_internet)
                viewModel.getAlertLiveData().postValue(mess)
            }
        }

        fun onClickButtonFilter(view: View){
            if (selectedOption != DtdConstants.SEL_OPTION_PEOPLE) {
                when (view.tag) {
                    DtdConstants.RB_UNSEL_TAG -> {
                        layoutBS.ivFilter.setImageResource(R.drawable.ic_filter_icon)
                        layoutBS.llFilter.tag = DtdConstants.RB_SEL_TAG
                        if (selectedOption == DtdConstants.SEL_OPTION_STORES) {
                            layoutBS.groupFilterItemsNotRequredInPeople.visibility = View.VISIBLE
                            layoutBS.groupFilterOptions.visibility = View.GONE
                        } else if (selectedOption == DtdConstants.SEL_OPTION_BARS) {
                            layoutBS.groupFilterItemsNotRequredInPeople.visibility = View.GONE
                            layoutBS.groupFilterOptions.visibility = View.VISIBLE
                        }
                    }

                    DtdConstants.RB_SEL_TAG -> {
                        layoutBS.ivFilter.setImageResource(R.drawable.ic_filter_icon)
                        layoutBS.llFilter.tag = DtdConstants.RB_UNSEL_TAG
                        if (selectedOption == DtdConstants.SEL_OPTION_STORES) {
                            layoutBS.groupFilterItemsNotRequredInPeople.visibility = View.GONE
                            layoutBS.groupFilterOptions.visibility = View.GONE
                        } else if (selectedOption == DtdConstants.SEL_OPTION_BARS) {
                            layoutBS.groupFilterItemsNotRequredInPeople.visibility = View.GONE
                            layoutBS.groupFilterOptions.visibility = View.GONE
                        }
                    }
                }
            }else{
                var messageSnack = Message()
                messageSnack.obj = App.app.getString(R.string.no_filter_for_people)
                viewModel.getAlertLiveData().postValue(messageSnack)
            }

            setBottomSheetPeekHeight()

        }

        fun onClickFollowBarsStores(view: View){
            if(prefs.getBoolean(DtdConstants.IS_LOGGED_IN)) {
                when (selectedOption) {
                    DtdConstants.SEL_OPTION_BARS -> {
                        viewModel.postFUBar(1, id_follow_btn, user_id_follow_btn)
                    }

                    DtdConstants.SEL_OPTION_STORES -> {
                        viewModel.postFUStore(1, id_follow_btn, user_id_follow_btn)
                    }
                }
            }else{

                var msg = Message()
                msg.arg1 = DtdConstants.SHOW_REGISTER_FORM
                handleMessages(msg)

//                var mess = Message()
//                mess.obj = resources.getString(R.string.message_follow_when_no_login)
//                viewModel.getAlertLiveData().postValue(mess)
            }
        }

        fun onClickUnFollowBarsStores(view: View){
            if(prefs.getBoolean(DtdConstants.IS_LOGGED_IN)) {
                when(selectedOption){
                    DtdConstants.SEL_OPTION_BARS->{
                        viewModel.postFUBar(0,id_follow_btn,user_id_follow_btn)
                    }

                    DtdConstants.SEL_OPTION_STORES->{
                        viewModel.postFUStore(0,id_follow_btn,user_id_follow_btn)
                    }
                }
            }else{
                var msg = Message()
                msg.arg1 = DtdConstants.SHOW_REGISTER_FORM
                handleMessages(msg)

//                var mess = Message()
//                mess.obj = resources.getString(R.string.message_follow_when_no_login)
//                viewModel.getAlertLiveData().postValue(mess)
            }
        }

        fun onClickFollowTeam(barTeam: BarTeam){
            if(prefs.getBoolean(DtdConstants.IS_LOGGED_IN)) {
                when(barTeam.isBar){
                    true->{
                        viewModel.postFUBarTeam(1, barTeam.barId, barTeam.teamId, barTeam.deviceToken, prefs.getInt(DtdConstants.ID))
                    }
                    false->{
                        viewModel.postFUStoreTeam(1, barTeam.barId, barTeam.teamId, barTeam.deviceToken, prefs.getInt(DtdConstants.ID))
                    }
                }
            }else{

                var msg = Message()
                msg.arg1 = DtdConstants.SHOW_REGISTER_FORM
                handleMessages(msg)

//                var mess = Message()
//                mess.obj = resources.getString(R.string.message_follow_when_no_login)
//                viewModel.getAlertLiveData().postValue(mess)
            }
        }

        fun onClickUnfollowTeam(barTeam : BarTeam){
            if(prefs.getBoolean(DtdConstants.IS_LOGGED_IN)) {

                when(barTeam.isBar){
                    true->{
                        viewModel.postFUBarTeam(0, barTeam.barId, barTeam.teamId, barTeam.deviceToken, prefs.getInt(DtdConstants.ID))
                    }
                    false->{
                        viewModel.postFUStoreTeam(0, barTeam.barId, barTeam.teamId, barTeam.deviceToken, prefs.getInt(DtdConstants.ID))
                    }
                }
            }else{

                var msg = Message()
                msg.arg1 = DtdConstants.SHOW_REGISTER_FORM
                handleMessages(msg)

//                var mess = Message()
//                mess.obj = resources.getString(R.string.message_follow_when_no_login)
//                viewModel.getAlertLiveData().postValue(mess)
            }
        }

        fun onClicktvDimBG(view: View){
            if (networkUtils.isInternetAvailable(this@MainActivity)) {
                behaviour.state = BottomSheetBehavior.STATE_COLLAPSED
            }else{
                var mess = Message()
                mess.obj = resources.getString(R.string.connect_internet)
                viewModel.getAlertLiveData().postValue(mess)
            }
        }

        fun onClickUber(view: View){
            val pm: PackageManager = packageManager
            try {
                pm.getPackageInfo("com.ubercab", PackageManager.GET_ACTIVITIES)
                val uri =
                    "uber://?action=setPickup&pickup=my_location&dropoff[latitude]=$bar_latitude&dropoff[longitude]=$bar_longitute"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(uri)
                startActivity(intent)
            } catch (e: PackageManager.NameNotFoundException) {
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=com.ubercab")
                        )
                    )
                } catch (anfe: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=com.ubercab")
                        )
                    )
                }
            }
        }

        fun onClickDirections(view: View){
            val geoUri = "http://maps.google.com/maps?q=loc:$bar_latitude,$bar_longitute"

            val gmmIntentUri = Uri.parse("google.navigation:q=$bar_latitude,$bar_longitute")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)

        }

        fun onClickivExpand(view: View){
            if (networkUtils.isInternetAvailable(this@MainActivity)) {

                if ((selectedOption == DtdConstants.SEL_OPTION_PEOPLE &&
                            behaviour.state == BottomSheetBehavior.STATE_EXPANDED &&
                            ::filterPopUp.isInitialized &&
                            filterPopUp.filterView.visibility == View.VISIBLE)){

                    filterPopUp.filterView.visibility = View.GONE
                    behaviour.state = BottomSheetBehavior.STATE_COLLAPSED

                    layoutBS.ivArrowRight.visibility = View.GONE
                    layoutBS.ivArrowLeft.visibility = View.GONE

                }else if(selectedOption == DtdConstants.SEL_OPTION_PEOPLE &&
                    behaviour.state == BottomSheetBehavior.STATE_COLLAPSED &&
                    ::filterPopUp.isInitialized &&
                    filterPopUp.filterView.visibility == View.GONE){

                    behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                    filterPopUp.filterView.visibility = View.VISIBLE

                    if(markers.size > 2) {
                        layoutBS.ivArrowRight.visibility = View.VISIBLE
                        layoutBS.ivArrowLeft.visibility = View.VISIBLE
                    }

                }else if(behaviour.state == BottomSheetBehavior.STATE_EXPANDED){
                    behaviour.state = BottomSheetBehavior.STATE_COLLAPSED
                    layoutBS.ivArrowRight.visibility = View.GONE
                    layoutBS.ivArrowLeft.visibility = View.GONE

                }else{
                    behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                    if(markers.size > 2) {
                        layoutBS.ivArrowRight.visibility = View.VISIBLE
                        layoutBS.ivArrowLeft.visibility = View.VISIBLE
                    }
                }
            }else{
                var mess = Message()
                mess.obj = resources.getString(R.string.connect_internet)
                viewModel.getAlertLiveData().postValue(mess)
            }
        }

        fun onClickivHamburgur(view: View){

            when(prefs.getBoolean(DtdConstants.IS_LOGGED_IN)){
                true->{
                        var msg = Message()
                        msg.arg2 = 3 // just any value greater than 0
                        msg.arg1 = DtdConstants.SHOW_EDIT_PROFILE
                        handleMessages(msg)
                }
                false->{
                    viewOutLoggedPopUp()
                }
            }
        }

        fun onClickRightArrow(view: View){
            rvAdapter?.let {
                markers?.let {
                   if (StoresRVAdapter.selected_pos < (markers.size-1)) {
                    ++StoresRVAdapter.selected_pos
                    layoutBS.rvStores.scrollToPosition(StoresRVAdapter.selected_pos)

                    selectItem(rvAdapter,
                        StoresRVAdapter.selected_pos,
                        markers[StoresRVAdapter.selected_pos])
                  } // End of if
                }
             }
        }

        fun onClickLeftArrow(view: View){
//            if (!layoutBS.etSearch.isFocused){
            rvAdapter?.let {
                markers?.let {
                    if (StoresRVAdapter.selected_pos < markers.size - 1) {
                        if (StoresRVAdapter.selected_pos > 0)
                            --StoresRVAdapter.selected_pos

                        layoutBS.rvStores.scrollToPosition(StoresRVAdapter.selected_pos)

                        selectItem(
                            rvAdapter,
                            StoresRVAdapter.selected_pos,
                            markers[StoresRVAdapter.selected_pos]
                        )

                    } // End of if
                }
            }
//            }
        }


        fun swipeGesture(){
            layoutBS.scview.setOnTouchListener(object : OnSwipeTouchListener(this@MainActivity){
                override fun onSwipeRight() {
                    super.onSwipeRight()

                    if (!layoutBS.etSearch.isFocused){

                        rvAdapter?.let {
                            markers?.let {
                                if (StoresRVAdapter.selected_pos < markers.size - 1) {
                                    if (StoresRVAdapter.selected_pos > 0)
                                        --StoresRVAdapter.selected_pos

                                    layoutBS.rvStores.scrollToPosition(StoresRVAdapter.selected_pos)

                                    selectItem(
                                        rvAdapter,
                                        StoresRVAdapter.selected_pos,
                                        markers[StoresRVAdapter.selected_pos]
                                    )

                                } // End of if
                            }
                        }
                    }

                }

                override fun onSwipeLeft() {
                    super.onSwipeLeft()

                    if (!layoutBS.etSearch.isFocused){
                        rvAdapter?.let {
                            markers?.let {

                                //                           if (StoresRVAdapter.selected_pos > -) {
                                ++StoresRVAdapter.selected_pos

                                layoutBS.rvStores.scrollToPosition(StoresRVAdapter.selected_pos)

                                selectItem(
                                    rvAdapter,
                                    StoresRVAdapter.selected_pos,
                                    markers[StoresRVAdapter.selected_pos]
                                )

//                           } // End of if
                            }
                        }
                    }
                }
            })
        }

        fun removeGesture(){
            layoutBS.scview.setOnTouchListener(null)
        }

        fun bottomSheetCallBack(){
            behaviour.setBottomSheetCallback(object : BottomSheetCallback(){

                override fun onSlide(p0: View, p1: Float) {

                }

                override fun onStateChanged(p0: View, p1: Int) {
                    if (p1 == BottomSheetBehavior.STATE_EXPANDED){
                        layoutBS.ivExpand.setImageResource(R.drawable.arrow_left)
                    }else{
                        layoutBS.ivExpand.setImageResource(R.drawable.arrow_right)
                    }

                    if (behaviour.state == BottomSheetBehavior.STATE_EXPANDED)
                        binding.tvDimBG.visibility = View.VISIBLE
                    else if (behaviour.state == BottomSheetBehavior.STATE_COLLAPSED)
                        binding.tvDimBG.visibility = View.GONE
                }

            })
        }

        fun whichIsSelected():String{
            return when {
                rbBars.tag == DtdConstants.RB_SEL_TAG -> {
                    DtdConstants.SEL_OPTION_BARS
                }
                rbStoress.tag == DtdConstants.RB_SEL_TAG -> {
                    DtdConstants.SEL_OPTION_STORES
                }
                else -> {
                    DtdConstants.SEL_OPTION_PEOPLE
                }
            }
        }

        fun onClickFilterETSearchIcon(v:View){

            var isTextThere = layoutBS.etSearch.text?:""

            ApplicationUtils().hideKeyboard(this@MainActivity,layoutBS.etSearch)

            if (isTextThere.isNotEmpty()) {

                when (selectedOption) {
                    DtdConstants.SEL_OPTION_BARS -> {
                        var requestBars = GetSearchBarsRequest()

                        var appsLatLng = dtdUtils.getAppsLatLng()

                        requestBars.barLatitude = appsLatLng[0]
                        requestBars.barLongitude = appsLatLng[1]
                        requestBars.query = layoutBS.etSearch.text.toString()

                        viewModel.getDataSearchBars(requestBars)
                    }

                    DtdConstants.SEL_OPTION_STORES -> {
                        var requestStores = GetStoreSearchRequest()

                        var appsLatLng = dtdUtils.getAppsLatLng()


                        requestStores.barLatitude = appsLatLng[0]
                        requestStores.barLongitude = appsLatLng[1]
                        requestStores.query = layoutBS.etSearch.text.toString()

                        viewModel.getDataSearchStores(requestStores)
                    }

                    DtdConstants.SEL_OPTION_PEOPLE -> {

                        var appsLatLng = dtdUtils.getAppsLatLng()


                        viewModel.getSearchPeopleFrontend(prefs.getInt(DtdConstants.ID),appsLatLng[0],appsLatLng[1],layoutBS.etSearch.text.toString())

                    }
                }
            }else{
                var mes = Message()
                mes.obj = "Please enter some keywords to search"
                handleMessages(mes)
            }
        }

        fun onClickCross(v:View){

            isFromSearchFilterCrossIcon = true


            if (selectedOption == DtdConstants.SEL_OPTION_PEOPLE){

                layoutBS.etSearch.text?.clear()
                filterPopUp.adapter.updateList(listPeoplePretending
                )

            }else{

                layoutBS.rvStores.visibility = View.VISIBLE
                layoutBS.etSearch.text?.clear()
                clickEvent.swipeGesture()

                if (::filterPopUp.isInitialized) {
                    filterPopUp.dismissIfShowing()
                }

                if (::rvAdapter.isInitialized && markers.size > 0) {
                    selectItem(rvAdapter, prevPosition, markers[prevPosition])
                }

            }
        }




        fun onClickPickLocation(v:View){
            var msg = Message()
            msg.arg1 = DtdConstants.SHOW_PICK_LOCATION
            handleMessages(msg)
        }


    } // End of Click Events

    fun setRadioButtonForBars(){
        var sel_option = prefs.getString(DtdPrefsKeys.Keys.BARS_SELECTED_OPTION)

        layoutBS.tvEventsAndTasting.text = App.app.getString(R.string.events)
        layoutBS.tvSpecialsAndDelivery.text = App.app.getString(R.string.specials)

        when(sel_option){
            DtdPrefsKeys.Values.DEFAULT_STRING_VALUE->{
                layoutBS.ivEvents.setImageResource(R.drawable.btn_unsel_radio)
                layoutBS.rbEventsNoFilter.tag = DtdConstants.RB_UNSEL_TAG

                layoutBS.ivSpecialss.setImageResource(R.drawable.btn_unsel_radio)
                layoutBS.rbSpecialssNoFilter.tag = DtdConstants.RB_UNSEL_TAG
            }

            DtdPrefsKeys.Values.OPTION_ONE->{
                clickEvent.onClickrbSpecials(layoutBS.rbSpecialssNoFilter)
            }

            DtdPrefsKeys.Values.OPTION_TWO->{
                clickEvent.onClickrbEvents(layoutBS.rbEventsNoFilter)
            }
        }

        hitApi()
    }

    fun setRadioButtonForStores(){
        var sel_option = prefs.getString(DtdPrefsKeys.Keys.STORES_SELECTED_OPTION)

        layoutBS.tvEventsAndTasting.text = App.app.getString(R.string.tasting)
        layoutBS.tvSpecialsAndDelivery.text = App.app.getString(R.string.delivery__)

        when(sel_option){
            DtdPrefsKeys.Values.DEFAULT_STRING_VALUE->{
                layoutBS.ivEvents.setImageResource(R.drawable.btn_unsel_radio)
                layoutBS.rbEventsNoFilter.tag = DtdConstants.RB_UNSEL_TAG

                layoutBS.ivSpecialss.setImageResource(R.drawable.btn_unsel_radio)
                layoutBS.rbSpecialssNoFilter.tag = DtdConstants.RB_UNSEL_TAG
            }

            DtdPrefsKeys.Values.OPTION_ONE->{
                clickEvent.onClickrbSpecials(rbSpecialssNoFilter)
            }

            DtdPrefsKeys.Values.OPTION_TWO->{
                clickEvent.onClickrbEvents(rbEventsNoFilter)
            }
        }
        hitStoresApi()
    }



    fun openPickFromGallary() {
        var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        intent.setType("image/* video/* audio/*")
        intent.setType("image/*")
        startActivityForResult(Intent.createChooser(intent,
            "Select Media"), DtdConstants.PICK_IMAGE_FROM_CAMERA)
    }


    fun getRealPathFromURI(contentURI: Uri?): String? {
        var result:String? = ""
        contentURI?.let {
            var cursor = contentResolver.query(contentURI,
                null, null, null, null)
            if (cursor == null) {
                result = contentURI.path
            } else {
                cursor.moveToFirst()
                var idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                result = cursor.getString(idx)
                cursor.close()
            }
        }
        return result
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            DtdConstants.PICK_IMAGE_FROM_CAMERA->{
                var url = getRealPathFromURI(data?.data)
            }

            DtdConstants.AUTOCOMPLETE_REQUEST_CODE->{
                if(resultCode == AutocompleteActivity.RESULT_OK){
                    data?.let {
                        var place = Autocomplete.getPlaceFromIntent(data)

                        var latlng = place.latLng

                        prefs.saveString(DtdPrefsKeys.Keys.LATITUDE,latlng?.latitude.toString())
                        prefs.saveString(DtdPrefsKeys.Keys.LONGITUDE,latlng?.longitude.toString())
                        prefs.saveString(DtdPrefsKeys.Keys.PICKED_LOCATION,place.name)

                        layoutBS.autoCompleteTextView.text = place.name

                        layoutBS.rbBars.tag = DtdConstants.RB_UNSEL_TAG
                        clickEvent.onClickrbBars(layoutBS.rbBars)
                    }?: kotlin.run {
                        pickLocationSnackBar()
                    }
                }else{
                    pickLocationSnackBar()
                }
            }
        }
    }

    fun pickLocationSnackBar(){

        var messageSnack = Message()
        messageSnack.obj = App.app.getString(R.string.choose_location)
        viewModel.getAlertLiveData().postValue(messageSnack)

//        ToastSnackUtils.snackBar(binding.root, App.app.getString(R.string.unable_to_pick_latlong),
//            Snackbar.LENGTH_LONG,
//            false,
//            App.app.getString(R.string.choose_location),
//            View.OnClickListener {
//                var msg = Message()
//                msg.arg1 = DtdConstants.SHOW_PICK_LOCATION
//                handleMessages(msg)
//            })
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            var v = currentFocus
            if (v is AppCompatEditText) {
                var outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    ApplicationUtils().hideKeyboard(this,layoutBS.etSearch)
                }
            }
        }

        return super.dispatchTouchEvent( event )
    }


    override fun onBackPressed() {

        if (::behaviour.isInitialized) {
            when {

                (::filterPopUp.isInitialized &&
                        filterPopUp.popUp.isShowing &&
                        selectedOption == DtdConstants.SEL_OPTION_PEOPLE &&
                        behaviour.state == BottomSheetBehavior.STATE_EXPANDED)->{

                    filterPopUp.filterView.visibility = View.GONE
                    behaviour.state = BottomSheetBehavior.STATE_COLLAPSED
                }

                (::filterPopUp.isInitialized && filterPopUp.popUp.isShowing)->{
                    filterPopUp.dismissIfShowing()
                    clickEvent.onClickCross(layoutBS.ivFileterETCross)
                }

                (::menuLogedInPopUp.isInitialized  && menuLogedInPopUp.getPopUp().isShowing )-> {
                    menuLogedInPopUp.getPopUp().dismiss()

                }
                (::menuLogedOutPopUp.isInitialized && menuLogedOutPopUp.getPopUp().isShowing)-> {

                    menuLogedOutPopUp.getPopUp().dismiss()
                }

                behaviour.state == BottomSheetBehavior.STATE_EXPANDED -> {
                    behaviour.state = BottomSheetBehavior.STATE_COLLAPSED
                }

                else -> {
                    super.onBackPressed()
                }
            }
        }else{
            super.onBackPressed()
        }
    }

    private fun hitApi() {
        if(layoutBS.rbBars.tag == DtdConstants.RB_SEL_TAG) {

            var barsRequest = GetBarsDataRequest()

            var appsLatLng = dtdUtils.getAppsLatLng()


            barsRequest.barLatitude = appsLatLng[0]
            barsRequest.barLongitude = appsLatLng[1]


            if (layoutBS.rbSpecialssNoFilter.tag == DtdConstants.RB_SEL_TAG) {
                barsRequest.drinkSpecial = "drink_special"
            } else {
                barsRequest.drinkSpecial = ""
            }

            if (layoutBS.rbEventsNoFilter.tag == DtdConstants.RB_SEL_TAG) {
                barsRequest.events = "events"
            } else {
                barsRequest.events = ""
            }

            if (layoutBS.rbDrinksOnly.tag == DtdConstants.RB_SEL_TAG) {
                barsRequest.drinks_only = "drinks_only"
            } else {
                barsRequest.drinks_only = ""
            }

            if (layoutBS.rbNightClub.tag == DtdConstants.RB_SEL_TAG) {
                barsRequest.night_club = "night_club"
            } else {
                barsRequest.night_club = ""
            }

            if (layoutBS.rbRestaurants.tag == DtdConstants.RB_SEL_TAG) {
                barsRequest.restaurant = "restaurant"
            } else {
                barsRequest.restaurant = ""
            }

            if (layoutBS.rbPatio.tag == DtdConstants.RB_SEL_TAG) {
                barsRequest.patio = "patio"
            } else {
                barsRequest.patio = ""
            }

            if (layoutBS.rbTheMeBar.tag == DtdConstants.RB_SEL_TAG) {
                barsRequest.theme_bar = "theme_bar"
            } else {
                barsRequest.theme_bar = ""
            }



            viewModel.getBarsData(barsRequest,handler)
        }
    }

    fun hitStoresApi(){
        if(layoutBS.rbStoress.tag == DtdConstants.RB_SEL_TAG) {
            var storesRequest = GetStoresRequest()

            var appsLatLng = dtdUtils.getAppsLatLng()


            storesRequest.store_latitude = appsLatLng[0]
            storesRequest.store_longitude = appsLatLng[1]

            if (rbSpecialssNoFilter.tag == DtdConstants.RB_SEL_TAG) {
                storesRequest.delivery = APIs.P_DELIVERY
            } else if (rbEventsNoFilter.tag == DtdConstants.RB_SEL_TAG) {
                storesRequest.tastings = APIs.P_TASTING
            }

            viewModel.getDataStores(storesRequest)
        }
    }


    fun hitStoreDetail(markerModel: MarkerModel){
        if(layoutBS.rbStoress.tag == DtdConstants.RB_SEL_TAG) {
            var storeDetail = GetStoresDetailRequest()
            storeDetail.store_id = markerModel.bar_id
            storeDetail.miles = markerModel.miles
            storeDetail.user_id = prefs.getInt(DtdConstants.ID)
            viewModel.getDataStoresDetail(storeDetail)
        }
    }

    fun selectItem(rvAdapter: StoresRVAdapter, position: Int, markerModel : MarkerModel){
        if(position != prevPosition || position == 0 || isFromSearchFilterCrossIcon) {
            isFromSearchFilterCrossIcon = false
            rvAdapter.changeBackground(position)
            rvStores.scrollToPosition(position)

            bar_latitude =  markerModel.markerOptions.position.latitude
            bar_longitute =  markerModel.markerOptions.position.longitude

            if (layoutBS.rbPeopless.tag != DtdConstants.RB_SEL_TAG){

                if (isFromBars){
                    hitDetailWebService(markerModel)
                }else{
                    hitStoreDetail(markerModel)
                }
                prevPosition = position
            }

        }
    }

    override fun onMapReady(map: GoogleMap?) {

        googleMap = map
        map?.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle))

        var appsLatLng = dtdUtils.getAppsLatLng()
        var latlng = LatLng(appsLatLng[0],appsLatLng[1])

        dtdUtils.addCircleToMap(map,latlng)


        // Commented these bcz these are called in premissionGranted
//        setUpMap(map)
//        progresDialog.show()

        requestLocationPermission()

    }

    private fun requestLocationPermission() {
        var permissionList = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
        var rationaleList = arrayOf(getString(R.string.location_permission_required))
        requestPermission(this,permissionList,rationaleList,DtdConstants.LOCATION_REQUEST_CODE)
    }

    private fun setUpMap(map: GoogleMap?){

        var appsLatLng = dtdUtils.getAppsLatLng()
        var latlng = LatLng(appsLatLng[0],appsLatLng[1])

        map?.let {

            dtdUtils.addCircleToMap(map,latlng)

            if (selectedOption != DtdConstants.SEL_OPTION_PEOPLE) {
                map.setOnMarkerClickListener(MapMarkerBounce(getMainThreadHandler()))
            }

            var infoWindow = CustomInfoWindowMaps(this@MainActivity)
            map?.setInfoWindowAdapter(infoWindow)
        }

        openNotificationPopUpWhenFromNotification()

    }


    private fun getMainThreadHandler(): Handler{
        return object : Handler(mainLooper){
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (selectedOption != DtdConstants.SEL_OPTION_PEOPLE) {
                    var model = msg.obj as MarkerModel
                    rvStores.scrollToPosition(model.index)
                    behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                    selectItem(rvAdapter, model.index, markers[model.index])
                }
            }
        }
    }

    private fun hitDetailWebService(markerModel: MarkerModel){
        if(layoutBS.rbBars.tag == DtdConstants.RB_SEL_TAG) {
            var barsReq = GetBarDetailsRequest()
            barsReq.barId = markerModel.bar_id
            barsReq.dollar = markerModel.dollar
            barsReq.user_id = prefs.getInt(DtdConstants.ID)

            try {
                barsReq.miles = markerModel.distance?.toDouble()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            viewModel.getBarsDetail(barsReq)
        }
    }

    private fun clearDetail(){
        layoutBS.vpBarImages.adapter = null
        layoutBS.tvBarName.text = ""
        layoutBS.tvBarStreetAddress.text = ""
        layoutBS.tvDesctiption.text = ""
        layoutBS.tvFeaturesHeading.text = ""
        layoutBS.tvSpecialEventsText.text = ""

        layoutBS.llHours.visibility = View.GONE
        layoutBS.llPhone.visibility = View.GONE
        layoutBS.tvUrl.visibility = View.GONE
        layoutBS.tvTeamsHeading.visibility = View.GONE
        layoutBS.tvDividerTeams.visibility = View.GONE
        layoutBS.tvDividerBarImges.visibility = View.GONE
        layoutBS.rvTeams.visibility = View.GONE
        layoutBS.tvSpecialEventsText.visibility = View.GONE

//        layoutBS.ivArrowLeft.visibility = View.GONE
//        layoutBS.ivArrowRight.visibility = View.GONE

        layoutBS.tvFollowBarStore.visibility = View.GONE
        layoutBS.tvUnFollowBarStore.visibility = View.GONE
        layoutBS.tvEventHeading.visibility = View.GONE
        layoutBS.tvUpcommingHeading.visibility = View.GONE

        rvTodayEvents.adapter = null
        rvUpcoming.adapter = null

        layoutBS.tvDeliveryCharges.text = ""
        layoutBS.tvDeliveryDescription.text = ""
        layoutBS.llDelivery.visibility = View.GONE

        layoutBS.llUberDirections.visibility = View.GONE

        layoutBS.rvFeatures.layoutManager = null
        layoutBS.rvFeatures.adapter = null
    }

    private fun dollorString(dollors: Int?): String{
        return when(dollors){
            1-> {
                "$"
            }
            2->{
                "$$"
            }
            3->{
                "$$$"
            }
            else->{
                ""
            }
        }
    }

    private fun setDetail(isFromBarsFlag: Boolean ,barDetails: Bardetilsdata) {
        if(markers.isNotEmpty())
        {
            var list = arrayListOf<String>()
            for (obj in barDetails.barGallery){
                list.add(obj)
            }

            layoutBS.tvBarStreetAddress.visibility = View.VISIBLE
            layoutBS.vpBarImages.adapter = ImageAdapter(this,list,handler)
            layoutBS.tvBarName.text = barDetails.barName
            layoutBS.tvBarStreetAddress.text = barDetails.barStreetAddress
            layoutBS.tvFeaturesHeading.text = getString(R.string.features)
            layoutBS.llUberDirections.visibility = View.VISIBLE

            layoutBS.llPhone.visibility  = View.VISIBLE
            layoutBS.llHours.visibility = View.VISIBLE
            layoutBS.tvUrl.visibility = View.VISIBLE

            layoutBS.tvPhoneValue.text = barDetails.owner_phone

            try {
                layoutBS.tvHoursValue.text = barDetails.bar_timing
                    .replace("From:", "")
                    .replace("To", "-")
            }catch (e: java.lang.Exception){
                e.printStackTrace()
            }

            layoutBS.tvUrl.text = barDetails.bar_website
            layoutBS.tvUrl.movementMethod = LinkMovementMethod()

            when(selectedOption){
                DtdConstants.SEL_OPTION_BARS->{
                    layoutBS.tvDesctiption.text = ""
                    layoutBS.tvDesctiption.visibility = View.GONE
                }
                DtdConstants.SEL_OPTION_STORES->{
                    layoutBS.tvDesctiption.visibility = View.VISIBLE
                    layoutBS.tvDesctiption.text = barDetails.barDesc
                }
            }


            if (barDetails.bar_follow) {
                layoutBS.tvUnFollowBarStore.visibility = View.VISIBLE
                layoutBS.tvFollowBarStore.visibility = View.GONE
            } else {
                layoutBS.tvUnFollowBarStore.visibility = View.GONE
                layoutBS.tvFollowBarStore.visibility = View.VISIBLE
            }

            id_follow_btn = barDetails.id
            user_id_follow_btn = prefs.getInt(DtdConstants.ID)

            barDetails.barTeam?.let {
                if (it.isNotEmpty()) {
                    layoutBS.rvTeams.visibility = View.VISIBLE
                    layoutBS.tvTeamsHeading.visibility = View.VISIBLE
                    layoutBS.tvDividerTeams.visibility = View.VISIBLE

                    layoutBS.rvTeams.layoutManager = LinearLayoutManager(
                        this@MainActivity,
                        LinearLayoutManager.HORIZONTAL, false
                    )

                    teamsAdapter = TeamsAdapter(
                        it,
                        this,
                        clickEvent::onClickFollowTeam,
                        clickEvent::onClickUnfollowTeam
                    )
                    layoutBS.rvTeams.adapter = teamsAdapter
                } else {
                    layoutBS.rvTeams.visibility = View.GONE
                    layoutBS.tvTeamsHeading.visibility = View.GONE
                    layoutBS.tvDividerTeams.visibility = View.GONE
                }
            } ?: run {
                layoutBS.rvTeams.visibility = View.GONE
                layoutBS.tvTeamsHeading.visibility = View.GONE
                layoutBS.tvDividerTeams.visibility = View.GONE
            }

            barDetails.features?.let {
                if (barDetails.features.size > 0) {

                    layoutBS.tvFeaturesHeading.visibility = View.VISIBLE

                    var featuresStr = StringBuffer()
                    featuresStr.append(App.app.getString(R.string.features)+" ")

                    var index = -1
                    var arrayLength = barDetails.features.size-1
                    for(obj in barDetails.features){
                        featuresStr.append(obj)
                        if (++index != arrayLength)
                            featuresStr.append(", ")
                    }

                    var spanStr = SpannableString(featuresStr)
                    var spanBold = StyleSpan(Typeface.BOLD)
                    spanStr.setSpan(spanBold,0,10, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

                    layoutBS.tvFeaturesHeading.text = spanStr
//                layoutBS.tvFeaturesHeading.typeface = dtdUtils.fontNeutraMedium()




//                layoutBS.rvFeatures.visibility = View.VISIBLE
//                layoutBS.tvFeaturesHeading.visibility = View.VISIBLE
//                layoutBS.rvFeatures.layoutManager = LinearLayoutManager(this)
//                layoutBS.rvFeatures.adapter = FeaturesRVAdapter(barDetails.features, this)
                } else {

                    layoutBS.tvFeaturesHeading.visibility = View.GONE
//                layoutBS.rvFeatures.visibility = View.GONE
//                layoutBS.tvFeaturesHeading.visibility = View.GONE
                }

            } ?: kotlin.run {
                layoutBS.rvFeatures.visibility = View.GONE
                layoutBS.tvFeaturesHeading.visibility = View.GONE
            }

            layoutBS.tvmiles.text = barDetails.miles + " miles"

            if (isFromBarsFlag) {
                layoutBS.llDelivery.visibility = View.GONE

            layoutBS.tvDollor.text = dollorString(barDetails.dollar.toInt())

            } else {
                layoutBS.tvDelivery.text = App.app.getString(R.string.delivery)

                layoutBS.llDelivery.visibility = View.VISIBLE

                layoutBS.tvDeliveryCharges.text =
                    if(barDetails.delivery_charges == null || barDetails.delivery_charges == ""){
                        "No Delivery"
                    }else{
                        barDetails.delivery_charges
                    }

//            if (barDetails.delivery_comment.isEmpty()){
//                layoutBS.tvDeliveryDescription.text = comments
//            }else{
//                layoutBS.tvDeliveryDescription.text = barDetails.store_website
//            }


//            layoutBS.tvWebsitelink.text = barDetails.store_website
//            layoutBS.tvWebsitelink.movementMethod = LinkMovementMethod()


                /* layoutBS.tvWebsitelink.isClickable = true
                layoutBS.tvWebsitelink.setMovementMethod(LinkMovementMethod.getInstance())
                var linkText = "<a href='"+barDetails.store_website+"'></a>"
                layoutBS.tvWebsitelink.text = Html.fromHtml(linkText)*/

            }

            if (barDetails.todayEvent.size > 0) {
                layoutBS.tvEventHeading.visibility = View.VISIBLE
                layoutBS.rvTodayEvents.visibility = View.VISIBLE

                var spevHeadingText = StringBuffer()
                var end_index = 0
                if (rbSpecialss.tag == DtdConstants.RB_SEL_TAG) {
                    spevHeadingText.append("Specials : ")
                    end_index = 10
                }
                else if (rbEvents.tag == DtdConstants.RB_SEL_TAG) {
                    spevHeadingText.append("Events : ")
                    end_index = 9
                }


                for (obj in barDetails.todayEvent){

                    if (obj.event_category.contains(DtdConstants.MJ_PROMOTION) ||
                        obj.event_category.contains(DtdConstants.DRINK_SPECIAL) ||
                        obj.event_category.contains(DtdConstants.FOOD_DRINK_SPECIAL) ||
                        obj.event_category.contains(DtdConstants.EVENT)){

                        spevHeadingText.append(obj.eventDesc)
                        layoutBS.tvSpecialEventsText.visibility = View.VISIBLE

                        var spanStr = SpannableString(spevHeadingText)
                        var spanBold = StyleSpan(Typeface.BOLD)
                        spanStr.setSpan(spanBold,0,end_index, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

                        layoutBS.tvSpecialEventsText.text = spanStr

                        break
                    }
                }


                var layoutmanager = LinearLayoutManager(this)
                layoutBS.rvTodayEvents.layoutManager = layoutmanager
                layoutBS.rvTodayEvents.adapter =
                    TodayEventsRVAdapter(isFromBarsFlag, barDetails.todayEvent, this)
            } else {
                layoutBS.tvEventHeading.visibility = View.GONE
                layoutBS.rvTodayEvents.visibility = View.GONE
                layoutBS.tvSpecialEventsText.visibility = View.GONE
            }

            if (barDetails.comingEvents.size > 0) {
                layoutBS.tvUpcommingHeading.visibility = View.VISIBLE
                layoutBS.rvUpcoming.visibility = View.VISIBLE

                var layoutmanagerUpComing = LinearLayoutManager(this)
                layoutBS.rvUpcoming.layoutManager = layoutmanagerUpComing
                layoutBS.rvUpcoming.adapter =
                    UpComingEventsRVAdapter(isFromBarsFlag, barDetails.comingEvents, this)
            } else {
                layoutBS.tvUpcommingHeading.visibility = View.GONE
                layoutBS.rvUpcoming.visibility = View.GONE
            }
        }
    }

    private fun viewInLoggedPopUp() {
        menuLogedInPopUp = MenuLoggedInPopUp(this,handler)
        menuLogedInPopUp.getPopUp().animationStyle = R.style.pop_up_animation_style

        menuLogedInPopUp.getPopUp().setOnDismissListener {
            binding.tvMenuBackground.visibility = View.GONE
        }

        if (menuLogedInPopUp.getPopUp().isShowing) {
            menuLogedInPopUp.getPopUp().dismiss()
        } else {
            if (behaviour.state == BottomSheetBehavior.STATE_EXPANDED) {
                menuLogedInPopUp.getPopUp().showAsDropDown(binding.layoutBottomSheet.ivHamburgur)
                binding.tvMenuBackground.visibility = View.VISIBLE
//                popUp.showAtLocation(ivHamburgur, Gravity.TOP,0, ivHamburgur.height)
            } else {
                // Old one
                var location = IntArray(2)
                layoutBS.ivHamburgur.getLocationOnScreen(location)
                menuLogedInPopUp.getPopUp().showAtLocation(layoutBS.ivHamburgur, Gravity.NO_GRAVITY,
                    location[0],
                    (location[1] -(layoutBS.ivHamburgur.top +  ApplicationUtils().convertDpToPixel(250.33f).toInt())))

                binding.tvMenuBackground.visibility = View.VISIBLE
                // Old one

//                    var ivExpang = layoutBS.clBottomLayout.getViewById(R.id.ivExpand)
//                    var autCompTV = layoutBS.clBottomLayout.getViewById(R.id.autoCompleteTextView)
//                    var clFilt = layoutBS.clBottomLayout.getViewById(R.id.clFilter)
//
//                    clFilt.apply {
//                        viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener{
//                            override fun onGlobalLayout() {
//                                var marginBottom = autCompTV.bottom + clFilt.height + ivExpang.height + ivExpang.height
//                                menuLogedInPopUp.getPopUp().showAtLocation(layoutBS.ivHamburgur, Gravity.NO_GRAVITY,20,marginBottom)
//
//                                clFilt.viewTreeObserver.removeOnGlobalLayoutListener(this)
//                            }
//                        })
//                    }

            }
        }
    }

    private fun viewOutLoggedPopUp() {
        menuLogedOutPopUp = MenuLoggedOutPopUp(this,handler)
        menuLogedOutPopUp.getPopUp().animationStyle = R.style.pop_up_animation_style

        menuLogedOutPopUp.getPopUp().setOnDismissListener {
            binding.tvMenuBackground.visibility = View.GONE
        }

        if (menuLogedOutPopUp.getPopUp().isShowing) {
            menuLogedOutPopUp.getPopUp().dismiss()

        } else {
            if (behaviour.state == BottomSheetBehavior.STATE_EXPANDED) {
                menuLogedOutPopUp.getPopUp().showAsDropDown(binding.layoutBottomSheet.ivHamburgur)
                binding.tvMenuBackground.visibility = View.VISIBLE
//                popUp.showAtLocation(ivHamburgur, Gravity.TOP,0, ivHamburgur.height)
            } else {
                var location = IntArray(2)
                layoutBS.ivHamburgur.getLocationOnScreen(location)
                menuLogedOutPopUp.getPopUp().showAtLocation(layoutBS.ivHamburgur, Gravity.NO_GRAVITY,
                    location[0],
                    (location[1] -(layoutBS.ivHamburgur.top +  ApplicationUtils().convertDpToPixel(90f).toInt())))

                binding.tvMenuBackground.visibility = View.VISIBLE

//                popUp.showAtLocation(ivHamburgur, Gravity.TOP,0, ivHamburgur.height)
            }
        }
    }

    fun handleProgressBar(){
        viewModel.getProgressBar().observe(this, Observer {
            when(it){
                true->{

                    if (!isDialogFragShowing(DtdConstants.SHOW_PROGRESS_DIALOG.toString())) {
                        progressLayout.setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_snackbar_style)

                        progressLayout.show(supportFragmentManager, DtdConstants.SHOW_PROGRESS_DIALOG.toString())
                    }

//                    binding.frameLayoutLoader.visibility = View.VISIBLE

//                    dtdUtils.loadGIFImageFromResourceWithCircular(this,binding.loaderImage,R.drawable.d_loader)
//                    progresDialog.show()
//                    binding.flprogressBar.visibility = View.VISIBLE
                }
                false->{
                    if (progressLayout != null && isDialogFragShowing(DtdConstants.SHOW_PROGRESS_DIALOG.toString())) {
                        progressLayout.dismiss()
                    }

//                    binding.frameLayoutLoader.visibility = View.GONE

//                    progresDialog.dismiss()
//                    binding.flprogressBar.visibility = View.GONE
                }
                else->{

                    if (progressLayout != null && isDialogFragShowing(DtdConstants.SHOW_PROGRESS_DIALOG.toString())) {
                        progressLayout.dismiss()
                    }

//                    binding.frameLayoutLoader.visibility = View.GONE

//                    progresDialog.dismiss()
//                    binding.flprogressBar.visibility = View.GONE
                }
            }
        })
    }

    var alertObserver = Observer<Message> {
        if (it.obj != null) {
            var message = it.obj as String

            if (!isDialogFragShowing(DtdConstants.SHOW_SNACKBAR_DIALOG.toString())) {
                var bundle = Bundle()
                bundle.putString(DtdPrefsKeys.Keys.SNACKBAR_MESSAGE, message)
                bundle.putString(DtdPrefsKeys.Keys.SNACKBAR_BUTTON, "Retry")

                snackbarFragment.arguments = bundle
                snackbarFragment.setStyle(
                    DialogFragment.STYLE_NORMAL,
                    R.style.dialog_snackbar_style
                )
                snackbarFragment.show(
                    supportFragmentManager,
                    DtdConstants.SHOW_SNACKBAR_DIALOG.toString()
                )
            } else {
                snackbarFragment.updateMessage(message, "Retry")
            }
        }

/*
            var dialog = alertUtils.showInformationAlert(
                this,
                resources.getString(R.string.alert),
                message,
                resources.getString(R.string.ok),object : InformationAlertCallbacks{
                    override fun eventOkOfInformationAlert() {
                        when(it.arg1){
                            DtdConstants.BAR_FILTER_FLAG->{
                                layoutBS.etSearch.text?.clear()
                                layoutBS.etSearch.requestFocus()
                            }
                        }
                    }
                })*/

//            if(!dialog.isShowing) dialog.show()

    }



    fun handleAlerts(){
        viewModel.getAlertLiveData().observe(this,alertObserver)
    }

    fun handleMessages(msg: Message) {
        var obj = msg.obj
        when (msg.arg1) {

            DtdConstants.SHOW_APP_TOUR->{
                var isFirstTime = prefs.getBoolean(DtdPrefsKeys.Keys.IS_FIRST_TIME)
                if (!isFirstTime){
                    var dialogFrag = AppTourDialog()
                    dialogFrag.setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_fragment_style)
                    dialogFrag.show(supportFragmentManager, DtdConstants.SHOW_APP_TOUR.toString())
                }
                prefs.saveBoolean(DtdPrefsKeys.Keys.IS_FIRST_TIME,true)
            }

            DtdConstants.SHOW_IMAGES_PAGER->{
                var dialogFrag = ImagesSliderFragment()
                var bundle = Bundle()
                bundle.putInt(DtdConstants.POSITION,msg.arg2)
                bundle.putStringArrayList(DtdConstants.LIST_DATA,obj as java.util.ArrayList<String>)
                dialogFrag.arguments = bundle
                dialogFrag.setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_fragment_style_full)
                dialogFrag.show(supportFragmentManager, DtdConstants.SHOW_IMAGES_PAGER.toString())
            }

            DtdConstants.SHOW_REGISTER_FORM -> {
                attachObserverStateAndProvinces()
                viewModel.getStatesAndProvinces()
            }

            DtdConstants.SHOW_LOGIN_FORM -> {
                var dialogFrag = LoginDialog()
                dialogFrag.setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_fragment_style)
                dialogFrag.show(supportFragmentManager, DtdConstants.SHOW_LOGIN_FORM.toString())
            }

            DtdConstants.SHOW_LOGIN_POP_UP->{
                if (msg.arg2 != DtdConstants.FROM_LOGIN){
                    var dialogFrag = RegistrationProfileCompletion()
                    dialogFrag.setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_fragment_style)
                    dialogFrag.show(supportFragmentManager, DtdConstants.SHOW_LOGIN_POP_UP.toString())
                }else{
                    viewInLoggedPopUp()
                }

//                 clickEvent.onClickrbBars(rbBars)
            }


            DtdConstants.SHOW_PROFILE->{
                var dialogFrag = ProfileDialog()
                var bundle = Bundle()
                if (msg.arg2 > 0){
                    var filterModel = msg.obj
                    filterModel?.let {
                        if (it is SearchFilterModel)
                            bundle.putParcelable(DtdPrefsKeys.Keys.SEARCH_FILTER_MODEL,it)
                    }

                    bundle.putParcelable(DtdPrefsKeys.Keys.SEARCH_FILTER_MODEL,obj as SearchFilterModel)
                    bundle.putInt(DtdPrefsKeys.Keys.USER_ID,msg.arg2)
                    bundle.putString(DtdPrefsKeys.Keys.PROFILE_FROM,DtdPrefsKeys.Values.PROFILE_FROM_OTHER)

                    dialogFrag.setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_fragment_style)
                    viewModel.liveDataGetProfile().removeObservers(this)
                    attachObserverForProfile(dialogFrag,bundle)
                    var id:Long = msg.arg2?.toLong()?:0
                    if (id > 0)
                        viewModel.getProfile(id)
                    else{
                        var snacMss = Message()
                        snacMss.arg1 = DtdConstants.SHOW_SNACKBAR
                        snacMss.obj = getString(R.string.incorrect_id_0)
                        handleMessages(snacMss)
                    }
                }else{
//                     bundle.putInt(DtdPrefsKeys.Keys.USER_ID,pref.getID)
                    bundle.putString(DtdPrefsKeys.Keys.PROFILE_FROM,DtdPrefsKeys.Values.PROFILE_FROM_LOGIN_MENU)

                    dialogFrag.arguments = bundle
                    dialogFrag.setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_fragment_style)
                    dialogFrag.show(supportFragmentManager,DtdConstants.SHOW_PROFILE.toString())
                }
            }

            DtdConstants.SHOW_EDIT_PROFILE->{

                var isHitNoUI = msg.arg2 > 0
                if(!isHitNoUI)
                    hitEditProfileApi(obj,EditProfileDialog(),isHitNoUI)
                else
                    viewModel.repository.getEditProfile(true)
            }

            DtdConstants.SHOW_FOLLOW->{
                var follower = prefs.getInt(DtdConstants.FOLLOW_BY)
                var following = prefs.getInt(DtdConstants.FOLLOW_TO)

                if (follower > 0 || following > 0) {

                    var dialogFrag = FollowersFollowingDialog()
                    dialogFrag.setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_fragment_style)
                    dialogFrag.show(supportFragmentManager,DtdConstants.SHOW_FOLLOW.toString())

                }else{
                    var snackMess = Message()
                    snackMess.arg1 = DtdConstants.SHOW_SNACKBAR
                    snackMess.obj = getString(R.string.neither_nor_follower_following)
                    handleMessages(snackMess)
                }
            }

            DtdConstants.SHOW_FEEDBACK->{
                var dialogFrag = FeedbackDialog()
                dialogFrag.setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_fragment_style)
                dialogFrag.show(supportFragmentManager,DtdConstants.SHOW_FEEDBACK.toString())
            }

            DtdConstants.HANDLE_FOR_SEARCH_POPUP->{
//                 var filterSearch = obj as SearchFilterModel
//                 when(filterSearch.from){
//                        DtdConstants.SEL_OPTION_BARS->{
//                            var markerModel = MarkerModel(MarkerOptions(),false,filterSearch.id,filterSearch.distanceDouble,
//                                0,"","","","",0,
//                                mutableListOf())
//                            hitDetailWebService(markerModel)
//                            layoutBS.rvStores.visibility = View.GONE
////                            layoutBS.etSearch.text?.clear()
//                            ApplicationUtils().hideKeyboard(this,layoutBS.etSearch)
//
//                        }
//
//                        DtdConstants.SEL_OPTION_STORES->{
//                            var markerModel = MarkerModel(MarkerOptions(),
//                                false,filterSearch.id,filterSearch.distanceDouble,
//                                0,"","","","",0, mutableListOf())
//                            hitStoreDetail(markerModel)
//                            layoutBS.rvStores.visibility = View.GONE
//
////                            layoutBS.etSearch.text?.clear()
//                            ApplicationUtils().hideKeyboard(this,layoutBS.etSearch)
//                        }
//
//                        DtdConstants.SEL_OPTION_PEOPLE->{
////                            var bundle = Bundle()
////                            bundle.putString(DtdPrefsKeys.Keys.PROFILE_FROM,DtdPrefsKeys.Values.PROFILE_FROM_PEOPLE)
////                            bundle.putInt(DtdPrefsKeys.Keys.USER_ID,filterSearch.id)
////
////                            var dialogFrag = ProfileDialog()
////                            dialogFrag.arguments = bundle
////                            dialogFrag.setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_fragment_style)
////                            dialogFrag.show(supportFragmentManager,DtdConstants.SHOW_PROFILE.toString())
//                        }
//                 }
            }

            DtdConstants.SHOW_MESSAGE_ALL_USERS->{
                viewModel.getAllUserChat(prefs.getInt(DtdConstants.ID))
            }

            DtdConstants.SHOW_SEARCH_PEOPLE->{

                var msg = Message()
                msg.arg1 = DtdConstants.SHOW_CHAT_HISTORY
                msg.arg2 = 1

                var dataObj = com.mjdistillers.drinkthedrink.model.response.chat_all_user.Datum()
                dataObj.fromId = 0
                dataObj.toId = 0

                msg.obj = dataObj

                handleMessages(msg)

            }

            DtdConstants.SHOW_CHAT_HISTORY->{

                var bundle = Bundle()

                if (msg.arg2 == 3){
                    var userid = msg.what
                    if (userid > 0){
                        var searchFilterModel = obj
                        searchFilterModel?.let {
                            if (it is SearchFilterModel)
                                bundle.putParcelable(DtdPrefsKeys.Keys.SEARCH_FILTER_MODEL,it)
                        }
                    }else{
                        var message = Message()
                        message.arg1 = DtdConstants.SHOW_SNACKBAR
                        message.arg2 = Snackbar.LENGTH_LONG
                        message.obj = getString(R.string.unable_to_get_uid)
                        handleMessages(message)
                    }

                    var dialogFrag = ChatHistoryDialog()
                    dialogFrag.arguments = bundle
                    dialogFrag.setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_fragment_style)
                    dialogFrag.show(supportFragmentManager,DtdConstants.SHOW_CHAT_HISTORY.toString())

                }else{
                    var datum = obj as com.mjdistillers.drinkthedrink.model.response.chat_all_user.Datum
                    var isFromNewUser = msg.arg2 == 1

                    bundle.putParcelable(DtdPrefsKeys.Keys.USER_CHAT_OBJECT,datum)
                    bundle.putBoolean(DtdPrefsKeys.Keys.IS_FROM_USER,isFromNewUser)

                    var fromId = 0
                    var followId = 0

                    if (datum.fromId == prefs.getInt(DtdConstants.ID)) {
                        fromId = datum.fromId
                        followId = datum.toId
                    } else {
                        fromId = datum.toId
                        followId = datum.fromId
                    }

                    if (!isFromNewUser){
                        viewModel.getChatHistory(fromId, followId)
                        attachObserverMessageHistory(bundle)
                    }else{
                        var dialogFrag = ChatHistoryDialog()
                        dialogFrag.arguments = bundle
                        dialogFrag.setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_fragment_style)
                        dialogFrag.show(supportFragmentManager,DtdConstants.SHOW_CHAT_HISTORY.toString())
                    }
                }
            }

            DtdConstants.FOLLOW_UNFOLOW_HANDLING->{

                if (!::followUnFollowClicks.isInitialized){
                    followUnFollowClicks = FollowUnFollowClickHandling()
                }

                followUnFollowClicks.handleClick(msg, msg.obj as SearchFilterModel)

//                 if (!::followUnFollowClicks.isInitialized){
//                    followUnFollowClicks = FollowUnFollowClickHandling()
//                 }
            }

            DtdConstants.LOGOUT_CLICKED->{
                if(::filterPopUp.isInitialized){
                    filterPopUp.dismissIfShowing()
                }

                AlertUtils().showOkCancelAlert(this,
                    "Logout",
                    "Are you sure you want to log out?",
                    "YES","NO",
                    object : OkCancelAlertCallbacks{
                        override fun eventOkOfOkCancleAlert() {
                            viewModel.getLogout(prefs.getInt(DtdConstants.ID),prefs.getDeviceToken())
                        }

                        override fun eventCancelOfOkCancelAlert() {
                        }

                    })
            }

            DtdConstants.NOTIFICATION_CLICKED->{
                var notificationType = obj as String
                when(notificationType){
                    DtdPrefsKeys.Values.NOTIFICATION_TYPE_MESSAGE->{
                        viewModel.getAllUserChat(prefs.getInt(DtdConstants.ID))
                    }

                    DtdPrefsKeys.Values.NOTIFICATION_TYPE_REQUEST->{
                        viewModel.getAllNotifications(prefs.getInt(DtdConstants.ID))
                    }

                    DtdPrefsKeys.Values.NOTIFICATION_TYPE_ACCEPT->{

                    }
                }
            }

            DtdConstants.SHOW_EXTERNAL_BROWSER->{
                openInBrowser(obj as String)
            }

            DtdConstants.SHOW_WEBVIEW_DIALOG->{
                var dialogWebView = WebViewDialog()
                var bundle = Bundle()

                if (msg.arg2 == APIs.P_ONE) {
                    bundle.putString(
                        DtdPrefsKeys.Keys.WEB_VIEW_FROM,
                        App.app.getString(R.string.terms_and_conditions))

                    bundle.putString(DtdPrefsKeys.Keys.WEB_VIEW_URL,DtdConstants.TERMS_AND_CONDITIONS_URL)
                }
                else {
                    bundle.putString(
                        DtdPrefsKeys.Keys.WEB_VIEW_FROM,
                        App.app.getString(R.string.privacy_policy))

                    bundle.putString(DtdPrefsKeys.Keys.WEB_VIEW_URL,DtdConstants.PRIVACY_POLICY_URL)
                }

                dialogWebView.arguments = bundle
                dialogWebView.setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_fragment_style)
                dialogWebView.show(supportFragmentManager,DtdConstants.SHOW_WEBVIEW_DIALOG.toString())
            }

            DtdConstants.SHOW_INVITE_PEOPLE->{

//                 var dialogFrag = RegistrationProfileCompletion()
//                 dialogFrag.setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_fragment_style)
//                 dialogFrag.show(supportFragmentManager, DtdConstants.SHOW_LOGIN_POP_UP.toString())

                var choiceListModel = mutableListOf<ChoiceListModel>()
                var choice1 = ChoiceListModel()
                choice1.text = App.app.getString(R.string.email)

                var choice2 = ChoiceListModel()
                choice2.text = App.app.getString(R.string.text_message)

                choiceListModel.add(choice1)
                choiceListModel.add(choice2)

                AlertUtils().showChooseOneFromListAlertNoActions(this, App.app.getString(R.string.invite_through),
                    object:OneChoiceNoActionsCallbacks{
                        override fun eventListItemSelected(position: Int) {
                            when(position){
                                0->{
                                    var dialogFrag = InvitePeopleDialog()
                                    dialogFrag.setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_fragment_style)
                                    dialogFrag.show(supportFragmentManager,DtdConstants.SHOW_INVITE_PEOPLE.toString())
                                }
                                1->{
                                    shareText(App.app.getString(R.string.invite_text),IntConstants.APP_FLAG_ANY)
                                }
                            }
                        }
                    },choiceListModel)
            }

            DtdConstants.SHOW_FORGOT_PASSWORD->{
                var dialogFrag = ForgotPasswordDialog()
                dialogFrag.setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_fragment_style)
                dialogFrag.show(supportFragmentManager,DtdConstants.SHOW_FORGOT_PASSWORD.toString())
            }

            DtdConstants.SHOW_SNACKBAR->{
                viewModel.getAlertLiveData().postValue(msg)
//
//                 var duration = if (msg.arg2 == Snackbar.LENGTH_LONG) Snackbar.LENGTH_LONG
//                 else Snackbar.LENGTH_SHORT
//
//                 obj?.let {
//                     if (obj is String) {
//                         ToastSnackUtils.snackBar(binding.root,obj,duration,false)
//                     }
//                 }
            }

            DtdConstants.SHOW_UPDATE_PASSWORD->{
                var bundle = Bundle()

                if (msg.arg2 > 0) {
                    bundle.putInt(DtdConstants.ID, msg.arg2)
                }

                var dialogFrag = UpdatePasswordDialog()
                dialogFrag.arguments = bundle
                dialogFrag.setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_fragment_style)
                dialogFrag.show(supportFragmentManager,DtdConstants.SHOW_UPDATE_PASSWORD.toString())
            }


            DtdConstants.SHOW_CONTACT->{
            }

            DtdConstants.SHOW_PICK_LOCATION->{
                startAutocompleteActivity()
            }

            DtdConstants.SHOW_ALL_NOTIFICATIONS->{
                viewModel.getAllNotifications(prefs.getInt(DtdConstants.ID))
            }

            DtdConstants.HIT_FOR_UPDATE_USER_PROFILE->{

                viewModel.postUpdateUser(obj as UpdateRoleRequest)
            }

            DtdConstants.FINISH_APP->{
                finishAffinity()
            }
        }
    }

    override fun permissionGranted(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {

        when(requestCode){
            DtdConstants.LOCATION_REQUEST_CODE->{
                if(grantResults.contains(1)) {
                    var currentPlaceResponse = placesClient.findCurrentPlace(
                        FindCurrentPlaceRequest.newInstance(
                            listOf(
                                Place.Field.LAT_LNG,
                                Place.Field.ADDRESS,
                                Place.Field.NAME)))

                    currentPlaceResponse.addOnCompleteListener {
                        if(it.isSuccessful) {
                            var resultPlace = it.result
                            resultPlace?.let {
                                var latlng = it.placeLikelihoods[0].place.latLng
                                var name = it.placeLikelihoods[0].place.name

                                prefs.saveString(
                                    DtdPrefsKeys.Keys.LONGITUDE,
                                    latlng?.longitude.toString())

                                prefs.saveString(
                                    DtdPrefsKeys.Keys.LATITUDE,
                                    latlng?.latitude.toString())


                                prefs.saveString(DtdPrefsKeys.Keys.PICKED_LOCATION,name)
                                layoutBS.autoCompleteTextView.text = name

                            }
                        }else{

                            var message = Message()
                            message.obj = getString(R.string.unable_fetching_location)
                            viewModel.getAlertLiveData().postValue(message)

//                            ToastSnackUtils.toastShort(MainActivity@this,getString(R.string.unable_fetching_location))
                            clickEvent.onClickrbBars(layoutBS.rbBars)
                            layoutBS.autoCompleteTextView.text = DtdConstants.DEFAULT_LOCATAION
                        }

                        setUpMap(googleMap)
                        viewModel.getProgressBar().postValue(true)

//                        binding.frameLayoutLoader.visibility = View.VISIBLE
//                        progresDialog.show()
                        clickEvent.onClickrbBars(layoutBS.rbBars)


                    }
                }else{
                    setUpMap(googleMap)
                    viewModel.getProgressBar().postValue(true)
                    var location = prefs.getString(DtdPrefsKeys.Keys.PICKED_LOCATION)
                    if (location.isNotEmpty()){
                        layoutBS.autoCompleteTextView.text = location
                    }else{
                        prefs.saveString(DtdPrefsKeys.Keys.PICKED_LOCATION,DtdConstants.DEFAULT_LOCATAION)
                        layoutBS.autoCompleteTextView.text = DtdConstants.DEFAULT_LOCATAION
                    }
//                    binding.frameLayoutLoader.visibility = View.VISIBLE
//                        progresDialog.show()
                    clickEvent.onClickrbBars(layoutBS.rbBars)
                }
            }
        }
    }

    private fun openNotificationPopUpWhenFromNotification(){
        var msg = Message()
        msg.arg1 = DtdConstants.NOTIFICATION_CLICKED
        msg.obj = notificationType
        handleMessages(msg)
    }

    inner class FollowUnFollowClickHandling{

//        lateinit var message: Message
//        lateinit var searchModel: SearchFilterModel

        fun handleClick(message: Message, searchModel: SearchFilterModel){
            if (searchModel.isFromBlock == 0) {
                when (searchModel.followBtnText) {
                    App.app.getString(R.string.follow) -> {
                        attachObserverFollowUnfollowPeople(message, searchModel)
                        viewModel.postFUPeople(
                            1,
                            prefs.getInt(DtdConstants.ID),
                            searchModel.id,
                            searchModel.deviceToken,
                            prefs.getDeviceToken()
                        )
                    }

                    App.app.getString(R.string.unfollow) -> {
                        attachObserverFollowUnfollowPeople(message, searchModel)
                        viewModel.postFUPeople(
                            0,
                            prefs.getInt(DtdConstants.ID),
                            searchModel.id,
                            searchModel.deviceToken,
                            prefs.getDeviceToken()
                        )
                    }
                }
            }else{
                when (searchModel.blockBtnText) {
                    App.app.getString(R.string.block) -> {
                        attachObserverBlockUnblock(message, searchModel)
                        viewModel.postBlockHit(prefs.getInt(DtdConstants.ID),1,searchModel.user_id)
                    }

                    App.app.getString(R.string.unblock) -> {
                        attachObserverBlockUnblock(message, searchModel)
                        viewModel.postBlockHit(prefs.getInt(DtdConstants.ID),0,searchModel.user_id)
                    }
                }
            }

            /*           when {
                           searchModel.requested->{
                               var messageReply = Message()
                               messageReply.arg1 = DtdConstants.FOLLOW_REQUESTED
                               messageReply.arg2 = searchModel.position
                               messageReply.obj = App.app.getString(R.string.requested)
                               message.replyTo.send(messageReply)
                           }

                           searchModel.publicUser->{  // Follow
                               attachObserverRequestNotification(message,searchModel)
                               viewModel.getRequestPushNotification(prefs.getInt(DtdConstants.ID),
                                   prefs.getString(DtdConstants.NAME),
                                   searchModel.id)
                           }

                           else->{
                               if (searchModel.followBy != 0 ||
                                   searchModel.followTo != 0 ||
                                   searchModel.initiateId != 0){   // Unfollow

                                   if (dtdUtils.isCurrUserId(searchModel.initiateId)){  // Un follow
                                       attachObserverFollowUnFollowTo(message,searchModel)
                                       viewModel.getFollowUnfollowTo(
                                           prefs.getInt(DtdConstants.ID),
                                           searchModel.id,
                                           APIs.P_ZERO,
                                           searchModel.deviceToken)

                                   }else if(searchModel.followBy != 0 && searchModel.followTo != 0){ // Unfollow
                                       attachObserverFollowUnFollowBack(message,searchModel)
                                       viewModel.getFollowUnfollowBack(
                                           searchModel.id,
                                           prefs.getInt(DtdConstants.ID),
                                           APIs.P_ZERO,
                                           searchModel.deviceToken)
                                   }else if(dtdUtils.isCurrUserId(searchModel.followTo)){
                                           if(dtdUtils.isCurrUserId(searchModel.followBy)){ // UnFollow
                                               attachObserverFollowUnFollowBack(message,searchModel)
                                               viewModel.getFollowUnfollowBack(
                                                   searchModel.id,
                                                   prefs.getInt(DtdConstants.ID),
                                                   APIs.P_ZERO,
                                                   searchModel.deviceToken)
                                           }else{ // FollowBack
                                               attachObserverRequestNotification(message,searchModel)
                                                  viewModel.getRequestPushNotification(prefs.getInt(DtdConstants.ID),
                                                   prefs.getString(DtdConstants.NAME),
                                                   searchModel.id)
                                           }
                                   }else if(dtdUtils.isCurrUserId(searchModel.followBy)){
                                       if(dtdUtils.isCurrUserId(searchModel.followTo)){ // UnFollow
                                           attachObserverFollowUnFollowTo(message,searchModel)
                                           viewModel.getFollowUnfollowTo(
                                               prefs.getInt(DtdConstants.ID),
                                               searchModel.id,
                                               APIs.P_ZERO,
                                               searchModel.deviceToken)
                                       }else{ // FollowBack
                                           attachObserverRequestNotification(message,searchModel)
                                           viewModel.getRequestPushNotification(prefs.getInt(DtdConstants.ID),
                                               prefs.getString(DtdConstants.NAME),
                                               searchModel.id)
                                       }
                                   }
                               }
                           }
                       }*/
        }


        private fun attachObserverFollowUnfollowPeople(message: Message, searchModel: SearchFilterModel){
            viewModel.liveDataFUPeople().removeObservers(this@MainActivity)
            var messageToReply = message
            var locaMessage = Message()
            locaMessage.arg1 = message.arg1
            locaMessage.replyTo = message.replyTo

            var fupeopleobserver = Observer<FUBarStoreTeamResponse> {
                var messageSnack = Message()

                var messageStr = "";

                it.data?.message?.let {
                    if(it.size > 0) {
                        messageSnack.obj = it[0]
                        messageStr = it[0]
                        viewModel.getAlertLiveData().postValue(messageSnack)

                        var messageReply = Message()
                        messageReply.arg1 = DtdConstants.FOLLOW_REQUEST_NOTIFICATION
                        messageReply.obj = messageStr
                        messageReply.arg2 = searchModel.position

                        locaMessage.replyTo.send(messageReply)
                    }
                }
            }
            viewModel.liveDataFUPeople().observe(this@MainActivity,fupeopleobserver)
        }

        private fun attachObserverBlockUnblock(message: Message, searchModel: SearchFilterModel){
            viewModel.liveDataBlock().removeObservers(this@MainActivity)

            var messageToReply = message
            var locaMessage = Message()
            locaMessage.arg1 = message.arg1
            locaMessage.replyTo = message.replyTo

            var observerBlock = Observer<GetBlockUserResp> {
                var messageSnack = Message()
                var messageStr = "";

                it.data.message?.let {
                    if (it.isNotEmpty()){
                        var msg = it[0]
                        var messageSnack = Message()
                        messageSnack.obj = msg
                        viewModel.getAlertLiveData().postValue(messageSnack)

                        var messageReply = Message()
                        messageReply.arg1 = DtdConstants.FOLLOW_REQUEST_NOTIFICATION
                        messageReply.obj = App.app.getString(R.string.requested)
                        messageReply.arg2 = searchModel.position
                        locaMessage.replyTo.send(messageReply)
                    }
                }
            }

            viewModel.liveDataBlock().observe(this@MainActivity,observerBlock)

        }

        private fun attachObserverRequestNotification(message: Message, searchModel: SearchFilterModel){
            viewModel.liveDataRequestPushNotification().removeObservers(this@MainActivity)

            var observerRequestNotification = Observer<GetPushNotificationResponse> {
//                if (::filterPopUp.isInitialized && ::searchModel.isInitialized && ::message.isInitialized){
//                    ToastSnackUtils.snackBar(filterPopUp.filterView,
//                        getString(R.string.request_sent_to)+searchModel.name,
//                        Snackbar.LENGTH_LONG,false)

                var messageSnack = Message()
                messageSnack.obj = getString(R.string.request_sent_to)+searchModel.name
                viewModel.getAlertLiveData().postValue(messageSnack)


                var messageReply = Message()
                messageReply.arg1 = DtdConstants.FOLLOW_REQUEST_NOTIFICATION
                messageReply.obj = App.app.getString(R.string.requested)
                messageReply.arg2 = searchModel.position
                message.replyTo.send(messageReply)
//                }else{
//                    ToastSnackUtils.snackBar(filterPopUp.filterView,getString(R.string.request_not_sent),
//                        Snackbar.LENGTH_LONG,true)
//                }
            }

            viewModel.liveDataRequestPushNotification().observe(this@MainActivity,observerRequestNotification)
        }

        private fun attachObserverFollowUnFollowTo(message: Message, searchModel: SearchFilterModel){
            viewModel.liveDataFollowUnFollowTo().removeObservers(this@MainActivity)

            var observerFollowUnfollowTo = Observer<GetFollowUnfollowTO_BackResponse_Decline_Logout> {
//                if (::filterPopUp.isInitialized && ::searchModel.isInitialized && ::message.isInitialized) {

                var messageString = App.app.getString(R.string.problem_occured)

                it?.data?.message?.let {
                    messageString = it[0]
                }

//                    ToastSnackUtils.snackBar(filterPopUp.filterView,
//                        messageString,Snackbar.LENGTH_LONG,false)

                var messageSnack = Message()
                messageSnack.obj = getString(R.string.request_sent_to)+searchModel.name
                viewModel.getAlertLiveData().postValue(messageSnack)


                var messageReply = Message()
                messageReply.arg1 = DtdConstants.FOLLOW_UNFOLLOW_TO
                messageReply.arg2 = searchModel.position

                if(it.requestedFor == APIs.P_ONE){
                    messageReply.obj = App.app.getString(R.string.unfollow)
                }else{
                    messageReply.obj = App.app.getString(R.string.follow)
                }

                message.replyTo.send(messageReply)
//                }
            }

            viewModel.liveDataFollowUnFollowTo().observe(this@MainActivity,observerFollowUnfollowTo)
        }

        private fun attachObserverFollowUnFollowBack(message: Message, searchModel: SearchFilterModel){
            viewModel.liveDataFollowUnFollowBack().removeObservers(this@MainActivity)


            var observerFollowUnFollowBack = Observer<GetFollowUnfollowTO_BackResponse_Decline_Logout>{

//                if (::filterPopUp.isInitialized && ::searchModel.isInitialized && ::message.isInitialized) {

                var messageString = App.app.getString(R.string.problem_occured)

                it?.data?.message?.let {
                    messageString = it[0]
                }

//                    ToastSnackUtils.snackBar(filterPopUp.filterView,
//                        messageString,Snackbar.LENGTH_LONG,false)

                var messageSnack = Message()
                messageSnack.obj = getString(R.string.request_sent_to)+searchModel.name
                viewModel.getAlertLiveData().postValue(messageSnack)


                var messageReply = Message()
                messageReply.arg1 = DtdConstants.FOLLOW_UNFOLLOW_BACK

                if (it.requestedFor == APIs.P_ONE)
                    messageReply.obj = App.app.getString(R.string.unfollow_back)
                else
                    messageReply.obj = App.app.getString(R.string.follow_back)

                messageReply.arg2 = searchModel.position

                message.replyTo.send(messageReply)
            }
//            }

            viewModel.liveDataFollowUnFollowBack().observe(this@MainActivity,observerFollowUnFollowBack)
        }

        private fun attachObserverDeclineRequest(message: Message, searchModel: SearchFilterModel){
            viewModel.liveDataDeclineRequest().removeObservers(this@MainActivity)

            var observerDeclineRequest= Observer<GetFollowUnfollowTO_BackResponse_Decline_Logout>{
            }
            viewModel.liveDataDeclineRequest().observe(this@MainActivity,observerDeclineRequest)
        }
    }

    private fun startAutocompleteActivity() {
        val autocompleteIntent: Intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
            listOf(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG))
            .build(this)
        startActivityForResult(
            autocompleteIntent,DtdConstants.AUTOCOMPLETE_REQUEST_CODE)
    }


}


