package com.example.busapp2.main

import android.app.Application
import com.example.busapp2.models.BusAppModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {
    val buses = ArrayList<BusAppModel>()
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("busApp started")
        buses.add(BusAppModel("Waterford","Wexford"))
    }
}
