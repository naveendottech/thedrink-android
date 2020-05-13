package com.mjdistillers.drinkthedrink.utilities

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Handler
import android.os.Message
import android.text.InputFilter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.Snackbar
import com.mjdistillers.drinkthedrink.App
import com.mjdistillers.drinkthedrink.DtdConstants
import com.mjdistillers.drinkthedrink.DtdPrefsKeys
import com.mjdistillers.drinkthedrink.R
import com.mjdistillers.drinkthedrink.model.MarkerModel
import com.mjdistillers.drinkthedrink.model.SearchFilterModel
import com.mjdistillers.drinkthedrink.model.TeamsModel
import com.mjdistillers.drinkthedrink.model.response.BarTeam
import com.mjdistillers.drinkthedrink.model.response.GetBarsDataResponseData
import com.mjdistillers.drinkthedrink.model.response.bar_details.Bardetilsdata
import com.mjdistillers.drinkthedrink.model.response.bar_details.ComingEvent
import com.mjdistillers.drinkthedrink.model.response.bar_details.GetBarsDetailsResponse
import com.mjdistillers.drinkthedrink.model.response.bar_details.TodayEvent
import com.mjdistillers.drinkthedrink.model.response.bar_search.GetBarsSearchResponse
import com.mjdistillers.drinkthedrink.model.response.edit_profile.GetEditProfile
import com.mjdistillers.drinkthedrink.model.response.get_followers.UserFollower
import com.mjdistillers.drinkthedrink.model.response.get_following.UserFollowing
import com.mjdistillers.drinkthedrink.model.response.get_people.GetPeopleResponse
import com.mjdistillers.drinkthedrink.model.response.get_stores.Datum
import com.mjdistillers.drinkthedrink.model.response.login.GetUserLoginResponse
import com.mjdistillers.drinkthedrink.model.response.registration.GetUserRegisterationResponse
import com.mjdistillers.drinkthedrink.model.response.search_people.GetSearchPeopleResponse
import com.mjdistillers.drinkthedrink.model.response.search_people_api.GetSearchPeopleFrontendResponse
import com.mjdistillers.drinkthedrink.model.response.store_detail.GetStoresDetailResponse
import com.mjdistillers.drinkthedrink.model.response.stores_search.GetStoreSearchResponse
import com.mjdistillers.drinkthedrink.model.response.update_profile.Data
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*
import java.util.concurrent.Executors
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class DtdUtils(app: Application) {

    var prefs = SharedPrefsUtils(app)


    fun saveForUserLogin(userLogin: GetUserLoginResponse?){
        var visbiltiyStatus = userLogin?.data?.visibilityStatus?:"0"
        if(visbiltiyStatus == ""){
            visbiltiyStatus = "0"
        }

        userLogin?.let {

        prefs.saveString(DtdConstants.PROFILE_IMAGE, userLogin?.data?.profileImage)
        prefs.saveInt(DtdConstants.FOLLOW_BY, userLogin?.data?.followBy)
        prefs.saveInt(DtdConstants.FOLLOW_TO, userLogin?.data?.followTo)
        prefs.saveString(DtdConstants.VISIBILITY_STATUS, visbiltiyStatus)
        prefs.saveString(DtdConstants.NAME, userLogin?.data?.name)
        prefs.saveBoolean(DtdConstants.IS_LOGGED_IN,true)
        prefs.saveString(DtdConstants.SERVER_TOKEN,userLogin?.data?.token)
        prefs.saveString(DtdConstants.EMAIL,userLogin?.data?.email)
        prefs.saveString(DtdConstants.PHONE,userLogin?.data?.phone)
        prefs.saveString(DtdConstants.ADDRESS,userLogin?.data?.address)
        prefs.saveInt(DtdConstants.ID,userLogin?.data?.id)
        prefs.saveString(DtdConstants.FAVORITE_DRINK,userLogin?.data?.favDrink)
        prefs.saveString(DtdConstants.FAVORITE_COCKTAIL,userLogin?.data?.favCocktail)
        prefs.saveString(DtdConstants.FAVORITE_SPIRIT,userLogin?.data?.favSpirit)
        prefs.saveString(DtdConstants.MY_STATUS,userLogin?.data?.myStatus)
        prefs.saveString(DtdConstants.FAV_ALCOHOL,userLogin?.data?.fav_alcohol)
        prefs.saveString(DtdConstants.FAV_LIQUORE,userLogin?.data?.fav_liquor)
        prefs.saveString(DtdConstants.SPECIALITY,userLogin?.data?.speciality)

        prefs.saveString(DtdConstants.CITY, it.data.city)
        prefs.saveString(DtdConstants.STATE, it.data?.state)
        prefs.saveString(DtdConstants.COUNTRY, it.data?.country)
        prefs.saveInt(DtdConstants.WORKS_AT, it.data?.workAt)

        prefs.saveString(DtdConstants.DRINK_IMAGE_1,userLogin?.data?.drinkImage1)
        prefs.saveString(DtdConstants.DRINK_IMAGE_2,userLogin?.data?.drinkImage2)
        prefs.saveString(DtdConstants.DRINK_IMAGE_3,userLogin?.data?.drinkImage3)
        }
    }

    fun saveForRegistration(register: GetUserRegisterationResponse?){

        var visbiltiyStatus = register?.registrationData?.visibilityStatus?:"0"
        if(visbiltiyStatus == ""){
            visbiltiyStatus = "0"
        }

        register?.let {
        var userRegister = register.registrationData
        prefs.saveString(DtdConstants.PROFILE_IMAGE, userRegister?.profileImage)
        prefs.saveInt(DtdConstants.FOLLOW_BY, userRegister?.followBy)
        prefs.saveInt(DtdConstants.FOLLOW_TO, userRegister?.followTo)
        prefs.saveString(DtdConstants.VISIBILITY_STATUS, visbiltiyStatus)
        prefs.saveString(DtdConstants.NAME, userRegister?.name)
        prefs.saveBoolean(DtdConstants.IS_LOGGED_IN,true)
        prefs.saveString(DtdConstants.SERVER_TOKEN,userRegister?.token)
        prefs.saveString(DtdConstants.EMAIL,userRegister?.email)
        prefs.saveString(DtdConstants.PHONE,userRegister?.phone)
        prefs.saveString(DtdConstants.ADDRESS,userRegister?.address)
        prefs.saveInt(DtdConstants.ID,userRegister?.id)
        prefs.saveString(DtdConstants.FAV_ALCOHOL,userRegister?.fav_alcohol)
        prefs.saveString(DtdConstants.FAV_LIQUORE,userRegister?.fav_liquor)
        prefs.saveString(DtdConstants.SPECIALITY,userRegister?.speciality)


        prefs.saveString(DtdConstants.DRINK_IMAGE_1,userRegister?.drink_image_1)
        prefs.saveString(DtdConstants.DRINK_IMAGE_2,userRegister?.drink_image_2)
        prefs.saveString(DtdConstants.DRINK_IMAGE_3,userRegister?.drink_image_3)
        }
    }

    fun saveForEditProfile(editProfile: GetEditProfile?){

        var visbiltiyStatus = editProfile?.data?.visibilityStatus?:"0"
        if(visbiltiyStatus == ""){
            visbiltiyStatus = "0"
        }

        editProfile?.let {
        var profileImg = editProfile.data
        prefs.saveString(DtdConstants.PROFILE_IMAGE, profileImg?.profileImage)
        prefs.saveInt(DtdConstants.FOLLOW_BY, profileImg?.followBy)
        prefs.saveInt(DtdConstants.FOLLOW_TO, profileImg?.followTo)
        prefs.saveString(DtdConstants.VISIBILITY_STATUS, visbiltiyStatus)
        prefs.saveString(DtdConstants.NAME, profileImg?.name)
        prefs.saveBoolean(DtdConstants.IS_LOGGED_IN,true)
        prefs.saveString(DtdConstants.SERVER_TOKEN,profileImg?.token)
        prefs.saveString(DtdConstants.EMAIL,profileImg?.email)
        prefs.saveString(DtdConstants.PHONE,profileImg?.phone)
        prefs.saveString(DtdConstants.ADDRESS,profileImg?.address)
        prefs.saveInt(DtdConstants.ID,profileImg?.id)
        prefs.saveString(DtdConstants.FAVORITE_DRINK,profileImg?.favDrink)
        prefs.saveString(DtdConstants.FAVORITE_COCKTAIL,profileImg?.favCocktail)
        prefs.saveString(DtdConstants.FAVORITE_SPIRIT,profileImg?.favSpirit)
        prefs.saveString(DtdConstants.MY_STATUS,profileImg?.myStatus)
        prefs.saveString(DtdConstants.FAV_ALCOHOL,profileImg?.fav_alcohol)
        prefs.saveString(DtdConstants.FAV_LIQUORE,profileImg?.fav_liquor)
        prefs.saveString(DtdConstants.SPECIALITY,profileImg?.speciality)

            when(profileImg?.role?.toInt()){
                DtdConstants.ROLE_HOSPITALITY->{
                    var bar_name = ""
                    if(profileImg?.barName == null) bar_name = ""
                    prefs.saveString(DtdConstants.WORKS_AT_STR,bar_name)
                }

                DtdConstants.ROLE_RETAIL_LIQUORE->{
                    var store_name = ""
                    if(profileImg?.storeName == null) store_name = ""
                    prefs.saveString(DtdConstants.WORKS_AT_STR,store_name)
                }

                else->{
                    prefs.saveString(DtdConstants.WORKS_AT_STR,"")
                }
            }


        prefs.saveString(DtdConstants.CITY, profileImg?.city)
        prefs.saveString(DtdConstants.STATE, profileImg?.state)
        prefs.saveString(DtdConstants.COUNTRY, profileImg?.country)
        prefs.saveInt(DtdConstants.WORKS_AT, profileImg?.works_at)

        prefs.saveString(DtdConstants.DRINK_IMAGE_1,profileImg?.drinkImage1)
        prefs.saveString(DtdConstants.DRINK_IMAGE_2,profileImg?.drinkImage2)
        prefs.saveString(DtdConstants.DRINK_IMAGE_3,profileImg?.drinkImage3)
        }
    }

    fun saveForUpdateProfile(dataStat: Data){

        var visbiltiyStatus = dataStat?.visibilityStatus?:"0"
        if(visbiltiyStatus == ""){
            visbiltiyStatus = "0"
        }

        prefs.saveString(DtdConstants.PROFILE_IMAGE, dataStat.profileImage)
        prefs.saveInt(DtdConstants.FOLLOW_BY, dataStat.followBy)
        prefs.saveInt(DtdConstants.FOLLOW_TO, dataStat.followTo)
        prefs.saveString(DtdConstants.VISIBILITY_STATUS,visbiltiyStatus)
        prefs.saveString(DtdConstants.NAME, dataStat?.name)
        prefs.saveBoolean(DtdConstants.IS_LOGGED_IN,true)
        prefs.saveString(DtdConstants.SERVER_TOKEN,dataStat?.token)
        prefs.saveString(DtdConstants.EMAIL,dataStat?.email)
        prefs.saveString(DtdConstants.PHONE,dataStat?.phone)
        prefs.saveString(DtdConstants.ADDRESS,dataStat?.address)
        prefs.saveInt(DtdConstants.ID,dataStat?.id)
        prefs.saveString(DtdConstants.FAVORITE_DRINK,dataStat?.favDrink)
        prefs.saveString(DtdConstants.FAVORITE_COCKTAIL,dataStat?.favCocktail)
        prefs.saveString(DtdConstants.FAVORITE_SPIRIT,dataStat?.favSpirit)
        prefs.saveString(DtdConstants.MY_STATUS,dataStat?.myStatus)
        prefs.saveString(DtdConstants.FAV_ALCOHOL,dataStat?.fav_alcohol)
        prefs.saveString(DtdConstants.FAV_LIQUORE,dataStat?.fav_liquor)
        prefs.saveString(DtdConstants.SPECIALITY,dataStat?.speciality)


        when(dataStat?.role?.toInt()){
            DtdConstants.ROLE_HOSPITALITY->{
                var bar_name = ""
                if(dataStat?.barName == null) bar_name = ""
                prefs.saveString(DtdConstants.WORKS_AT_STR,bar_name)
            }

            DtdConstants.ROLE_RETAIL_LIQUORE->{
                var store_name = ""
                if(dataStat?.storeName == null) store_name = ""
                prefs.saveString(DtdConstants.WORKS_AT_STR,store_name)
            }

            else->{
                prefs.saveString(DtdConstants.WORKS_AT_STR,"")
            }
        }


        prefs.saveString(DtdConstants.CITY, dataStat?.city)
        prefs.saveString(DtdConstants.STATE, dataStat?.state)
        prefs.saveString(DtdConstants.COUNTRY, dataStat?.country)
        prefs.saveInt(DtdConstants.WORKS_AT, dataStat?.work_at)


        prefs.saveString(DtdConstants.DRINK_IMAGE_1,dataStat?.drinkImage1)
        prefs.saveString(DtdConstants.DRINK_IMAGE_2,dataStat?.drinkImage2)
        prefs.saveString(DtdConstants.DRINK_IMAGE_3,dataStat?.drinkImage3)
    }

    fun saveForUpdateUserAfterRegistration(dataStat: com.mjdistillers.drinkthedrink.model.response.update_user.Data?){

        dataStat?.let {

        var visbiltiyStatus = dataStat?.visibilityStatus?:"0"
        if(visbiltiyStatus == "")
            visbiltiyStatus = "0"


        prefs.saveString(DtdConstants.PROFILE_IMAGE, dataStat.profileImage)
        prefs.saveInt(DtdConstants.FOLLOW_BY, dataStat.followBy)
        prefs.saveInt(DtdConstants.FOLLOW_TO, dataStat.followTo)
        prefs.saveString(DtdConstants.VISIBILITY_STATUS,visbiltiyStatus)
        prefs.saveString(DtdConstants.NAME, dataStat?.name)
        prefs.saveBoolean(DtdConstants.IS_LOGGED_IN,true)
        prefs.saveString(DtdConstants.SERVER_TOKEN,dataStat?.token)
        prefs.saveString(DtdConstants.EMAIL,dataStat?.email)
        prefs.saveString(DtdConstants.PHONE,dataStat?.phone)
        prefs.saveString(DtdConstants.ADDRESS,dataStat?.address)
        prefs.saveInt(DtdConstants.ID,dataStat?.id)
        prefs.saveString(DtdConstants.FAVORITE_DRINK,dataStat?.favDrink)
        prefs.saveString(DtdConstants.FAVORITE_COCKTAIL,dataStat?.favCocktail)
        prefs.saveString(DtdConstants.FAVORITE_SPIRIT,dataStat?.favSpirit)
        prefs.saveString(DtdConstants.MY_STATUS,dataStat?.myStatus)
        prefs.saveString(DtdConstants.SPECIALITY,dataStat?.speciality)


            when(dataStat?.role?.toInt()){
                DtdConstants.ROLE_HOSPITALITY->{
                    var bar_name = ""
                    if(dataStat?.barName == null) bar_name = ""
                    prefs.saveString(DtdConstants.WORKS_AT_STR,bar_name)
                }

                DtdConstants.ROLE_RETAIL_LIQUORE->{
                    var store_name = ""
                    if(dataStat?.storeName == null) store_name = ""
                    prefs.saveString(DtdConstants.WORKS_AT_STR,store_name)
                }

                else->{
                    prefs.saveString(DtdConstants.WORKS_AT_STR,"")
                }
            }

        prefs.saveString(DtdConstants.CITY, dataStat?.city)
        prefs.saveString(DtdConstants.STATE, dataStat?.state)
        prefs.saveString(DtdConstants.COUNTRY, dataStat?.country)


        prefs.saveString(DtdConstants.DRINK_IMAGE_1,dataStat?.drinkImage1)
        prefs.saveString(DtdConstants.DRINK_IMAGE_2,dataStat?.drinkImage2)
        prefs.saveString(DtdConstants.DRINK_IMAGE_3,dataStat?.drinkImage3)

        }

    }

    fun prepareMarkerModelList(barsList: List<GetBarsDataResponseData>?,
                               mldBars: MutableLiveData<ArrayList<MarkerModel>>)
    {
        var markersList = ArrayList<MarkerModel>()

        var df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING

        var executor = Executors.newSingleThreadExecutor()
        executor.submit(Runnable {

            if (barsList == null) dataOnIssueGetBars(mldBars)

            barsList?.let {
                try {
                    var count = 0
                    for (it in barsList) {
                        var lati = it.barLatitude?.toDouble()
                        var longi = it.barLongitude?.toDouble()

                        if (lati != null && longi != null) {
                            var options = MarkerOptions().position(LatLng(lati, longi))
                                .icon(getIcons(it.barMarker))

                            var imageURL = DtdConstants.IMAGE_BASE_URL+it.barImage

                            var barteams = mutableListOf<TeamsModel>()

                                it.barTeam?.let {
                                  for(item in it){
                                      var teamModel = TeamsModel(item.profileImg,
                                          item.name,
                                          item.designation,
                                          1)
                                      barteams.add(teamModel)
                                  }
                                }

                            var markermodel = MarkerModel(
                                options,
                                it.barMarker == "0",
                                it.id,
                                it.distance,
                                it.dollars,
                                dollorString(it.dollars) + "   "+getFormatedNumber(it.distance)+" mi",
                                it.barName,
                                df.format(it.distance),
                                imageURL,
                                count++,
                                barteams)

                            markersList.add(markermodel)
                        }
                    }
                }catch (e: Exception){
                    dataOnIssueGetBars(mldBars)
                    e.printStackTrace()
                }

                mldBars.postValue(markersList)
            }
        })
    }

    fun prepareMarkerModelListForStores(storesList: List<Datum>?,
                                        mldStores: MutableLiveData<ArrayList<MarkerModel>>){
        var markersList = ArrayList<MarkerModel>()

        var df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING

        var executor = Executors.newSingleThreadExecutor()
        executor.submit(Runnable {

            if (storesList == null) dataOnIssueGetStore(mldStores)

            storesList?.let {
                try {
                    var count = 0
                    for (it in storesList) {
                        var lati = it.storeLatitude?.toDouble()
                        var longi = it.storeLongitude?.toDouble()

                        if (lati != null && longi != null) {
                            var options = MarkerOptions().position(LatLng(lati, longi))
                                .icon(getIcons(it.storeMarker))

                            var imageURL = DtdConstants.IMAGE_BASE_URL+it.storeImage

                            var delivery:String = ""

                            delivery = if (it.delilvery == null){
                                ""
                            }else
                                it.delilvery.toString()


                            var storeteams = mutableListOf<TeamsModel>()

                            it.storeTeam?.let {
                                for(item in it){
                                    var teamModel = TeamsModel(item.profileImg,
                                        item.name,
                                        item.designation,
                                        1)
                                    storeteams.add(teamModel)
                                }
                            }

                            var markermodel = MarkerModel(
                                options,
                                it.storeMarker == "0",
                                it.id,
                                it.distance,
                                0,
                                delivery ,
                                it.storeName,
                                df.format(it.distance),
                                imageURL,
                                count++,
                                storeteams)

                            markersList.add(markermodel)
                        }
                    }
                }catch (e: Exception){
                    dataOnIssueGetBars(mldStores)
                    e.printStackTrace()
                }

                mldStores.postValue(markersList)
            }
        })
    }

    fun prepareMarkerModelListForPeople(peopleList: List<com.mjdistillers.drinkthedrink.model.response.get_people.Datum>?,
                                        mldPeople: MutableLiveData<ArrayList<MarkerModel>>){

        var markersList = ArrayList<MarkerModel>()
        var userId = prefs.getInt(DtdConstants.ID)
        var df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING

        var executor = Executors.newSingleThreadExecutor()
        executor.submit(Runnable {

            if (peopleList == null) dataOnIssueGetPeople(mldPeople)

            peopleList?.let {
                try {
                    var count = 0
                    for (it in peopleList) {
                        var lati = it.userLatitude?.toDouble()
                        var longi = it.userLongitude?.toDouble()

                        if (lati != null && longi != null) {
                            var options = MarkerOptions().position(LatLng(lati, longi))
                                .icon(getIcons("0"))

                            var imageURL = DtdConstants.IMAGE_BASE_URL+it.profileImage

                            if (it.deviceToken != null && it.deviceToken.isNotEmpty() && it.userId != userId) {
                            var markermodel = MarkerModel(
                                options,
                                true,
                                it.userId,
                                it.distance,
                                0,
                                "" ,
                                it.username,
                                df.format(it.distance),
                                imageURL,
                                count++,
                                mutableListOf<TeamsModel>())

                                markersList.add(markermodel)
                            }
                        }
                    }
                }catch (e: Exception){
                    dataOnIssueGetBars(mldPeople)
                    e.printStackTrace()
                }

                mldPeople.postValue(markersList)
            }
        })
    }

    private fun getIcons(barMarker: String?): BitmapDescriptor {
        return if (barMarker != null) {
            if (barMarker == "0") {
                BitmapDescriptorFactory.fromResource(R.drawable.map_marker_blue)
            } else {
                BitmapDescriptorFactory.fromResource(R.drawable.map_marker_red)
            }
        } else {
            BitmapDescriptorFactory.fromResource(R.drawable.map_marker_blue)
        }
    }

    private fun dataOnIssueGetStore(mldStores: MutableLiveData<ArrayList<MarkerModel>>){
        var respo = arrayListOf<MarkerModel>()
        mldStores.postValue(respo)
    }

    private fun dataOnIssueGetBars(mldBars: MutableLiveData<ArrayList<MarkerModel>>){
        var respo = arrayListOf<MarkerModel>()
        mldBars.postValue(respo)
    }

    private fun dataOnIssueGetPeople(mldPeople: MutableLiveData<ArrayList<MarkerModel>>){
        var respo = arrayListOf<MarkerModel>()
        mldPeople.postValue(respo)
    }

    fun getBarDetailObjectFromStoreDetail(storeDetail: GetStoresDetailResponse?): GetBarsDetailsResponse{
        var barsDetail = GetBarsDetailsResponse()

        storeDetail?.let {
                var barData = Bardetilsdata()
                var storeData = storeDetail.data
                var barTeams = mutableListOf<BarTeam>()

                var storeTeams = it.data.storeTeam

            storeTeams?.let {
                for (team in it){
                    var barTeam = BarTeam()
                    barTeam.barId = team.storeId
                    barTeam.name = team.name
                    barTeam.designation = team.designation
                    barTeam.profileImg = team.profileImg
                    barTeam.isBar = false
                    barTeam.userFollower = team.userFollower
                    barTeam.userFollowing = team.userFollowing
                    barTeams.add(barTeam)
                }
            }

        barsDetail.status = storeDetail.status
        barsDetail.bardetilsdata.barTeam = barTeams
        barData.barDesc = storeData.storeDesc
        barData.barGallery = storeData.storeGallery
        barData.barImage = storeData.storeImage
        barData.bar_follow = storeData.store_follow
        barData.barName = storeData.storeName
        barData.barStreetAddress = storeData.storeStreetAddress
        barData.store_website = storeData.store_website
        barData.bar_website = storeData.store_website
        barData.bar_timing = storeData.store_timing
        barData.owner_phone = storeData.owner_phone

        var eventsList = mutableListOf<ComingEvent>()
        storeData.comingEvents.iterator().forEach {
            var cmingEvent = ComingEvent()
            cmingEvent.drinkPrice = it.drinkPrice
            cmingEvent.endTime = it.endTime
            cmingEvent.eventDesc = it.eventDesc
            cmingEvent.eventImage = it.eventImage
            cmingEvent.storesEventsImage = DtdConstants.IMAGE_BASE_URL+DtdConstants.EVENTS_FOLDER_NAME+it.eventImage
            cmingEvent.eventType = it.eventType
            cmingEvent.event_category = it.event_category
            cmingEvent.foodPrice = it.foodPrice
            cmingEvent.event_category = DtdConstants.MJ_PROMOTION
            cmingEvent.id = it.id

            eventsList.add(cmingEvent)
        }

        barData.comingEvents = eventsList

        var todayList = mutableListOf<TodayEvent>()
        storeData.todayEvent.iterator().forEach {
            var todayEvents = TodayEvent()
            todayEvents.drinkPrice = it.drinkPrice
            todayEvents.endTime = it.endTime
            todayEvents.eventDesc = it.eventDesc

            todayEvents.storesEventsImage = DtdConstants.IMAGE_BASE_URL+DtdConstants.EVENTS_FOLDER_NAME+it.eventImage
            todayEvents.eventImage = it.eventImage
            todayEvents.eventType = it.eventType
            todayEvents.event_category = DtdConstants.FOOD_AND_DRINK_SPECIAL
            todayEvents.foodPrice = it.foodPrice
            todayEvents.id = it.id

            todayList.add(todayEvents)
        }

        barData.todayEvent = todayList

        barData.dollar = storeData.deliveryCharges
        barData.features = storeData.features
        barData.miles = storeData.miles
        barData.userId = storeData.userId
        barData.id = storeData.id

            var delivaryCharges = storeData.deliveryCharges
            
        barData.delivery_charges = if(delivaryCharges.isNullOrEmpty()) App.app.getString(R.string.no_delivery) else storeData.deliveryCharges
        barData.delivery_comment = storeData.deliveryComment

        barsDetail.bardetilsdata = barData

        }

        return barsDetail
    }

    fun getListOfSearchFromBarsSearch(respones: GetBarsSearchResponse?):List<SearchFilterModel>{
        var filterModelList = mutableListOf<SearchFilterModel>()

        if (respones != null){
            var datum = respones.data.data

            datum.listIterator().forEach {

                var filterModel =  SearchFilterModel()
                filterModel.distance = getFormatedNumber(it.distance) + " mi"
                filterModel.distanceDouble = it.distance
                filterModel.name = it.barName
                filterModel.id = it.id
                filterModel.imageUri = DtdConstants.IMAGE_BASE_URL+DtdConstants.PROFILE_BAR_IMAGES_FOLDER_NAME+it.bar_image
                filterModel.from = DtdConstants.SEL_OPTION_BARS

                filterModelList.add(filterModel)

            }
        }
        return filterModelList
    }

    fun getSearchFilterModelFromUserFollower(userFollow: UserFollower): SearchFilterModel{
        var searchModel = SearchFilterModel()
        userFollow.data[0].let {
            searchModel.id = it.id
            searchModel.deviceToken = it.apiToken
            searchModel.imageUri = DtdConstants.IMAGE_BASE_URL+DtdConstants.PROFILE_BAR_IMAGES_FOLDER_NAME+it.profileImage
            searchModel.name = it.name
        }
        return searchModel
    }


    fun getSearchFilterModelFromUserFollowing(userFollowing: UserFollowing): SearchFilterModel{
        var searchModel = SearchFilterModel()
        userFollowing.data[0].let {
            searchModel.id = it.id
            searchModel.deviceToken = it.apiToken
            searchModel.imageUri = DtdConstants.IMAGE_BASE_URL+DtdConstants.PROFILE_BAR_IMAGES_FOLDER_NAME+it.profileImage
            searchModel.name = it.name
        }
        return searchModel

    }

    fun getListOfSeachFromStoreSearch(respones: GetStoreSearchResponse?):List<SearchFilterModel>{
        var filterModelList = mutableListOf<SearchFilterModel>()

        if (respones != null){
            var datum = respones.data.data

            datum.listIterator().forEach {
                var filterModel =  SearchFilterModel()
                filterModel.distance = getFormatedNumber(it.distance)+" mi"
                filterModel.distanceDouble = it.distance
                filterModel.name = it.storeName
                filterModel.id = it.id
                filterModel.imageUri = DtdConstants.IMAGE_BASE_URL+DtdConstants.PROFILE_BAR_IMAGES_FOLDER_NAME+it.store_image
                filterModel.from = DtdConstants.SEL_OPTION_STORES
                filterModelList.add(filterModel)
            }
        }
        return filterModelList
    }

    fun getListOfSearchFromPeopleSearch(respones : GetSearchPeopleResponse?):List<SearchFilterModel>{
        var user_id = prefs.getInt(DtdConstants.ID)

        var filterModelList = mutableListOf<SearchFilterModel>()
        var userId = prefs.getInt(DtdConstants.ID)
        if (respones != null) {
            var datum = respones.data.data
            datum.listIterator().forEach {
                var filterModel =  SearchFilterModel()
//                filterModel.distance = getFormatedNumber(it.distance)+" mi"
//                filterModel.distanceDouble = it.distance

                filterModel.user_1 = it.user1?:0
                filterModel.user_2 = it.user2?:0
                filterModel.user_id = it.userId?:0
                filterModel.user_blocked_by = it.user_blocked_by
                filterModel.user_blocked_id = it.user_blocked_id


                filterModel.name = it.name
                filterModel.id = it.userId
                filterModel.deviceToken = it.deviceToken
                filterModel.imageUri = DtdConstants.IMAGE_BASE_URL+DtdConstants.PROFILE_FOLDER_NAME+it.profileImage
                filterModel.from = DtdConstants.FROM_PEOPLE_SEARCH_MESSAGES

                setFollowBtnText(filterModel,user_id)
                setBlockBtnText(filterModel,user_id)


                if (filterModel.deviceToken != null && filterModel.deviceToken.isNotEmpty() && filterModel.id != userId)
                filterModelList.add(filterModel)
            }
        }
        return filterModelList
        }


    fun getListOfSearchFromPeopleSearchFrontend(respones : GetSearchPeopleFrontendResponse?):List<SearchFilterModel>{
        var filterModelList = mutableListOf<SearchFilterModel>()
        var userId = prefs.getInt(DtdConstants.ID)
        if (respones != null) {
            var datum = respones.data.data
            datum.listIterator().forEach {
                var filterModel =  SearchFilterModel()
//                filterModel.distance = getFormatedNumber(it.distance)+" mi"
//                filterModel.distanceDouble = it.distance
                filterModel.name = it.name
                filterModel.id = it.userId

                filterModel.followBy = it.followBy
                filterModel.followTo = it.followTo
                filterModel.publicUser = it.publicUser
                filterModel.requested = it.requested?: false
                filterModel.initiateId = it.initiateId
//                filterModel.initiated = amIInitiater(it.initiate_id)
                filterModel.deviceToken = it.deviceToken

                filterModel.user_id = it.userId
                filterModel.user_blocked_by = it.user_blocked_by
                filterModel.user_blocked_id = it.user_blocked_id


                filterModel.imageUri = DtdConstants.IMAGE_BASE_URL+DtdConstants.PROFILE_FOLDER_NAME+it.profileImage
                filterModel.from = DtdConstants.SEL_OPTION_PEOPLE

//                setFollowBtnText(filterModel,userId)

                if (filterModel.deviceToken != null && filterModel.deviceToken.isNotEmpty() && filterModel.id != userId)
                filterModelList.add(filterModel)
            }
        }
        return filterModelList
    }

    fun getPeoplePretendingLikeSearch(respones : GetPeopleResponse?):List<SearchFilterModel>{

        var user_id = prefs.getInt(DtdConstants.ID)

        var filterModelList = mutableListOf<SearchFilterModel>()
        var userId = prefs.getInt(DtdConstants.ID)
        if (respones != null) {
            var datum = respones.data.data
            datum.listIterator().forEach {
                var filterModel =  SearchFilterModel()
//                filterModel.distance = getFormatedNumber(it.distance)+" mi"
//                filterModel.distanceDouble = it.distance
                filterModel.name = it.name
                filterModel.id = it.userId
                filterModel.user_1 = it.user1
                filterModel.user_2 = it.user2

                filterModel.user_id = it.userId
                filterModel.user_blocked_by = it.user_blocked_by
                filterModel.user_blocked_id = it.user_blocked_id


                filterModel.followBy = it.followBy
                filterModel.followTo = it.followTo
                filterModel.publicUser = it.publicUser
                filterModel.deviceToken = it.deviceToken

                filterModel.imageUri = DtdConstants.IMAGE_BASE_URL+DtdConstants.PROFILE_FOLDER_NAME+it.profileImage
                filterModel.from = DtdConstants.SEL_OPTION_PEOPLE

                setFollowBtnText(filterModel,user_id)
                setBlockBtnText(filterModel,user_id)

                if (filterModel.deviceToken != null && filterModel.deviceToken.isNotEmpty() && filterModel.id != userId)
                filterModelList.add(filterModel)
            }
        }

        return filterModelList
    }


    private fun setFollowBtnText(filterModel: SearchFilterModel, user_id:Int) {
        filterModel.followBtnText =
            when{
                filterModel.user_1 == 0 && filterModel.user_2 == 0->{
                    App.app.getString(R.string.follow)
                }
                user_id == filterModel.user_1->{
                    App.app.getString(R.string.unfollow)

                }
                else->{
                    App.app.getString(R.string.follow)
                }
            }
    }

    private fun setBlockBtnText(filterModel: SearchFilterModel, user_id:Int) {
        filterModel.blockBtnText = when (user_id) {
                filterModel.user_blocked_by -> {
                    App.app.getString(R.string.unblock)
                }
                else -> {
                    App.app.getString(R.string.block)
                }
            }
    }

    fun loadImageNoTransform(context: Context, imageView: ImageView,urlString: String,placeholder: String){

        var placeholderRes = R.drawable.image_loading

        when(placeholder){
            DtdConstants.PLACEHOLDER_NO_IMAGE->{
                placeholderRes = R.drawable.image_loading
            }
            DtdConstants.PLACEHOLDER_PROFILE_IMAGE->{
                placeholderRes = R.drawable.profile_placeholder
            }
            DtdConstants.PLACEHOLDER_TEAMS_IMAGE->{
                placeholderRes = R.drawable.ic_placeholder_team
            }
        }

        var isValidURL = false
        isValidURL = try {
            urlString.substring(urlString.lastIndexOf("/"), urlString.length).contains(".")
        }catch (e: Exception){
            false
        }

        if (isValidURL) {
            Glide.with(context)
                .load(urlString)
                .placeholder(placeholderRes)
                .error(R.drawable.placeholder_no_image)
                .apply(RequestOptions.noAnimation())
                .apply(RequestOptions.noTransformation())
                .into(imageView)
        }else{
            if(placeholder !=  DtdConstants.PLACEHOLDER_TEAMS_IMAGE)
                imageView.setImageResource(R.drawable.placeholder_no_image)
            else
                imageView.setImageResource(placeholderRes)
        }
    }

    fun loadImageWithCircleTransform(context: Context,
                                     imageView: ImageView,
                                     urlString: String,
                                     placeholder: String) {


        var placeholderRes = R.drawable.profile_placeholder

        when (placeholder) {
            DtdConstants.PLACEHOLDER_NO_IMAGE -> {
                placeholderRes = R.drawable.profile_placeholder
            }
            DtdConstants.PLACEHOLDER_TEAMS_IMAGE->{
                placeholderRes = R.drawable.ic_placeholder_team
            }
        }


        var isValidURL = false
        isValidURL = try {
            urlString.substring(urlString.lastIndexOf("/"),urlString.length).contains(".")
        }catch (e: java.lang.Exception){
            false
        }

        if (isValidURL) {
            Glide.with(context).load(urlString)
                .placeholder(placeholderRes)
                .error(R.drawable.placeholder_no_image)
                .apply(RequestOptions.noAnimation())
                .apply(RequestOptions.circleCropTransform())
                .into(imageView)
        }else{
            if(placeholder !=  DtdConstants.PLACEHOLDER_TEAMS_IMAGE)
                imageView.setImageResource(R.drawable.placeholder_no_image)
            else
                imageView.setImageResource(placeholderRes)        }
    }

    fun loadGIFImageFromResourceWithCircular(context: Context,
                                             imageView: ImageView,
                                             imageResource: Int) {
        Glide.with(context).asGif().load(imageResource)
                .into(imageView)
    }


    fun addCircleToMap(map: GoogleMap?, latlng: LatLng){
        val circleoptions = CircleOptions()
            .strokeWidth(10f)
            .center(latlng)
            .radius(5000.0)
            .strokeColor(Color.parseColor("#809fcdc3"))
            .fillColor(Color.parseColor("#354cb2b3"))

        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 12.0f))
        map?.setMaxZoomPreference(15.0f)

