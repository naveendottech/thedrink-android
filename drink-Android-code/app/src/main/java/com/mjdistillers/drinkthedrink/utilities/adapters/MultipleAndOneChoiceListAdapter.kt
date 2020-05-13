package com.mjdistillers.drinkthedrink.utilities.adapters

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mjdistillers.drinkthedrink.App
import com.mjdistillers.drinkthedrink.utilities.models.ChoiceListModel
import com.mjdistillers.drinkthedrink.R
import com.mjdistillers.drinkthedrink.utilities.DtdUtils

/**
 * This List Adapter is being used in list Alert Utilities.
 *
 * @context is Conext of activity
 * @type : is type of list like
 * 0 for OnceChoice
 * 1 for MultipleChoice
 *
 * @listOfChoices : List having ChoicesListModel
 *
 */


class MultipleAndOneChoiceListAdapter(
    var context: Context,
    var type: Int,
    var listOfChoices: MutableList<ChoiceListModel>
) :  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var dtdUtils:DtdUtils
    init {
        dtdUtils = DtdUtils(App.app)
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return if (type == 0) {
            OneChoiceHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.layout_one_choice_list_item,
                    p0,
                    false
                )
            )
        } else if (type == 1) {
            MultipleChoiceHolder(
                LayoutInflater.from(
                    context
                ).inflate(R.layout.layout_multiple_choice_list_item, p0, false)
            )
        } else {
            OneChoiceNoActionHolder(
                LayoutInflater.from(
                    context
                ).inflate(R.layout.layout_one_choice_no_action, p0, false)
            )
        }
    }

    override fun getItemCount(): Int {
        return listOfChoices.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is OneChoiceHolder && type == 0) {
            holder.textView.text = listOfChoices[position].text
            holder.radio.isChecked = listOfChoices[position].isChecked
            holder.textView.typeface =  dtdUtils.fontNeutraMedium()
        } else if (holder is MultipleChoiceHolder && type == 1) {
            holder.textView.text = listOfChoices[position].text
            holder.checkbox.isChecked = listOfChoices[position].isChecked
            holder.textView.typeface =  dtdUtils.fontNeutraMedium()

        } else if (holder is OneChoiceNoActionHolder && type == 2) {
            holder.textView.text = listOfChoices[position].text
            holder.textView.typeface =  dtdUtils.fontNeutraMedium()
        }


    }

    /**
     * Holder class for MultipleChoiceList
     * */
    class MultipleChoiceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var layout: RelativeLayout = itemView.findViewById(R.id.rlMultipleChoiceListItem)
        var checkbox: CheckBox = itemView.findViewById(R.id.checkboxMultipleChoice)
        var textView: TextView = itemView.findViewById(R.id.tvTextMultipleChoice)
    }

    /**
     * Holder class for OneChoiceList
     * */
    class OneChoiceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var layout: RelativeLayout = itemView.findViewById(R.id.rlOneChoiceListItem)
        var radio: RadioButton = itemView.findViewById(R.id.radioOneChoice)
        var textView: TextView = itemView.findViewById(R.id.tvTextOneChoice)
    }

    /**
     * Holder class for One choice but no action button list
     * */
    class OneChoiceNoActionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var layout: RelativeLayout = itemView.findViewById(R.id.rlOneChoiceNoActionListItem)
        var textView: TextView = itemView.findViewById(R.id.tvOneChoiceNoAction)
    }
}