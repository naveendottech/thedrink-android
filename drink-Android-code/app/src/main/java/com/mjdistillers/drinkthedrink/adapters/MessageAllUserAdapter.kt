package com.mjdistillers.drinkthedrink.adapters

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mjdistillers.drinkthedrink.App
import com.mjdistillers.drinkthedrink.DtdConstants
import com.mjdistillers.drinkthedrink.R
import com.mjdistillers.drinkthedrink.databinding.MessageUserListItemBinding
import com.mjdistillers.drinkthedrink.model.response.chat_all_user.Datum
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import com.mjdistillers.drinkthedrink.utilities.SharedPrefsUtils
import javax.inject.Inject


class MessageAllUserAdapter(var items : ArrayList<Datum>, var context: Context) :
    RecyclerView.Adapter<MessageAllUserAdapter.ViewHolder>() , Filterable {

    var itemsNewFilteredList = ArrayList<Datum>()

    lateinit var binding: MessageUserListItemBinding

    @Inject
    lateinit var layoutInflater: LayoutInflater

    @Inject
    lateinit var dtdUtils: DtdUtils

    @Inject
    lateinit var pref:SharedPrefsUtils

    init {
        App.app.getComponent().inject(this)
    }

    class ViewHolder (val bindingview: MessageUserListItemBinding) : RecyclerView.ViewHolder(bindingview.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(layoutInflater,
            R.layout.message_user_list_item,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemsNewFilteredList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = itemsNewFilteredList[position]
        holder.bindingview.dataModel = item

        if (position %2 == 0){
            holder.bindingview.root.setBackgroundColor(Color.parseColor("#F5F5F2"))
        }else{
            holder.bindingview.root.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }

        var name = if (item.fromId == pref.getInt(DtdConstants.ID)) {
            item.toName
        } else {
            item.fromName
        }

        holder.bindingview.tvUserName.text = name

        applyFonts(holder)

        var imageURL = DtdConstants.IMAGE_BASE_URL+DtdConstants.PROFILE_FOLDER_NAME+item.fromProfileImage
        dtdUtils.loadImageWithCircleTransform(context,holder.bindingview.ivUserImage,imageURL,DtdConstants.PLACEHOLDER_NO_IMAGE)
    }

    private fun applyFonts(view: ViewHolder) {
        view?.bindingview.tvMessage.typeface = dtdUtils.fontNeutraMedium()
        view?.bindingview.tvUserName.typeface = dtdUtils.fontNeutraMedium()
    }


    fun updateList(items : ArrayList<Datum>){
        this.items=items
        itemsNewFilteredList.addAll(items)
        notifyDataSetChanged()
    }



    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                itemsNewFilteredList.clear()

                if (TextUtils.isEmpty(constraint)) {
                    itemsNewFilteredList.addAll(items)
                } else {
                    var filteredList: ArrayList<Datum> = ArrayList()
                    for (obj in items) {

                        var name = if (obj.fromId == pref.getInt(DtdConstants.ID)) {
                            obj.toName
                        } else {
                            obj.fromName
                        }

                        if (name.toLowerCase().contains(constraint.toString().toLowerCase()) ||
                            obj.message.toLowerCase().contains(constraint.toString().toLowerCase())){
                            filteredList.add(obj)
                        }
                    }
                    itemsNewFilteredList.addAll(filteredList)
                }

                val filterResults = FilterResults()
                filterResults.values = itemsNewFilteredList

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                Handler().postDelayed(Runnable {
                    notifyDataSetChanged()
                }, 100)
            }

        }
    }

}