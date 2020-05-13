package com.mjdistillers.drinkthedrink

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.mjdistillers.drinkthedrink.adapters.ImageAdapter

class ImagesSliderFragment : DialogFragment(), View.OnClickListener{

    lateinit var ivRight:ImageView
    lateinit var ivLeft:ImageView
    lateinit var ivCross:ImageView
    lateinit var viewPager2: ViewPager
    var pos = 0
    var list = arrayListOf<String>()

    override fun onStart() {
        super.onStart()
        var dialogFreg = dialog
        dialogFreg?.let {
            it.setCanceledOnTouchOutside(false)
            it.window?.setWindowAnimations(R.style.dialog_fragment_style)

            dialogFreg.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT)
            var color_bt = Color.parseColor("#11000000")
            it.window?.setBackgroundDrawable(ColorDrawable(color_bt))
        }

                    dialogFreg?.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        arguments?.let {
            if (it.containsKey(DtdConstants.POSITION))
                pos = it.getInt(DtdConstants.POSITION)
            if (it.containsKey(DtdConstants.LIST_DATA))
                list = it.getStringArrayList(DtdConstants.LIST_DATA)?: arrayListOf()
        }

        var view = LayoutInflater.from(activity as MainActivity).inflate(R.layout.layout_image_slider,container,false)
        ivLeft = view.findViewById(R.id.ivLeft)
        ivRight = view.findViewById(R.id.ivRight)
        ivCross = view.findViewById(R.id.ivCorss)

        viewPager2 = view.findViewById(R.id.vp2Images)

        if(list.size == 1 || list.isEmpty()) {
            ivRight.visibility = View.GONE
            ivLeft.visibility = View.GONE
        }

        viewPager2.adapter = ImageAdapter(activity as MainActivity,list,null)

        viewPager2.currentItem = pos

        viewPager2.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
                if (position == 0) ivLeft.visibility = View.GONE
                else if (position == list.size-1) ivRight.visibility = View.GONE
                else{
                    ivLeft.visibility = View.VISIBLE
                    ivRight.visibility = View.VISIBLE
                }
            }
        })

        ivLeft.setOnClickListener(this)
        ivCross.setOnClickListener(this)
        ivRight.setOnClickListener(this)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ivLeft->{
                var itemPos = viewPager2.currentItem
                if (itemPos > 0)
                    viewPager2.currentItem = itemPos-1
            }
            R.id.ivRight->{
                var itemPos = viewPager2.currentItem
                var lengthList = list.size - 1
                if (itemPos < lengthList)
                    viewPager2.currentItem = itemPos+1
            }

            R.id.ivCorss->{
                dialog?.dismiss()
            }
        }
    }

}