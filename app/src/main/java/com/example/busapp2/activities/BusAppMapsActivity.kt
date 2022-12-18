package com.example.busapp2.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.busapp2.databinding.ActivityBusAppMapsBinding
import com.example.busapp2.databinding.ContentBusAppMapsBinding
import com.example.busapp2.main.MainApp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class BusAppMapsActivity : AppCompatActivity(),GoogleMap.OnMarkerClickListener  {

    private lateinit var binding: ActivityBusAppMapsBinding
    private lateinit var contentBinding: ContentBusAppMapsBinding
    lateinit var map: GoogleMap
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusAppMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        contentBinding = ContentBusAppMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)
        app = application as MainApp
        contentBinding.mapView.getMapAsync {
            map = it
            configureMap()
        }
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

    override fun onMarkerClick(marker: Marker): Boolean {
        contentBinding.currentOrigin.text = marker.title
        contentBinding.currentDescription.text = marker.title
        contentBinding.route.text = marker.title
        contentBinding.departureTime.text = marker.title
        contentBinding.arrivalTime.text = marker.title
        return false
    }

}