package com.example.busapp2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.busapp2.R
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
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp
        i("BusApp activity started...")


        binding.btnAdd.setOnClickListener() {
            busApp.origin = binding.busAppOrigin.text.toString()
            busApp.destination = binding.busAppDestination.text.toString()
            if (busApp.origin.isNotEmpty()) {
                app.buses.create(busApp.copy())
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
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_busapp, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}