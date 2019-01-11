package com.le.aestroider.app

import android.app.Application

class AestroiderApp : Application() {

    companion object {
        lateinit var aestroiderApp: AestroiderApp
    }

    override fun onCreate() {
        super.onCreate()
        aestroiderApp = this
    }
}