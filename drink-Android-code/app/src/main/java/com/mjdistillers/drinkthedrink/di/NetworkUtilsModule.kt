package com.mjdistillers.drinkthedrink.di

import com.mjdistillers.drinkthedrink.utilities.NetworkUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkUtilsModule {

    @Provides
    @Singleton
    fun provideNetworkUtils(): NetworkUtils{
        return NetworkUtils()
    }
}