package com.mjdistillers.drinkthedrink.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mjdistillers.drinkthedrink.App
import com.mjdistillers.drinkthedrink.DtdConstants
import com.mjdistillers.drinkthedrink.R
import com.mjdistillers.drinkthedrink.databinding.LayoutEventsItemBinding
import com.mjdistillers.drinkthedrink.model.response.bar_details.TodayEvent
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import javax.inject.Inject

class
TodayEventsRVAdapter(var isFromBars: Boolean, private val items : List<TodayEvent>, val context: Context):
    RecyclerView.Adapter<TodayEventsRVAdapter.ViewHolder>()  {

    lateinit var binding:LayoutEventsItemBinding

    @Inject
    lateinit var dtdUtil: DtdUtils

    init {
        App.app.getComponent().inject(this)
    }


    class ViewHolder (val binding: LayoutEventsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_events_item,parent,false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            var item = items[position]
            holder.binding.todayEvents = item



        applyFonts(holder)

            if (isFromBars)
                getImageType(items[position].event_category?: "",holder.binding.ivTodayEvents)
            else{
                dtdUtil.loadImageWithCircleTransform(context,binding.ivTodayEvents,items[position].storesEventsImage,
                    DtdConstants.PLACEHOLDER_NO_IMAGE)
            }
    }

    fun applyFonts(holder: ViewHolder){
        holder.binding.tvEventName.typeface = dtdUtil.fontFutura()
        holder.binding.tvEventTime.typeface = dtdUtil.fontFutura()
        holder.binding.tvEventsDescription.typeface = dtdUtil.fontFuturaBook()

    }

    private fun getImageType(eventType: String,imageView: ImageView) {
        when(eventType){

            DtdConstants.DRINK_SPECIAL,DtdConstants.FOOD_AND_DRINK_SPECIAL,DtdConstants.MJ_PROMOTION->{
                imageView.setImageResource(R.drawable.d)
            }
            else->{
                imageView.setImageResource(R.drawable.e)
            }
        }
    }

}