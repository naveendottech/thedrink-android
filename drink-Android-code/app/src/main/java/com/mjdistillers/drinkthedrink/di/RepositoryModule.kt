package com.mjdistillers.drinkthedrink.di

import android.app.Application
import com.mjdistillers.drinkthedrink.model.DTDRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideDTDRepository(app: Application) : DTDRepository{
        return DTDRepository(app)
    }
}