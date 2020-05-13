package com.mjdistillers.drinkthedrink.adapters

import android.content.Context
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mjdistillers.drinkthedrink.App
import com.mjdistillers.drinkthedrink.DtdConstants
import com.mjdistillers.drinkthedrink.R
import com.mjdistillers.drinkthedrink.model.response._all_notifications.Datum
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import kotlinx.android.synthetic.main.notification_list_item.view.*
import javax.inject.Inject

class NotificationListAdapter(val items : ArrayList<Datum>, val context: Context,var acceptDeclinedHandler: Handler):
    RecyclerView.Adapter<NotificationListAdapter.ViewHolder>() {

    @Inject
    lateinit var dtdUtils:DtdUtils

    val followed = "Followed"
    val following = "Following"

    init {
        App.app.getComponent().inject(this)
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val tvAccept: TextView = view.tvAccept
        val tvDecline:TextView = view.tvDecline

        val ivUserImage: ImageView = view.ivUserImage
        val tvMessage: TextView = view.tvMessage

//        val tvUserName: TextView = view.tvUserName
//        val tvDistance: TextView = view.tvDistance
//        val tvDisabled: TextView = view.tvDisabled
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.notification_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            var obj = items[position]
            obj.position = position
//            holder.tvUserName.text = obj.followUsername
//            holder.tvDistance.text = obj.followEmail


            var imageUrl = DtdConstants.IMAGE_BASE_URL+DtdConstants.PROFILE_FOLDER_NAME+obj.followProfileImage
            dtdUtils.loadImageWithCircleTransform(context,holder.ivUserImage,imageUrl,DtdConstants.PLACEHOLDER_PROFILE_IMAGE)

        var nameToShow = obj.followUsername?:obj.followName?:""

        when(obj.notifications){

            followed->{
                holder.tvMessage.text = context.getString(R.string.notificaions_message_one,nameToShow)
            }

            following->{
                holder.tvMessage.text = context.getString(R.string.notificaions_message_two,nameToShow)
            }

           /* DtdConstants.NOTIFICATION_ACCEPTED->{
                holder.tvAccept.visibility = View.GONE
                holder.tvDecline.visibility = View.GONE

//                holder.tvDisabled.visibility = View.VISIBLE
//                holder.tvDisabled.text = DtdConstants.NOTIFICATION_ACCEPTED
            }

            DtdConstants.NOTIFICATION_DECLINED->{
                holder.tvAccept.visibility = View.GONE
                holder.tvDecline.visibility = View.GONE

                holder.tvDisabled.visibility = View.VISIBLE
                holder.tvDisabled.text = DtdConstants.NOTIFICATION_DECLINED
            }

            DtdConstants.NOTIFICATION_REQUESTED-> {
                holder.tvAccept.visibility = View.VISIBLE
                holder.tvDecline.visibility = View.VISIBLE

                holder.tvDisabled.visibility = View.GONE
            }*/
        }

      /*  var clickListener = View.OnClickListener {
            when(it.id){
                R.id.tvAccept->{
                    var msg = Message()
                    msg.what = DtdConstants.NOTIFICATION_ACCEPT
                    msg.obj = obj
                    acceptDeclinedHandler.sendMessage(msg)
                }

                R.id.tvDecline->{
                    var msg = Message()
                    msg.what = DtdConstants.NOTIFICATION_DECLINE
                    msg.obj = obj
                    acceptDeclinedHandler.sendMessage(msg)
                }
            }
        }
*/
        applyFonts(holder)

//        holder.tvAccept.setOnClickListener(clickListener)
//        holder.tvDecline.setOnClickListener(clickListener)
    }

    private fun applyFonts(view: ViewHolder) {
//        view?.tvUserName.typeface = dtdUtils.fontNeutraMedium()
//        view?.tvDistance.typeface = dtdUtils.fontNeutraMedium()
//        view?.tvDisabled.typeface = dtdUtils.fontFuturaBold()
        view?.tvMessage.typeface = dtdUtils.fontNeutraMedium()
        view?.tvAccept.typeface = dtdUtils.fontNeutraMedium()
        view?.tvDecline.typeface = dtdUtils.fontNeutraMedium()
    }


    fun updateListItem(position: Int, isAccepted: Boolean){
//        items.removeAt(position)

        notifyItemRemoved(position)

        if (items.isEmpty()){
            var msg = Message.obtain(acceptDeclinedHandler)
            msg.what = DtdConstants.NO_ITEM_LEFT
            msg.obj = isAccepted
            msg.sendToTarget()
        }

//        if(isAccepted){
//            items[position].notifications = DtdConstants.NOTIFICATION_ACCEPTED
//        }else{
//            items[position].notifications = DtdConstants.NOTIFICATION_DECLINED
//        }
//        notifyItemChanged(position)
    }

}