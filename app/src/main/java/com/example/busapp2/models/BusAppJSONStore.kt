package com.example.busapp2.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.example.busapp2.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "busapp.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<BusAppModel>>() {}.type

fun generateRandomId(): Int {
    return Random().nextInt()
}

class BusAppJSONStore(private val context: Context) : BusAppStore {

    var buses = mutableListOf<BusAppModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<BusAppModel> {
        logAll()
        return buses
    }

    override fun create(busApp: BusAppModel) {
        busApp.busid = generateRandomId()
        buses.add(busApp)
        serialize()
    }

    override fun delete(busApp: BusAppModel) {
        buses.remove(busApp)
        serialize()
    }

    override fun update(busApp: BusAppModel) {
        val busAppList = findAll() as ArrayList<BusAppModel>
        var foundBusApp: BusAppModel? = busAppList.find { p -> p.busid == busApp.busid }
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
        }
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(buses, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        buses = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        buses.forEach { Timber.i("$it") }
    }
}


class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }

}

