package com.mjdistillers.drinkthedrink.di

import com.mjdistillers.drinkthedrink.utilities.ApplicationUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationUtilsModule {

    @Provides
    fun provideApplicationUtilsModule(): ApplicationUtils{
        return ApplicationUtils()
    }

}