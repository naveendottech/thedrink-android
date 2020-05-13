package com.mjdistillers.drinkthedrink.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mjdistillers.drinkthedrink.App
import com.mjdistillers.drinkthedrink.DtdConstants
import com.mjdistillers.drinkthedrink.R
import com.mjdistillers.drinkthedrink.databinding.LayoutStoresItemBinding
import com.mjdistillers.drinkthedrink.model.MarkerModel
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import javax.inject.Inject

class StoresRVAdapter(private val items : ArrayList<MarkerModel>, var context: Context) :
    RecyclerView.Adapter<StoresRVAdapter.ViewHolder>() {

    lateinit var binding: LayoutStoresItemBinding

    @Inject
    lateinit var dtdUtils: DtdUtils

    init {
        App.app.getComponent().inject(this)
    }

    companion object{
       var selected_pos = 0

        /*@BindingAdapter("srcImage")
        fun setImageViewResource(imageView: ImageView, imageURL: String) {
            Glide.with(imageView.context)
                .load(imageURL)
                .into(imageView)
        }*/
    }

    class ViewHolder (val binding: LayoutStoresItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.layout_stores_item,parent,false)



        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.markerModel = items[position]

            dtdUtils.loadImageNoTransform(context,holder.binding.ivBar,
                items[position].barImage?:"",DtdConstants.PLACEHOLDER_NO_IMAGE)

        if (selected_pos == position) holder.itemView.setBackgroundResource(R.drawable.selector_bg)
        else  holder.itemView.setBackgroundResource(0)

        applyFonts(holder)

    }


    public fun getCurruntList():ArrayList<MarkerModel>{
        return items
    }

    private fun applyFonts(holder: ViewHolder) {
        holder.binding.tvName.typeface = dtdUtils.fontFuturaBook()
        holder.binding.tvCost.typeface = dtdUtils.fontFuturaBook()
    }


    override fun getItemCount(): Int {
        return items.size
    }

    fun changeBackground(position: Int){
        selected_pos = position
        notifyDataSetChanged()
    }

    private fun dollorString(dollors: Int?): String{
        return when(dollors){
            1-> {
                "$"
            }
            2->{
                "$$"
            }
            3->{
                "$$$"
            }
            else->{
                ""
            }
        }
    }


}
