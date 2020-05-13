package com.mjdistillers.drinkthedrink.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mjdistillers.drinkthedrink.App
import com.mjdistillers.drinkthedrink.R
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import kotlinx.android.synthetic.main.layout_features_list_item.view.*
import javax.inject.Inject

class FeaturesRVAdapter(val items : List<String>, val context: Context):
    RecyclerView.Adapter<FeaturesRVAdapter.ViewHolder>() {

    @Inject
    lateinit var dtdUtils:DtdUtils

    init {
        App.app.getComponent().inject(this)
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        val tvName = view.tvFeature
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.layout_features_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = items[position]
        applyFonts(holder)
    }

    fun applyFonts(holder: ViewHolder){
        holder.tvName.typeface = dtdUtils.fontFuturaBook()

    }
}