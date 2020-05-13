package com.mjdistillers.drinkthedrink.utilities.interfaces

import com.mjdistillers.drinkthedrink.utilities.models.ChoiceListModel


/**
 * Callback for Once Choice Alert
 * Methods called as per the name suggests
 * */
interface OneChoiceAlertCallbacks {
    fun eventDoneOfOneChoice(choice: ChoiceListModel)
    fun eventCancelOfOneChoice()
}