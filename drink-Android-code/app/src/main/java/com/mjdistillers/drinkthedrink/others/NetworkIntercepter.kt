package com.mjdistillers.drinkthedrink.others

import android.app.Application
import com.mjdistillers.drinkthedrink.App.Companion.app
import com.mjdistillers.drinkthedrink.DtdConstants
import com.mjdistillers.drinkthedrink.utilities.NetworkUtils
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject

class NetworkIntercepter(var networkUtils: NetworkUtils): Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            return if (!networkUtils.isInternetAvailable(app)){
                val contentType: MediaType? = "application/json".toMediaTypeOrNull()
                val body: ResponseBody = JSONObject().toString().toResponseBody(contentType)

                Response.Builder()
                    .code(DtdConstants.RES_CODE_NO_INTERNET)
                    .protocol(Protocol.HTTP_2)
                    .message("No Internet")
                    .request(chain.request())
                    .body(body)
                    .build()
            }else{
                chain.proceed(chain.request())
            }
        }
}