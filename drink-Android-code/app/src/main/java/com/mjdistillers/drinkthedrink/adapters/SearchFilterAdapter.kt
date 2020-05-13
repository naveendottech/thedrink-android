package com.mjdistillers.drinkthedrink.adapters

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Message
import android.os.Messenger
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mjdistillers.drinkthedrink.App
import com.mjdistillers.drinkthedrink.DtdConstants
import com.mjdistillers.drinkthedrink.R
import com.mjdistillers.drinkthedrink.databinding.LayoutMainSearchFilterItemBinding
import com.mjdistillers.drinkthedrink.model.SearchFilterModel
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import com.mjdistillers.drinkthedrink.utilities.SharedPrefsUtils
import java.lang.Exception
import javax.inject.Inject

class SearchFilterAdapter(var from:String,
                          private var items:List<SearchFilterModel>,
                          val context: Context,
                          var mainHandler: Handler, var clickFunction: (position: Int)->Unit)
    : RecyclerView.Adapter<SearchFilterAdapter.ViewHolder>() {

    lateinit var binding: LayoutMainSearchFilterItemBinding

    var isLoggedIn = false

    @Inject
    lateinit var dtdUtil: DtdUtils

    @Inject
    lateinit var prefs:SharedPrefsUtils



    init {
        App.app.getComponent().inject(this)
        isLoggedIn = prefs.getBoolean(DtdConstants.IS_LOGGED_IN)

    }

    class ViewHolder(val binding: LayoutMainSearchFilterItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_main_search_filter_item,parent,false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var followCliks = false
        var currItem = items[position]
        holder.binding.filterModel = currItem

        if (currItem.from == DtdConstants.SEL_OPTION_PEOPLE) {
            dtdUtil.loadImageWithCircleTransform(
                context,
                holder.binding.ivUserImage,
                currItem.imageUri,
                DtdConstants.PLACEHOLDER_PROFILE_IMAGE)
        }else{
            dtdUtil.loadImageNoTransform(
                context,
                holder.binding.ivUserImage,
                currItem.imageUri,
                DtdConstants.PLACEHOLDER_PROFILE_IMAGE)
        }

//        Glide.with(context).load(items[position].imageUri)
//            .apply(RequestOptions.noAnimation())
//            .apply(RequestOptions.noTransformation())
//            .into( holder.binding.ivUserImage)

        if (position %2 == 0){
            holder.binding.root.setBackgroundColor(Color.parseColor("#F5F5F2"))
        }else{
            holder.binding.root.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }

        if ((currItem.from == DtdConstants.SEL_OPTION_PEOPLE || currItem.from == DtdConstants.FROM_PEOPLE_SEARCH_MESSAGES) && isLoggedIn) {

            if (currItem.from == DtdConstants.SEL_OPTION_PEOPLE){
                holder.binding.tvFollowFolowingMessage.visibility = View.GONE
                holder.binding.tvFollowFolowing.visibility = View.VISIBLE
                holder.binding.tvFollowFolowing.text = currItem.followBtnText

                holder.binding.tvBlockMessage.visibility = View.GONE
                holder.binding.tvFollowFolowingBlock.visibility = View.VISIBLE
                holder.binding.tvFollowFolowingBlock.text = currItem.blockBtnText

            }else{
                holder.binding.tvFollowFolowing.visibility = View.GONE
                holder.binding.tvFollowFolowingMessage.visibility = View.VISIBLE
                holder.binding.tvFollowFolowingMessage.text = currItem.followBtnText

                holder.binding.tvFollowFolowingBlock.visibility = View.GONE
                holder.binding.tvBlockMessage.visibility = View.VISIBLE
                holder.binding.tvBlockMessage.text = currItem.blockBtnText
            }

            when(currItem.followBtnText){

                App.app.getString(R.string.follow)->{
                    holder.binding.tvFollowFolowing.setBackgroundResource(R.drawable.main_action_popup_btn_bg)
                    holder.binding.tvFollowFolowing.text = App.app.getString(R.string.follow)

                    holder.binding.tvFollowFolowingMessage.text = App.app.getString(R.string.follow)
                    holder.binding.tvFollowFolowingMessage.setBackgroundResource(R.drawable.main_action_popup_btn_bg)
                }

                App.app.getString(R.string.unfollow)->{
                holder.binding.tvFollowFolowing.setBackgroundResource(R.drawable.main_action_popup_btn_bg)
                holder.binding.tvFollowFolowing.text = App.app.getString(R.string.unfollow)

                    holder.binding.tvFollowFolowingMessage.setBackgroundResource(R.drawable.main_action_popup_btn_bg)
                    holder.binding.tvFollowFolowingMessage.text = App.app.getString(R.string.unfollow)
                }
            }

            when(currItem.blockBtnText){
                App.app.getString(R.string.block)->{
                    holder.binding.tvFollowFolowingBlock.setBackgroundResource(R.drawable.main_action_popup_btn_bg)
                    holder.binding.tvFollowFolowingBlock.text = App.app.getString(R.string.block)

                    holder.binding.tvBlockMessage.text = App.app.getString(R.string.block)
                    holder.binding.tvBlockMessage.setBackgroundResource(R.drawable.main_action_popup_btn_bg)

                    holder.binding.tvFollowFolowing.setBackgroundResource(R.drawable.main_action_popup_btn_bg)
                    holder.binding.tvFollowFolowingMessage.setBackgroundResource(R.drawable.main_action_popup_btn_bg)

                    followCliks = true

                }
                App.app.getString(R.string.unblock)->{
                    holder.binding.tvFollowFolowingBlock.setBackgroundResource(R.drawable.main_action_popup_btn_bg)
                    holder.binding.tvFollowFolowingBlock.text = App.app.getString(R.string.unblock)

                    holder.binding.tvBlockMessage.text = App.app.getString(R.string.unblock)
                    holder.binding.tvBlockMessage.setBackgroundResource(R.drawable.main_action_popup_btn_bg)

                    holder.binding.tvFollowFolowing.setBackgroundResource(R.drawable.main_action_popup_disabled_btn_bg)
//                    holder.binding.tvFollowFolowing.text = App.app.getString(R.string.follow)
//                    holder.binding.tvFollowFolowingMessage.text = App.app.getString(R.string.follow)
                    holder.binding.tvFollowFolowingMessage.setBackgroundResource(R.drawable.main_action_popup_disabled_btn_bg)

                    followCliks = false


                }
            }


//            if (items[position].person_status == (DtdConstants.PERSON_STATUS_PUBLIC)) {
//                holder.binding.tvFollowFolowing.text = App.app.resources.getString(R.string.follow)
//                holder.binding.tvFollowFolowing.setBackgroundResource(R.drawable.main_action_popup_btn_bg)
//            }else{
//                holder.binding.tvFollowFolowing.text = App.app.resources.getString(R.string.following)
//                holder.binding.tvFollowFolowing.setBackgroundResource(R.drawable.main_action_popup_disabled_btn_bg)
//                }
        }/*else {
            // When comes from people search from messages.
            holder.binding.tvFollowFolowing.visibility = View.GONE
            holder.binding.tvFollowFolowingMessage.visibility = View.VISIBLE
        }*/

        var clickEvents = View.OnClickListener {
            when(it.id){
                R.id.tvFollowFolowingMessage, R.id.tvFollowFolowing->{
                    var item = currItem
                    item.isFromBlock = 0
                    item.position = position
                    handleClick(item)
                }

                R.id.tvBlockMessage,R.id.tvFollowFolowingBlock->{
                    var item = currItem
                    item.isFromBlock = 1
                    item.position = position
                    handleClick(item)
                }

                R.id.llMainFilterAdapter->{
                    clickFunction(position)
                }
            }
        }

        applyFonts(holder)


        if (followCliks) {
            holder.binding.tvFollowFolowingMessage.setOnClickListener(clickEvents)
            holder.binding.tvFollowFolowing.setOnClickListener(clickEvents)
        }else{
            holder.binding.tvFollowFolowingMessage.setOnClickListener(null)
            holder.binding.tvFollowFolowing.setOnClickListener(null)
        }

        holder.binding.tvBlockMessage.setOnClickListener(clickEvents)
        holder.binding.tvFollowFolowingBlock.setOnClickListener(clickEvents)
        holder.binding.llMainFilterAdapter.setOnClickListener(clickEvents)

    }

    fun applyFonts(holder: ViewHolder){
        holder.binding.tvUserName.typeface = dtdUtil.fontNeutraMedium()
        holder.binding.tvDistance.typeface = dtdUtil.fontNeutraMedium()
        holder.binding.tvFollowFolowing.typeface = dtdUtil.fontNeutraMedium()
        holder.binding.tvFollowFolowingMessage.typeface = dtdUtil.fontNeutraMedium()
        holder.binding.tvBlockMessage.typeface = dtdUtil.fontNeutraMedium()
        holder.binding.tvFollowFolowingBlock.typeface = dtdUtil.fontNeutraMedium()
    }



    private fun handleClick(searchModel: SearchFilterModel) {

        var message = Message.obtain(mainHandler)

        var messenger = Messenger(object : Handler(){
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                updateAsPerMessage(msg)
            }
        })

        message.replyTo = messenger
        message.arg1 = DtdConstants.FOLLOW_UNFOLOW_HANDLING
        message.obj = searchModel
        message.sendToTarget()
    }

    private fun updateAsPerMessage(msg: Message) {
                var btnText= App.app.getString(R.string.follow)
                var fModel = items[msg.arg2]
//                try{
//                    btnText = msg.obj as String
//                }catch (e: Exception){ e.printStackTrace()}
                if (msg.arg2 > -1) {  // arg2 is position in list
                    if(fModel.isFromBlock == 0) {
                        when (fModel.followBtnText) {

                            App.app.getString(R.string.follow) -> {
                                fModel.followBtnText = App.app.getString(R.string.unfollow)
                            }

                            App.app.getString(R.string.unfollow) -> {
                                fModel.followBtnText = App.app.getString(R.string.follow)
                            }
                        }
                        notifyItemChanged(msg.arg2)
                    }else{
                        when (fModel.blockBtnText) {
                            App.app.getString(R.string.block) -> {
                                fModel.blockBtnText = App.app.getString(R.string.unblock)
                                fModel.followBtnText = App.app.getString(R.string.follow)
                            }

                            App.app.getString(R.string.unblock) -> {
                                fModel.blockBtnText = App.app.getString(R.string.block)
                                fModel.followBtnText = App.app.getString(R.string.follow)
                            }
                        }
                        notifyItemChanged(msg.arg2)
                    }
            }
    }

    fun getCurrentList():List<SearchFilterModel>{
        return items
    }

    fun updateList(itemsList :List<SearchFilterModel>){
        this.from = from
        items = itemsList
        notifyDataSetChanged()
    }

}