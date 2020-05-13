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
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mjdistillers.drinkthedrink.App
import com.mjdistillers.drinkthedrink.DtdConstants
import com.mjdistillers.drinkthedrink.MainActivity
import com.mjdistillers.drinkthedrink.R
import com.mjdistillers.drinkthedrink.adapters.FollowersAdapter
import com.mjdistillers.drinkthedrink.adapters.FollowingAdapter
import com.mjdistillers.drinkthedrink.model.response.get_followers.GetFollowersResponse
import com.mjdistillers.drinkthedrink.model.response.get_following.GetFollowingResponse
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import com.mjdistillers.drinkthedrink.utilities.SharedPrefsUtils
import kotlinx.android.synthetic.main.layout_following_follower.*
import kotlinx.android.synthetic.main.layout_following_follower.view.*
import javax.inject.Inject

class FollowersFollowingDialog : DialogFragment() {


    lateinit var pref: SharedPrefsUtils
    lateinit var viewFF:View
    var followersCount = 0
    var followingCount = 0

    @Inject
    lateinit var dtdUtils: DtdUtils


    init {
        App.app.getComponent().inject(this)
    }

    override fun onStart() {
        super.onStart()

        var dialogFreg = dialog
        dialogFreg?.setCanceledOnTouchOutside(false)
        dialogFreg?.let {
            it.window?.setWindowAnimations(R.style.dialog_fragment_style)
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dialogreg = super.onCreateDialog(savedInstanceState)
        pref = SharedPrefsUtils(activity as MainActivity)
        dialogreg.window?.requestFeature(Window.FEATURE_NO_TITLE)

        viewFF = LayoutInflater.from(activity as MainActivity).inflate(R.layout.layout_following_follower,
            null, false)


        viewFF.ivClose.setOnClickListener { dialogreg?.dismiss() }


        attachObserverFollowing(viewFF)
        attachObserverFollowers(viewFF)


        var idd = pref.getInt(DtdConstants.ID).toLong()

        (activity as MainActivity).viewModel.getFollowers(idd)
        (activity as MainActivity).viewModel.getFollowing(idd)

        dialogreg.setContentView(viewFF)


        applyFonts(viewFF)

        return dialogreg
    }

    private fun applyFonts(view: View) {
        view.tvFollowers.typeface = dtdUtils.fontFutura()
        view.tvFollowing.typeface = dtdUtils.fontFutura()
        view.tvFollowersCount.typeface = dtdUtils.fontNeutraMedium()
        view.tvFollowingCount.typeface = dtdUtils.fontNeutraMedium()
    }

    private fun attachObserverFollowers(view: View) {
        var observer =   Observer<GetFollowersResponse> {
            if (activity != null) {
                if (it.status){
                    it.data.userFollowers?.let {
                        followersCount = it.size
                        view.rvFollowers.layoutManager = LinearLayoutManager(activity as MainActivity)
                        view.rvFollowers.adapter = FollowersAdapter(it, activity as MainActivity,(activity as MainActivity).handler)
                    }
                    view.tvFollowersCount.text = it.data.followBy.toString()
                }
            }
//  HIDE TEAMS VISIBILITY WHEN PEOPLE ARE CLICKED
            if (followersCount == 0 && followingCount == 0){

                var message = Message()
                message.arg1 = DtdConstants.SHOW_SNACKBAR
                message.obj = getString(R.string.neither_nor_follower_following)
                (activity as MainActivity).handleMessages(message)

                dialog?.dismiss()
            }
        }
        (activity as MainActivity).viewModel.liveDataFollowers().observe(activity as MainActivity,observer)
    }

    private fun attachObserverFollowing(view : View) {
        var observer =  Observer<GetFollowingResponse> {
            if (activity != null){
                if (it.status){
                    it.data.userFollowing?.let {
                        followingCount = it.size
                        view.rvFollowing.layoutManager = LinearLayoutManager(activity as MainActivity)
                        view.rvFollowing.adapter = FollowingAdapter(it,activity as MainActivity,(activity as MainActivity).handler)
                    }
                    view.tvFollowingCount.text =  it.data.followTo.toString()
                }
            }

            if (followersCount == 0 && followingCount == 0){
                var message = Message()
                message.arg1 = DtdConstants.SHOW_SNACKBAR
                message.obj = getString(R.string.neither_nor_follower_following)
                (activity as MainActivity).handleMessages(message)
                dialog?.dismiss()
            }

        }
        (activity as MainActivity).viewModel.liveDataFollowing().observe(activity as MainActivity, observer)
    }
}