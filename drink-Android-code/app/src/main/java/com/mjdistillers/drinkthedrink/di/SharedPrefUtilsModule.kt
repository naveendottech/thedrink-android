package com.mjdistillers.drinkthedrink.di

import android.app.Application
import com.mjdistillers.drinkthedrink.utilities.SharedPrefsUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPrefUtilsModule {

    @Singleton
    @Provides
    fun provideSharedPrefUtils(app: Application): SharedPrefsUtils{
        return SharedPrefsUtils(app)
    }

}