package com.mjdistillers.drinkthedrink.adapters

import android.content.Context
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mjdistillers.drinkthedrink.App
import com.mjdistillers.drinkthedrink.DtdConstants
import com.mjdistillers.drinkthedrink.R
import com.mjdistillers.drinkthedrink.model.response.get_following.UserFollowing
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import kotlinx.android.synthetic.main.layout_follow_following_item.view.*
import javax.inject.Inject

class FollowingAdapter (private val items : List<UserFollowing>, var context: Context, var handler: Handler) :
    RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {
        companion object{
            var selected_pos = 0

            /*@BindingAdapter("srcImage")
            fun setImageViewResource(imageView: ImageView, imageURL: String) {
                Glide.with(imageView.context)
                    .load(imageURL)
                    .into(imageView)
            }*/
        }

    @Inject
    lateinit var dtdUtil: DtdUtils

    init {
        App.app.getComponent().inject(this)
    }

        class ViewHolder (val view: View) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            var view = LayoutInflater.from(context).inflate(R.layout.layout_follow_following_item,
                parent,false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            var data = items[position].data[0]

            dtdUtil.loadImageWithCircleTransform(context,holder.itemView.ivUserImage,
                DtdConstants.IMAGE_BASE_URL+DtdConstants.PROFILE_FOLDER_NAME+data.profileImage,
                DtdConstants.PLACEHOLDER_TEAMS_IMAGE)

            holder.itemView.tvUserName.text = data.name

                holder.itemView.llFollowUnfollow.setOnClickListener {
                    var msg = Message.obtain(handler)
                    msg.arg1 = DtdConstants.SHOW_PROFILE
                    var searchmodel = dtdUtil.getSearchFilterModelFromUserFollowing(items[position])
                    msg.arg2 = searchmodel.id
                    msg.obj = searchmodel
                    msg.sendToTarget()
                }

            holder.itemView.tvUserName.typeface = dtdUtil.fontNeutraMedium()

        }

    fun getListSize():Int{
        return items.size
    }

        override fun getItemCount(): Int {
            return items.size
        }
}
