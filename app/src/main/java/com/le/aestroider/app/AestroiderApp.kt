package com.le.aestroider.app

import android.app.Application
import com.le.aestroider.di.AppModule
import com.le.aestroider.di.DaggerDataComponent
import com.le.aestroider.di.DataComponent
import com.le.aestroider.di.DataModule

class AestroiderApp : Application() {

    companion object {
        lateinit var aestroiderApp: AestroiderApp
        lateinit var dataComponent : DataComponent
    }

    override fun onCreate() {
        super.onCreate()
        aestroiderApp = this
        dataComponent = DaggerDataComponent.builder().appModule(AppModule(this)).dataModule(DataModule()).build()
    }
}