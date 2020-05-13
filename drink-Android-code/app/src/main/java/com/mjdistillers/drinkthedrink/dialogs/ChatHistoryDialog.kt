package com.mjdistillers.drinkthedrink.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mjdistillers.drinkthedrink.*
import com.mjdistillers.drinkthedrink.adapters.ChatWindowAdapter
import com.mjdistillers.drinkthedrink.model.SearchFilterModel
import com.mjdistillers.drinkthedrink.model.request.SendMessageRequest
import com.mjdistillers.drinkthedrink.model.response.user_chat_history.GetUserChatHistoryResponse
import com.mjdistillers.drinkthedrink.model.response.chat_all_user.Datum
import com.mjdistillers.drinkthedrink.utilities.*
import com.mjdistillers.drinkthedrink.utilities.interfaces.OneChoiceNoActionsCallbacks
import com.mjdistillers.drinkthedrink.utilities.interfaces.PermissionUtilsCallbacks
import com.mjdistillers.drinkthedrink.utilities.interfaces.PickImageCallbacks
import com.mjdistillers.drinkthedrink.utilities.models.ChoiceListModel
import kotlinx.android.synthetic.main.layout_chat_window.view.*
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import javax.inject.Inject

class ChatHistoryDialog:DialogFragment(), View.OnClickListener, PickImageCallbacks, PermissionUtilsCallbacks{

    val PICK_IMAGE_FROM_CAMERA = 0
    val PICK_IMAGE_FROM_GALLARY = 1

    var isShareImage = false
    lateinit var imageFile:File

    @Inject
    lateinit var pref: SharedPrefsUtils

    lateinit var objKeys:Datum

    lateinit var userChatHistoryResponse: GetUserChatHistoryResponse

    @Inject
    lateinit var dtdUtils:DtdUtils

    @Inject
    lateinit var applicationUtils:ApplicationUtils

    lateinit var viewchat:View

    var toIDForNewUser = 0

    var liveDataFilterModel = MutableLiveData<SearchFilterModel>()

    private lateinit var filterPopUp:FilterSearchPopUp

    var isFromUser  = false

    var searchFilterModel:SearchFilterModel? = null

    override fun onStart() {
        super.onStart()

        var dialogFrag = dialog
        dialogFrag?.let {
            it.window?.setWindowAnimations(R.style.dialog_fragment_style)
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dialogChatHistory = super.onCreateDialog(savedInstanceState)
        dialogChatHistory.window?.requestFeature(Window.FEATURE_NO_TITLE)

        // removing previously attached observers
        (activity as MainActivity).viewModel.liveDataChatHistory().removeObservers(this)

        App.app.getComponent().inject(this)

        viewchat  = LayoutInflater.from(activity as MainActivity)
            .inflate(R.layout.layout_chat_window,null,false)

        viewchat.ivClose.setOnClickListener(this)
        viewchat.ivSend.setOnClickListener(this)
        viewchat.ivBack.setOnClickListener(this)
        viewchat.ivSearchReceiver.setOnClickListener(this)
        viewchat.ivCapture.setOnClickListener(this)

        var bundle = arguments

        bundle?.let {

            if (bundle.containsKey(DtdPrefsKeys.Keys.SEARCH_FILTER_MODEL)){
                    searchFilterModel = bundle.getParcelable<SearchFilterModel>(DtdPrefsKeys.Keys.SEARCH_FILTER_MODEL)
            }else {
                if (bundle.containsKey(DtdPrefsKeys.Keys.USER_CHAT_OBJECT)) {
                    var objDatum = it.getParcelable<Datum>(DtdPrefsKeys.Keys.USER_CHAT_OBJECT)
                        objDatum?.let { it ->
                            objKeys = it
                        }
                }

                if (bundle.containsKey(DtdPrefsKeys.Keys.IS_FROM_USER)) {
                    isFromUser = it.getBoolean(DtdPrefsKeys.Keys.IS_FROM_USER)
                }else{
                    isFromUser = false
                }

                if (bundle.containsKey(DtdPrefsKeys.Keys.RESPONSE_MESSAGES_HISTORY)){
                    var chathistory = it.getParcelable<GetUserChatHistoryResponse>(DtdPrefsKeys.Keys.RESPONSE_MESSAGES_HISTORY)
                    chathistory?.let {
                        userChatHistoryResponse = chathistory
                    }
                }
            }
        }

        if (isFromUser){
            viewchat.groupSearchPeople.visibility = View.VISIBLE
            viewchat.groupTopHeading.visibility = View.GONE
            viewchat.ivUserImage.visibility = View.GONE
        }else{
            viewchat.groupSearchPeople.visibility = View.GONE
            viewchat.groupTopHeading.visibility = View.VISIBLE
            viewchat.ivUserImage.visibility = View.VISIBLE
        }

        if (::objKeys.isInitialized) {
            objKeys?.let {
                var fromProfileImage =
                    DtdConstants.IMAGE_BASE_URL + DtdConstants.PROFILE_FOLDER_NAME + it.fromProfileImage

                attachObserverMessageHistory(viewchat, fromProfileImage)
                attachObserverSendMessage()

                var fromId = 0
                var followId = 0

                if (it.fromId == pref.getInt(DtdConstants.ID)) {
                    fromId = it.fromId
                    followId = it.toId
                } else {
                    fromId = it.toId
                    followId = it.fromId
                }

                dtdUtils.loadImageWithCircleTransform(
                    activity as MainActivity,
                    viewchat.ivUserImage,
                    fromProfileImage, DtdConstants.PLACEHOLDER_NO_IMAGE
                )

                viewchat.tvUserName.text = it.fromName

                if (!isFromUser){
                    // (activity as MainActivity).viewModel.getChatHistory(fromId, followId)
                    setUpRecyclerView(viewchat, userChatHistoryResponse, fromProfileImage)
                }
            }
        }

        viewchat.etSearchReceiver.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                filterPopUp = FilterSearchPopUp(activity as MainActivity,(activity as MainActivity)
                    .handler,DtdConstants.SEL_OPTION_PEOPLE,liveDataFilterModel)
                if (!filterPopUp.popUp.isShowing){
                    filterPopUp.popUp.showAsDropDown(viewchat.etSearchReceiver)
                }
            }
        }


