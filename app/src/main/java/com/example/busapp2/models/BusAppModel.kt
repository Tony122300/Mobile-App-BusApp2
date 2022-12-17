package com.example.busapp2.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class BusAppModel(var busid: Int = 0,var origin: String = "", var destination: String = "",    var image: Uri = Uri.EMPTY, var lat : Double = 0.0, var lng: Double = 0.0, var zoom: Float = 0f) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable