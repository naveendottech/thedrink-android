package com.mjdistillers.drinkthedrink.di

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(var app: Application) {

    @Singleton
    @Provides
    fun provideApplication(): Application{
        return app
    }
}