        attachObserverForSearchFilter()
        attachObserverForFilterModel(viewchat)

        dialogChatHistory.setContentView(viewchat)


        if(searchFilterModel != null){
            searchFilterModel?.let {
                liveDataFilterModel.postValue(searchFilterModel)
            }

            attachObserverMessageHistory(viewchat, searchFilterModel?.imageUri?:"")
            attachObserverSendMessage()
        }

        applyFonts(viewchat)

        // Hinding send options if to user is Admin, Bcz any user cann't send message to admin

        if(searchFilterModel?.id == 1){
            viewchat.ivCapture.visibility = View.GONE
            viewchat.ivSend.visibility = View.GONE
            viewchat.etTextMessage.visibility = View.GONE
        }else{
            viewchat.ivCapture.visibility = View.VISIBLE
            viewchat.ivSend.visibility = View.VISIBLE
            viewchat.etTextMessage.visibility = View.VISIBLE
        }

        if (::objKeys.isInitialized)
        if (objKeys.toId == 1){
            viewchat.ivCapture.visibility = View.GONE
            viewchat.ivSend.visibility = View.GONE
            viewchat.etTextMessage.visibility = View.GONE
        }else{
            viewchat.ivCapture.visibility = View.VISIBLE
            viewchat.ivSend.visibility = View.VISIBLE
            viewchat.etTextMessage.visibility = View.VISIBLE
        }


