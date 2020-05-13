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
import com.mjdistillers.drinkthedrink.databinding.LayoutComingEventsBinding
import com.mjdistillers.drinkthedrink.model.response.bar_details.ComingEvent
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import javax.inject.Inject

class UpComingEventsRVAdapter(var isFromBars: Boolean,private val items : List<ComingEvent>, val context: Context):
    RecyclerView.Adapter<UpComingEventsRVAdapter.ViewHolder>()  {

    lateinit var binding:LayoutComingEventsBinding
    @Inject
    lateinit var dtdUtils: DtdUtils

    class ViewHolder (val binding: LayoutComingEventsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        App.app.getComponent().inject(this)

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_coming_events,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = items[position]
        holder.binding.comingEvents = item

        applyFonts(holder)

        if (isFromBars) {
            var eventType = items[position].event_category
            eventType?.let {
                getImageType(it, holder.binding.ivTodayEvents)
            }
        }else{
            dtdUtils.loadImageNoTransform(context,binding.ivTodayEvents,items[position].storesEventsImage,DtdConstants.PLACEHOLDER_NO_IMAGE)
        }
    }

    fun applyFonts(holder: ViewHolder){
        holder.binding.tvEventName.typeface = dtdUtils.fontFutura()
        holder.binding.tvEventTime.typeface = dtdUtils.fontFutura()
        holder.binding.tvEventsDescription.typeface = dtdUtils.fontFuturaBook()

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