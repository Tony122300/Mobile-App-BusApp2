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
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusappBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp
        i("BusApp activity started...")

        if (intent.hasExtra("busApp_edit")) {
            edit = true
            busApp = intent.extras?.getParcelable("busApp_edit")!!
            binding.busAppOrigin.setText(busApp.origin)
            binding.busAppDestination.setText(busApp.destination)
            binding.btnAdd.setText(R.string.save_bus)
        }

        binding.btnAdd.setOnClickListener() {
            busApp.origin = binding.busAppOrigin.text.toString()
            busApp.destination = binding.busAppDestination.text.toString()
            if (busApp.origin.isNotEmpty()) {
                Snackbar.make(it,R.string.enter_Bus_Origin,Snackbar.LENGTH_LONG).show()
            }
            else {
                if(edit){
                    app.buses.update(busApp.copy())
                }else{
                    app.buses.create(busApp.copy())
                }
            }
            setResult(RESULT_OK)
            finish()
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