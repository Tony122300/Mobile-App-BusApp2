package com.example.busapp2.models

import timber.log.Timber.i
var lastId = 0

internal fun getBusId(): Int{
    return lastId++
}
class BusAppMemStore : BusAppStore {

    val buses = ArrayList<BusAppModel>()

    override fun findAll(): List<BusAppModel> {
        return buses
    }

    override fun create(busApp: BusAppModel) {
        busApp.busid = getBusId()
        buses.add(busApp)
        logAll()
    }

    override fun update(busApp: BusAppModel) {
        var foundBusApp: BusAppModel? = buses.find { p -> p.busid == busApp.busid }
        if (foundBusApp != null) {
            foundBusApp.origin = busApp.origin
            foundBusApp.destination = busApp.destination
            foundBusApp.route = busApp.route
            foundBusApp.departureTime = busApp.departureTime
            foundBusApp.arrivalTime = busApp.arrivalTime
            foundBusApp.image = busApp.image
            foundBusApp.lat = busApp.lat
            foundBusApp.lng = busApp.lng
            foundBusApp.zoom = busApp.zoom
            logAll()
        }
    }

  private fun logAll(){
        buses.forEach{i("${it}")}
    }

    override fun delete(busApp: BusAppModel) {
        buses.remove(busApp)
    }
}