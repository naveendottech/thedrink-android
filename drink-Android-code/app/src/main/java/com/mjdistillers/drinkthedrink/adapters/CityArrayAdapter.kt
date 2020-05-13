package com.mjdistillers.drinkthedrink.adapters

import android.content.Context
import android.graphics.Color
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.mjdistillers.drinkthedrink.App
import com.mjdistillers.drinkthedrink.R
import com.mjdistillers.drinkthedrink.model.response.get_cities.City
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import java.io.FilterReader

class CityArrayAdapter(context: Context, var items: List<String>) : ArrayAdapter<String>(context,R.layout.layout_role_spinner_item ,items) {


    var dtdUtils = DtdUtils(App.app)
    var allItems = mutableListOf<String>()
    var selectedPosition = 0

    override fun getCount(): Int {
        return allItems.size
    }

    override fun getPosition(item: String?): Int {
        return super.getPosition(item)
    }


    override fun getItem(position: Int): String? {
        return items[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
         view = LayoutInflater.from(context).inflate(R.layout.layout_role_spinner_item,parent,false)
        var textView = view.findViewById<TextView>(R.id.tvRoleName)
        textView.text = allItems[position].toUpperCase()
        textView.setTextColor(Color.BLACK)
//        textView.textSize = App.app.resources.getDimension(R.dimen.TEXT_SIZE_NORMAL)
        textView.typeface = dtdUtils.fontNeutraMedium()
        view.tag =  allItems[position]


        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        view = LayoutInflater.from(context).inflate(R.layout.layout_role_spinner_item,parent,false)
        var textView = view.findViewById<TextView>(R.id.tvRoleName)
        textView.text = allItems[position]
        textView.setTextColor(Color.BLACK)
//        textView.textSize = App.app.resources.getDimension(R.dimen.TEXT_SIZE_SMALL)
        textView.typeface = dtdUtils.fontNeutraMedium()

        view.tag =  allItems[position]



        textView.setOnClickListener {
            selectedPosition = position
        }

        return view
    }

    override fun getFilter(): Filter {
        return MyFilter()
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }



    inner class MyFilter: Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var listItems = mutableListOf<String>()
            constraint?.let {
                if (!constraint.isEmpty()) {
                    for (obj in items) {
                        if (obj.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            listItems.add(obj)
                        }
                    }
                }else{
                    listItems.addAll(items)
                }
            }

                var result = FilterResults()
                result.values = listItems
                result.count = listItems.size
            return result
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            results?.let {
                allItems = it.values as MutableList<String>
                notifyDataSetChanged()
            }
        }

    }


}