package com.mjdistillers.drinkthedrink.model

import com.mjdistillers.drinkthedrink.APIs
import com.mjdistillers.drinkthedrink.model.request.*
import com.mjdistillers.drinkthedrink.model.response.GetBarsDataResponse
import com.mjdistillers.drinkthedrink.model.response._all_notifications.GetAllNotificationsResponse
import com.mjdistillers.drinkthedrink.model.response.bar_details.GetBarsDetailsResponse
import com.mjdistillers.drinkthedrink.model.response.bar_search.GetBarsSearchResponse
import com.mjdistillers.drinkthedrink.model.response.chat_all_user.GetAllUsesrsChatResponse
import com.mjdistillers.drinkthedrink.model.response.stores_search.GetStoreSearchResponse
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
import com.mjdistillers.drinkthedrink.model.response.update_password.GetUpdatePasswordResponse
import com.mjdistillers.drinkthedrink.model.response.update_user.GetUpdateUserResponse
import com.mjdistillers.drinkthedrink.model.response.user_block.GetBlockUserResp
import com.mjdistillers.drinkthedrink.model.response.user_chat_history.GetUserChatHistoryResponse
import com.mjdistillers.drinkthedrink.model.response.user_visibility.GetUserVisibilityResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitApisInterface {

    @POST(APIs.A_GET_BARS_DATA)
    fun getBarsData(@Body barDataReq: GetBarsDataRequest):Call<GetBarsDataResponse>

    @POST(APIs.A_BAR_DETIAL)
    fun getBarDetail(@Body barDetail: GetBarDetailsRequest): Call<GetBarsDetailsResponse>

    @GET(APIs.A_FRONT_ADS)
    fun getFullAds(): Call<GetFrontAdsResponse>

    @POST(APIs.A_USER_LOGIN)
    fun getUserLogin(@Body userLoginRequest: GetUserLoginRequest): Call<GetUserLoginResponse>

    @POST(APIs.A_USER_SIGN_UP)
    fun getUserSignUp(@Body userRegistration: GetRegistrationRequest): Call<GetUserRegisterationResponse>

    @POST(APIs.A_GET_FOLLOWERS)
    fun getFollowers(@Query("id") id: Long): Call<GetFollowersResponse>

    @POST(APIs.A_GET_FOLLOWING)
    fun getFollowing(@Query("id") id: Long): Call<GetFollowingResponse>

    @Multipart
    @POST(APIs.A_UPDATE_USER_PROFILE)
    fun getUpdateUserProfile(@PartMap partMap:Map<String, RequestBody>): Call<String>

    @POST(APIs.A_EDIT_PROFILE)
    fun getEditProfile(@Body editUserReq: GetEditUserRequest): Call<GetEditProfile>

    @POST(APIs.A_CHANGE_VISIBILITY)
    fun getChangeVisibility(@Body visibilityChange: GetUserVisibilityChange): Call<GetUserVisibilityResponse>

    @POST(APIs.A_GET_STORES)
    fun getStores(@Body stores: GetStoresRequest): Call<GetStoresResponse>

    @POST(APIs.A_GET_STORES_DETAIL)
    fun getStoresDetail(@Body stores: GetStoresDetailRequest): Call<GetStoresDetailResponse>

    @POST(APIs.A_GET_SEARCH_BARS)
    fun getSearchBars(@Body bars: GetSearchBarsRequest): Call<GetBarsSearchResponse>


    @POST(APIs.A_GET_SEARCH_STORES)
    fun getSearchStores(@Body stores: GetStoreSearchRequest): Call<GetStoreSearchResponse>

    @POST(APIs.A_GET_FEEDBACK)
    fun getFeedback(@Body stores: GetStoreSearchRequest): Call<GetStoresDetailResponse>

    @POST(APIs.A_UPDATE_ROLE)
    fun postUpdateRole(@Body updateRole: UpdateRoleRequest) : Call<GetUpdateUserResponse>

    @FormUrlEncoded
    @POST(APIs.A_GET_ALL_USER_CHAT)
    fun getAllUserChat(@Field("user_id") user_id: Int): Call<GetAllUsesrsChatResponse>

    @FormUrlEncoded
    @POST(APIs.A_GET_USER_CHAT_HISTORY)
    fun getAllUserChatHistory(@Field("user_id") user_id: Int,
                              @Field("follow_id") follow_id: Int) : Call<GetUserChatHistoryResponse>

    @POST(APIs.A_SEND_MESSAGE)
    fun getSendMessage(@Body sendMessageRequest: SendMessageRequest): Call<GetSendMessageResponse>

    @FormUrlEncoded
    @POST(APIs.A_SEARCH_PEOPLE)
    fun getSearchPeople(@Field("user_id") longitude: Int,
                        @Field("search") search: String): Call<GetSearchPeopleResponse>

    @FormUrlEncoded
    @POST(APIs.A_SEARCH_FRONTEND)
    fun getSearchPeopleFrontend(@Field("user_id") user_id: Int,
                                @Field("latitude") latitude: Double,
                                @Field("longitude") longitude: Double,
                                @Field("search") search: String): Call<GetSearchPeopleFrontendResponse>


    @POST(APIs.A_GET_PEOPLE)
    fun getGetPeople(@Body sendMessageRequest: GetPeopleRequest): Call<GetPeopleResponse>

    @FormUrlEncoded
    @POST(APIs.A_GET_PROFILE)
    fun getProfile(@Field("user_id") user_id: Long): Call<GetProfileResponse>

    @FormUrlEncoded
    @POST(APIs.A_REQUEST_PUSH_NOTIFICATION)
    fun getRequestPushNotification(@Field("user_id") user_id: Int,
                                   @Field("user_name") user_name: String,
                                   @Field("follow_id") follow_id: Int): Call<GetPushNotificationResponse>


    @FormUrlEncoded
    @POST(APIs.A_FOLLOW_TO_UNFOLLOW_TO)
    fun getFollowUnfollowTo(@Field("id") user_id: Int,
                            @Field("follow_id") user_name: Int,
                            @Field("followStatus") followStatus: Int,
                            @Field("device_token") device_token: String): Call<GetFollowUnfollowTO_BackResponse_Decline_Logout>


    @FormUrlEncoded
    @POST(APIs.A_FOLLOW_BACK_UNFOLLOW_BACK)
    fun getFollowUnfollowBack(@Field("id") user_id: Int,
                            @Field("follow_id") follow_id: Int,
                            @Field("followStatus") followStatus: Int,
                            @Field("device_token") device_token: String): Call<GetFollowUnfollowTO_BackResponse_Decline_Logout>

    @FormUrlEncoded
    @POST(APIs.A_DECLINE_REQUEST)
    fun getDeclineRequest(@Field("user_id") user_id: Int,
                              @Field("follow_id") follow_id: Int): Call<GetFollowUnfollowTO_BackResponse_Decline_Logout>


    @FormUrlEncoded
    @POST(APIs.A_GET_ALL_NOTIFICATIONS)
    fun getAllNotification(@Field("id") user_id: Int): Call<GetAllNotificationsResponse>

    @FormUrlEncoded
    @POST(APIs.A_LOGOUT)
    fun getLogout(@Field("id") id: Int,
                  @Field("device_token") device_token: String): Call<GetFollowUnfollowTO_BackResponse_Decline_Logout>


    @FormUrlEncoded
    @POST(APIs.A_FORGOT_PASSWORD)
    fun postForgetPassword(@Field("email") email: String) : Call<GetForgotPasswordResponse>

    @FormUrlEncoded
    @POST(APIs.A_UPDATE_PASSWORD)
    fun postUpdatePassword(@Field("user_id") user_id: Int,@Field("password") password: String,
                           @Field("old_password") old_password: String,@Field("key") key: String) : Call<GetUpdatePasswordResponse>


    @FormUrlEncoded
    @POST(APIs.A_GET_FEEDBACK)
    fun postFeedback(@Field("feedback") feedback: String) : Call<GetPostFeedbackResponse>

    @FormUrlEncoded
    @POST(APIs.A_GET_CITIES)
    fun postGetCities(@Field("country") country: String,
                      @Field("state_code") state_code: String) : Call<GetCitiesResponse>


    @GET(APIs.A_STATES_PROVINCES)
    fun getStatesAndProvinces(): Call<GetStatesAndPrvincesResponse>

    @FormUrlEncoded
    @POST(APIs.A_BAR_FOLLOW_UNFOLLOW)
    fun postFUBar(@Field("followStatus") followStatus: Int,
                           @Field("bar_id") bar_id: Int,
                           @Field("user_id") user_id: Int): Call<FUBarStoreTeamResponse>

    @FormUrlEncoded
    @POST(APIs.A_STORE_FOLLOW_UNFOLLOW)
    fun postFUStore(@Field("followStatus") followStatus: Int,
                  @Field("store_id") store_id: Int,
                  @Field("user_id") user_id: Int): Call<FUBarStoreTeamResponse>

    @FormUrlEncoded
    @POST(APIs.A_BAR_TEAM_FOLLOW_UNFOLLOW)
    fun postFUBarTeam(@Field("followStatus") followStatus: Int,
                    @Field("bar_id") bar_id: Int,
                    @Field("follow_id") follow_id: Int,
                    @Field("device_token") device_token: String,
                    @Field("user_id") user_id : Int): Call<FUBarStoreTeamResponse>

    @FormUrlEncoded
    @POST(APIs.A_STORE_TEAM_FOLLOW_UNFOLLOW)
    fun postFUStoreTeam(@Field("followStatus") followStatus: Int,
                      @Field("store_id") store_id: Int,
                      @Field("follow_id") follow_id: Int,
                      @Field("device_token") device_token: String,
                      @Field("user_id") user_id : Int): Call<FUBarStoreTeamResponse>

    @FormUrlEncoded
    @POST(APIs.A_FOLLOW_UNFOLLOW_PEOPLE)
    fun postFUPeople(@Field("followStatus") followStatus: Int,
                        @Field("id") store_id: Int,
                        @Field("follow_id") follow_id: Int,
                        @Field("device_token") device_token: String,
                        @Field("user_device_token") user_device_token : String): Call<FUBarStoreTeamResponse>

    @FormUrlEncoded
    @POST(APIs.A_USER_BLOCK_UNBLOCK)
    fun postBlockUnblock(@Field("user_id") user_id: Int,
                     @Field("block_status") block_status: Int,
                     @Field("block_id") block_id: Int): Call<GetBlockUserResp>

}