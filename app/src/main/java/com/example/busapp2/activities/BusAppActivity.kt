package com.example.busapp2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.busapp2.databinding.ActivityBusappBinding
import com.google.android.material.snackbar.Snackbar
import com.example.busapp2.models.BusAppModel
import timber.log.Timber
import timber.log.Timber.i


class BusAppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBusappBinding
    var busApp = BusAppModel()
    val buses = ArrayList<BusAppModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusappBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.plant(Timber.DebugTree())
        i("Busapp Activity started...")

        binding.btnAdd.setOnClickListener() {
            busApp.BusOrigin = binding.busAppTitle.text.toString()
            if (busApp.BusOrigin.isNotEmpty()) {
                buses.add(busApp)
                i("add Button Pressed: ${busApp.BusOrigin}")
                for(i in buses.indices){
                    i("BusApp[$i]:${this.buses[i]}")
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