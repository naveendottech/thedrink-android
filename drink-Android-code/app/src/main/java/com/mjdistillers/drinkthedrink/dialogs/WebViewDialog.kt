package com.mjdistillers.drinkthedrink.dialogs

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.webkit.*
import androidx.fragment.app.DialogFragment
import com.mjdistillers.drinkthedrink.App
import com.mjdistillers.drinkthedrink.DtdPrefsKeys
import com.mjdistillers.drinkthedrink.MainActivity
import com.mjdistillers.drinkthedrink.R
import com.mjdistillers.drinkthedrink.utilities.ApplicationUtils
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import com.mjdistillers.drinkthedrink.utilities.SharedPrefsUtils
import kotlinx.android.synthetic.main.layout_web_view_dialog.view.*
import javax.inject.Inject

class WebViewDialog: DialogFragment(), View.OnClickListener {

    @Inject
    lateinit var pref: SharedPrefsUtils

    @Inject
    lateinit var dtdUtils: DtdUtils

    override fun onStart() {
        super.onStart()
        var dialogFreg = dialog
        dialogFreg?.let {
            it.window?.setWindowAnimations(R.style.dialog_fragment_style)
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dialogAllUsers = super.onCreateDialog(savedInstanceState)
        dialogAllUsers.window?.requestFeature(Window.FEATURE_NO_TITLE)

        var viewAllMess  = LayoutInflater.from(activity as MainActivity)
            .inflate(R.layout.layout_web_view_dialog,null,false)

        var url = ""
        var webViewFrom = ""

        arguments?.let {
            if (it.containsKey(DtdPrefsKeys.Keys.WEB_VIEW_URL))
                url = it.getString(DtdPrefsKeys.Keys.WEB_VIEW_URL)?:""
            if (it.containsKey(DtdPrefsKeys.Keys.WEB_VIEW_FROM))
                webViewFrom = it.getString(DtdPrefsKeys.Keys.WEB_VIEW_FROM)?:""
        }

        App.app.getComponent().inject(this)


        viewAllMess.ivClose.setOnClickListener(this)


        viewAllMess.webView.webViewClient = object : WebViewClient(){

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                var updateURL = url
                url?.let{
                    when{
                        it.contains("drinkthedrink")->{
                            (activity as MainActivity).openInBrowser("https://drinkthedrink.com")
                            dialog?.dismiss()
                        }

                        it.contains("social@")->{
                            var receipientList =  arrayOf("social@mjdistillers.com")
                            (activity as MainActivity).sendEmail(receipientList,"","")
                            dialog?.dismiss()
                        }
                        else->{
                            return super.shouldOverrideUrlLoading(view, updateURL)

                        }
                    }
                }
                return super.shouldOverrideUrlLoading(view, updateURL)
            }



            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                viewAllMess.webViewProgressbar.visibility = View.GONE
            }

        }


        if (URLUtil.isValidUrl(url)) {
            viewAllMess.tvWebViewHeading.text = webViewFrom
            viewAllMess.webView.loadUrl("file:///android_asset/terms_policies.html")
        }

        viewAllMess.setOnClickListener(this)

        applyFonts(viewAllMess)

        dialogAllUsers.setContentView(viewAllMess)

        return dialogAllUsers

    }

    private fun applyFonts(view: View) {
        view?.let {
            view.tvWebViewHeading.typeface = dtdUtils.fontFutura()
        }
    }

    override fun onClick(v: View?) {
            if (v?.id == R.id.ivClose){
                dialog?.dismiss()
            }
    }
}