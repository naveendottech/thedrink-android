package com.mjdistillers.drinkthedrink.utilities.interfaces

import com.mjdistillers.drinkthedrink.utilities.models.ChoiceListModel


/**
 * Done and Cancel Callbacks for MultiChoice alert
 * Callbacks get called as per their name proclaiming
 * */
interface MultipleChoiceCallbacks {
    fun eventDoneOfMultiple(list: MutableList<ChoiceListModel>)
    fun eventCancelOfMultiple()
}