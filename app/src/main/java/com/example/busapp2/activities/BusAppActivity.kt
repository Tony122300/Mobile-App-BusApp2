package com.example.busapp2.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.busapp2.R
import com.example.busapp2.databinding.ActivityBusappBinding
import com.example.busapp2.helpers.showImagePicker
import com.example.busapp2.main.MainApp
import com.google.android.material.snackbar.Snackbar
import com.example.busapp2.models.BusAppModel
import com.example.busapp2.models.Location
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import timber.log.Timber
import timber.log.Timber.i


class BusAppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBusappBinding
    var busApp = BusAppModel()
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
   // var location = Location(52.245696, -7.139102, 15f)
    lateinit var app: MainApp
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        edit = false
        binding = ActivityBusappBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

//        //Initialize the bottom navigation view
//        //create bottom navigation view object
//        val bottomNavigationView = findViewById<BottomNavigationView
//                >(R.id.bottom_navigatin_view)
//        val navController = findNavController(R.id.nav_fragment)
//        bottomNavigationView.setupWithNavController(navController
//        )

        app = application as MainApp
        i("BusApp started...")

        if (intent.hasExtra("busApp_edit")) {
            edit = true
            busApp = intent.extras?.getParcelable("busApp_edit")!!
            binding.busAppOrigin.setText(busApp.origin)
            binding.busAppDestination.setText(busApp.destination)
            binding.busRoute.setText(busApp.route)
            binding.departureTime.setText(busApp.departureTime)
            binding.arrivalTime.setText(busApp.arrivalTime)
            binding.busRoute.setText(busApp.route)
            binding.btnAdd.setText(R.string.save_bus)
            Picasso.get()
                .load(busApp.image)
                .into(binding.busAppImage)
            if (busApp.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_BusApp_image)
            }
        }

        binding.busStationLocation.setOnClickListener {
            i ("Set Location Pressed")
        }

        binding.btnAdd.setOnClickListener() {
            busApp.origin = binding.busAppOrigin.text.toString()
            busApp.destination = binding.busAppDestination.text.toString()
            busApp.route = binding.busRoute.text.toString()
            busApp.departureTime = binding.departureTime.text.toString()
            busApp.arrivalTime = binding.arrivalTime.text.toString()
            Toast.makeText(applicationContext,"Bus Station Added", Toast.LENGTH_SHORT).show()
            if (busApp.origin.isEmpty() || busApp.destination.isEmpty() || busApp.route.isEmpty() || busApp.departureTime.isEmpty() || busApp.arrivalTime.isEmpty()
                    ) {
                Snackbar.make(it, R.string.enter_Bus_Origin, Snackbar.LENGTH_LONG).show()
            } else {
                if (edit) {
                    app.buses.update(busApp.copy())
                } else {
                    app.buses.create(busApp.copy())
                }
            }
            setResult(RESULT_OK)
            finish()
        }

        binding.chooseImage.setOnClickListener {
            Toast.makeText(applicationContext,"Image added", Toast.LENGTH_SHORT).show()
            showImagePicker(imageIntentLauncher,this)

        }

        binding.busStationLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (busApp.zoom != 0f) {
                location.lat =  busApp.lat
                location.lng = busApp.lng
                location.zoom = busApp.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }
        registerImagePickerCallback()
        registerMapCallback()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_busapp, menu)
        if(edit) menu.getItem(0).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                setResult(99)
                app.buses.delete(busApp)
                finish()
            }
            R.id.item_cancel -> {  finish()  }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")

                            val image = result.data!!.data!!
                            contentResolver.takePersistableUriPermission(image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            busApp.image = image

                            Picasso.get()
                                .load(busApp.image)
                                .into(binding.busAppImage)
                            binding.chooseImage.setText(R.string.change_BusApp_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            busApp.lat = location.lat
                            busApp.lng = location.lng
                            busApp.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

}