        return dialogChatHistory
    }

    private fun applyFonts(view: View) {
        view.tvUserName.typeface = dtdUtils.fontNeutraMedium()
        view.etSearchReceiver.typeface = dtdUtils.fontNeutraMedium()
        view.tvDateMessage.typeface = dtdUtils.fontNeutraMedium()
        view.etTextMessage.typeface = dtdUtils.fontNeutraMedium()
    }

    override fun onResume() {
        super.onResume()

        applicationUtils.showSoftKeyboard(activity as MainActivity,viewchat.etSearchReceiver)
    }

    private fun attachObserverForFilterModel(viewModel: View) {
        liveDataFilterModel.observe(activity as MainActivity, Observer {
            toIDForNewUser = it.id

            viewchat.groupTopHeading.visibility = View.VISIBLE
            viewchat.groupSearchPeople.visibility = View.GONE
            viewchat.ivUserImage.visibility = View.VISIBLE

            dtdUtils.loadImageWithCircleTransform(activity as MainActivity,viewchat.ivUserImage,it.imageUri,DtdConstants.PLACEHOLDER_NO_IMAGE)
            viewchat.tvUserName.text = it.name

            (activity as MainActivity).viewModel.getChatHistory(pref.getInt(DtdConstants.ID), it.id)

            applicationUtils.showSoftKeyboard(activity as MainActivity,viewchat.etTextMessage)
        })
    }


    private fun attachObserverForSearchFilter() {

        var observer = androidx.lifecycle.Observer<List<SearchFilterModel>> {
            //            filterPopUp.updateViewForListSize(it.size)
            if (it.isEmpty()){
                var mes = Message()
                mes.arg1 = DtdConstants.BAR_FILTER_FLAG
                mes.obj = App.app.getString(R.string.no_data_found_for_filter,viewchat?.etSearchReceiver?.text.toString())
                (activity as MainActivity).viewModel.getAlertLiveData().postValue(mes)
//                handleMessages(mes)
            }else{
                if (::filterPopUp.isInitialized){
                    if (!filterPopUp.popUp.isShowing) filterPopUp.popUp.showAsDropDown(viewchat?.etSearchReceiver)
                    filterPopUp.adapter.updateList(it) // Using from: Bars here, becacuse Do not want to show following button
                }
            }
        }
        (activity as MainActivity).viewModel.liveDataSearchBars().observe(this,observer)
    }

    private fun attachObserverSendMessage(){
        (activity as MainActivity).viewModel.liveDataSendMessage().observe(activity as MainActivity,
            Observer {

                if(activity != null) {

                    viewchat.etTextMessage.text?.clear()

                    var fromId = 0
                    var followId = 0

                    if(searchFilterModel == null) {
                        if (objKeys.fromId == pref.getInt(DtdConstants.ID)) {
                            fromId = objKeys.fromId
                            followId = objKeys.toId
                        } else {
                            fromId = objKeys.toId
                            followId = objKeys.fromId
                        }

                        if (isFromUser) {
                            fromId = pref.getInt(DtdConstants.ID)
                            followId = toIDForNewUser
                        }
                    }else{
                        fromId = pref.getInt(DtdConstants.ID)
                        followId = searchFilterModel?.id?:0
                    }


                    var message = it?.data?.message?.get(0)


                    message?.let {
                        var messageSnack = Message()
                        messageSnack.obj = message

                        (activity as MainActivity).viewModel.getAlertLiveData().postValue(messageSnack)
//                        ToastSnackUtils.toastLong(activity as MainActivity,message)
                    }

                    (activity as MainActivity).viewModel.getChatHistory(fromId, followId)
                }
            })
    }

    private fun attachObserverMessageHistory(view: View, fromImage: String) {
        var observer = Observer<GetUserChatHistoryResponse> {
                userChatHistoryResponse = it
                setUpRecyclerView(view, it, fromImage)
        }

        (activity as MainActivity).viewModel.liveDataChatHistory().observe(activity as MainActivity,observer)
    }

    private fun setUpRecyclerView(view: View, it: GetUserChatHistoryResponse?,fromImage: String) {
        if (activity != null) {

            val fromFormatDate = SimpleDateFormat(DateTimeUtils.SERVER_FORMAT)
            val toFormatDate = SimpleDateFormat(DateTimeUtils.DateFormats.MonthDY)

            var dataList = it?.data?.data
            var userId = pref.getInt(DtdConstants.ID)
            dataList?.let {
                var linearLayoutManager =  LinearLayoutManager(activity as MainActivity)
                view.rvUserChat.layoutManager = linearLayoutManager
                view.rvUserChat.adapter = ChatWindowAdapter(it, activity as MainActivity, userId, fromImage)

                view.rvUserChat.addOnScrollListener(object : RecyclerView.OnScrollListener(){

                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)

                        if (RecyclerView.SCROLL_STATE_SETTLING == newState) {
                        try{
                            var visibleItem =
                                linearLayoutManager.findFirstVisibleItemPosition()
                            if (visibleItem > 0 && visibleItem < it.size) {
                                var date =
                                    toFormatDate.format(fromFormatDate.parse(it[visibleItem].date))
                                viewchat.tvDateMessage.text = date
                            }
                        }catch (e: Exception){e.printStackTrace()}
                      }
                    }
                })

                if (it.size > 0)
                view.rvUserChat.smoothScrollToPosition(it.size)
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ivBack->{
                var mes = Message()
                mes.arg1 = DtdConstants.SHOW_MESSAGE_ALL_USERS
                mes.obj = com.mjdistillers.drinkthedrink.model.response.chat_all_user.Datum()
                (activity as MainActivity).handleMessages(mes)
                dialog?.dismiss()
            }

            R.id.ivClose->{
                dialog?.dismiss()
            }

            R.id.ivSend->{
                var textLength = viewchat.etTextMessage.text?.length?:0

                if (isShareImage) {
                    textLength = "test123".length
                }

                if (::userChatHistoryResponse.isInitialized) {
                    if (textLength > 0){
                var sendMessage = SendMessageRequest()

                        if (isShareImage) {
                            sendMessage.message = "test123"
                        }

                if (searchFilterModel == null){
                objKeys?.let {

                    var fromId = 0
                    var followId = 0
                    var deviceToken = ""

                    var followUserId = 0

                        followUserId = if (it.fromId == pref.getInt(DtdConstants.ID)) it.toId
                        else it.fromId

                        if (it.fromId == pref.getInt(DtdConstants.ID)) {
                            fromId = it.fromId
                            followId = it.toId
                            deviceToken = userChatHistoryResponse.data.follow_device_token

                        } else {
                            fromId = it.toId
                            followId = it.fromId
                            deviceToken = userChatHistoryResponse.data.user_device_token
                        }

                        if (isFromUser) {
                            fromId = pref.getInt(DtdConstants.ID)
                            followId = toIDForNewUser
                            followUserId = toIDForNewUser
                        }

                        viewchat?.let {
                            sendMessage.from = fromId
                            sendMessage.to = followId
                            sendMessage.follow_id = followUserId
                            sendMessage.user_id = pref.getInt(DtdConstants.ID)
                            if (!isShareImage) sendMessage.message = viewchat.etTextMessage.text.toString()
                            sendMessage.device_token = deviceToken


                            when(isShareImage){
                                true->{
                                    (activity as MainActivity).viewModel.getShareImageInChat(sendMessage,imageFile)
                                    isShareImage = false
                                }
                                false->{
                                    (activity as MainActivity).viewModel.getSendMessage(sendMessage)
                                }
                            }
                        }
                    }
                }else{
                    viewchat?.let {
                        sendMessage.from = pref.getInt(DtdConstants.ID)
                        sendMessage.to = searchFilterModel?.id
                        sendMessage.follow_id = searchFilterModel?.id
                        sendMessage.user_id = pref.getInt(DtdConstants.ID)
                        if (!isShareImage) sendMessage.message = viewchat.etTextMessage.text.toString()
                        sendMessage.device_token = searchFilterModel?.deviceToken




                        when(isShareImage){
                            true->{
                                (activity as MainActivity).viewModel.getShareImageInChat(sendMessage,imageFile)
                                isShareImage = false
                            }
                            false->{
                                (activity as MainActivity).viewModel.getSendMessage(sendMessage)
                            }
                        }
                    }
                }

               }else{
                        var message = Message()
                        message.obj = getString(R.string.enter_message)
                        (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)

//                        ToastSnackUtils.toastShort(activity as MainActivity,getString(R.string.enter_message))
                }
                }else{

                    var message = Message()
                    message.obj = getString(R.string.choose_reciever)
                    (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)

//                    ToastSnackUtils.toastShort(activity as MainActivity,getString(R.string.choose_reciever))
                }
            }

            R.id.ivSearchReceiver ->{
                (activity as MainActivity).viewModel.getSearchPeople(
                    pref.getInt(DtdConstants.ID),
                    viewchat.etSearchReceiver.text.toString())
            }

            R.id.ivCapture->{
                isShareImage = true
                onImageClick()
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
        list.add(ch2)

        AlertUtils().showChooseOneFromListAlertNoActions(activity as MainActivity,
            getString(R.string.pick_image),object : OneChoiceNoActionsCallbacks {
                override fun eventListItemSelected(position: Int) {
                    when(position){
                        0->{
                            val permissions = arrayOf(android.Manifest.permission.CAMERA,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            val rationale = arrayOf("This permission is required to capture image from camera")
                            (activity as MainActivity).requestPermission(this@ChatHistoryDialog,permissions,rationale,PICK_IMAGE_FROM_CAMERA)
                        }
                        1->{
                            val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                            val rationale = arrayOf("We need this Permission to read image")
                            (activity as MainActivity).requestPermission(this@ChatHistoryDialog,permissions,rationale,PICK_IMAGE_FROM_GALLARY)
                        }
                    }
                }
            }, list)
    }

    override fun resultPickFromCamera(data: File?) {
        data?.let {
            imageFile = data
            isShareImage = true
            viewchat.ivSend.performClick()

        }
    }

    override fun resultPickFromMedia(data: File?) {
        data?.let {
            imageFile = data
            isShareImage = true
            viewchat.ivSend.performClick()
        }
    }

    override fun permissionGranted(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PICK_IMAGE_FROM_GALLARY->{
                if (!grantResults.contains(0)){
                    (activity as MainActivity).pickImageFromMedia(this)
                }else{

                    var message = Message()
                    message.obj = getString(R.string.allow_permissions)
                    (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)
                }
            }
            PICK_IMAGE_FROM_CAMERA->{
                if (!grantResults.contains(0)){
                    (activity as MainActivity).pickImageFromCamera(this)
                }else{
                    var message = Message()
                    message.obj = getString(R.string.allow_permissions)
                    (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)
                }
            }
        }    }

}