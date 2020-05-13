package com.mjdistillers.drinkthedrink.di

import com.mjdistillers.drinkthedrink.utilities.AlertUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AlertUtilsModule {

    @Provides
    @Singleton
    fun provideAlertUtils(): AlertUtils{
         return AlertUtils()
    }

}