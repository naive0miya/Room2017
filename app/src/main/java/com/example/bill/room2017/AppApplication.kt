package com.example.bill.room2017

import android.app.Application
import com.facebook.stetho.Stetho

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}
