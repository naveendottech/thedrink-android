package com.mjdistillers.drinkthedrink

class DtdConstants{

    companion object{

        const val IMAGE_BASE_URL = "https://s3.us-east-2.amazonaws.com/thedrinkimg/public/"
        const val PRIVACY_POLICY_URL = "https://drinkthedrink.com/index.php/privacyPolicy"
        const val TERMS_AND_CONDITIONS_URL = "https://drinkthedrink.com/index.php/privacyPolicy"

        const val RB_SEL_TAG = "sel"
        const val RB_UNSEL_TAG = "unsel"
        const val SHARED_PREF_FILE_NAME = "dtddata"
        const val SHARED_PREF_DEVICE_TOKEN_FILE_NAME = "dtddatadevicetoken"
        const val PROFILE_FOLDER_NAME = "profiles/"
        const val EVENTS_FOLDER_NAME = "events/"
        const val PROFILE_BAR_IMAGES_FOLDER_NAME = "bar_images/thumbs/"

        const val DRINK_SPECIAL = "Drink Special"
        const val FOOD_DRINK_SPECIAL = "Food & Drink Special"

        const val FOOD_AND_DRINK_SPECIAL = "Food & Drink Special"
        const val MJ_PROMOTION = "Mj Promotion"
        const val TAG_REGISTRATION_DIALOG  = "RegisterDialog"
        const val TAG_LOG_DEFAULT  = "DTDLOGS"
        const val VALID_PASSWORD = "Valid Password"
        const val MY_STATUS = "my_status"
        const val FAV_LIQUORE = "fav_liquore"
        const val FAV_ALCOHOL = "fav_alcohol"
        const val SPECIALITY = "speciality"
        const val WORKS_AT_STR = "worksatstr"
        const val CITY = "city"
        const val STATE = "state"
        const val COUNTRY = "country"
        const val PROBLEM_OCCURED ="Some Problem Occurred"
        const val FOLLOW = "follow"
        const val UNFOLLOW = "unfollow"
        const val MJPROMOTION = "mjPromotion"
        const val DRINK_SPCL = "Drink Special"
        const val EVENT = "Event"

        const val DUMMY_DISTANCE_SIGNATURE = "lasjfklasjflkjsafashfahlsfdkfhlas"

        // Miami LatLng
        const val DEFAULT_LATITUDE = 25.7616798
        const val DEFAULT_LONGITUDE = -80.19179020000001
        const val DEFAULT_LOCATAION = "Miami"

        const val PLACEHOLDER_NO_IMAGE ="Placeholdernoimage"
        const val PLACEHOLDER_PROFILE_IMAGE ="PlaceholderprofileImage"
        const val PLACEHOLDER_TEAMS_IMAGE ="placeholderteamsimage"


        const val REQUEST_AUTO = "AUTO"
        const val REQUEST_GEN = "GEN"

        const val NOTIFICATION_DECLINED = "Declined"
        const val NOTIFICATION_ACCEPTED = "Accepted"
        const val NOTIFICATION_REQUESTED = "Requested"
        const val NOTIFICATION_KEY = "notiffication_key"
        const val AUTOCOMPLETE_REQUEST_CODE = 81


        const val NOTIFICATION_ACCEPT = 1
        const val NOTIFICATION_DECLINE = 2
        const val NO_ITEM_LEFT = 3

        const val UPLOAD_IMAGE_SIZE = 100

        const val DELIVERY = ""

        const val RES_CODE_NO_INTERNET = 1212
        const val RES_CODE_401 = 401
        const val RES_CODE_200 = 200


        const val BAR_FILTER_FLAG = 101

        val ROLES_ARRAY = listOf("","Bar Tender","Promoter","Reveler")
        val COUNTRY_ARRAY = listOf("Choose Country","USA","Canada")
        val FAVOURITE_ALCOHOL_ARRAY = listOf("Choose Alcohol","Vodka","Gin","Rum","Bourbon","Tequila")
        val DAYS_ARRAY = listOf("Choose Day","SUNDAY","MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY")
        val YES_NO_ARRAY = listOf("Choose an option","YES","NO")
        val NEW_ROLE_ARRAY = listOf("Choose Working At","Bar Tender","Liquor Store","Promoter","Other")
        val NEW_NEW_ROLE_ARRAY = listOf("Choose Role","Hospitality","Retail Liquore","Events")

        val TAG_SEARCH = "Search"
        val TAG_CROSS = "cross"


        const val DTD_IMAGE_1 = "drink_image_1"
        const val DTD_IMAGE_2 = "drink_image_2"
        const val DTD_IMAGE_3 = "drink_image_3"

        const val SHOW_REGISTER_FORM = 1
        const val SHOW_LOGIN_FORM = 2
        const val SHOW_LOGIN_POP_UP = 3
        const val SHOW_PROFILE = 4
        const val SHOW_EDIT_PROFILE = 5
        const val SHOW_FOLLOW = 6
        const val HANDLE_FOR_SEARCH_POPUP = 7
        const val SHOW_FEEDBACK = 8
        const val SHOW_MESSAGE_ALL_USERS = 9
        const val SHOW_CHAT_HISTORY = 10
        const val HIT_REQUEST_PUSH_NOTIFICATION_API = 11
        const val FINISH_APP = 12
        const val FOLLOW_UNFOLOW_HANDLING = 13
        const val LOGOUT_CLICKED = 14
        const val NOTIFICATION_CLICKED = 15
        const val SHOW_WEBVIEW_DIALOG = 16
        const val SHOW_INVITE_PEOPLE = 17
        const val SHOW_FORGOT_PASSWORD = 18
        const val SHOW_SNACKBAR = 19
        const val SHOW_UPDATE_PASSWORD = 20
        const val SHOW_CONTACT = 21
        const val SHOW_PICK_LOCATION = 22
        const val SHOW_ALL_NOTIFICATIONS = 23
        const val SHOW_SNACKBAR_DIALOG = 24
        const val SHOW_PROGRESS_DIALOG = 25
        const val HIT_FOR_UPDATE_USER_PROFILE = 26
        const val SHOW_SEARCH_PEOPLE = 27
        const val FROM_LOGIN = 28
        const val SHOW_IMAGES_PAGER = 29
        const val SHOW_APP_TOUR = 30
        const val SHOW_EXTERNAL_BROWSER = 31

        const val FOLLOW_REQUEST_NOTIFICATION = 1
        const val FOLLOW_REQUESTED = 2
        const val FOLLOW_FOLLOW_TO =  3
        const val FOLLOW_FOLLOW_BACK = 4
        const val FOLLOW_UNFOLLOW_TO = 5
        const val FOLLOW_UNFOLLOW_BACK = 6
        const val FOLLOW_DECLINE = 7

        const val SNACKBAR_DURATION = 5000L


        const val PICK_IMAGE_FROM_CAMERA = 1
        const val LOCATION_REQUEST_CODE = 2

        // Login API Constants
        const val PROFILE_IMAGE = "profile_image"
        const val FOLLOW_BY = "follow_by"
        const val FOLLOW_TO = "follow_to"
        const val VISIBILITY_STATUS = "visibility_status"
        const val NAME = "name"
        const val IS_LOGGED_IN = "is_logged_in"
        const val SERVER_TOKEN = "server_token"
        const val EMAIL = "email"
        const val PHONE = "phone"
        const val ADDRESS = "address"
        const val ID = "ID"
        const val WORKS_AT = "works_at"
        const val FAVORITE_DRINK = "faovorite_drink"
        const val FAVORITE_COCKTAIL = "faovorite_cocktail"
        const val FAVORITE_SPIRIT = "faovorite_spirit"
        const val DRINK_IMAGE_1 = "drink_image_1"
        const val DRINK_IMAGE_2 = "drink_image_2"
        const val DRINK_IMAGE_3 = "drink_image_3"
        const val FROM_PEOPLE_SEARCH_MESSAGES = "peoplesearchmess"
        const val STATES = "states"
        const val PROVINCES = "provinces"
        const val POSITION = "position"
        const val LIST_DATA = "listdata"

        const val FONT_NEUTRA_MEDIUM = "neutra_medium.otf"
        const val FONT_FUTURA = "futur.ttf"
        const val FONT_FUTURA_BOLD = "futura_bold.ttf"
        const val FONT_FUTURA_BOOK = "futura_book.ttf"


        const val NO_IMAGE = ""

        const val SEL_OPTION_BARS = "BAR"
        const val SEL_OPTION_PEOPLE = "PEOPLE"
        const val SEL_OPTION_STORES = "STORES"

//        const val ROLE_BAR_TENDER = 7
////        const val ROLE_PROMOTOR = 4
////        const val ROLE_STORE_MANAGER = 8
////        const val ROLE_USER = 3
////        const val ROLE_OWNER = 2

        const val ROLE_CUSTOMER = 3
        const val ROLE_HOSPITALITY = 7
        const val ROLE_RETAIL_LIQUORE = 8
        const val ROLE_EVENTS= 4

        const val TAG_EXPLODE_TRANS = "Explode"
        const val  TAG_SLIDE_TRANS = "Slide"
        const val  TAG_FADE_TRANS = "Fade"
        const val  TAG_NONE_TRANS = "None"

        const val PERSON_STATUS_FOLLOWER = "follower"
        const val PERSON_STATUS_FOLLOWING = "following"
        const val PERSON_STATUS_PUBLIC = "public"






    }

}