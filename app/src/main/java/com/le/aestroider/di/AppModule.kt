package com.le.aestroider.di

import com.le.aestroider.app.AestroiderApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: AestroiderApp) {

    @Singleton
    @Provides
    fun providesApplication(): AestroiderApp = application

}