package com.example.busapp2.activities

import BusAppAdapter
import BusAppListener
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.busapp2.R
import com.example.busapp2.databinding.ActivityBusappListBinding
import com.example.busapp2.main.MainApp
import com.example.busapp2.models.BusAppModel

class BusAppListActivity : AppCompatActivity(), BusAppListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityBusappListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusappListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        //binding.recyclerView.adapter = BusAppAdapter(app.buses)
        binding.recyclerView.adapter = BusAppAdapter(app.buses.findAll(),this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, BusAppActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.notifyItemRangeChanged(0, app.buses.findAll().size)
            }
        }


    override fun onBusAppClick(buses: BusAppModel) {
        val launcherIntent = Intent(this, BusAppActivity::class.java)
        launcherIntent.putExtra("BusApp_edit",buses)
        getClickResult.launch(launcherIntent)
    }

    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.notifyItemRangeChanged(0, app.buses.findAll().size)
            }
        }
}