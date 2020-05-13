package com.mjdistillers.drinkthedrink.utilities.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.RadioGroup
import androidx.appcompat.widget.AppCompatRadioButton
import com.google.android.gms.internal.phenotype.zzh.init


class CustomRadioButton : AppCompatRadioButton{

    constructor(context: Context) : super(context){
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet):    super(context, attrs){
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?,    defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    override fun toggle() {
        if (isChecked) isChecked = false
        else super.toggle()

    }

    override fun getAccessibilityClassName(): CharSequence {
        return CustomRadioButton::class.java.name
    }

}