package com.mjdistillers.drinkthedrink.di

import android.app.Application
import com.mjdistillers.drinkthedrink.utilities.DtdUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DtdUtilsModule {

    @Provides
    @Singleton
    fun provideDtdUtils(app: Application): DtdUtils{
        return DtdUtils(app)
    }
}