package com.mjdistillers.drinkthedrink.utilities

import android.app.Dialog
import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.mjdistillers.drinkthedrink.App
import com.mjdistillers.drinkthedrink.R
import com.mjdistillers.drinkthedrink.utilities.adapters.MultipleAndOneChoiceListAdapter
import com.mjdistillers.drinkthedrink.utilities.listtouchlisteners.ItemTouchEventCatcher
import com.mjdistillers.drinkthedrink.utilities.models.ChoiceListModel
import com.mjdistillers.drinkthedrink.utilities.interfaces.*
import kotlinx.android.synthetic.main.layout_alert_information.*
import kotlinx.android.synthetic.main.layout_alert_multiple_choice.*

/**
 * Contains all kind of alert utilities
 * */
class AlertUtils {


    /**
     * Only information will be shown to user.
     * ok button in callback makes sure that use is aware for the showing information.
     * We can also perform some action on Ok
     *
     * To perform Action we have to pass callbacks to method
     * */
    fun showInformationAlert(
        context: Context,
        headingText: String,
        messageText: String,
        positiveButtonText: String,
        informationAlertCallbacks: InformationAlertCallbacks? = null
    ) : Dialog {
        var dialog = Dialog(context)
        dialog.setContentView(R.layout.layout_alert_information)
        dialog.tvTitle.text = headingText
        dialog.tvMessage.text = messageText
        dialog.tvActionOne.visibility = View.GONE
        dialog.tvActionTwo.visibility = View.GONE
        dialog.tvActionThree.text = positiveButtonText
        dialog.tvActionThree.visibility = View.VISIBLE
        dialog.tvTitle.visibility = View.VISIBLE

        dialog.tvMessage.movementMethod = ScrollingMovementMethod()

        dialog.setCanceledOnTouchOutside(false)

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        dialog.tvActionThree.setOnClickListener {
            informationAlertCallbacks?.let {
                informationAlertCallbacks.eventOkOfInformationAlert()
            }
            dialog.dismiss()
        }

        return dialog
    }

    /**
     * This is an alert with two actions
     * OK : ok acts as an positive button.
     * Cancel : acts as negative button. just dismisses the dialog
     *
     * */
    fun showOkCancelAlert(
        context: Context,
        headingText: String,
        messageText: String,
        positiveButtonText: String,
        negativeButtonText: String,
        okCancelAlertCallbacks: OkCancelAlertCallbacks? = null) {
        var dialog = Dialog(context)
        dialog.setContentView(R.layout.layout_alert_information)
        dialog.tvTitle.text = headingText
        dialog.tvMessage.text = messageText
        dialog.tvActionOne.visibility = View.GONE
        dialog.tvActionTwo.text = positiveButtonText
        dialog.tvActionThree.text = negativeButtonText
        dialog.tvActionThree.visibility = View.VISIBLE
        dialog.tvTitle.visibility = View.VISIBLE
        dialog.setCanceledOnTouchOutside(false)
        dialog.tvMessage.movementMethod = ScrollingMovementMethod()


        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        var onClickListener = View.OnClickListener {
            when (it?.id) {
                R.id.tvActionTwo -> {
                    okCancelAlertCallbacks?.let {
                        okCancelAlertCallbacks?.eventOkOfOkCancleAlert()
                    }
                    dialog.dismiss()
                }
                R.id.tvActionThree -> {
                    okCancelAlertCallbacks?.let {
                        okCancelAlertCallbacks?.eventCancelOfOkCancelAlert()
                    }
                    dialog.dismiss()
                }
            }
        }

        dialog.tvActionTwo.setOnClickListener(onClickListener)
        dialog.tvActionThree.setOnClickListener(onClickListener)

        dialog.show()
    }

/**
 * This is an alert with three Actions
 * agree: User agrees the information
 * disagree: User disagrees the information
 * cancel: No Action. just cancel the alert
 *
 * */
    fun showAgreeDisagreeCancelAlert(
        context: Context,
        headingText: String,
        messageText: String,
        agreeButtonText: String,
        disagreeButtonText: String,
        agreeDisagreeCancelAlertCallbacks: AgreeDisagreeCancelAlertCallbacks? = null) {
        var dialog = Dialog(context)
        dialog.setContentView(R.layout.layout_alert_information)
        dialog.tvTitle.text = headingText
        dialog.tvMessage.text = messageText
        dialog.tvActionOne.visibility = View.VISIBLE
        dialog.tvActionTwo.visibility = View.VISIBLE
        dialog.tvActionThree.visibility = View.VISIBLE
        dialog.tvTitle.visibility = View.VISIBLE
        dialog.tvActionTwo.text = agreeButtonText
        dialog.tvActionThree.text = disagreeButtonText
        dialog.setCanceledOnTouchOutside(false)
        dialog.tvMessage.movementMethod = ScrollingMovementMethod()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        var onClickListener = View.OnClickListener {
            when (it?.id) {
                R.id.tvActionOne -> {
                    agreeDisagreeCancelAlertCallbacks?.let {
                        agreeDisagreeCancelAlertCallbacks.eventCancelOfAgreeAlert()
                    }
                    dialog.dismiss()
                }
                R.id.tvActionTwo -> {
                    agreeDisagreeCancelAlertCallbacks?.let {
                        agreeDisagreeCancelAlertCallbacks?.eventAgreeOfAgreeAlert()
                    }
                    dialog.dismiss()
                }
                R.id.tvActionThree -> {
                    agreeDisagreeCancelAlertCallbacks?.let {
                        agreeDisagreeCancelAlertCallbacks?.eventDisagreeOfAgreeAlert()
                    }
                    dialog.dismiss()
                }
            }
        }

        dialog.tvActionOne.setOnClickListener(onClickListener)
        dialog.tvActionTwo.setOnClickListener(onClickListener)
        dialog.tvActionThree.setOnClickListener(onClickListener)

        dialog.show()
    }


