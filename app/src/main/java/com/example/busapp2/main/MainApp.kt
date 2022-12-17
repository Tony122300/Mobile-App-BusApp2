package com.example.busapp2.main

import android.app.Application
import com.example.busapp2.models.BusAppJSONStore
import com.example.busapp2.models.BusAppMemStore
import com.example.busapp2.models.BusAppModel
import com.example.busapp2.models.BusAppStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {
    lateinit var buses: BusAppStore
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        buses = BusAppJSONStore(applicationContext)
        i("busApp started")
    }
}
