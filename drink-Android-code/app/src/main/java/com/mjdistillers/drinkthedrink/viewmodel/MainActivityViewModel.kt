package com.mjdistillers.drinkthedrink.viewmodel

import android.app.Application
import android.os.Handler
import android.os.Message
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mjdistillers.drinkthedrink.dialogs.AllNotificationsDialog
import com.mjdistillers.drinkthedrink.model.DTDRepository
import com.mjdistillers.drinkthedrink.model.MarkerModel
import com.mjdistillers.drinkthedrink.model.SearchFilterModel
import com.mjdistillers.drinkthedrink.model.request.*
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
import com.mjdistillers.drinkthedrink.model.response.get_profile.GetProfileResponse
import com.mjdistillers.drinkthedrink.model.response.login.GetUserLoginResponse
import com.mjdistillers.drinkthedrink.model.response.registration.GetUserRegisterationResponse
import com.mjdistillers.drinkthedrink.model.response.request_push_notification.GetPushNotificationResponse
import com.mjdistillers.drinkthedrink.model.response.search_people.GetSearchPeopleResponse
import com.mjdistillers.drinkthedrink.model.response.send_message.GetSendMessageResponse
import com.mjdistillers.drinkthedrink.model.response.states_provinces.GetStatesAndPrvincesResponse
import com.mjdistillers.drinkthedrink.model.response.store_detail.GetStoresDetailResponse
import com.mjdistillers.drinkthedrink.model.response.stores_search.GetStoreSearchResponse
import com.mjdistillers.drinkthedrink.model.response.update_cocktail.GetSaveCocktailImages
import com.mjdistillers.drinkthedrink.model.response.update_password.GetUpdatePasswordResponse
import com.mjdistillers.drinkthedrink.model.response.update_profile.GetUpdateProfileResponse
import com.mjdistillers.drinkthedrink.model.response.update_user.GetUpdateUserResponse
import com.mjdistillers.drinkthedrink.model.response.user_block.GetBlockUserResp
import com.mjdistillers.drinkthedrink.model.response.user_chat_history.GetUserChatHistoryResponse
import com.mjdistillers.drinkthedrink.model.response.user_visibility.GetUserVisibilityResponse
import java.io.File

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

        var repository: DTDRepository = DTDRepository(application)


        fun liveDataBars():  MutableLiveData<ArrayList<MarkerModel>> {
            return repository.liveDataBars()
        }

        fun liveDataSnackabr(): MutableLiveData<Message>{
            return repository.liveDataSnackabr()
        }


       fun getBarsData(barsData: GetBarsDataRequest, handler: Handler) {
             repository.getBarsData(barsData,handler)
       }

        fun liveDataBarsDetails() : MutableLiveData<GetBarsDetailsResponse?> {
            return repository.liveDataBarsDetail()
        }

        fun getBarsDetail(barsRequest: GetBarDetailsRequest){
            repository.getBarsDetail(barsRequest)
        }

       fun getFrontAds(): MutableLiveData<GetFrontAdsResponse>{
            return repository.getFrontAds()
       }

         fun liveDataLogin() : MutableLiveData<GetUserLoginResponse> {
             return repository.liveDataLogin()
         }


        fun getUserLogin(loginRequest: GetUserLoginRequest){
            repository.getUserLogin(loginRequest)
        }

        fun liveDataRegistration()  : MutableLiveData<GetUserRegisterationResponse> {
            return repository.liveDataSignUp()
        }

    fun liveDataUpdateUser()  : MutableLiveData<GetUpdateUserResponse> {
        return repository.liveDataUpdateUser()
    }

        fun getUserRegistration(loginRequest: GetRegistrationRequest){
            return repository.getSignUpUser(loginRequest)
        }


        fun liveDataFollowing(): MutableLiveData<GetFollowingResponse>{
            return repository.liveDataFollowing()
        }

        fun getFollowing(id: Long) {
            return repository.getFollowing(id)
        }

        fun getCities(country: String, state_code : String) {
            return repository.postCities(country,state_code)
        }

        fun liveDataCities() : MutableLiveData<GetCitiesResponse> {
            return repository.liveDataCities()
        }

        fun liveDataFollowers() : MutableLiveData<GetFollowersResponse> {
            return repository.liveDataFollowers()
        }
        fun liveDataBlock() : MutableLiveData<GetBlockUserResp> {
            return repository.liveDataBlockRequest()
         }

        fun postBlockHit(user_id: Int, block_status: Int, block_id: Int){
            repository.postBlockUser(user_id,block_status,block_id)
        }

        fun getFollowers(id: Long) {
            return repository.getFollowers(id)
        }

        fun liveDataUpdateProfile() :  MutableLiveData<GetUpdateProfileResponse>{
            return repository.liveDataUpdateProfile()
        }

        fun liveDataUpdatePassword(): MutableLiveData<GetUpdatePasswordResponse>{
            return repository.liveDataUpdatePassword()
        }

        fun getUpdatePassword(userId: Int, password: String, old_password: String,key: String){
            repository.postUpdatePassword(userId,password, old_password,key)
        }

        fun liveDataForgotPassword(): MutableLiveData<GetForgotPasswordResponse>{
            return repository.liveDataForgotPassword()
        }

        fun getForgotPassword(email: String){
            repository.postForgotPassword(email)
        }

        fun getUpdateProfile(updateRequest :GetUserUpdateProfileRequest){
            return repository.getUpdateUserProfile(updateRequest)
        }

        fun getEditProfile(isHitNoUI: Boolean) : MutableLiveData<GetEditProfile> {
            return repository.getEditProfile(isHitNoUI)
        }

        fun getEditProfileNoUI():MutableLiveData<GetEditProfile>{
            return repository.liveDataEditProfileNoUI()
         }


    fun liveDataUpdateUserVisiblity() : MutableLiveData<GetUserVisibilityResponse> {
            return repository.liveDataVisibility()
        }

        fun getUpdateVisibility(updateVisi: GetUserVisibilityChange){
            repository.getUpdateUserVisibility(updateVisi)
        }

        fun liveDataCocktailImage()   : MutableLiveData<GetSaveCocktailImages>{
            return repository.liveDataCocktailImage()
        }

        fun getUpdateCocktailImage(imageNumber: Int, filePath: String?){
            return repository.getUpdateCocktailImages(imageNumber,filePath)
        }

        fun getDeleteCocktailImage(imageNumber: Int, filePath: String?){
            return repository.getDeleteCocktailImage(imageNumber,filePath)
        }

        fun getShareImageInChat(messageRequest: SendMessageRequest, filePath: File){
            return repository.getMessageSendPics(messageRequest,filePath)
        }


        fun getProgressBar(): MutableLiveData<Boolean>{
            return repository.getProgressLiveData()
        }

        fun getAlertLiveData():MutableLiveData<Message>{
            return repository.getAlertLiveData()
        }

        fun liveDataStores():MutableLiveData<ArrayList<MarkerModel>>{
            return repository.liveDataStores()
        }

        fun  getDataStores(stores: GetStoresRequest){
            repository.getStores(stores)
        }


        fun liveDataStoresDetail():MutableLiveData<GetStoresDetailResponse>{
            return repository.liveDataStoreDetail()
        }

        fun  getDataStoresDetail(stores: GetStoresDetailRequest){
            repository.getStoreDetails(stores)
        }


        fun liveDataSearchStores(): MutableLiveData<List<SearchFilterModel>>{
            return repository.liveDataStoresSearch()
        }

        fun liveDataSearchBars(): MutableLiveData<List<SearchFilterModel>>{
            return repository.liveDataBarsSearch()
        }

        fun getDataSearchBars(barsRequest: GetSearchBarsRequest){
            repository.getBarsSearch(barsRequest)
        }

        fun getDataSearchStores(storesRequest: GetStoreSearchRequest){
            repository.getStoreSearch(storesRequest)
        }

        fun getAllUserChat(user_id: Int){
            repository.getAllUserChat(user_id)
        }

        fun getChatHistory(user_id: Int, follow_id: Int){
            repository.getUserChatHistory(user_id,follow_id)
        }


        fun getSendMessage(sendMessageRequest: SendMessageRequest){
            repository.getSendMessage(sendMessageRequest)
        }


        fun getSearchPeople(user_id: Int,search: String){
            repository.getSearchPeople(user_id,search)
        }

        fun getSearchPeopleFrontend(user_id: Int,latitude: Double,longitude: Double,search: String){
            repository.getSearchPeopleFrontend(user_id,latitude,longitude,search)
        }

        fun getPeople(getPeopleRequest: GetPeopleRequest){
            repository.getPeople(getPeopleRequest)
        }

        fun getProfile(user_id : Long){
                repository.getProfile(user_id)
        }



        fun liveDataAlluser(): MutableLiveData<GetAllUsesrsChatResponse>{
                return repository.liveDataAlluser()
        }

        fun liveDataChatHistory(): MutableLiveData<GetUserChatHistoryResponse>{
            return repository.liveDataChatHistory()
        }

        fun liveDataSendMessage(): MutableLiveData<GetSendMessageResponse>{
            return repository.liveDataSendMessage()
        }

        fun liveDataSeachPeople(): MutableLiveData<List<SearchFilterModel>>{
            return repository.liveDataSeachPeople()
        }

        fun liveDataPretendingLikeSearch(): MutableLiveData<List<SearchFilterModel>>{
            return repository.liveDataPeoplePretendingSearch()
        }

        fun liveDataGetPeople(): MutableLiveData<ArrayList<MarkerModel>>{
            return repository.liveDataPeople()
        }

        fun liveDataGetProfile(): MutableLiveData<GetProfileResponse>{
            return repository.liveDataProfile()
        }

        fun liveDataRequestPushNotification(): MutableLiveData<GetPushNotificationResponse>{
            return repository.liveDataRequestPushNotification()
        }


        fun liveDataFollowUnFollowTo(): MutableLiveData<GetFollowUnfollowTO_BackResponse_Decline_Logout>{
            return repository.liveDataFollowUnFollowTo()
        }

        fun liveDataFollowUnFollowBack(): MutableLiveData<GetFollowUnfollowTO_BackResponse_Decline_Logout>{
            return repository.liveDataFollowUnFollowBack()

        }

        fun liveDataDeclineRequest(): MutableLiveData<GetFollowUnfollowTO_BackResponse_Decline_Logout>{
            return repository.liveDataDeclineRequest()
         }

        fun liveDataLogout(): MutableLiveData<GetFollowUnfollowTO_BackResponse_Decline_Logout>{
            return repository.liveDataLogout()
        }

        fun  getRequestPushNotification(user_id:Int, user_name: String, follow_id : Int){
            repository.getRequestPushNotification(user_id,user_name,follow_id)
        }

        fun getFollowUnfollowTo(user_id:Int, follow_id: Int, followStatus : Int, device_token: String){
            repository.getFollowUnfollowTo(user_id,follow_id,followStatus,device_token)
        }

        fun getFollowUnfollowBack(user_id:Int, follow_id: Int, followStatus : Int, device_token: String){
            repository.getFollowUnfollowBack(user_id,follow_id,followStatus,device_token)
        }

        fun getLogout(user_id: Int, device_token: String){
            repository.getLogOut(user_id,device_token)
        }


        fun liveDataAllNotifications(): MutableLiveData<GetAllNotificationsResponse>{
            return repository.liveDataAllNotifications()
        }

        fun getAllNotifications(user_id: Int){
            repository.getAllNotifications(user_id)
        }

        fun getDeclineRequest(user_id:Int, follow_id : Int) {
            repository.getDeclineRequest(user_id,follow_id)
        }

        fun liveDataFeedback(): MutableLiveData<GetPostFeedbackResponse>{
            return repository.liveDataFeeback()
        }



        fun postFeedback(feedback: String){
            repository.postFeedback(feedback)
        }


        fun liveDataStatesAndProvinces(): MutableLiveData<GetStatesAndPrvincesResponse>{
            return repository.liveDataStatesAndProvinces()
        }

        fun getStatesAndProvinces(){
            repository.getStatesAndProvinces()
        }


        fun liveDataFUBar() : MutableLiveData<FUBarStoreTeamResponse>{
            return repository.liveDataFUBar()
        }

        fun liveDataFUStore() : MutableLiveData<FUBarStoreTeamResponse>{
            return repository.liveDataFUStore()
        }


        fun liveDataFUBarTeam() : MutableLiveData<FUBarStoreTeamResponse>{
                return repository.liveDataFUBarTeam()
        }

        fun liveDataFUStoreTeam() : MutableLiveData<FUBarStoreTeamResponse>{
            return repository.liveDataFUStoreTeam()
        }

        fun liveDataFUPeople(): MutableLiveData<FUBarStoreTeamResponse>{
            return repository.liveDataFUPeople()
        }

        fun postFUPeople(followStatus: Int, id: Int, follow_id: Int,device_token: String,user_device_token : String){
            repository.getFUPeople(followStatus, id, follow_id, device_token,user_device_token )
        }

        fun postFUBar(followStatus: Int, bar_id: Int, user_id: Int){
            repository.getFUBar(followStatus, bar_id, user_id)
        }

        fun postFUStore(followStatus: Int, store_id: Int, user_id: Int){
            repository.getFUStore(followStatus, store_id, user_id)
         }

        fun postFUBarTeam(followStatus: Int, bar_id: Int, follow_id: Int, device_token: String, user_id : Int){
            repository.getFUTeamBar(followStatus, bar_id, follow_id, device_token,user_id)
        }

        fun postFUStoreTeam(followStatus: Int, store_id: Int, follow_id: Int, device_token: String, user_id : Int){
            repository.getFUTeamStore(followStatus, store_id, follow_id, device_token,user_id)
         }

        fun postUpdateUser(updateRole: UpdateRoleRequest){
            repository.postUpdateUser(updateRole)
        }
}