    /**
     * This alert just show an information with no actions.
     * and no respond form user
     * */

    fun showHeaderInformationWithNoActionsAlert(
        context: Context,
        headingText: String,
        messageText: String
    ) {

        var dialog = Dialog(context)
        dialog.setContentView(R.layout.layout_alert_information)
        dialog.tvTitle.text = headingText
        dialog.tvMessage.text = messageText
        dialog.tvActionOne.visibility = View.GONE
        dialog.tvActionTwo.visibility = View.GONE
        dialog.tvActionThree.visibility = View.GONE
        dialog.tvTitle.visibility = View.VISIBLE
        dialog.setCanceledOnTouchOutside(true)
        dialog.tvMessage.movementMethod = ScrollingMovementMethod()


        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        dialog.show()
    }


    /**
     * This alert shows only information without action and No Heading is involved here
     * */
    fun showInformationOnlyWithNoActionsAlert(
        context: Context,
        messageText: String
    ) {
        var dialog = Dialog(context)
        dialog.setContentView(R.layout.layout_alert_information)
        dialog.tvMessage.text = messageText
        dialog.tvActionOne.visibility = View.GONE
        dialog.tvActionTwo.visibility = View.GONE
        dialog.tvActionThree.visibility = View.GONE
        dialog.tvTitle.visibility = View.GONE
        dialog.setCanceledOnTouchOutside(true)
        dialog.tvMessage.movementMethod = ScrollingMovementMethod()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        dialog.show()
    }

/**
 * This is an alert containing list. so that user can choose multiple values at once.
 * */
    fun showMultipleChoiceAlert(
    context: Context,
    headingText: String,
    doneButtonText: String,
    cancelButtonText: String,
    multipleOrOneChoiceInterface: MultipleChoiceCallbacks,
    list: MutableList<ChoiceListModel>
    ) {
        var dialog = Dialog(context)
        dialog.setContentView(R.layout.layout_alert_multiple_choice)
        dialog.tvTitleM.text = headingText

        dialog.recyclerView.layoutManager = LinearLayoutManager(context)

        var multipleAndOneChoiceAdaper = MultipleAndOneChoiceListAdapter(context, 1, list)
        dialog.recyclerView.adapter = multipleAndOneChoiceAdaper


        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(false)

        var touchListener = object : TouchListenerInreface {
            override fun onSingleTap(view: View, position: Int) {
                multipleAndOneChoiceAdaper.listOfChoices[position].isChecked =
                    !multipleAndOneChoiceAdaper.listOfChoices[position].isChecked
                multipleAndOneChoiceAdaper.notifyDataSetChanged()
            }

            override fun onDoubleTap(view: View, position: Int) {
                //  TO DO
            }

            override fun onLongPressed(view: View, position: Int) {
                //  TO DO
            }
        }

        var clickListener = View.OnClickListener {
            when (it?.id) {
                R.id.tvActionOneM -> {
                    dialog.dismiss()
                    multipleOrOneChoiceInterface.eventCancelOfMultiple()
                }
                R.id.tvActionTwoM -> {
                    dialog.dismiss()
                    multipleOrOneChoiceInterface.eventDoneOfMultiple(multipleAndOneChoiceAdaper.listOfChoices)
                }
            }
        }

        dialog.tvActionOneM.setOnClickListener(clickListener)
        dialog.tvActionTwoM.setOnClickListener(clickListener)


        dialog.recyclerView.addOnItemTouchListener(
            ItemTouchEventCatcher(
                context,
                dialog.recyclerView,
                touchListener
            )
        )

        dialog.tvActionOneM.text = cancelButtonText
        dialog.tvActionTwoM.text = doneButtonText

        dialog.show()

    }

