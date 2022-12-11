package com.example.busapp2.models

import timber.log.Timber.i

class BusAppMemStore : BusAppStore {

    val buses = ArrayList<BusAppModel>()

    override fun findAll(): List<BusAppModel> {
        return buses
    }

    override fun create(busApp: BusAppModel) {
        buses.add(busApp)
        logAll()
    }

    fun logAll(){
        buses.forEach{i("${it}")}
    }
}