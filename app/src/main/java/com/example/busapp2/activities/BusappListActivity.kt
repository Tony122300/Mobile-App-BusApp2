package com.example.busapp2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.busapp2.R
import com.example.busapp2.main.MainApp

class BusAppListActivity : AppCompatActivity() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_busapp_list)
        app = application as MainApp
    }
}