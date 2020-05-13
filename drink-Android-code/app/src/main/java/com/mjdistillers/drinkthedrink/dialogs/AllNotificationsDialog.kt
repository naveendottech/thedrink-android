package com.mjdistillers.drinkthedrink.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mjdistillers.drinkthedrink.*
import com.mjdistillers.drinkthedrink.adapters.NotificationListAdapter
import com.mjdistillers.drinkthedrink.model.response._all_notifications.Datum
import com.mjdistillers.drinkthedrink.model.response._all_notifications.GetAllNotificationsResponse
import com.mjdistillers.drinkthedrink.model.response.follow_unfollow_to_back_decline_logout.GetFollowUnfollowTO_BackResponse_Decline_Logout
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import com.mjdistillers.drinkthedrink.utilities.SharedPrefsUtils
import com.mjdistillers.drinkthedrink.utilities.ToastSnackUtils
import kotlinx.android.synthetic.main.layout_all_notificaions_dialog.view.*
import javax.inject.Inject

class AllNotificationsDialog : DialogFragment(), View.OnClickListener {

    @Inject
    lateinit var pref: SharedPrefsUtils

    lateinit var viewNotifications: View

    lateinit var adapter: NotificationListAdapter

    @Inject
    lateinit var dtdUtils: DtdUtils

    var itemPosition = 0

    override fun onStart() {
        super.onStart()
        var dialogFreg = dialog
        dialogFreg?.let {
            it.window?.setWindowAnimations(R.style.dialog_fragment_style)

            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dialogAllNotificaions = super.onCreateDialog(savedInstanceState)
        dialogAllNotificaions.window?.requestFeature(Window.FEATURE_NO_TITLE)

        App.app.getComponent().inject(this)

        viewNotifications  = LayoutInflater.from(activity as MainActivity)
            .inflate(R.layout.layout_all_notificaions_dialog,null,false)

        viewNotifications.ivClose.setOnClickListener(this)

        attachObserverAccept()
        attachObserverDecline()
//        attObserverAllNotifications()

        arguments?.let {
            if (it.containsKey(DtdPrefsKeys.Keys.NOTIFICATIONS_DATA)){
                var list = it.getParcelableArrayList<Datum>(DtdPrefsKeys.Keys.NOTIFICATIONS_DATA)
                list?.let {

                    viewNotifications.rvNotifications.layoutManager = LinearLayoutManager(activity as MainActivity)
                    adapter = NotificationListAdapter(list, activity as MainActivity,getHandlerForAcceptAndDecline())
                    viewNotifications?.rvNotifications?.adapter = adapter
                }
            }
        }

        dialogAllNotificaions.setContentView(viewNotifications)

        applyFonts(viewNotifications)

        return dialogAllNotificaions
    }

    private fun applyFonts(view: View) {
        view?.tvMessagesHeading.typeface = dtdUtils.fontFutura()
    }


    private fun attachObserverDecline() {
        var observer = Observer<GetFollowUnfollowTO_BackResponse_Decline_Logout>{

            var messageString = App.app.getString(R.string.problem_occured)

            it?.data?.message?.let {
                if (it.isNotEmpty())
                messageString = it[0]
            }

            var message = Message()
            message.arg1 = DtdConstants.SHOW_SNACKBAR
            message.arg2 = 1
            message.obj = messageString

            (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)

//            ToastSnackUtils.snackBar(viewNotifications,
//                messageString, Snackbar.LENGTH_LONG,false)

            adapter.updateListItem(itemPosition,false)

        }

        (activity as MainActivity).viewModel.liveDataDeclineRequest().observe(activity as MainActivity,observer)
    }

    private fun attachObserverAccept() {
        var observer = Observer<GetFollowUnfollowTO_BackResponse_Decline_Logout>{

            var messageString = App.app.getString(R.string.problem_occured)

            it?.data?.message?.let {
                if (it.isNotEmpty())
                    messageString = it[0]
            }

            var message = Message()
            message.arg1 = DtdConstants.SHOW_SNACKBAR
            message.obj = messageString

            (activity as MainActivity).viewModel.getAlertLiveData().postValue(message)



//            ToastSnackUtils.snackBar(viewNotifications,
//                messageString, Snackbar.LENGTH_LONG,false)


            adapter.updateListItem(itemPosition,true)

        }

        (activity as MainActivity).viewModel.liveDataFollowUnFollowTo().observe(activity as MainActivity,observer)
    }


    private fun getHandlerForAcceptAndDecline(): Handler{
        return object : Handler(Looper.myLooper()){
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)

                var datum = msg.obj as Datum
                itemPosition = datum.position

                when(msg.what){
                DtdConstants.NOTIFICATION_ACCEPT->{
                    (activity as MainActivity).viewModel.getFollowUnfollowTo(
                        datum.followId,
                        pref.getInt(DtdConstants.ID),
                        APIs.P_ONE,
                        datum.followDeviceToken)
                }

                DtdConstants.NOTIFICATION_DECLINE->{
                    (activity as MainActivity).viewModel.getDeclineRequest(pref.getInt(DtdConstants.ID),
                        datum.followId)
                }

                DtdConstants.NO_ITEM_LEFT->{
                    var messageSnackbar = Message()
                    messageSnackbar.arg1 = DtdConstants.SHOW_SNACKBAR
                    messageSnackbar.obj = App.app.getString(R.string.success)
                    (activity as MainActivity).handleMessages(messageSnackbar)

/*                    if (msg.obj as Boolean){
                        (activity as MainActivity).handleMessages(messageSnackbar)
                    }else{
                    }*/
                    dismiss()
                }
              }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ivClose->{
                dialog?.dismiss()
            }
        }
    }


}