package com.renatsayf.androidcheatsheet

import android.app.Application

class App: Application() {

    companion object {

        lateinit var INSTANCE: App
            private set
    }

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this
    }
}