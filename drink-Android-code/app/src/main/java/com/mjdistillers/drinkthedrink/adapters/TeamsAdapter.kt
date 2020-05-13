package com.mjdistillers.drinkthedrink.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mjdistillers.drinkthedrink.App
import com.mjdistillers.drinkthedrink.DtdConstants
import com.mjdistillers.drinkthedrink.R
import com.mjdistillers.drinkthedrink.databinding.LayoutTeamsListItemBinding
import com.mjdistillers.drinkthedrink.model.response.BarTeam
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import com.mjdistillers.drinkthedrink.utilities.LogUtils
import com.mjdistillers.drinkthedrink.utilities.SharedPrefsUtils
import javax.inject.Inject

class TeamsAdapter(private var items : List<BarTeam>, var context: Context, var followTeam: (BarTeam)->Unit, var unfollowTeam: (BarTeam)->Unit) :
    RecyclerView.Adapter<TeamsAdapter.ViewHolder>() {

    @Inject
    lateinit var dtdUtils: DtdUtils

    lateinit var currBarTeam:BarTeam

    @Inject
    lateinit var prefs:SharedPrefsUtils

    var userId = 0;

    var prevPositio = -1;

    lateinit var binding: LayoutTeamsListItemBinding


    init {
        prevPositio = -1
        App.app.getComponent().inject(this)
        userId = prefs.getInt(DtdConstants.ID)
    }

    companion object{
        var selected_pos = 0
    }

    class ViewHolder (val binding: LayoutTeamsListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.layout_teams_list_item,parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var teamModel = items[position]
        teamModel.position = position
        holder.binding.teamModel = teamModel

        dtdUtils.loadImageNoTransform(context,holder.binding.ivUserImage,
            teamModel.profileImg?:"", DtdConstants.PLACEHOLDER_TEAMS_IMAGE)


        holder.binding.tvFollowTeam.setOnClickListener{
//            if (prevPositio > -1) items[prevPositio].justRevertButton = false
            currBarTeam = teamModel
            followTeam(teamModel)
        }

        holder.binding.tvUnFollowTeam.setOnClickListener{
//            if (prevPositio > -1) items[prevPositio].justRevertButton = false
            currBarTeam = teamModel
            unfollowTeam(teamModel)
        }

        holder.itemView.setBackgroundResource(0)
//        if (selected_pos == position) holder.itemView.setBackgroundResource(R.drawable.selector_bg)
//        else  holder.itemView.setBackgroundResource(0)

//        if (!teamModel.isFollowVisible && !teamModel.isUnfollowVisible) {
    /*    if (teamModel.justRevertButton == DtdConstants.FOLLOW){
            LogUtils.logd("In JustRevertButton")
                    when{
                        holder. binding.tvFollowTeam.visibility == View.VISIBLE ->{
                            holder.binding.tvUnFollowTeam.visibility = View.VISIBLE
                            holder.binding.tvFollowTeam.visibility = View.GONE
                            teamModel.isFollowVisible = false
                         }
                        holder.binding.tvUnFollowTeam.visibility == View.VISIBLE ->{
                            holder.binding.tvUnFollowTeam.visibility = View.GONE
                            holder.binding.tvFollowTeam.visibility = View.VISIBLE
                            teamModel.isFollowVisible = true
                        }
                    }
        }else if(teamModel.justRevertButton == DtdConstants.UNFOLLOW){

        } */

        when (teamModel.justRevertButton) {
            DtdConstants.FOLLOW -> {
                holder.binding.tvUnFollowTeam.visibility = View.GONE
                holder.binding.tvFollowTeam.visibility = View.VISIBLE
                teamModel.isFollowVisible = true
            }
            DtdConstants.UNFOLLOW -> {
                holder.binding.tvUnFollowTeam.visibility = View.VISIBLE
                holder.binding.tvFollowTeam.visibility = View.GONE
                teamModel.isFollowVisible = false
            }
            else -> {
                when {
                    teamModel.userFollower == false -> {
                        holder.binding.tvUnFollowTeam.visibility = View.GONE
                        holder.binding.tvFollowTeam.visibility = View.VISIBLE
                        teamModel.isFollowVisible = true
                    }
                    teamModel.userFollower == true->{
                        holder.binding.tvUnFollowTeam.visibility = View.VISIBLE
                        holder.binding.tvFollowTeam.visibility = View.GONE
                        teamModel.isFollowVisible = false
                    }
                    else -> {
                        LogUtils.logd("ELSE CASE")
                    }
                }
            }
        }

        applyFonts(holder)

    }

    private fun applyFonts(holder: ViewHolder) {
        holder.binding.tvStrOne.typeface = dtdUtils.fontFuturaBook()
        holder.binding.tvStrTwo.typeface = dtdUtils.fontFuturaBook()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getCurrList():List<BarTeam>{
        return items
    }

    fun changeBackground(position: Int){
        selected_pos = position
        notifyDataSetChanged()
    }

}

