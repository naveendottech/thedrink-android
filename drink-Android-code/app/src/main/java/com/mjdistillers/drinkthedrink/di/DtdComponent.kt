package com.mjdistillers.drinkthedrink.di

import android.content.Context
import androidx.fragment.app.DialogFragment
import com.mjdistillers.drinkthedrink.MainActivity
import com.mjdistillers.drinkthedrink.adapters.*
import com.mjdistillers.drinkthedrink.dialogs.*
import com.mjdistillers.drinkthedrink.model.DTDRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    RetrofitModule::class,
    RepositoryModule::class,
    AlertUtilsModule::class,
    DtdUtilsModule::class,
    NetworkUtilsModule::class,
    NetworkInterceptorModule::class,
    LayoutInflaterModule::class,
    SharedPrefUtilsModule::class,
    ApplicationUtilsModule::class
])

interface DtdComponent{

    fun inject(app: DTDRepository)
    fun inject(mainActivity: MainActivity)
    fun inject(editProfileDialog: EditProfileDialog)
    fun inject(context: Context)
    fun inject(allUsersMessagesDialog: AllUsersMessagesDialog)
    fun inject(messageAllUserAdapter: MessageAllUserAdapter)
    fun inject(chatHistoryDialog: ChatHistoryDialog)
    fun inject(chatWindowAdapter: ChatWindowAdapter)
    fun inject(menuLoggedInPopUp: MenuLoggedInPopUp)
    fun inject(upComingEventsRVAdapter: UpComingEventsRVAdapter)
    fun inject(advertisementAdapter: AdvertisementAdapter)
    fun inject(storesRVAdapter: StoresRVAdapter)
    fun inject(searFilterAdapter: SearchFilterAdapter)
    fun inject(profileDialog: ProfileDialog)
    fun inject(imageAdapter: ImageAdapter)
    fun inject(todayeventsadapter: TodayEventsRVAdapter)
    fun inject(folllowerAdapter: FollowersAdapter)
    fun inject(followingAdapter: FollowingAdapter)
    fun inject(loginDialog: LoginDialog)
    fun inject(allNotificationDialog: AllNotificationsDialog)
    fun inject(notificationList: NotificationListAdapter)
    fun inject(webViewDialog: WebViewDialog)
    fun inject(invitePeople: InvitePeopleDialog)
    fun inject(forgotpasswd: ForgotPasswordDialog)
    fun inject(updatePassword: UpdatePasswordDialog)
    fun inject(featuresRVAdapter: FeaturesRVAdapter)
    fun inject(menupoUp: MenuLoggedOutPopUp)
    fun inject(infoWindow: CustomInfoWindowMaps)
    fun inject(registerDialog: RegisterDialog)
    fun inject(roleTypeSpin: RoleTypeSpinnerAdapter)
    fun inject(stateSpin: StateSpinnerAdapter)
    fun inject(province: ProvincesAndTerriteriesAdapter)
    fun inject(feedback: FeedbackDialog)
    fun inject(followfolwoo: FollowersFollowingDialog)
    fun inject(snackfrag: SnackbarFragment)
    fun inject(termsAdapter: TeamsAdapter)
    fun inject(registrationProfileCompletion: RegistrationProfileCompletion)



}