package com.mjdistillers.drinkthedrink

class APIs{

    // constants Preceding A are api names
    // constants Preceding P are params


    companion object{
        // LOCAL LIVE URL
//        const val BASE_URL = "http://18.221.17.130/drinkthedrink/api/"

        // DEV URL
//         const val BASE_URL = "http://18.221.17.130/thedrink/api/"

        // LIVE URL
           const val BASE_URL = "https://drinkthedrink.com/api/"

        const val A_GET_BARS_DATA = "get-bars-data"
        const val P_BAR_LATI = "bar_latitude"
        const val P_BAR_LONGI = "bar_longitude"
        const val P_DRINK_SPECIAL = "drink_special"
        const val P_EVENTS = "events"
        const val P_DELIVERY = "delivery"
        const val P_TASTING = "tastings"


        const val P_ZERO = 0
        const val P_ONE = 1

        const val V_PRIVATE = "private"
        const val V_PUBLIC = "public"

        const val V_YES = "yes"
        const val V_NO = "no"

        // State and Provinces keys
        const val P_ABBREVIATION = "abbreviation"
        const val P_FULL_NAME = "full_name"
        const val P_ID = "id"

        const val A_BAR_DETIAL = "bar-detail"
        const val A_FRONT_ADS = "front-adds"
        const val A_USER_LOGIN = "user/login"
        const val A_USER_SIGN_UP = "user/register"
        const val A_GET_FOLLOWING = "get-following-api"
        const val A_GET_FOLLOWERS = "get-followers-api"
        const val A_UPDATE_USER_PROFILE = "update-user"
        const val A_UPDATE_COCKTAIL_IMAGE = "update-cocktail-images"
        const val A_EDIT_PROFILE = "edit-user"
        const val A_CHANGE_VISIBILITY = "user-visibility"
        const val A_GET_STORES = "get-stores"
        const val A_GET_STORES_DETAIL = "store-detail"
        const val A_GET_SEARCH_BARS = "search-bars-api"
        const val A_GET_SEARCH_STORES = "search-stores-api"
        const val A_GET_FEEDBACK = "feedback"
        const val A_GET_ALL_USER_CHAT = "get-all-users-chat"
        const val A_GET_USER_CHAT_HISTORY = "get-user-chat-history"
        const val A_SEND_MESSAGE = "send-message"
        const val A_SEARCH_PEOPLE = "search-people"
        const val A_SEARCH_FRONTEND = "search-people-api"
        const val A_GET_PEOPLE = "get-people"
        const val A_GET_PROFILE = "get-profile"
        const val A_REQUEST_PUSH_NOTIFICATION = "request-push-notification"
        const val A_FOLLOW_TO_UNFOLLOW_TO = "follow-to-unfollow-to"
        const val A_FOLLOW_BACK_UNFOLLOW_BACK = "follow-back-unfollow-back"
        const val A_DECLINE_REQUEST = "decline-request"
        const val A_GET_ALL_NOTIFICATIONS = "get-all-notifications"
        const val A_LOGOUT = "logout"
        const val A_FORGOT_PASSWORD = "user/forgot-password"
        const val A_UPDATE_PASSWORD = "user/update-password"
        const val A_STATES_PROVINCES = "get-country-state"
        const val A_BAR_FOLLOW_UNFOLLOW = "bar-follow-unfollow"
        const val A_STORE_FOLLOW_UNFOLLOW = "store-follow-unfollow"
        const val A_BAR_TEAM_FOLLOW_UNFOLLOW = "bar-team-follow-unfollow"
        const val A_STORE_TEAM_FOLLOW_UNFOLLOW = "store-team-follow-unfollow"
        const val A_FOLLOW_UNFOLLOW_PEOPLE = "follow-unfollow"
        const val A_UPDATE_ROLE = "user/update-role"
        const val A_GET_CITIES = "get-cities"
        const val A_USER_BLOCK_UNBLOCK = "user-block-unblock"
    }
}