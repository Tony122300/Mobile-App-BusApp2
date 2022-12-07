package com.example.busapp2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.busapp2.databinding.ActivityBusappBinding
import com.example.busapp2.main.MainApp
import com.google.android.material.snackbar.Snackbar
import com.example.busapp2.models.BusAppModel
import timber.log.Timber
import timber.log.Timber.i


class BusAppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBusappBinding
    var busApp = BusAppModel()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusappBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("BusApp activity started...")
        binding.btnAdd.setOnClickListener() {
            busApp.BusOrigin = binding.origin.text.toString()
            busApp.BusOrigin = binding.desination.text.toString()
            if (busApp.BusOrigin.isNotEmpty()) {
                app.buses.add(busApp.copy())
                i("add Button Pressed: ${busApp.BusOrigin}")
                for(i in app.buses.indices){
                    i("BusApp[$i]:${this.app.buses[i]}")
                }
            }
            else {
                Snackbar
                    .make(it,"Please Enter a Bus Station", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}