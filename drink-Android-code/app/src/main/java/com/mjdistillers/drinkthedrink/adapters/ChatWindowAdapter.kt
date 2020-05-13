package com.mjdistillers.drinkthedrink.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.mjdistillers.drinkthedrink.App
import com.mjdistillers.drinkthedrink.DtdConstants
import com.mjdistillers.drinkthedrink.R
import com.mjdistillers.drinkthedrink.databinding.MessageUserListItemBinding
import com.mjdistillers.drinkthedrink.model.response.user_chat_history.Datum
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import javax.inject.Inject

class ChatWindowAdapter(val items : List<Datum>,
                        var context: Context,
                        var userId: Int,
                        var fromImage: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var binding: MessageUserListItemBinding

    @Inject
    lateinit var layoutInflater: LayoutInflater

    @Inject
    lateinit var dtdUtils: DtdUtils

    init {
        App.app.getComponent().inject(this)
    }

    class ViewHolderSend (val view: View) : RecyclerView.ViewHolder(view){

        var tvMessage:TextView? = null
        var ivImage:ImageView? = null

        init {
            tvMessage = view.findViewById(R.id.tvMessageSend)
            ivImage = view.findViewById(R.id.ivImage)
        }

    }

    class ViewHolderReceived (val view: View) : RecyclerView.ViewHolder(view){

        var tvMessage:TextView? = null
        var ivUserImage:ImageView? = null
        var ivImage:ImageView? = null


        init {
            tvMessage = view.findViewById(R.id.tvMessageRecieved)
            ivUserImage = view.findViewById(R.id.ivMessageUserImage)
            ivImage = view.findViewById(R.id.ivImage)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view:View
        return when(viewType){
            0->{
                view = layoutInflater.inflate(R.layout.chat_window_message_sent_list_item,parent,false)

                ViewHolderSend(view)
            }
            1->{
                view = layoutInflater.inflate(R.layout.chat_window_message_received_list_item,parent,false)
                ViewHolderReceived(view)
            }
            else->{
                view = layoutInflater.inflate(R.layout.chat_window_message_sent_list_item,parent,false)
                ViewHolderSend(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    override fun getItemViewType(position: Int): Int {
        return when (items[position].from) {
            userId -> {
                0
            }
            else -> {
                1
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderSend){
            items[position]?.chat?.let {
                when(it.contains(".jpg")){
                    true->{
                        var iamgeURL = items[position].img_base_url+items[position].message_image+items[position].chat
                        holder.tvMessage?.visibility = View.GONE
                        holder.ivImage?.visibility = View.VISIBLE
                        holder.ivImage?.let {
                            dtdUtils.loadImageNoTransform(context,it,iamgeURL,DtdConstants.PLACEHOLDER_NO_IMAGE)
                        }
                    }
                    false->{
                        holder.tvMessage?.visibility = View.VISIBLE
                        holder.ivImage?.visibility = View.GONE
                        holder.tvMessage?.text = it
                        holder.tvMessage?.typeface = dtdUtils.fontNeutraMedium()
                    } }
            }
        }
        else{
            var view = holder as ViewHolderReceived
            items[position].chat?.let {
                when(it.contains(".jpg")){
                    true->{
                        var iamgeURL = items[position].img_base_url+items[position].message_image+items[position].chat
                        holder.tvMessage?.visibility = View.GONE
                        holder.ivImage?.visibility = View.VISIBLE
                        holder.ivImage?.let {
                            dtdUtils.loadImageNoTransform(context,it,iamgeURL,DtdConstants.PLACEHOLDER_NO_IMAGE)
                        }
                    }
                    false->{
                        holder.tvMessage?.visibility = View.VISIBLE
                        holder.ivImage?.visibility = View.GONE
                        holder.tvMessage?.text = it
                        holder.tvMessage?.typeface = dtdUtils.fontNeutraMedium()
                    }
                }

            }

            var imageView = view.ivUserImage
            imageView?.let {
                dtdUtils.loadImageWithCircleTransform(context,imageView,fromImage, DtdConstants.PLACEHOLDER_NO_IMAGE)
            }
        }
    }


}