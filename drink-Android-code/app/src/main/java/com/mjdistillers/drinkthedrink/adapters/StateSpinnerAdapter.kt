package com.mjdistillers.drinkthedrink.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.mjdistillers.drinkthedrink.APIs
import com.mjdistillers.drinkthedrink.App
import com.mjdistillers.drinkthedrink.R
import com.mjdistillers.drinkthedrink.model.response.states_provinces.State
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import kotlinx.android.synthetic.main.layout_role_spinner_item.view.*
import javax.inject.Inject

class StateSpinnerAdapter(var context: Context?,
                          var list:List<com.mjdistillers.drinkthedrink.model.response.edit_profile.State>) : BaseAdapter() {

    @Inject
    lateinit var dtdUtils: DtdUtils

    init {
        App.app.getComponent().inject(this)
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        return if (convertView == null) {
            var view = LayoutInflater.from(context)
                .inflate(R.layout.layout_role_spinner_item, parent, false)
            view.tvRoleName.text = list[position].fullName.toUpperCase()
            applyFonts(view,1)
            view
        }else{
            convertView.tvRoleName.text = list[position].fullName.toUpperCase()
            applyFonts(convertView,1)
            convertView
        }
    }

    fun applyFonts(view: View, type:Int){
        if (type == 1){
            view.tvRoleName.typeface = dtdUtils.fontFuturaBook()
        }else{
            view.tvRoleName.typeface = dtdUtils.fontNeutraMedium()
        }
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return if (convertView == null) {
            var view = LayoutInflater.from(context)
                .inflate(R.layout.layout_role_spinner_item, parent, false)
            view.tvRoleName.text = list[position].fullName
            applyFonts(view,2)
            view
        }else{
            convertView.tvRoleName.text = list[position].fullName
            applyFonts(convertView,2)
            convertView
        }
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }


    override fun getCount(): Int {
        return list.size
    }
}