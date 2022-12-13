package com.example.busapp2.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class BusAppModel(var busid: Int = 0,var origin: String = "", var destination: String = "",    var image: Uri = Uri.EMPTY) : Parcelable
