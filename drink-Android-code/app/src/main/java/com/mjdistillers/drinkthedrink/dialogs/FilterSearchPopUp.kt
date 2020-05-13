package com.mjdistillers.drinkthedrink.dialogs

import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.PopupWindow
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mjdistillers.drinkthedrink.*
import com.mjdistillers.drinkthedrink.adapters.SearchFilterAdapter
import com.mjdistillers.drinkthedrink.model.SearchFilterModel
import com.mjdistillers.drinkthedrink.utilities.ApplicationUtils
import com.mjdistillers.drinkthedrink.utilities.SharedPrefsUtils
import kotlinx.android.synthetic.main.layout_filter_search_pop_up.view.*

class FilterSearchPopUp(var context: Context, var mainHandler: Handler,var from:String,
                        var liveDataPassModel: MutableLiveData<SearchFilterModel>?)  {

    var prefs = SharedPrefsUtils(context)
    var popUp: PopupWindow
     var adapter: SearchFilterAdapter
    var filterView: View

    init {
        filterView = LayoutInflater.from(context).inflate(R.layout.layout_filter_search_pop_up,null,false)

        var rvSearch = filterView.rvSearch
        adapter = SearchFilterAdapter(from, mutableListOf(),context,mainHandler,::onItemClicked)
        rvSearch.layoutManager = LinearLayoutManager(context)
        rvSearch.adapter = adapter

        var applicationUtils = ApplicationUtils()

        rvSearch.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING){
                    applicationUtils.hideKeyboardWithoutView(context as MainActivity)
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

        popUp = PopupWindow(filterView, FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.WRAP_CONTENT)
//        popUp.width = ApplicationUtils().getDisplayHeightAndWidthInDP(context)[0].toInt()-30
        popUp.animationStyle = R.style.pop_up_animation_style



        popUp.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        popUp.inputMethodMode = PopupWindow.INPUT_METHOD_NEEDED
    }


    fun onItemClicked(position: Int): Unit {
        if (liveDataPassModel == null){
            if (prefs.getString(DtdPrefsKeys.Keys.SEL_OPTION) != DtdConstants.SEL_OPTION_PEOPLE){
                var message = Message.obtain(mainHandler)
                message.arg1 = DtdConstants.HANDLE_FOR_SEARCH_POPUP
                message.obj = adapter.getCurrentList()[position]
                message.sendToTarget()
                dismissIfShowing()
            }else{
                var msg = Message.obtain(mainHandler)
                msg.arg1 = DtdConstants.SHOW_PROFILE
                msg.arg2 = adapter.getCurrentList()[position].id
                msg.obj = adapter.getCurrentList()[position]
                msg.sendToTarget()
            }
        }else{
            liveDataPassModel?.postValue(adapter.getCurrentList()[position])
            dismissIfShowing()
        }

        /*
        var touchEventCatcher = ItemTouchEventCatcher(context,filterView.rvSearch,
            object : TouchListenerInreface{
                override fun onSingleTap(view: View, position: Int) {

                }

                override fun onDoubleTap(view: View, position: Int) {

                }

                override fun onLongPressed(view: View, position: Int) {

                }

            } )

        filterView.rvSearch.addOnItemTouchListener(touchEventCatcher)*/
    }



    fun updateViewForListSize(listSize: Int){
        if(listSize == 0){
            filterView.tvFilterMessage.visibility = View.VISIBLE
            filterView.tvFilterMessage.text = App.app.getString(R.string.no_data_found)
            filterView.rvSearch.visibility = View.GONE
            dismissIfShowing()
        }else{
            filterView.tvFilterMessage.visibility = View.GONE
            filterView.rvSearch.visibility = View.VISIBLE
        }
    }


     fun followUnFollowHandling(message: Message) {
        var searchModel = message.obj as SearchFilterModel
         when{
             searchModel.initiateId == 0 ->{

             }
         }
    }

    fun dismissIfShowing(){
        if (popUp.isShowing)
             adapter.updateList(mutableListOf())
             popUp.dismiss()
    }
}