    /**
     * This alert contains a list with radio buttons
     * so that user can choose only one item.
     * */
    fun showOneChoiceAlert(
        context: Context,
        headingText: String,
        doneButtonText: String,
        cancelButtonText: String,
        oneChoiceAlertCallbacks: OneChoiceAlertCallbacks,
        list: MutableList<ChoiceListModel>
    ) {
        var dialog = Dialog(context)
        dialog.setContentView(R.layout.layout_alert_multiple_choice)
        dialog.tvTitleM.text = headingText

        dialog.recyclerView.layoutManager = LinearLayoutManager(context)

        var multipleAndOneChoiceAdaper = MultipleAndOneChoiceListAdapter(context, 0, list)
        dialog.recyclerView.adapter = multipleAndOneChoiceAdaper

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(false)

        var lastChecked = 0
        var touchListener = object : TouchListenerInreface {
            override fun onSingleTap(view: View, position: Int) {
                multipleAndOneChoiceAdaper.listOfChoices[lastChecked].isChecked = false
                multipleAndOneChoiceAdaper.listOfChoices[position].isChecked = true
                multipleAndOneChoiceAdaper.notifyDataSetChanged()
                lastChecked = position
            }

            override fun onDoubleTap(view: View, position: Int) {
                //  TO DO
            }

            override fun onLongPressed(view: View, position: Int) {
                //  TO DO
            }
        }

        var clickListener = View.OnClickListener {
            when (it?.id) {
                R.id.tvActionOneM -> {
                    dialog.dismiss()
                    oneChoiceAlertCallbacks.eventCancelOfOneChoice()
                }
                R.id.tvActionTwoM -> {
                    dialog.dismiss()
                    oneChoiceAlertCallbacks.eventDoneOfOneChoice(multipleAndOneChoiceAdaper.listOfChoices[lastChecked])
                }
            }
        }

        dialog.tvActionOneM.setOnClickListener(clickListener)
        dialog.tvActionTwoM.setOnClickListener(clickListener)


        dialog.recyclerView.addOnItemTouchListener(
            ItemTouchEventCatcher(
                context,
                dialog.recyclerView,
                touchListener
            )
        )

        dialog.tvActionOneM.text = cancelButtonText
        dialog.tvActionTwoM.text = doneButtonText

        dialog.show()
    }

    /**
    * this alert allows user to choose only one item. without any action button.
     * As the user will choose the item. alert will be dismissed
    * */

    fun showChooseOneFromListAlertNoActions(
        context: Context,
        headingText: String,
        oneChoiceNoActionCallbacks: OneChoiceNoActionsCallbacks,
        list: MutableList<ChoiceListModel>
    ) {

        var dtdUtils = DtdUtils(App.app)


        var dialog = Dialog(context)
        dialog.setContentView(R.layout.layout_alert_one_choice_no_action)
        dialog.tvTitleM.text = headingText
        dialog.tvTitleM.typeface = dtdUtils.fontFutura()

        dialog.tvActionOneM.visibility = View.GONE
        dialog.tvActionTwoM.visibility = View.GONE

        dialog.tvActionOneM.typeface = dtdUtils.fontNeutraMedium()
        dialog.tvActionTwoM.typeface = dtdUtils.fontNeutraMedium()

        dialog.recyclerView.layoutManager = LinearLayoutManager(context)

        var multipleAndOneChoiceAdaper = MultipleAndOneChoiceListAdapter(context, 2, list)
        dialog.recyclerView.adapter = multipleAndOneChoiceAdaper

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        var touchListener = object : TouchListenerInreface {
            override fun onSingleTap(view: View, position: Int) {
                dialog.dismiss()
                oneChoiceNoActionCallbacks.eventListItemSelected(position)
            }

            override fun onDoubleTap(view: View, position: Int) {
                //  TO DO
            }

            override fun onLongPressed(view: View, position: Int) {
                //  TO DO
            }
        }

        dialog.recyclerView.addOnItemTouchListener(
            ItemTouchEventCatcher(
                context,
                dialog.recyclerView,
                touchListener
            )
        )
        dialog.show()
    }


}