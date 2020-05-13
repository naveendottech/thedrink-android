package com.mjdistillers.drinkthedrink.di

import android.app.Application
import android.view.LayoutInflater
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LayoutInflaterModule {

    @Singleton
    @Provides
    fun provideLayoutInflater(app:Application): LayoutInflater
    {
        return LayoutInflater.from(app)
    }

}