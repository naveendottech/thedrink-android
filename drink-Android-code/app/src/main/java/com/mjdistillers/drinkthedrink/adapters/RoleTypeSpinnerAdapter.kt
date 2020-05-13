package com.mjdistillers.drinkthedrink.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.mjdistillers.drinkthedrink.App
import com.mjdistillers.drinkthedrink.R
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import kotlinx.android.synthetic.main.layout_role_spinner_item.view.*
import javax.inject.Inject

class RoleTypeSpinnerAdapter(var context: Context?,var list: List<String>) : BaseAdapter() {


    @Inject
    lateinit var dtdUtils: DtdUtils

    init {
        App.app.getComponent().inject(this)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        return if (convertView == null) {
            var view = LayoutInflater.from(context).inflate(R.layout.layout_role_spinner_item, parent, false)
                view.tvRoleName.text = list[position].toUpperCase()
                applyFonts(view,1)
                view
        }else{
            convertView.tvRoleName.text = list[position].toUpperCase()
            applyFonts(convertView,1)
            convertView
        }
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return if (convertView == null) {
            var view = LayoutInflater.from(context)
                .inflate(R.layout.layout_role_spinner_item, parent, false)
            view.tvRoleName.text = list[position]
            applyFonts(view,2)
            view
        }else{
            convertView.tvRoleName.text = list[position]
            applyFonts(convertView,2)
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