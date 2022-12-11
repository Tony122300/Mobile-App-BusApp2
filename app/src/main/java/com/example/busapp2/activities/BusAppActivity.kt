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
            busApp.origin = binding.busAppOrigin.text.toString()
            busApp.destination = binding.busAppDestination.text.toString()
            if (busApp.origin.isNotEmpty()) {
                app.buses.add(busApp.copy())
                i("add Button Pressed: ${busApp}")
                for(i in app.buses.indices){
                    i("BusApp[$i]:${this.app.buses[i]}")
                }
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar
                    .make(it,"Please Enter a Bus Station", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}