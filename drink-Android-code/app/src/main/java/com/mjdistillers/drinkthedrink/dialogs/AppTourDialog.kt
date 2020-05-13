package com.mjdistillers.drinkthedrink.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.viewpager.widget.ViewPager
import com.mjdistillers.drinkthedrink.MainActivity
import com.mjdistillers.drinkthedrink.R
import com.mjdistillers.drinkthedrink.adapters.AdvertisementAdapter
import com.mjdistillers.drinkthedrink.adapters.ImageAdapter
import com.mjdistillers.drinkthedrink.adapters.TourImageAdapter

class AppTourDialog:DialogFragment(){

    lateinit var viewPager:ViewPager

    override fun onStart() {
        super.onStart()

        var dialogFreg = dialog
        dialogFreg?.let {
            it.setCanceledOnTouchOutside(false)
            it.window?.setWindowAnimations(R.style.dialog_fragment_style)

//            dialogFreg.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT,
//                ConstraintLayout.LayoutParams.MATCH_PARENT)
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dialogreg = super.onCreateDialog(savedInstanceState)
        dialogreg.window?.requestFeature(Window.FEATURE_NO_TITLE)

        var view = LayoutInflater.from(activity as MainActivity)
            .inflate(R.layout.layout_app_tour,null,false)

        setUpViewpagerAndTextChanges(view)

        dialogreg.setContentView(view)
        return dialogreg
    }

    private fun setUpViewpagerAndTextChanges(view: View) {
         viewPager = view.findViewById(R.id.vpImages)
        var tvHeading = view.findViewById<TextView>(R.id.tvHeading)
        var tvDescription = view.findViewById<TextView>(R.id.tvDescription)
        var ivClose = view.findViewById<ImageView>(R.id.ivClose)

        var rlNextBtn = view.findViewById<RelativeLayout>(R.id.rlNextBtn)
        var ivDotOne = view.findViewById<ImageView>(R.id.ivDotOne)
        var ivDotTwo = view.findViewById<ImageView>(R.id.ivDotTwo)
        var ivDotThree = view.findViewById<ImageView>(R.id.ivDotThree)
        var tvButtonText = view.findViewById<TextView>(R.id.tvButtonText)

        ivDotOne.setImageResource(R.drawable.ic_dot_gray)
        ivDotTwo.setImageResource(R.drawable.ic_dot_white)
        ivDotThree.setImageResource(R.drawable.ic_dot_white)

        var onclicEvent = View.OnClickListener {
            when(it.id){
                R.id.ivDotOne->{
                    viewPager.currentItem = 0
                }
                R.id.ivDotTwo->{
                    viewPager.currentItem = 1
                }
                R.id.ivDotThree->{
                    viewPager.currentItem = 2
                }

            }
        }

        ivDotOne.setOnClickListener(onclicEvent)
        ivDotTwo.setOnClickListener(onclicEvent)
        ivDotThree.setOnClickListener(onclicEvent)

        viewPager.adapter = TourImageAdapter(activity as MainActivity)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                when(position){
                    0->{
                        tvHeading.text = getString(R.string.search)
                        tvDescription.text = getString(R.string.search_message)
                        tvButtonText.text = getString(R.string.next)

                        ivDotOne.setImageResource(R.drawable.ic_dot_gray)
                        ivDotTwo.setImageResource(R.drawable.ic_dot_white)
                        ivDotThree.setImageResource(R.drawable.ic_dot_white)
                    }

                    1->{
                        tvHeading.text = getString(R.string.find)
                        tvDescription.text = getString(R.string.find_message)
                        tvButtonText.text = getString(R.string.next)

                        ivDotOne.setImageResource(R.drawable.ic_dot_white)
                        ivDotTwo.setImageResource(R.drawable.ic_dot_gray)
                        ivDotThree.setImageResource(R.drawable.ic_dot_white)
                    }

                    2->{
                        tvHeading.text = getString(R.string.drink)
                        tvDescription.text = getString(R.string.drink_message)
                        tvButtonText.text = getString(R.string.start)

                        ivDotOne.setImageResource(R.drawable.ic_dot_white)
                        ivDotTwo.setImageResource(R.drawable.ic_dot_white)
                        ivDotThree.setImageResource(R.drawable.ic_dot_gray)
                    }
                }
            }
        })

        ivClose.setOnClickListener {
            dialog?.dismiss()
        }

        rlNextBtn.setOnClickListener {
            when(tvButtonText.text){
                getString(R.string.next)->{
                    if (viewPager.currentItem in 0..1){
                        viewPager.setCurrentItem(viewPager.currentItem +1)
                    }
                }

                getString(R.string.start)->{
                    dialog?.dismiss()
                }
            }
        }

    }

}