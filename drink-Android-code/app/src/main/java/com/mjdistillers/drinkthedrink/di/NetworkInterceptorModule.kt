package com.mjdistillers.drinkthedrink.di

import com.mjdistillers.drinkthedrink.others.NetworkIntercepter
import com.mjdistillers.drinkthedrink.utilities.NetworkUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkInterceptorModule {

    @Provides
    @Singleton
    fun provideNetworkInterceptor(networkUtils: NetworkUtils):NetworkIntercepter{
        return NetworkIntercepter(networkUtils)

    }
}