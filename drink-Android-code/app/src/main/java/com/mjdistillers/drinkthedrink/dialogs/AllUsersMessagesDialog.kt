package com.mjdistillers.drinkthedrink.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mjdistillers.drinkthedrink.*
import com.mjdistillers.drinkthedrink.adapters.MessageAllUserAdapter
import com.mjdistillers.drinkthedrink.model.response.chat_all_user.Datum
import com.mjdistillers.drinkthedrink.model.response.chat_all_user.GetAllUsesrsChatResponse
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import com.mjdistillers.drinkthedrink.utilities.SharedPrefsUtils
import com.mjdistillers.drinkthedrink.utilities.interfaces.TouchListenerInreface
import com.mjdistillers.drinkthedrink.utilities.listtouchlisteners.ItemTouchEventCatcher
import kotlinx.android.synthetic.main.layout_message_users_list.view.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class AllUsersMessagesDialog : DialogFragment(), View.OnClickListener {

    @Inject
    lateinit var pref: SharedPrefsUtils

    @Inject
    lateinit var dtdUtils: DtdUtils

    lateinit var adapter: MessageAllUserAdapter

    override fun onStart() {
        super.onStart()
        var dialogFreg = dialog
        dialogFreg?.let {
            it.window?.setWindowAnimations(R.style.dialog_fragment_style)
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dialogAllUsers = super.onCreateDialog(savedInstanceState)
        dialogAllUsers.window?.requestFeature(Window.FEATURE_NO_TITLE)

        App.app.getComponent().inject(this)

        var viewAllMess  = LayoutInflater.from(activity as MainActivity)
            .inflate(R.layout.layout_message_users_list,null,false)

//        attachObservers(viewAllMess)

        viewAllMess.ivClose.setOnClickListener(this)
        viewAllMess.ivSearch.setOnClickListener(this)
        viewAllMess.ivEdit.setOnClickListener(this)

        var handler = Handler(Looper.myLooper())

        arguments?.let {
            if (it.containsKey(DtdPrefsKeys.Keys.ALL_USER_MESSAGES_LIST)){
                var list = it.getParcelableArrayList<Datum>(DtdPrefsKeys.Keys.ALL_USER_MESSAGES_LIST)
                list?.let {
                    setUpRecyclerView(viewAllMess,it)
                }
            }
        }

         var timer = Timer()
         var DELAY:Long = 600

        viewAllMess.etSearchMessage.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                    s?.let {
//                    if (s.toString().length > 1) {
                      var timerTask = object : TimerTask() {
                          override fun run() {

                              handler.post(object :Runnable{
                                  override fun run() {
                                      adapter.filter.filter(s)
                                  }
                              })
                          }
                      }

                      timer.cancel()
                      timer = Timer()

                      timer.schedule(timerTask, DELAY)
//                  }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        viewAllMess?.etSearchMessage?.filters = arrayOf(
            dtdUtils.obtainFilterFirstCharNoSpace(viewAllMess?.etSearchMessage),
            dtdUtils.obtainInputFilterSpecialChars(viewAllMess?.etSearchMessage)
        )

//        (activity as MainActivity).viewModel.getAllUserChat(pref.getInt(DtdConstants.ID))
//        (activity as MainActivity).viewModel.getAllUserChat(11896)


        applyFonts(viewAllMess)
        dialogAllUsers.setContentView(viewAllMess)
        return dialogAllUsers
    }


    private fun applyFonts(view: View) {
        view?.tvMessagesHeading.typeface = dtdUtils.fontFutura()
        view?.etSearchMessage.typeface = dtdUtils.fontNeutraMedium()
    }


//    private fun attachObservers(view: View) {
//        var observer =
//            androidx.lifecycle.Observer<GetAllUsesrsChatResponse>
//            {
//                    t -> setUpRecyclerView(view,t)
//            }
//
//        (activity as MainActivity).viewModel.liveDataAlluser().observe(activity as MainActivity,observer)
//    }

    private fun setUpRecyclerView(view: View, dataList: ArrayList<Datum>) {
        dataList?.let {
            if (activity != null){
                view?.rvUserAllMessages?.layoutManager = LinearLayoutManager(activity as MainActivity)
                adapter = MessageAllUserAdapter(it,activity as MainActivity)
                adapter.updateList(it)
                view?.rvUserAllMessages?.adapter = adapter
            view.rvUserAllMessages.addOnItemTouchListener(
                ItemTouchEventCatcher(
                    activity as MainActivity,
                    view.rvUserAllMessages,
                    object : TouchListenerInreface {
                        override fun onSingleTap(view: View, position: Int) {

                            var msg = Message()
                            msg.obj = dataList[position]
                            msg.arg1 = DtdConstants.SHOW_CHAT_HISTORY
                            (activity as MainActivity).handleMessages(msg)

                            dialog?.dismiss()
                        }

                        override fun onDoubleTap(view: View, position: Int) {

                        }

                        override fun onLongPressed(view: View, position: Int) {

                        }
                    }))
             }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ivEdit->{
                var msg = Message()
                msg.arg1 = DtdConstants.SHOW_CHAT_HISTORY
                msg.arg2 = 1

                var dataObj = Datum()
                dataObj.fromId = 0
                dataObj.toId = 0

                msg.obj = dataObj

                (activity as MainActivity).handleMessages(msg)
                dialog?.dismiss()
            }

            R.id.ivSearch->{

            }

            R.id.ivClose->{
                dialog?.dismiss()
            }


        }
    }

}