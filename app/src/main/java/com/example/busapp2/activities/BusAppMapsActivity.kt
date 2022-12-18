package com.example.busapp2.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.busapp2.databinding.ActivityBusAppMapsBinding
import com.example.busapp2.databinding.ContentBusAppMapsBinding
import com.example.busapp2.main.MainApp
import com.example.busapp2.models.BusAppModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso

class BusAppMapsActivity : AppCompatActivity(),GoogleMap.OnMarkerClickListener  {

    private lateinit var binding: ActivityBusAppMapsBinding
    private lateinit var contentBinding: ContentBusAppMapsBinding
    lateinit var map: GoogleMap
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MainApp
        binding = ActivityBusAppMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        contentBinding = ContentBusAppMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)
        contentBinding.mapView.getMapAsync {
            map = it
            configureMap()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }

    private fun configureMap() {
        map.uiSettings.isZoomControlsEnabled = true
        app.buses.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.origin).position(loc)
            map.addMarker(options)?.tag = it.busid
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
            map.setOnMarkerClickListener(this)
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val foundBusApp: BusAppModel? = app.buses.findAll().find { p -> p.busid == marker.tag }
        contentBinding.currentOrigin.text = foundBusApp?.origin
        contentBinding.currentDescription.text = foundBusApp?.destination
        contentBinding.route.text = foundBusApp?.route
        contentBinding.departureTime.text = foundBusApp?.departureTime
        contentBinding.arrivalTime.text = foundBusApp?.arrivalTime
        Picasso.get().load(foundBusApp?.image).into(contentBinding.imageView2)
        return false
    }

}