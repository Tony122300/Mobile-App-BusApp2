package com.example.busapp2.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.busapp2.R
import com.example.busapp2.databinding.ActivityBusappBinding
import com.example.busapp2.helpers.showImagePicker
import com.example.busapp2.main.MainApp
import com.google.android.material.snackbar.Snackbar
import com.example.busapp2.models.BusAppModel
import com.squareup.picasso.Picasso
import timber.log.Timber
import timber.log.Timber.i


class BusAppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBusappBinding
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    var busApp = BusAppModel()
    lateinit var app: MainApp
    val IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false
        binding = ActivityBusappBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        i("BusApp started...")
        app = application as MainApp

        if (intent.hasExtra("busApp_edit")) {
            edit = true
            busApp = intent.extras?.getParcelable("busApp_edit")!!
            binding.busAppOrigin.setText(busApp.origin)
            binding.busAppDestination.setText(busApp.destination)
            binding.btnAdd.setText(R.string.save_bus)
            Picasso.get()
                .load(busApp.image)
                .into(binding.busAppImage)
            if (busApp.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_BusApp_image)
            }
        }

        binding.btnAdd.setOnClickListener() {
            busApp.origin = binding.busAppOrigin.text.toString()
            busApp.destination = binding.busAppDestination.text.toString()
            if (busApp.origin.isEmpty()) {
                Snackbar.make(it, R.string.enter_Bus_Origin, Snackbar.LENGTH_LONG).show()
            } else {
                if (edit) {
                    app.buses.update(busApp.copy())
                } else {
                    app.buses.create(busApp.copy())
                }
            }
            i("add Button Pressed: $busApp")
            setResult(RESULT_OK)
            finish()
        }
        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }
        registerImagePickerCallback()
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
    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            busApp.image = result.data!!.data!!
                            Picasso.get()
                                .load(busApp.image)
                                .into(binding.busAppImage)
                            binding.chooseImage.setText(R.string.change_BusApp_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }
}