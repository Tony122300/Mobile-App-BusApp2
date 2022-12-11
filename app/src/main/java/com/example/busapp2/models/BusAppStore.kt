package com.example.busapp2.models

interface BusAppStore {
    fun findAll(): List<BusAppModel>
    fun create(buses: BusAppModel)
    fun update(buses: BusAppModel)
}