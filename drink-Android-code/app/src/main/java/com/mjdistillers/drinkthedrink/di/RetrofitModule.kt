package com.mjdistillers.drinkthedrink.di

import com.google.gson.GsonBuilder
import com.mjdistillers.drinkthedrink.APIs
import com.mjdistillers.drinkthedrink.others.NetworkIntercepter
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class RetrofitModule {


    @Singleton
    @Provides
    fun provideRetrofit(networkUtils: NetworkIntercepter): Retrofit{
        var loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        var okhttp = OkHttpClient.Builder()
        okhttp.connectTimeout(60, TimeUnit.SECONDS)
        okhttp.readTimeout(60, TimeUnit.SECONDS)
        okhttp.writeTimeout(60, TimeUnit.SECONDS)
        okhttp.addInterceptor(loggingInterceptor)
        okhttp.addInterceptor(networkUtils)

//        var gsonBuilder = GsonBuilder()
//            .serializeNulls()
//            .create()

        
        return Retrofit.Builder()
            .baseUrl(APIs.BASE_URL)
            .client(okhttp.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }




}