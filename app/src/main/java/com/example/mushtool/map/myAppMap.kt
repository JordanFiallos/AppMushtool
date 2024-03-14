package com.example.mushtool.map

import android.app.Application
import org.osmdroid.config.Configuration

class myAppMap : Application() {
    override fun onCreate() {
        super.onCreate()

        Configuration.getInstance().userAgentValue = packageName

    }
}