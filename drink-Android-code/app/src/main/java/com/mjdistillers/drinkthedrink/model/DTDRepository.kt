package com.mjdistillers.drinkthedrink.model

import android.app.Application
import android.os.AsyncTask
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.LinearLayout
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.mjdistillers.drinkthedrink.*
import com.mjdistillers.drinkthedrink.model.request.*
import com.mjdistillers.drinkthedrink.model.response.GetBarsDataResponse
import com.mjdistillers.drinkthedrink.model.response.GetUnauthorizedResponse2
import com.mjdistillers.drinkthedrink.model.response._all_notifications.GetAllNotificationsResponse
import com.mjdistillers.drinkthedrink.model.response.bar_details.GetBarsDetailsResponse
import com.mjdistillers.drinkthedrink.model.response.bar_search.GetBarsSearchResponse
import com.mjdistillers.drinkthedrink.model.response.chat_all_user.GetAllUsesrsChatResponse
import com.mjdistillers.drinkthedrink.model.response.edit_profile.GetEditProfile
import com.mjdistillers.drinkthedrink.model.response.feedback.GetPostFeedbackResponse
import com.mjdistillers.drinkthedrink.model.response.follow_unfollow_to_back_decline_logout.GetFollowUnfollowTO_BackResponse_Decline_Logout
import com.mjdistillers.drinkthedrink.model.response.forgot_password.GetForgotPasswordResponse
import com.mjdistillers.drinkthedrink.model.response.front_ads.GetFrontAdsResponse
import com.mjdistillers.drinkthedrink.model.response.fu_bar_store_team.FUBarStoreTeamResponse
import com.mjdistillers.drinkthedrink.model.response.get_cities.GetCitiesResponse
import com.mjdistillers.drinkthedrink.model.response.get_followers.GetFollowersResponse
import com.mjdistillers.drinkthedrink.model.response.get_following.GetFollowingResponse
import com.mjdistillers.drinkthedrink.model.response.get_people.GetPeopleResponse
import com.mjdistillers.drinkthedrink.model.response.get_profile.GetProfileResponse
import com.mjdistillers.drinkthedrink.model.response.get_stores.GetStoresResponse
import com.mjdistillers.drinkthedrink.model.response.login.GetUserLoginResponse
import com.mjdistillers.drinkthedrink.model.response.registration.GetUserRegisterationResponse
import com.mjdistillers.drinkthedrink.model.response.request_push_notification.GetPushNotificationResponse
import com.mjdistillers.drinkthedrink.model.response.search_people.GetSearchPeopleResponse
import com.mjdistillers.drinkthedrink.model.response.search_people_api.GetSearchPeopleFrontendResponse
import com.mjdistillers.drinkthedrink.model.response.send_message.GetSendMessageResponse
import com.mjdistillers.drinkthedrink.model.response.states_provinces.GetStatesAndPrvincesResponse
import com.mjdistillers.drinkthedrink.model.response.store_detail.GetStoresDetailResponse
import com.mjdistillers.drinkthedrink.model.response.stores_search.GetStoreSearchResponse
import com.mjdistillers.drinkthedrink.model.response.unauthorized.Data
import com.mjdistillers.drinkthedrink.model.response.unauthorized.GetUnauthorizedResponse
import com.mjdistillers.drinkthedrink.model.response.update_cocktail.GetSaveCocktailImages
import com.mjdistillers.drinkthedrink.model.response.update_password.GetUpdatePasswordResponse
import com.mjdistillers.drinkthedrink.model.response.update_profile.GetUpdateProfileResponse
import com.mjdistillers.drinkthedrink.model.response.update_user.GetUpdateUserResponse
import com.mjdistillers.drinkthedrink.model.response.user_block.GetBlockUserResp
import com.mjdistillers.drinkthedrink.model.response.user_chat_history.GetUserChatHistoryResponse
import com.mjdistillers.drinkthedrink.model.response.user_visibility.GetUserVisibilityResponse
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import com.mjdistillers.drinkthedrink.utilities.SharedPrefsUtils
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DTDRepository(var application: Application) {

    private lateinit var mldBarDetails: MutableLiveData<GetBarsDetailsResponse?>
    private lateinit var mldBars : MutableLiveData<ArrayList<MarkerModel>>
    private var mldAds = MutableLiveData<GetFrontAdsResponse>()
    private lateinit var mldLogin : MutableLiveData<GetUserLoginResponse>
    private lateinit var mldRegistration : MutableLiveData<GetUserRegisterationResponse>
    private lateinit var mldFollowers : MutableLiveData<GetFollowersResponse>
    private lateinit var mldFollowing : MutableLiveData<GetFollowingResponse>
    private lateinit var mldUpdateProfile : MutableLiveData<GetUpdateProfileResponse>
    private lateinit var mldUpdateCocktailImage : MutableLiveData<GetSaveCocktailImages>
    private lateinit var mldStores : MutableLiveData<ArrayList<MarkerModel>>
    private lateinit var mldPeople : MutableLiveData<ArrayList<MarkerModel>>
    private lateinit var mldPeoplePretendingSearch : MutableLiveData<List<SearchFilterModel>>
    private lateinit var mldStoresDetail : MutableLiveData<GetStoresDetailResponse>
    private lateinit var mldBarsSearch : MutableLiveData<List<SearchFilterModel>>
    private lateinit var mldStoresSearch : MutableLiveData<List<SearchFilterModel>>
    private lateinit var mldProfile : MutableLiveData<GetProfileResponse>
    private lateinit var mldFeedback : MutableLiveData<GetPostFeedbackResponse>
    private lateinit var mldRequestPushNotification : MutableLiveData<GetPushNotificationResponse>
    private lateinit var mldFollowUnfollowTo : MutableLiveData<GetFollowUnfollowTO_BackResponse_Decline_Logout>
    private lateinit var mldFollowUnfollowBack : MutableLiveData<GetFollowUnfollowTO_BackResponse_Decline_Logout>
    private lateinit var mldDeclineReq : MutableLiveData<GetFollowUnfollowTO_BackResponse_Decline_Logout>
    private lateinit var mldLogout : MutableLiveData<GetFollowUnfollowTO_BackResponse_Decline_Logout>
    private lateinit var mldAllNotification : MutableLiveData<GetAllNotificationsResponse>

    private lateinit var liveDataAlluser: MutableLiveData<GetAllUsesrsChatResponse>
    private lateinit var liveDataChatHistory: MutableLiveData<GetUserChatHistoryResponse>
    private lateinit var liveDataSendMessage: MutableLiveData<GetSendMessageResponse>
    private lateinit var liveDataSeachPeople: MutableLiveData<List<SearchFilterModel>>
    private lateinit var liveDataUpdatePassword: MutableLiveData<GetUpdatePasswordResponse>
    private lateinit var liveDataForgotPassword: MutableLiveData<GetForgotPasswordResponse>
    private lateinit var liveDataStatesAndProvinces: MutableLiveData<GetStatesAndPrvincesResponse>
    private lateinit var liveDataCities: MutableLiveData<GetCitiesResponse>
    private lateinit var liveDataSnackbar: MutableLiveData<Message>

    private var mldEditProfile = MutableLiveData<GetEditProfile>()
    private var mldEditProfileNoUi = MutableLiveData<GetEditProfile>()
    lateinit var mldUserVisibility:MutableLiveData<GetUserVisibilityResponse>

    lateinit var mldFUBar:MutableLiveData<FUBarStoreTeamResponse>
    lateinit var mldFUStore:MutableLiveData<FUBarStoreTeamResponse>
    lateinit var mldFUBarTeam:MutableLiveData<FUBarStoreTeamResponse>
    lateinit var mldFUStoreTeam:MutableLiveData<FUBarStoreTeamResponse>
    lateinit var mldFUPeople:MutableLiveData<FUBarStoreTeamResponse>
    lateinit var mldUpdateUser:MutableLiveData<GetUpdateUserResponse>
    lateinit var mldBlockUser:MutableLiveData<GetBlockUserResp>

    var mldAlert = MutableLiveData<Message>()
    var mldProgressBar = MutableLiveData<Boolean>()

    var retro:RetrofitApisInterface
    var prefs = SharedPrefsUtils(application)

    @Inject
    lateinit var app:Application

    @Inject
    lateinit var retrofit:Retrofit

    @Inject
    lateinit var dtdUtils:DtdUtils


    init {
        App.app.getComponent().inject(this)
        retro = retrofit.create(RetrofitApisInterface::class.java)
    }

    fun showAlert(message: String){

        var mes = Message()
        mes.obj = message
//        liveDataSnackbar.postValue(mes)
        mldAlert.postValue(mes)
    }

    fun getProgressLiveData(): MutableLiveData<Boolean>{
        return mldProgressBar
    }

    fun getAlertLiveData(): MutableLiveData<Message>{
        return mldAlert
    }


    fun liveDataBars(): MutableLiveData<ArrayList<MarkerModel>> {
            mldBars = MutableLiveData()
            return mldBars
    }

    fun liveDataSnackabr(): MutableLiveData<Message>{
        liveDataSnackbar = MutableLiveData()
        return liveDataSnackbar
    }

    fun liveDataPeople(): MutableLiveData<ArrayList<MarkerModel>> {
        mldPeople = MutableLiveData()
        return mldPeople
    }

    fun liveDataCities(): MutableLiveData<GetCitiesResponse> {
        liveDataCities = MutableLiveData()
        return liveDataCities
    }

    fun liveDataProfile(): MutableLiveData<GetProfileResponse> {
        mldProfile = MutableLiveData()
        return mldProfile
    }

    fun liveDataFUBar(): MutableLiveData<FUBarStoreTeamResponse> {
        mldFUBar = MutableLiveData()
        return mldFUBar
    }

    fun liveDataFUStore(): MutableLiveData<FUBarStoreTeamResponse> {
        mldFUStore = MutableLiveData()
        return mldFUStore
    }

    fun liveDataFUBarTeam(): MutableLiveData<FUBarStoreTeamResponse> {
        mldFUBarTeam = MutableLiveData()
        return mldFUBarTeam
    }

    fun liveDataFUStoreTeam(): MutableLiveData<FUBarStoreTeamResponse> {
        mldFUStoreTeam = MutableLiveData()
        return mldFUStoreTeam
    }

    fun liveDataFUPeople(): MutableLiveData<FUBarStoreTeamResponse> {
        mldFUPeople = MutableLiveData()
        return mldFUPeople
    }

    fun liveDataEditProfileNoUI() : MutableLiveData<GetEditProfile>{
        return mldEditProfileNoUi
    }

    fun liveDataUpdateUser(): MutableLiveData<GetUpdateUserResponse> {
        mldUpdateUser = MutableLiveData()
        return mldUpdateUser
    }

    fun postUpdateUser(updateUser: UpdateRoleRequest){
        mldProgressBar.postValue(true)

        var call = retro.postUpdateRole(updateUser)
        call.enqueue(object : Callback<GetUpdateUserResponse>{
            override fun onFailure(call: Call<GetUpdateUserResponse>, t: Throwable) {
                mldProgressBar.postValue(false)
                showAlert(application.getString(R.string.problem_occured))
            }

            override fun onResponse(call: Call<GetUpdateUserResponse>,
                                    response: Response<GetUpdateUserResponse>) {
                mldProgressBar.postValue(false)
                when(response.code()){
                    DtdConstants.RES_CODE_200->{
                        dtdUtils.saveForUpdateUserAfterRegistration(response.body()?.data)
                        mldUpdateUser.postValue(response.body())
                    }
                    DtdConstants.RES_CODE_NO_INTERNET->{
                        showAlert(app.getString(R.string.connect_internet))
                    }
                    DtdConstants.RES_CODE_401->{
                        var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                        showAlert(unauth.data.message[0])
                    }
                    else->{
                        showAlert(app.getString(R.string.problem_occured))
                    }
                }
            }
        })
    }

    fun getFUPeople(followStatus: Int, id: Int, follow_id: Int,device_token: String, user_device_token : String){
        mldProgressBar.postValue(true)

        var call  = retro.postFUPeople(followStatus,id,follow_id,device_token,user_device_token)
        call.enqueue(object  : Callback<FUBarStoreTeamResponse>{
            override fun onFailure(call: Call<FUBarStoreTeamResponse>, t: Throwable) {
                mldProgressBar.postValue(false)
                showAlert(application.getString(R.string.problem_occured))
            }

            override fun onResponse(
                call: Call<FUBarStoreTeamResponse>,
                response: Response<FUBarStoreTeamResponse>) {
                mldProgressBar.postValue(false)
                when(response.code()){
                    DtdConstants.RES_CODE_200->{
                        mldFUPeople.postValue(response.body())
                    }
                    DtdConstants.RES_CODE_NO_INTERNET->{
                        showAlert(app.getString(R.string.connect_internet))
                    }
                    DtdConstants.RES_CODE_401->{
                        var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                        showAlert(unauth.data.message[0])
                    }
                    else->{
                        showAlert(app.getString(R.string.problem_occured))
                    }
                }
            }

        })
    }


    fun postCities(country: String, state_code : String){
        mldProgressBar.postValue(true)

        var call  = retro.postGetCities(country,state_code)
        call.enqueue(object  : Callback<GetCitiesResponse>{
            override fun onFailure(call: Call<GetCitiesResponse>, t: Throwable) {
                mldProgressBar.postValue(false)
                showAlert(application.getString(R.string.problem_occured))
            }

            override fun onResponse(
                call: Call<GetCitiesResponse>,
                response: Response<GetCitiesResponse>) {
                mldProgressBar.postValue(false)
                when(response.code()){
                    DtdConstants.RES_CODE_200->{
                        liveDataCities.postValue(response.body())
                    }
                    DtdConstants.RES_CODE_NO_INTERNET->{
                        showAlert(app.getString(R.string.connect_internet))
                    }
                    DtdConstants.RES_CODE_401->{
                        var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                        showAlert(unauth.data.message[0])
                    }
                    else->{
                        showAlert(app.getString(R.string.problem_occured))
                    }
                }
            }
        })
    }


    fun getListFilteredFromDollors(list: ArrayList<MarkerModel>, oneDollor: LinearLayout, twoDollor: LinearLayout, threeDollor: LinearLayout){

        var one = if(oneDollor.tag == DtdConstants.RB_SEL_TAG) 1 else -1
        var two = if(twoDollor.tag == DtdConstants.RB_SEL_TAG) 2 else -1
        var three = if(threeDollor.tag == DtdConstants.RB_SEL_TAG) 3 else -1

        var asynchTask = object : AsyncTask<Void,Void,Void>(){

            var filteredList = ArrayList<MarkerModel>()

            override fun onPreExecute() {
                super.onPreExecute()



                mldProgressBar.postValue(true)
            }

            override fun doInBackground(vararg params: Void?): Void? {
                for(model in list){

                    model.dollar?.let {
                        if(it == one || it == two || it == three || it == 0){
                            filteredList.add(model)
                        }
                    }
                }  // End of for loop
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                mldProgressBar.postValue(false)
//                if (filteredList.size > 0){

                var dummyModel = MarkerModel(MarkerOptions(),false,0,0.0,0,"","","","",0,
                    mutableListOf())

                dummyModel.distance = DtdConstants.DUMMY_DISTANCE_SIGNATURE
                filteredList.add(dummyModel)

                    mldBars.postValue(filteredList)
//                }else{
//                    showAlert("No data found")
//                }
            }
        }

        asynchTask.execute()

    }


    fun getBarsData(barsData: GetBarsDataRequest,handler: Handler){
        mldProgressBar.postValue(true)

        var call = retro.getBarsData(barsData)

        call.enqueue(object : Callback<GetBarsDataResponse>{
            override fun onFailure(call: Call<GetBarsDataResponse>, t: Throwable) {
                showAlert(app.getString(R.string.problem_occured))
                mldProgressBar.postValue(false)

                var mes = Message.obtain(handler)
                mes.arg1 = DtdConstants.SHOW_APP_TOUR
                mes.sendToTarget()


            }

            override fun onResponse(
                call: Call<GetBarsDataResponse>,
                response: Response<GetBarsDataResponse>) {
                mldProgressBar.postValue(false)

                var mes = Message.obtain(handler)
                mes.arg1 = DtdConstants.SHOW_APP_TOUR
                mes.sendToTarget()

                when(response.code()){
                    DtdConstants.RES_CODE_200->{
                        dtdUtils.prepareMarkerModelList(response.body()?.data,mldBars)
                    }

                    DtdConstants.RES_CODE_NO_INTERNET->{
                        showAlert(application.getString(R.string.connect_internet))
                    }

                    DtdConstants.RES_CODE_401->{
                       var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                        showAlert(unauth.data.message[0])
                    }
                    else->{
                        showAlert(app.getString(R.string.problem_occured))
                    }
                }
            }
        })
    }

    fun liveDataBarsDetail() : MutableLiveData<GetBarsDetailsResponse?>{
        mldBarDetails = MutableLiveData()
        return mldBarDetails
    }

    fun liveDataBarsSearch() : MutableLiveData<List<SearchFilterModel>>{
        mldBarsSearch = MutableLiveData()
        return mldBarsSearch
    }

    fun liveDataStoresSearch() : MutableLiveData<List<SearchFilterModel>>{
        mldStoresSearch = MutableLiveData()
        return mldStoresSearch
    }

    fun getBarsSearch(barSearchReq: GetSearchBarsRequest){
        mldProgressBar.postValue(true)

        var call = retro.getSearchBars(barSearchReq)
        call.enqueue(object : Callback<GetBarsSearchResponse>{
            override fun onFailure(call: Call<GetBarsSearchResponse>, t: Throwable) {
                mldProgressBar.postValue(false)
                showAlert(application.getString(R.string.problem_occured))
            }

            override fun onResponse(call: Call<GetBarsSearchResponse>, response: Response<GetBarsSearchResponse>) {
                mldProgressBar.postValue(false)

                when(response.code()){
                    DtdConstants.RES_CODE_200->{
                        var searchList = dtdUtils.getListOfSearchFromBarsSearch(response.body())
                        mldBarsSearch.postValue(searchList)
                    }
                    DtdConstants.RES_CODE_NO_INTERNET->{
                        showAlert(app.getString(R.string.connect_internet))
                    }
                    DtdConstants.RES_CODE_401->{
                        var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                        showAlert(unauth.data.message[0])
                    }
                    else->{
                        showAlert(app.getString(R.string.problem_occured))
                    }
                }
            }
        })
    }

    fun getStoreSearch(storeSearchRequest: GetStoreSearchRequest){
        mldProgressBar.postValue(true)

        var call = retro.getSearchStores(storeSearchRequest)
        call.enqueue(object : Callback<GetStoreSearchResponse>{
            override fun onFailure(call: Call<GetStoreSearchResponse>, t: Throwable) {
                mldProgressBar.postValue(false)
                showAlert(application.getString(R.string.problem_occured))
            }

            override fun onResponse(call: Call<GetStoreSearchResponse>, response: Response<GetStoreSearchResponse>) {
                mldProgressBar.postValue(false)

                when(response.code()){
                    DtdConstants.RES_CODE_200->{
                        var searchList = dtdUtils.getListOfSeachFromStoreSearch(response.body())
                        mldBarsSearch.postValue(searchList)

                    }
                    DtdConstants.RES_CODE_NO_INTERNET->{
                        showAlert(app.getString(R.string.connect_internet))
                    }
                    DtdConstants.RES_CODE_401->{
                        var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                        showAlert(unauth.data.message[0])
                    }
                    else->{
                        showAlert(app.getString(R.string.problem_occured))
                    }
                }
            }
        })
    }

    fun getBarsDetail(barsRequest: GetBarDetailsRequest){
        mldProgressBar.postValue(true)

        var call = retro.getBarDetail(barsRequest)
        call.enqueue(object : Callback<GetBarsDetailsResponse>{
            override fun onFailure(call: Call<GetBarsDetailsResponse>, t: Throwable) {
                mldProgressBar.postValue(false)
                showAlert(application.getString(R.string.problem_occured))
            }

            override fun onResponse(
                call: Call<GetBarsDetailsResponse>,
                response: Response<GetBarsDetailsResponse>) {
                mldProgressBar.postValue(false)

                when(response.code()){
                    DtdConstants.RES_CODE_200->{
                        var barsDetail = response.body()
                        barsDetail?.isFromBars = true
                        mldBarDetails.postValue(barsDetail)
                    }
                    DtdConstants.RES_CODE_NO_INTERNET->{
                        showAlert(app.getString(R.string.connect_internet))
                    }
                    DtdConstants.RES_CODE_401->{
                        var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                        showAlert(unauth.data.message[0])
                    }
                    else->{
                        showAlert(app.getString(R.string.problem_occured))
                    }
                }
            }
        })
    }

    fun getFrontAds(): MutableLiveData<GetFrontAdsResponse>{
        mldProgressBar.postValue(true)

        var call = retro.getFullAds()
        call.enqueue(object : Callback<GetFrontAdsResponse>{
            override fun onFailure(call: Call<GetFrontAdsResponse>, t: Throwable) {
                mldProgressBar.postValue(false)
                showAlert(application.getString(R.string.problem_occured))
            }

            override fun onResponse(call: Call<GetFrontAdsResponse>, response: Response<GetFrontAdsResponse>) {
                mldProgressBar.postValue(false)

                when(response.code()){
                    DtdConstants.RES_CODE_200->{
                        mldAds.postValue(response.body())
                    }
                    DtdConstants.RES_CODE_NO_INTERNET->{
                        showAlert(app.getString(R.string.connect_internet))
                    }
                    DtdConstants.RES_CODE_401->{
                        var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                        showAlert(unauth.data.message[0])
                    }
                    else->{
                        showAlert(app.getString(R.string.problem_occured))
                    }
                }
            }
        })
        return mldAds
    }

    fun liveDataLogin(): MutableLiveData<GetUserLoginResponse>{
        mldLogin = MutableLiveData()
        return mldLogin
    }

    fun getFUBar(followStatus: Int, bar_id: Int, user_id: Int){
        mldProgressBar.postValue(true)

        var call  = retro.postFUBar(followStatus,bar_id,user_id)
        call.enqueue(object  : Callback<FUBarStoreTeamResponse>{
            override fun onFailure(call: Call<FUBarStoreTeamResponse>, t: Throwable) {
                mldProgressBar.postValue(false)
                showAlert(application.getString(R.string.problem_occured))
            }

            override fun onResponse(
                call: Call<FUBarStoreTeamResponse>,
                response: Response<FUBarStoreTeamResponse>) {
                mldProgressBar.postValue(false)
                when(response.code()){
                    DtdConstants.RES_CODE_200->{
                        mldFUBar.postValue(response.body())
                    }
                    DtdConstants.RES_CODE_NO_INTERNET->{
                        showAlert(app.getString(R.string.connect_internet))
                    }
                    DtdConstants.RES_CODE_401->{
                        var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                        showAlert(unauth.data.message[0])
                    }
                    else->{
                        showAlert(app.getString(R.string.problem_occured))
                    }
                }
            }

        })
    }



    fun getFUStore(followStatus: Int, store_id: Int, user_id: Int){
        mldProgressBar.postValue(true)

        var call  = retro.postFUStore(followStatus,store_id,user_id)
        call.enqueue(object  : Callback<FUBarStoreTeamResponse>{
            override fun onFailure(call: Call<FUBarStoreTeamResponse>, t: Throwable) {
                mldProgressBar.postValue(false)
                showAlert(application.getString(R.string.problem_occured))
            }

            override fun onResponse(
                call: Call<FUBarStoreTeamResponse>,
                response: Response<FUBarStoreTeamResponse>) {
                mldProgressBar.postValue(false)
                when(response.code()){
                    DtdConstants.RES_CODE_200->{
                        mldFUStore.postValue(response.body())
                    }
                    DtdConstants.RES_CODE_NO_INTERNET->{
                        showAlert(app.getString(R.string.connect_internet))
                    }
                    DtdConstants.RES_CODE_401->{
                        var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                        showAlert(unauth.data.message[0])
                    }
                    else->{
                        showAlert(app.getString(R.string.problem_occured))
                    }
                }
            }

        })
    }


    fun getFUTeamBar(followStatus: Int, bar_id: Int, follow_id: Int, device_token: String, user_id : Int){
        mldProgressBar.postValue(true)

        var call  = retro.postFUBarTeam(followStatus,bar_id,follow_id, device_token,user_id)
        call.enqueue(object  : Callback<FUBarStoreTeamResponse>{
            override fun onFailure(call: Call<FUBarStoreTeamResponse>, t: Throwable) {
                mldProgressBar.postValue(false)
                showAlert(application.getString(R.string.problem_occured))
            }

            override fun onResponse(
                call: Call<FUBarStoreTeamResponse>,
                response: Response<FUBarStoreTeamResponse>) {
                mldProgressBar.postValue(false)
                when(response.code()){
                    DtdConstants.RES_CODE_200->{
                        mldFUBarTeam.postValue(response.body())
                    }
                    DtdConstants.RES_CODE_NO_INTERNET->{
                        showAlert(app.getString(R.string.connect_internet))
                    }

                    DtdConstants.RES_CODE_401->{
                        var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                        showAlert(unauth.data.message[0])
                    }
                    else->{
                        showAlert(app.getString(R.string.problem_occured))
                    }
                }
            }
        })
    }

    fun getFUTeamStore(followStatus: Int, store_id: Int, follow_id: Int, device_token: String, user_id : Int){
        mldProgressBar.postValue(true)

        var call  = retro.postFUStoreTeam(followStatus,store_id,follow_id, device_token,user_id)
        call.enqueue(object  : Callback<FUBarStoreTeamResponse>{
            override fun onFailure(call: Call<FUBarStoreTeamResponse>, t: Throwable) {
                mldProgressBar.postValue(false)
                showAlert(application.getString(R.string.problem_occured))
            }

            override fun onResponse(
                call: Call<FUBarStoreTeamResponse>,
                response: Response<FUBarStoreTeamResponse>) {
                mldProgressBar.postValue(false)
                when(response.code()){
                    DtdConstants.RES_CODE_200->{
                        mldFUBarTeam.postValue(response.body())
                    }
                    DtdConstants.RES_CODE_NO_INTERNET->{
                        showAlert(app.getString(R.string.connect_internet))
                    }
                    DtdConstants.RES_CODE_401->{
                        var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                        showAlert(unauth.data.message[0])
                    }
                    else->{
                        showAlert(app.getString(R.string.problem_occured))
                    }
                }
            }
        })
    }

    fun getUserLogin(userLoginRequest: GetUserLoginRequest){
        mldProgressBar.postValue(true)

        var call = retro.getUserLogin(userLoginRequest)
        call.enqueue(object : Callback<GetUserLoginResponse>{
            override fun onFailure(call: Call<GetUserLoginResponse>, t: Throwable) {

                mldProgressBar.postValue(false)
                showAlert(application.getString(R.string.problem_occured))
            }

            override fun onResponse(call: Call<GetUserLoginResponse>, response: Response<GetUserLoginResponse>) {
                mldProgressBar.postValue(false)
                when(response.code()){
                    DtdConstants.RES_CODE_200->{

                        dtdUtils.saveForUserLogin(response.body())
                        mldLogin.postValue(response.body())
                    }
                    DtdConstants.RES_CODE_NO_INTERNET->{
                        showAlert(app.getString(R.string.connect_internet))
                    }
                    DtdConstants.RES_CODE_401->{
                        var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                        showAlert(unauth.data.message[0])
                    }
                    else->{
                        showAlert(app.getString(R.string.problem_occured))
                    }
                }
            }
        })
    }

    fun liveDataSignUp() : MutableLiveData<GetUserRegisterationResponse>{
        mldRegistration = MutableLiveData()
        return mldRegistration
    }

    fun getSignUpUser(registrationRequest: GetRegistrationRequest) {
        mldProgressBar.postValue(true)

        var call = retro.getUserSignUp(registrationRequest)
        call.enqueue(object : Callback<GetUserRegisterationResponse>{
            override fun onFailure(call: Call<GetUserRegisterationResponse>, t: Throwable) {
                mldProgressBar.postValue(false)

                showAlert(application.getString(R.string.problem_occured))
            }

            override fun onResponse(call: Call<GetUserRegisterationResponse>,
                                    response: Response<GetUserRegisterationResponse>) {
                mldProgressBar.postValue(false)

                when(response.code()){
                    DtdConstants.RES_CODE_200->{
                        dtdUtils.saveForRegistration(response.body())
                        mldRegistration.postValue(response.body())
                    }
                    DtdConstants.RES_CODE_NO_INTERNET->{
                        showAlert(app.getString(R.string.connect_internet))
                    }
                    DtdConstants.RES_CODE_401->{
                        var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                        showAlert(unauth.data.message[0])
                    }
                    else->{
                        showAlert(app.getString(R.string.problem_occured))
                    }
                }
            }
        })
    }


    fun liveDataFollowers() : MutableLiveData<GetFollowersResponse>{
        mldFollowers = MutableLiveData()
        return mldFollowers
    }

    fun getFollowers(id: Long){
        mldProgressBar.postValue(true)

        var call = retro.getFollowers(id)
        call.enqueue(object : Callback<GetFollowersResponse>{
            override fun onFailure(call: Call<GetFollowersResponse>, t: Throwable) {
                mldProgressBar.postValue(false)
                showAlert(application.getString(R.string.problem_occured))
            }

            override fun onResponse(call: Call<GetFollowersResponse>, response: Response<GetFollowersResponse>) {
                mldProgressBar.postValue(false)

                when(response.code()){
                    DtdConstants.RES_CODE_200->{
                        mldFollowers.postValue(response.body())
                    }
                    DtdConstants.RES_CODE_NO_INTERNET->{
                        showAlert(app.getString(R.string.connect_internet))
                    }
                    DtdConstants.RES_CODE_401->{
                        var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                        showAlert(unauth.data.message[0])
                    }
                    else->{
                        showAlert(app.getString(R.string.problem_occured))
                    }
                }
            }
        })
    }

    fun liveDataFollowing():  MutableLiveData<GetFollowingResponse>{
        mldFollowing = MutableLiveData()
        return mldFollowing
    }

    fun getFollowing(id: Long){
        mldProgressBar.postValue(true)

        var call = retro.getFollowing(id)
        call.enqueue(object : Callback<GetFollowingResponse>{
            override fun onFailure(call: Call<GetFollowingResponse>, t: Throwable) {
                mldProgressBar.postValue(false)
                showAlert(application.getString(R.string.problem_occured))
            }

            override fun onResponse(call: Call<GetFollowingResponse>, response: Response<GetFollowingResponse>) {
                mldProgressBar.postValue(false)

                when(response.code()){
                    DtdConstants.RES_CODE_200->{
                        mldFollowing.postValue(response.body())
                    }
                    DtdConstants.RES_CODE_NO_INTERNET->{
                        showAlert( app.getString(R.string.connect_internet))
                    }
                    DtdConstants.RES_CODE_401->{
                        var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                        showAlert(unauth.data.message[0])
                    }
                    else->{
                        showAlert(app.getString(R.string.problem_occured))
                    }
                }
            }
        })
    }

    fun liveDataUpdateProfile()  : MutableLiveData<GetUpdateProfileResponse>{
        mldUpdateProfile = MutableLiveData()
        return mldUpdateProfile
    }


    fun liveDataStoreDetail(): MutableLiveData<GetStoresDetailResponse>{
        mldStoresDetail = MutableLiveData()
        return mldStoresDetail
    }

    fun getStoreDetails(storeDetail: GetStoresDetailRequest){
        var call = retro.getStoresDetail(storeDetail)

        call.enqueue(object : Callback<GetStoresDetailResponse>{
            override fun onFailure(call: Call<GetStoresDetailResponse>, t: Throwable) {
                mldProgressBar.postValue(false)
                showAlert(application.getString(R.string.problem_occured))
            }

            override fun onResponse(call: Call<GetStoresDetailResponse>,
                                    response: Response<GetStoresDetailResponse>) {

                mldProgressBar.postValue(false)

                when(response.code()){
                    DtdConstants.RES_CODE_200->{
                        var barDetailFromStoreDetail = dtdUtils.getBarDetailObjectFromStoreDetail(response.body())
                        barDetailFromStoreDetail.isFromBars = false
                        mldBarDetails.postValue(barDetailFromStoreDetail)
                        Log.i("STORE","STORE")
                    }
                    DtdConstants.RES_CODE_NO_INTERNET->{
                        showAlert(app.getString(R.string.connect_internet))
                    }
                    DtdConstants.RES_CODE_401->{
                        var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                        showAlert(unauth.data.message[0])
                    }
                    else->{
                        showAlert(app.getString(R.string.problem_occured))
                    }
                }
            }
        })
    }

    fun getUpdateUserProfile(updateRequest: GetUserUpdateProfileRequest){

        object : AsyncTask<Void,Void,Void>(){

            override fun onPreExecute() {
                super.onPreExecute()

                mldProgressBar.postValue(true)

            }

            override fun doInBackground(vararg params: Void?): Void? {
                GetMultipartRequestToUpdateProfile(mldUpdateProfile,updateRequest)
             return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                mldProgressBar.postValue(false)

            }
        }.execute()
    }

    fun getEditProfile(isHitNoUI: Boolean): MutableLiveData<GetEditProfile>{
        mldProgressBar.postValue(true)
        mldEditProfile = MutableLiveData<GetEditProfile>()

        var editUser = GetEditUserRequest()
        editUser.id = prefs.getInt(DtdConstants.ID)
        editUser.token = prefs.getString(DtdConstants.SERVER_TOKEN)

        retro.getEditProfile(editUser).enqueue(object : Callback<GetEditProfile>{
            override fun onFailure(call: Call<GetEditProfile>, t: Throwable) {
                mldProgressBar.postValue(false)
                if (!isHitNoUI) showAlert(application.getString(R.string.problem_occured))
            }

            override fun onResponse(call: Call<GetEditProfile>, response: Response<GetEditProfile>) {
                mldProgressBar.postValue(false)
                when(response.code()){
                    DtdConstants.RES_CODE_200->{
                        dtdUtils.saveForEditProfile(response.body())
                        if (!isHitNoUI) mldEditProfile.postValue(response.body())
                        else mldEditProfileNoUi.postValue(response.body())
                    }
                    DtdConstants.RES_CODE_NO_INTERNET->{
                        if (!isHitNoUI) showAlert(app.getString(R.string.connect_internet))
                    }
                    DtdConstants.RES_CODE_401->{
                        var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                        showAlert(unauth.data.message[0])
                    }
                    else->{
                        showAlert(app.getString(R.string.problem_occured))
                    }
                }
            }
        })
        return mldEditProfile
    }


    fun liveDataStores(): MutableLiveData<ArrayList<MarkerModel>>{
        mldStores = MutableLiveData()
        return mldStores
    }

    fun liveDataRequestPushNotification(): MutableLiveData<GetPushNotificationResponse>{
        mldRequestPushNotification = MutableLiveData()
        return mldRequestPushNotification
    }

    fun liveDataFollowUnFollowTo(): MutableLiveData<GetFollowUnfollowTO_BackResponse_Decline_Logout>{
        mldFollowUnfollowTo = MutableLiveData()
        return mldFollowUnfollowTo
    }

    fun liveDataFollowUnFollowBack(): MutableLiveData<GetFollowUnfollowTO_BackResponse_Decline_Logout>{
        mldFollowUnfollowBack = MutableLiveData()
        return mldFollowUnfollowBack
    }

    fun liveDataDeclineRequest(): MutableLiveData<GetFollowUnfollowTO_BackResponse_Decline_Logout>{
        mldDeclineReq = MutableLiveData()
        return mldDeclineReq
    }

    fun liveDataBlockRequest(): MutableLiveData<GetBlockUserResp>{
        mldBlockUser = MutableLiveData()
        return mldBlockUser
    }

    fun liveDataLogout(): MutableLiveData<GetFollowUnfollowTO_BackResponse_Decline_Logout>{
        mldLogout = MutableLiveData()
        return mldLogout
    }


    fun postBlockUser(user_id: Int, block_status: Int, block_id: Int){
        mldProgressBar.postValue(true)

        retro.postBlockUnblock(user_id, block_status, block_id)
            .enqueue(object : Callback<GetBlockUserResp>{
                override fun onFailure(call: Call<GetBlockUserResp>, t: Throwable) {
                    mldProgressBar.postValue(false)
                    showAlert(application.getString(R.string.problem_occured))
                }

                override fun onResponse(call: Call<GetBlockUserResp>, response: Response<GetBlockUserResp>) {
                    mldProgressBar.postValue(false)
                    when(response.code()){
                        DtdConstants.RES_CODE_200->{
                            mldBlockUser.postValue(response.body())
                        }
                        DtdConstants.RES_CODE_NO_INTERNET->{
                            showAlert(app.getString(R.string.connect_internet))
                        }
                        DtdConstants.RES_CODE_401->{
                            var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                            showAlert(unauth.data.message[0])
                        }
                        else->{
                            showAlert(app.getString(R.string.problem_occured))
                        }
                    }
                }
            })
    }


    fun getRequestPushNotification(user_id:Int, user_name : String, follow_id: Int){
        mldProgressBar.postValue(true)

        retro.getRequestPushNotification(user_id,user_name,follow_id)
            .enqueue(object : Callback<GetPushNotificationResponse>{
                override fun onFailure(call: Call<GetPushNotificationResponse>, t: Throwable) {
                    mldProgressBar.postValue(false)
                    showAlert(application.getString(R.string.problem_occured))
                }

                override fun onResponse(call: Call<GetPushNotificationResponse>, response: Response<GetPushNotificationResponse>) {
                    mldProgressBar.postValue(false)

                    when(response.code()){
                        DtdConstants.RES_CODE_200->{

                        }
                        DtdConstants.RES_CODE_NO_INTERNET->{
                            showAlert(app.getString(R.string.connect_internet))
                        }
                        DtdConstants.RES_CODE_401->{
                            var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                            showAlert(unauth.data.message[0])
                        }
                        else->{
                            showAlert(app.getString(R.string.problem_occured))
                        }
                    }
                }
            })
    }

    fun getFollowUnfollowTo(user_id:Int, follow_id: Int, followStatus : Int, device_token: String){
        mldProgressBar.postValue(true)

        retro.getFollowUnfollowTo(user_id,follow_id,followStatus,device_token)
            .enqueue(object : Callback<GetFollowUnfollowTO_BackResponse_Decline_Logout>{
            override fun onFailure(call: Call<GetFollowUnfollowTO_BackResponse_Decline_Logout>, t: Throwable) {
                mldProgressBar.postValue(false)
                showAlert(application.getString(R.string.problem_occured))
            }

            override fun onResponse(call: Call<GetFollowUnfollowTO_BackResponse_Decline_Logout>, response: Response<GetFollowUnfollowTO_BackResponse_Decline_Logout>) {
                mldProgressBar.postValue(false)

                when(response.code()){
                    DtdConstants.RES_CODE_200->{
                        var resp = response.body()
                        resp?.requestedFor = followStatus
                        mldFollowUnfollowTo.postValue(response.body())
                    }
                    DtdConstants.RES_CODE_NO_INTERNET->{
                        showAlert(app.getString(R.string.connect_internet))
                    }
                    DtdConstants.RES_CODE_401->{
                        var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                        showAlert(unauth.data.message[0])
                    }
                    else->{
                        showAlert(app.getString(R.string.problem_occured))
                    }
                }
            }
        })
    }

    fun getFollowUnfollowBack(user_id:Int, follow_id: Int, followStatus : Int, device_token: String){
        mldProgressBar.postValue(true)

        retro.getFollowUnfollowBack(user_id,follow_id,followStatus,device_token)
            .enqueue(object : Callback<GetFollowUnfollowTO_BackResponse_Decline_Logout>{
                override fun onFailure(call: Call<GetFollowUnfollowTO_BackResponse_Decline_Logout>, t: Throwable) {
                    mldProgressBar.postValue(false)
                    showAlert(application.getString(R.string.problem_occured))
                }

                override fun onResponse(call: Call<GetFollowUnfollowTO_BackResponse_Decline_Logout>, response: Response<GetFollowUnfollowTO_BackResponse_Decline_Logout>) {
                    mldProgressBar.postValue(false)

                    when(response.code()){
                        DtdConstants.RES_CODE_200->{
                            var resp = response.body()
                            resp?.requestedFor = followStatus
                            mldFollowUnfollowBack.postValue(response.body())
                        }
                        DtdConstants.RES_CODE_NO_INTERNET->{
                            showAlert(app.getString(R.string.connect_internet))
                        }
                        DtdConstants.RES_CODE_401->{
                            var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                            showAlert(unauth.data.message[0])
                        }
                        else->{
                            showAlert(app.getString(R.string.problem_occured))
                        }
                    }
                }
            })
    }

    fun getDeclineRequest(user_id:Int, follow_id : Int){
        mldProgressBar.postValue(true)

        retro.getDeclineRequest(user_id,follow_id)
            .enqueue(object : Callback<GetFollowUnfollowTO_BackResponse_Decline_Logout>{
                override fun onFailure(call: Call<GetFollowUnfollowTO_BackResponse_Decline_Logout>, t: Throwable) {
                    mldProgressBar.postValue(false)
                    showAlert(application.getString(R.string.problem_occured))
                }

                override fun onResponse(call: Call<GetFollowUnfollowTO_BackResponse_Decline_Logout>,
                                        response: Response<GetFollowUnfollowTO_BackResponse_Decline_Logout>) {
                    mldProgressBar.postValue(false)

                    when(response.code()){
                        DtdConstants.RES_CODE_200->{
                            mldDeclineReq.postValue(response.body())
                        }
                        DtdConstants.RES_CODE_NO_INTERNET->{
                            showAlert(app.getString(R.string.connect_internet))
                        }
                        DtdConstants.RES_CODE_401->{
                            var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                            showAlert(unauth.data.message[0])
                        }
                        else->{
                            showAlert(app.getString(R.string.problem_occured))
                        }
                    }
                }
            })
    }

    fun getLogOut(user_id:Int, device_token: String){
        mldProgressBar.postValue(true)

        retro.getLogout(user_id,device_token)
            .enqueue(object : Callback<GetFollowUnfollowTO_BackResponse_Decline_Logout>{
                override fun onFailure(call: Call<GetFollowUnfollowTO_BackResponse_Decline_Logout>, t: Throwable) {
                    mldProgressBar.postValue(false)
                    showAlert(application.getString(R.string.problem_occured))
                }

                override fun onResponse(call: Call<GetFollowUnfollowTO_BackResponse_Decline_Logout>,
                                        response: Response<GetFollowUnfollowTO_BackResponse_Decline_Logout>) {
                    mldProgressBar.postValue(false)

                    when(response.code()){
                        DtdConstants.RES_CODE_200->{
                        mldLogout.postValue(response.body())
                        }
                        DtdConstants.RES_CODE_NO_INTERNET->{
                            showAlert(app.getString(R.string.connect_internet))
                        }
                        DtdConstants.RES_CODE_401->{
                            var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                            showAlert(unauth.data.message[0])
                        }
                        else->{
                            showAlert(app.getString(R.string.problem_occured))
                        }
                    }
                }
            })
    }

    fun liveDataAllNotifications():MutableLiveData<GetAllNotificationsResponse>{
        mldAllNotification = MutableLiveData()
        return mldAllNotification
    }

    fun getAllNotifications(user_id:Int){
        mldProgressBar.postValue(true)
        retro.getAllNotification(user_id)
            .enqueue(object : Callback<GetAllNotificationsResponse>{
                override fun onFailure(call: Call<GetAllNotificationsResponse>, t: Throwable) {
                    mldProgressBar.postValue(false)
                    showAlert(application.getString(R.string.problem_occured))
                }

                override fun onResponse(call: Call<GetAllNotificationsResponse>, response: Response<GetAllNotificationsResponse>) {
                    mldProgressBar.postValue(false)

                    when(response.code()){
                        DtdConstants.RES_CODE_200->{
                            mldAllNotification.postValue(response.body())
                        }
                        DtdConstants.RES_CODE_NO_INTERNET->{
                            showAlert(app.getString(R.string.connect_internet))
                        }
                        DtdConstants.RES_CODE_401->{
                            var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                            showAlert(unauth.data.message[0])
                        }
                        else->{
                            showAlert(app.getString(R.string.problem_occured))
                        }
                    }
                }
            })
    }

    fun getForgotPassword(emailId: String){
        mldProgressBar.postValue(true)
//
//        retro.getForgotPassword(emailId)
//            .enqueue(object : Callback<GetAllNotificationsResponse>{
//                override fun onFailure(call: Call<GetAllNotificationsResponse>, t: Throwable) {
//                    mldProgressBar.postValue(false)
//                    showAlert(application.getString(R.string.problem_occured))
//                }
//
//                override fun onResponse(call: Call<GetAllNotificationsResponse>, response: Response<GetAllNotificationsResponse>) {
//                    mldProgressBar.postValue(false)
//
//                    when(response.code()){
//                        DtdConstants.RES_CODE_200->{
//                            mldAllNotification.postValue(response.body())
//                        }
//                        DtdConstants.RES_CODE_NO_INTERNET->{
//                            showAlert(app.getString(R.string.connect_internet))
//                        }
//                        DtdConstants.RES_CODE_401->{
//                            var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
//                            showAlert(unauth.data.message[0])
//                        }
//                        else->{
//                            showAlert(app.getString(R.string.problem_occured))
//                        }
//                    }
//                }
//            })
    }


    fun getStores(request: GetStoresRequest){
        mldProgressBar.postValue(true)

        retro.getStores(request).enqueue(object : Callback<GetStoresResponse>{
            override fun onFailure(call: Call<GetStoresResponse>, t: Throwable) {
                mldProgressBar.postValue(false)
                showAlert(application.getString(R.string.problem_occured))
            }

            override fun onResponse(call: Call<GetStoresResponse>, response: Response<GetStoresResponse>) {
                mldProgressBar.postValue(false)

                when(response.code()){
                    DtdConstants.RES_CODE_200->{
                        dtdUtils.prepareMarkerModelListForStores(response.body()?.data,mldStores)
                    }
                    DtdConstants.RES_CODE_NO_INTERNET->{
                        showAlert(app.getString(R.string.connect_internet))
                    }
                    DtdConstants.RES_CODE_401->{
                        var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                        showAlert(unauth.data.message[0])
                    }
                    else->{
                        showAlert(app.getString(R.string.problem_occured))
                    }
                }
            }
        })
    }

    fun liveDataVisibility(): MutableLiveData<GetUserVisibilityResponse>{
        mldUserVisibility = MutableLiveData()
        return mldUserVisibility
    }

    fun getUpdateUserVisibility(changeVisi: GetUserVisibilityChange){
        mldProgressBar.postValue(true)

        retro.getChangeVisibility(changeVisi).enqueue(object : Callback<GetUserVisibilityResponse>{
            override fun onFailure(call: Call<GetUserVisibilityResponse>, t: Throwable) {
                mldProgressBar.postValue(false)
                showAlert(application.getString(R.string.problem_occured))
            }

            override fun onResponse(call: Call<GetUserVisibilityResponse>,
                                    response: Response<GetUserVisibilityResponse>) {
                mldProgressBar.postValue(false)

                var resp = response.body()

                when(response.code()){
                    DtdConstants.RES_CODE_200->{
                        resp?.checked = changeVisi.checked
                        mldUserVisibility.postValue(resp)
                    }
                    DtdConstants.RES_CODE_NO_INTERNET->{
                        showAlert(app.getString(R.string.connect_internet))
                    }
                    DtdConstants.RES_CODE_401->{
                        var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                        showAlert(unauth.data.message[0])
                    }
                    else->{
                        showAlert(app.getString(R.string.problem_occured))
                    }
                }
            }
        })
    }

    fun liveDataCocktailImage() : MutableLiveData<GetSaveCocktailImages> {
            mldUpdateCocktailImage = MutableLiveData()
        return mldUpdateCocktailImage
    }


    fun liveDataAlluser(): MutableLiveData<GetAllUsesrsChatResponse> {
        liveDataAlluser = MutableLiveData()
        return liveDataAlluser
    }

    fun liveDataChatHistory(): MutableLiveData<GetUserChatHistoryResponse> {
        liveDataChatHistory = MutableLiveData()
        return liveDataChatHistory
    }

    fun liveDataSendMessage(): MutableLiveData<GetSendMessageResponse> {
        liveDataSendMessage = MutableLiveData()
        return liveDataSendMessage
    }

    fun liveDataSeachPeople(): MutableLiveData<List<SearchFilterModel>> {
        liveDataSeachPeople = MutableLiveData()
        return liveDataSeachPeople
    }

    fun liveDataPeoplePretendingSearch(): MutableLiveData<List<SearchFilterModel>> {
        mldPeoplePretendingSearch = MutableLiveData()
        return mldPeoplePretendingSearch
    }

    fun getAllUserChat(user_id : Int){
        mldProgressBar.postValue(true)

        retro.getAllUserChat(user_id)
            .enqueue(object : Callback<GetAllUsesrsChatResponse>{
                override fun onFailure(call: Call<GetAllUsesrsChatResponse>, t: Throwable) {
                    mldProgressBar.postValue(false)
                    showAlert(application.getString(R.string.problem_occured))
                }

                override fun onResponse(call: Call<GetAllUsesrsChatResponse>, response: Response<GetAllUsesrsChatResponse>) {
                    mldProgressBar.postValue(false)

                    var resp = response.body()

                    when(response.code()){
                        DtdConstants.RES_CODE_200->{
                            liveDataAlluser.postValue(resp)
                        }
                        DtdConstants.RES_CODE_NO_INTERNET->{
                            showAlert(app.getString(R.string.connect_internet))
                        }
                        DtdConstants.RES_CODE_401->{
                            var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                            showAlert(unauth.data.message[0])
                        }
                        else->{
                            showAlert(app.getString(R.string.problem_occured))
                        }
                    }
                }
            })
    }

    fun getUserChatHistory(user_id: Int, follow_id: Int){
        mldProgressBar.postValue(true)

        retro.getAllUserChatHistory(user_id,follow_id)
            .enqueue(object : Callback<GetUserChatHistoryResponse>{
                override fun onFailure(call: Call<GetUserChatHistoryResponse>, t: Throwable) {
                    mldProgressBar.postValue(false)
                    showAlert(application.getString(R.string.problem_occured))
                }

                override fun onResponse(call: Call<GetUserChatHistoryResponse>,
                                        response: Response<GetUserChatHistoryResponse>) {

                    mldProgressBar.postValue(false)

                    var resp = response.body()

                    when(response.code()){
                        DtdConstants.RES_CODE_200->{
                            liveDataChatHistory.postValue(resp)
                        }
                        DtdConstants.RES_CODE_NO_INTERNET->{
                            showAlert(app.getString(R.string.connect_internet))
                        }
                        DtdConstants.RES_CODE_401->{
                            var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                            showAlert(unauth.data.message[0])
                        }
                        else->{
                            showAlert(app.getString(R.string.problem_occured))
                        }
                    }
                }
            })
    }

    fun getSearchPeopleFrontend(user_id:Int,latitude: Double, longitude: Double,search: String){

        mldProgressBar.postValue(true)

//        retro.getSearchPeopleFrontend(11896,DtdConstants.DEFAULT_LATITUDE,DtdConstants.DEFAULT_LONGITUDE,search).enqueue(
        retro.getSearchPeopleFrontend(user_id,latitude,longitude,search).enqueue(
            object : Callback<GetSearchPeopleFrontendResponse>{
                override fun onFailure(call: Call<GetSearchPeopleFrontendResponse>, t: Throwable) {
                    mldProgressBar.postValue(false)
                    showAlert(application.getString(R.string.problem_occured))
                }

                override fun onResponse(call: Call<GetSearchPeopleFrontendResponse>,
                                        response: Response<GetSearchPeopleFrontendResponse>) {
                    mldProgressBar.postValue(false)

                    var resp = response.body()

                    when(response.code()){
                        DtdConstants.RES_CODE_200->{
                            var listFilter = dtdUtils.getListOfSearchFromPeopleSearchFrontend(resp)
                            mldBarsSearch.postValue(listFilter)
                        }
                        DtdConstants.RES_CODE_NO_INTERNET->{
                            showAlert(app.getString(R.string.connect_internet))
                        }
                        DtdConstants.RES_CODE_401->{
                            var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                            showAlert(unauth.data.message[0])
                        }
                        else->{
                            showAlert(app.getString(R.string.problem_occured))
                        }
                    }
                }
            })
    }



    fun getSearchPeople(user_id: Int,search: String){

        mldProgressBar.postValue(true)

        retro.getSearchPeople(user_id,search).enqueue(
                object : Callback<GetSearchPeopleResponse>{
                    override fun onFailure(call: Call<GetSearchPeopleResponse>, t: Throwable) {
                        mldProgressBar.postValue(false)
                        showAlert(application.getString(R.string.problem_occured))
                    }

                    override fun onResponse(call: Call<GetSearchPeopleResponse>,
                                            response: Response<GetSearchPeopleResponse>) {
                        mldProgressBar.postValue(false)

                        var resp = response.body()

                        when(response.code()){
                            DtdConstants.RES_CODE_200->{
                                var listFilter = dtdUtils.getListOfSearchFromPeopleSearch(resp)
                                mldBarsSearch.postValue(listFilter)
                            }
                            DtdConstants.RES_CODE_NO_INTERNET->{
                                showAlert(app.getString(R.string.connect_internet))
                            }
                            DtdConstants.RES_CODE_401->{
                                var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                                showAlert(unauth.data.message[0])
                            }
                            else->{
                                showAlert(app.getString(R.string.problem_occured))
                            }
                        }
                    }
                })
    }

    fun getPeople(request: GetPeopleRequest){
        mldProgressBar.postValue(true)

        retro.getGetPeople(request).enqueue(object : Callback<GetPeopleResponse>{
            override fun onFailure(call: Call<GetPeopleResponse>, t: Throwable) {
                mldProgressBar.postValue(false)
                showAlert(application.getString(R.string.problem_occured))
            }

            override fun onResponse(call: Call<GetPeopleResponse>, response: Response<GetPeopleResponse>) {
                mldProgressBar.postValue(false)
                when(response.code()){
                    DtdConstants.RES_CODE_200->{
                        var data = response.body()?.data?.data
                        var searchListPeople = dtdUtils.getPeoplePretendingLikeSearch(response.body())
                        mldPeoplePretendingSearch.postValue(searchListPeople)
                        dtdUtils.prepareMarkerModelListForPeople(data,mldPeople)
                    }
                    DtdConstants.RES_CODE_NO_INTERNET->{
                        showAlert(app.getString(R.string.connect_internet))
                    }
                    DtdConstants.RES_CODE_401->{
                        var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                        showAlert(unauth.data.message[0])
                    }
                    else->{
                        showAlert(app.getString(R.string.problem_occured))
                    }
                }
            }

        })
    }


    fun liveDataFeeback(): MutableLiveData<GetPostFeedbackResponse>{
        mldFeedback = MutableLiveData()
        return mldFeedback
    }

    fun liveDataUpdatePassword(): MutableLiveData<GetUpdatePasswordResponse>{
        liveDataUpdatePassword = MutableLiveData()
        return liveDataUpdatePassword
    }

    fun liveDataForgotPassword(): MutableLiveData<GetForgotPasswordResponse>{
        liveDataForgotPassword = MutableLiveData()
        return liveDataForgotPassword
    }

    fun liveDataStatesAndProvinces(): MutableLiveData<GetStatesAndPrvincesResponse>{
        liveDataStatesAndProvinces = MutableLiveData()
        return liveDataStatesAndProvinces
    }

    fun getStatesAndProvinces(){
        mldProgressBar.postValue(true)

        retro.getStatesAndProvinces().enqueue(object : Callback<GetStatesAndPrvincesResponse>{
            override fun onFailure(call: Call<GetStatesAndPrvincesResponse>, t: Throwable) {
                mldProgressBar.postValue(false)
                showAlert(application.getString(R.string.problem_occured))
            }

            override fun onResponse(call: Call<GetStatesAndPrvincesResponse>, response: Response<GetStatesAndPrvincesResponse>) {

                mldProgressBar.postValue(false)

                when(response.code()){
                    DtdConstants.RES_CODE_200->{
                        liveDataStatesAndProvinces.postValue(response.body())
                    }
                    DtdConstants.RES_CODE_NO_INTERNET->{
                        showAlert(app.getString(R.string.connect_internet))
                    }
                    DtdConstants.RES_CODE_401->{
                        var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                        showAlert(unauth.data.message[0])
                    }
                    else->{
                        showAlert(app.getString(R.string.problem_occured))
                    }
                }
            }
        })
    }

    fun postUpdatePassword(uesr_id: Int, password: String, old_password: String,key: String){
        mldProgressBar.postValue(true)

        retro.postUpdatePassword(uesr_id,password,old_password,key).enqueue(object : Callback<GetUpdatePasswordResponse>{
            override fun onFailure(call: Call<GetUpdatePasswordResponse>, t: Throwable) {
                mldProgressBar.postValue(false)
                showAlert(application.getString(R.string.problem_occured))
            }

            override fun onResponse(call: Call<GetUpdatePasswordResponse>, response: Response<GetUpdatePasswordResponse>) {

                mldProgressBar.postValue(false)

                when(response.code()){
                    DtdConstants.RES_CODE_200->{
                        liveDataUpdatePassword.postValue(response.body())
                    }
                    DtdConstants.RES_CODE_NO_INTERNET->{
                        showAlert(app.getString(R.string.connect_internet))
                    }
                    DtdConstants.RES_CODE_401->{
                        var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                        showAlert(unauth.data.message[0])
                    }
                    else->{
                        showAlert(app.getString(R.string.problem_occured))
                    }
                }

            }

        })
    }

    fun postForgotPassword(email: String){
        mldProgressBar.postValue(true)

        retro.postForgetPassword(email).enqueue(object : Callback<GetForgotPasswordResponse>{
            override fun onFailure(call: Call<GetForgotPasswordResponse>, t: Throwable) {
                mldProgressBar.postValue(false)
                showAlert(application.getString(R.string.problem_occured))
            }

            override fun onResponse(call: Call<GetForgotPasswordResponse>, response: Response<GetForgotPasswordResponse>) {

                mldProgressBar.postValue(false)

                when(response.code()){
                    DtdConstants.RES_CODE_200->{
                        liveDataForgotPassword.postValue(response.body())
                    }
                    DtdConstants.RES_CODE_NO_INTERNET->{
                        showAlert(app.getString(R.string.connect_internet))
                    }
                    DtdConstants.RES_CODE_401->{
                        var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                        showAlert(unauth.data.message[0])
                    }
                    else->{
                        showAlert(app.getString(R.string.problem_occured))
                    }
                }

            }

        })
    }


    fun postFeedback(feedback: String){

        mldProgressBar.postValue(true)

        retro.postFeedback(feedback).enqueue(object : Callback<GetPostFeedbackResponse>{
            override fun onFailure(call: Call<GetPostFeedbackResponse>, t: Throwable) {
                mldProgressBar.postValue(false)
                showAlert(application.getString(R.string.problem_occured))
            }

            override fun onResponse(call: Call<GetPostFeedbackResponse>,
                                    response: Response<GetPostFeedbackResponse>) {

                mldProgressBar.postValue(false)

                when(response.code()){
                    DtdConstants.RES_CODE_200->{
                        mldFeedback.postValue(response.body())
                    }
                    DtdConstants.RES_CODE_NO_INTERNET->{
                        showAlert(app.getString(R.string.connect_internet))
                    }
                    DtdConstants.RES_CODE_401->{
                        var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                        showAlert(unauth.data.message[0])
                    }
                    else->{
                        showAlert(app.getString(R.string.problem_occured))
                    }
                }
            }
        })
    }


    fun getProfile(user_id: Long){

        mldProgressBar.postValue(true)

        retro.getProfile(user_id).enqueue(object : Callback<GetProfileResponse>{
            override fun onFailure(call: Call<GetProfileResponse>, t: Throwable) {
                mldProgressBar.postValue(false)
                showAlert(application.getString(R.string.problem_occured))
            }

            override fun onResponse(call: Call<GetProfileResponse>, response: Response<GetProfileResponse>) {
                mldProgressBar.postValue(false)

                when(response.code()){
                    DtdConstants.RES_CODE_200->{
                        mldProfile.postValue(response.body())
                    }
                    DtdConstants.RES_CODE_NO_INTERNET->{
                        showAlert(app.getString(R.string.connect_internet))
                    }
                    DtdConstants.RES_CODE_401->{
                        var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                        showAlert(unauth.data.message[0])
                    }
                    else->{
                        showAlert(app.getString(R.string.problem_occured))
                    }
                }
            }

        })
    }

    fun getSendMessage(sendMessageRequest: SendMessageRequest){
        mldProgressBar.postValue(true)

        retro.getSendMessage(sendMessageRequest).enqueue(object : Callback<GetSendMessageResponse>{
                override fun onFailure(call: Call<GetSendMessageResponse>, t: Throwable) {
                    mldProgressBar.postValue(false)
                    showAlert(application.getString(R.string.problem_occured))
                }

                override fun onResponse(call: Call<GetSendMessageResponse>, response: Response<GetSendMessageResponse>) {
                    mldProgressBar.postValue(false)

                    var resp = response.body()

                    when(response.code()){
                        DtdConstants.RES_CODE_200->{
                            liveDataSendMessage.postValue(resp)
                        }
                        DtdConstants.RES_CODE_NO_INTERNET->{
                            showAlert(app.getString(R.string.connect_internet))
                        }
                        DtdConstants.RES_CODE_401->{
                            var unauth = getUnauthorizedResponseModel(response.errorBody()?.string())
                            showAlert(unauth.data.message[0])
                        }
                        else->{
                            showAlert(app.getString(R.string.problem_occured))
                        }
                    }
                }
            })
    }


    fun getDeleteCocktailImage(imageNum: Int,filePath: String?){

        object : AsyncTask<Void,Void,Void>(){

            override fun onPreExecute() {
                super.onPreExecute()
                mldProgressBar.postValue(true)
            }

            override fun doInBackground(vararg params: Void?): Void? {
                getCocktailPicsDelete(mldUpdateCocktailImage,imageNum,filePath)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                mldProgressBar.postValue(false)
            }
        }.execute()
    }


    fun getUpdateCocktailImages(imageNum: Int,filePath: String?){

        object : AsyncTask<Void,Void,Void>(){

            override fun onPreExecute() {
                super.onPreExecute()
                mldProgressBar.postValue(true)
            }

            override fun doInBackground(vararg params: Void?): Void? {
                getCocktailPicsUpload(mldUpdateCocktailImage,imageNum,filePath)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                mldProgressBar.postValue(false)
            }
        }.execute()
    }

    fun GetMultipartRequestToUpdateProfile(mldString: MutableLiveData<GetUpdateProfileResponse>, request: GetUserUpdateProfileRequest) {
        val id = "id"
        val token = "token"
        val name = "name"
        val email = "email"
        val phone = "phone"
        val username = "username"
        val role = "role"
        val address = "address"
        val city = "city"
        val state = "state"
        val country = "country"
        val my_status = "my_status"
        val work_at = "work_at"
        val fav_spirit = "fav_spirit"
        val fav_cocktail = "fav_cocktail"
        val fav_drink = "fav_drink"
        val visibility_status = "visibility_status"
        val profile_image = "profile_image"
        val fav_liquor = "fav_liquor"
        val fav_alcohol = "fav_alcohol"
        val speciality = "speciality"
        val outing_day = "outing_day"
        val alcohol_online = "alcohol_online"


        val response: okhttp3.Response
        try {
            val client = OkHttpClient.Builder()
            client.readTimeout(1, TimeUnit.MINUTES)
            var MEDIA_TYPE: MediaType? = null
            MEDIA_TYPE = "image/jpeg".toMediaTypeOrNull()
            val multipartBuilder = MultipartBody.Builder()
            multipartBuilder.setType(MultipartBody.FORM)
            multipartBuilder.addFormDataPart(id, Integer.toString(request.id))
            multipartBuilder.addFormDataPart(token, request.token)
            multipartBuilder.addFormDataPart(name, request.name)
            multipartBuilder.addFormDataPart(email, request.email)
            multipartBuilder.addFormDataPart(phone, request.phone)
            multipartBuilder.addFormDataPart(username, request.username)
            multipartBuilder.addFormDataPart(role, Integer.toString(request.role))
            multipartBuilder.addFormDataPart(address, request.address)
            multipartBuilder.addFormDataPart(city, request.city)
            multipartBuilder.addFormDataPart(state, request.state)
            multipartBuilder.addFormDataPart(country, request.country)
            multipartBuilder.addFormDataPart(my_status, request.my_status)
            multipartBuilder.addFormDataPart(alcohol_online, request.alcohol_online)
            multipartBuilder.addFormDataPart(outing_day, request.outing_day)
            //            multipartBuilder.addFormDataPart(my_status,Integer.toString(request.getVisibiltiy_status()));
            multipartBuilder.addFormDataPart(
                work_at,
                request.work_at.toString()
            )
            multipartBuilder.addFormDataPart(fav_spirit, request.fav_spirit)
            multipartBuilder.addFormDataPart(fav_cocktail, request.fav_cocktail)
            multipartBuilder.addFormDataPart(fav_drink, request.favouriteDrink)
            multipartBuilder.addFormDataPart(fav_liquor, request.fav_liquore)
            multipartBuilder.addFormDataPart(fav_alcohol, request.fav_alcohol)
            multipartBuilder.addFormDataPart(speciality, request.special)
            multipartBuilder.addFormDataPart(visibility_status, Integer.toString(request.visibiltiy_status))
            if (request.fileToUpload != null && request.fileToUpload != "") {
                val file = File(request.fileToUpload)
                if (file != null) {
                    multipartBuilder.addFormDataPart(
                        profile_image,
                        file.name,
                        RequestBody.create(MEDIA_TYPE, file)
                    )
                }
            }

            val requestBody: RequestBody = multipartBuilder.build()
            val requestt = Request.Builder()
                .url(APIs.BASE_URL + APIs.A_UPDATE_USER_PROFILE)
                .post(requestBody)
                .build()
            response = client.build().newCall(requestt).execute()

            if (response?.body != null) {

                var updateProfile = Gson().fromJson<GetUpdateProfileResponse>(
                    response.body?.string(),
                    GetUpdateProfileResponse::class.java)

                var dataStat = updateProfile.data
                dtdUtils.saveForUpdateProfile(dataStat)

                updateProfile.data.message[0]?.let {
                    showAlert(it)
                }

//                mldString.postValue(updateProfile)
            }

        } catch (e: Exception) {
            showAlert(App.app.getString(R.string.problem_occured))
            mldProgressBar.postValue(false)
            e.printStackTrace()
        }
    }


    fun getMessageSendPics(req: SendMessageRequest, file: File?){
        var from = req?.from?.toString()?:""
        var to = req?.to?.toString()?:""
        var user_id = req?.user_id?.toString()?:""
        var follow_id = req?.follow_id?.toString()?:""
        var message = req?.message?.toString()?:""
        var device_token = req?.device_token.toString()?:""

       var asynch = object :AsyncTask<String,String,String>(){
            override fun doInBackground(vararg params: String?): String {
                try {
                    val client = OkHttpClient.Builder()
                    client.readTimeout(30, TimeUnit.SECONDS)
                    var MEDIA_TYPE: MediaType? = null
                    MEDIA_TYPE = "image/jpeg".toMediaTypeOrNull()
                    val multipartBuilder = MultipartBody.Builder()
                    multipartBuilder.setType(MultipartBody.FORM)
                    multipartBuilder.addFormDataPart("from", from)
                    multipartBuilder.addFormDataPart("to", to)
                    multipartBuilder.addFormDataPart("user_id", user_id)
                    multipartBuilder.addFormDataPart("follow_id", follow_id)
                    multipartBuilder.addFormDataPart("message", message)
                    multipartBuilder.addFormDataPart("device_token", device_token)

                    if (file != null) {
                        multipartBuilder.addFormDataPart(
                            "message_image",
                            file.name,
                            RequestBody.create(MEDIA_TYPE, file)
                        )
                    }

                    val requestBody: RequestBody = multipartBuilder.build()
                    val requestt = Request.Builder()
                        .url(APIs.BASE_URL + APIs.A_SEND_MESSAGE)
                        .post(requestBody)
                        .build()

                    var response = client.build().newCall(requestt).execute()


                    if (response?.body != null) {
                        var sendMessage = Gson().fromJson<GetSendMessageResponse>(
                            response.body?.string(),
                            GetSendMessageResponse::class.java)

                        liveDataSendMessage.postValue(sendMessage)

                    }
                } catch (e: Exception) {
                    showAlert(App.app.getString(R.string.problem_occured))
                    mldProgressBar.postValue(false)
                    e.printStackTrace()
                }
                return String()
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
            }
        }

        asynch.execute()
    }



    fun getCocktailPicsDelete(liveData: MutableLiveData<GetSaveCocktailImages>,
                              imageNum: Int,
                              image: String?){

        val id = "id"
        val drink_image = "drink_image"
        val cocktail_image = "cocktail_image"
        val placeholder = "placeholder"

        var imageNumber:String

        imageNumber = when(imageNum){
            1->{
                DtdConstants.DRINK_IMAGE_1
            }
            2->{
                DtdConstants.DRINK_IMAGE_2
            }
            3->{
                DtdConstants.DRINK_IMAGE_3
            }
            else->{
                ""
            }
        }


        try {
            val client = OkHttpClient.Builder()
            client.readTimeout(30, TimeUnit.SECONDS)
            var MEDIA_TYPE: MediaType? = null
            MEDIA_TYPE = "image/jpeg".toMediaTypeOrNull()
            val multipartBuilder = MultipartBody.Builder()
            multipartBuilder.setType(MultipartBody.FORM)
            multipartBuilder.addFormDataPart(id, prefs.getInt(DtdConstants.ID).toString())
            multipartBuilder.addFormDataPart(drink_image, imageNumber)
            multipartBuilder.addFormDataPart(placeholder, "placeholder")
            multipartBuilder.addFormDataPart(cocktail_image, "")

//            if (image != null && image != "") {
//                val file = File(image)
//                if (file != null) {
//                    multipartBuilder.addFormDataPart(
//                        cocktail_image,
//                        file.name,
//                        RequestBody.create(MEDIA_TYPE, file)
//                    )
//                }
//            }

            val requestBody: RequestBody = multipartBuilder.build()
            val requestt = Request.Builder()
                .url(APIs.BASE_URL + APIs.A_UPDATE_COCKTAIL_IMAGE)
                .post(requestBody)
                .build()

            var response = client.build().newCall(requestt).execute()


            if (response?.body != null) {
                var updateCocktail = Gson().fromJson<GetSaveCocktailImages>(
                    response.body?.string(),
                    GetSaveCocktailImages::class.java)

                var dataStat = updateCocktail.data

                prefs.saveString(DtdConstants.DRINK_IMAGE_1,dataStat?.drinkImage1)
                prefs.saveString(DtdConstants.DRINK_IMAGE_2,dataStat?.drinkImage2)
                prefs.saveString(DtdConstants.DRINK_IMAGE_3,dataStat?.drinkImage3)

                liveData.postValue(updateCocktail)

            }
        } catch (e: Exception) {
            showAlert(App.app.getString(R.string.problem_occured))
            mldProgressBar.postValue(false)
            e.printStackTrace()
        }
    }



    fun getCocktailPicsUpload(liveData: MutableLiveData<GetSaveCocktailImages>,
                              imageNum: Int,
                              image: String?){

        val id = "id"
        val drink_image = "drink_image"
        val cocktail_image = "cocktail_image"

        var imageNumber:String

        imageNumber = when(imageNum){
            1->{
                DtdConstants.DRINK_IMAGE_1
            }
            2->{
                DtdConstants.DRINK_IMAGE_2
            }
            3->{
                DtdConstants.DRINK_IMAGE_3
            }
            else->{
                ""
            }
        }


        try {
            val client = OkHttpClient.Builder()
            client.readTimeout(30, TimeUnit.SECONDS)
            var MEDIA_TYPE: MediaType? = null
            MEDIA_TYPE = "image/jpeg".toMediaTypeOrNull()
            val multipartBuilder = MultipartBody.Builder()
            multipartBuilder.setType(MultipartBody.FORM)
            multipartBuilder.addFormDataPart(id, prefs.getInt(DtdConstants.ID).toString())
            multipartBuilder.addFormDataPart(drink_image, imageNumber)

            if (image != null && image != "") {
                val file = File(image)
                if (file != null) {
                    multipartBuilder.addFormDataPart(
                        cocktail_image,
                        file.name,
                        RequestBody.create(MEDIA_TYPE, file)
                    )
                }
            }

            val requestBody: RequestBody = multipartBuilder.build()
            val requestt = Request.Builder()
                .url(APIs.BASE_URL + APIs.A_UPDATE_COCKTAIL_IMAGE)
                .post(requestBody)
                .build()

            var response = client.build().newCall(requestt).execute()


            if (response?.body != null) {
                var updateCocktail = Gson().fromJson<GetSaveCocktailImages>(
                    response.body?.string(),
                    GetSaveCocktailImages::class.java)

                var dataStat = updateCocktail.data

                prefs.saveString(DtdConstants.DRINK_IMAGE_1,dataStat?.drinkImage1)
                prefs.saveString(DtdConstants.DRINK_IMAGE_2,dataStat?.drinkImage2)
                prefs.saveString(DtdConstants.DRINK_IMAGE_3,dataStat?.drinkImage3)

                liveData.postValue(updateCocktail)

            }
        } catch (e: Exception) {
            showAlert(App.app.getString(R.string.problem_occured))
            mldProgressBar.postValue(false)
            e.printStackTrace()
        }
    }

    fun getUnauthorizedResponseModel(resp: String?) : GetUnauthorizedResponse {

        var unauthResp = GetUnauthorizedResponse()

        var dataUnAuth = Data()


        unauthResp.status = false
        var listMessages = mutableListOf<String>()
        listMessages.add(DtdConstants.PROBLEM_OCCURED)
        dataUnAuth.message = listMessages
        unauthResp.data = dataUnAuth

        try{

        try {
            resp?.let {
                unauthResp = Gson().fromJson(resp, GetUnauthorizedResponse::class.java)
            }

        }catch (e: Exception){
            try {
                var unauthresp2 = Gson().fromJson(resp, GetUnauthorizedResponse2::class.java)
                unauthResp.status = unauthresp2.status
                var listMessages = mutableListOf<String>()
                listMessages.add(unauthresp2.data)
                var dataUnAuth = Data()
                dataUnAuth.message = listMessages
                unauthResp.data = dataUnAuth
            }catch (e: java.lang.Exception){
                unauthResp.status = false
                var listMessages = mutableListOf<String>()
                listMessages.add(DtdConstants.PROBLEM_OCCURED)
                var dataUnAuth = Data()
                dataUnAuth.message = listMessages
                unauthResp.data = dataUnAuth
                e.printStackTrace()
            }
            e.printStackTrace()
        }

        }catch (e:java.lang.Exception){
            e.printStackTrace()
        }

        return unauthResp
    }

}