//        var markerModelForCircle = MarkerModel(MarkerOptions().position(latlng),false,0,0.0,0,"0","bar","0.0","",0)

        var centerMarker = map?.addMarker(MarkerOptions().position(latlng))
//        centerMarker?.tag = markerModelForCircle
        centerMarker?.tag = DtdConstants.TAG_NONE_TRANS
        centerMarker?.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.map_center))
        centerMarker?.hideInfoWindow()
//        map?.setOnMarkerClickListener(null)
//        Account No - 6673000100004818
//        IFSC - PUNB0667300
        map?.addCircle(circleoptions)

    }

    fun getAppsLatLng() : DoubleArray{

        var latitude = prefs.getString(DtdPrefsKeys.Keys.LATITUDE)
        var longitude = prefs.getString(DtdPrefsKeys.Keys.LONGITUDE)

        var latlngArray: DoubleArray

        latlngArray = if (latitude == "" || longitude == ""){
            doubleArrayOf(DtdConstants.DEFAULT_LATITUDE,DtdConstants.DEFAULT_LONGITUDE)
        }else{
            doubleArrayOf(latitude.toDouble(),longitude.toDouble())
        }

        return latlngArray
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



    fun isCurrUserId(id: Int): Boolean{
        var id = prefs.getInt(DtdConstants.ID)
        return id == id
    }

    var df = DecimalFormat("0.00")

    fun getFormatedNumber(miles: Double?): String {
        return df.format(miles)

    }


    fun obtainFilterFirstCharNoSpace(editText: AutoCompleteTextView?) : InputFilter{

        var filter = InputFilter { source, start, end, dest, dstart, dend ->
            var c = source.toString()
            if (c != null && c.length == 1 && c == " ") {
                var msg = "Invalid first character"
                editText?.error = msg
                return@InputFilter ""
            }
            return@InputFilter c
        }

        return filter
    }

    fun obtainFilterFirstCharNoSpace(editText: AppCompatEditText?) : InputFilter{

        var filter = InputFilter { source, start, end, dest, dstart, dend ->
            var c = source.toString()
            if (c != null && c.length == 1 && c == " ") {
                var msg = "Invalid first character"
                editText?.error = msg
                return@InputFilter ""
            }
            return@InputFilter c
        }

        return filter
    }

    fun obtainInputFilterSpecialChars(editText: AutoCompleteTextView?): InputFilter{
        var filter = InputFilter { source, start, end, dest, dstart, dend ->
            var c = source.toString()


            var pt = Pattern.compile("[^a-zA-Z0-9_ ]")
            var match= pt.matcher(c)
            while(match.find())
            {
                var s = match.group()
                c=c.replace(s, "")
//                var mes = Message.obtain(handler)
//                mes.arg1 = DtdConstants.SHOW_SNACKBAR
//                mes.arg2 = Snackbar.LENGTH_LONG
                var msg = "Invalid character "+s
                editText?.error = msg
//                mes.sendToTarget()
            }
            return@InputFilter c
        }
        return filter
    }


    fun obtainInputFilterSpecialChars(editText: AppCompatEditText?): InputFilter{
        var filter = InputFilter { source, start, end, dest, dstart, dend ->
            var c = source.toString()


            var pt = Pattern.compile("[^a-zA-Z0-9_ ]")
            var match= pt.matcher(c)
            while(match.find())
            {
                var s = match.group()
                c=c.replace(s, "")
//                var mes = Message.obtain(handler)
//                mes.arg1 = DtdConstants.SHOW_SNACKBAR
//                mes.arg2 = Snackbar.LENGTH_LONG
                var msg = "Invalid character "+s
                editText?.error = msg
//                mes.sendToTarget()
            }
            return@InputFilter c
        }
        return filter
    }


    fun fontNeutraMedium() : Typeface{
         var am = App.app.assets
         var typeface = Typeface.createFromAsset(am,
            String.format(Locale.US, "fonts/%s", DtdConstants.FONT_NEUTRA_MEDIUM))
        return typeface
    }

    fun fontFutura() : Typeface{
        var am = App.app.assets
        var typeface = Typeface.createFromAsset(am,
            String.format(Locale.US, "fonts/%s", DtdConstants.FONT_FUTURA))
        return typeface
    }

    fun fontFuturaBold() : Typeface{
        var am = App.app.assets
        var typeface = Typeface.createFromAsset(am,
            String.format(Locale.US, "fonts/%s", DtdConstants.FONT_FUTURA_BOLD))
        return typeface
    }

    fun fontFuturaBook() : Typeface{
        var am = App.app.assets
        var typeface = Typeface.createFromAsset(am,
            String.format(Locale.US, "fonts/%s", DtdConstants.FONT_FUTURA_BOOK))
        return typeface
    }



}

