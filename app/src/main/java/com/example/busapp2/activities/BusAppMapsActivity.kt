package com.example.busapp2.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.busapp2.databinding.ActivityBusAppMapsBinding
import com.example.busapp2.databinding.ContentBusAppMapsBinding
import com.google.android.gms.maps.GoogleMap

class BusAppMapsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBusAppMapsBinding
    private lateinit var contentBinding: ContentBusAppMapsBinding
    lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBusAppMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        contentBinding = ContentBusAppMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)

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
}