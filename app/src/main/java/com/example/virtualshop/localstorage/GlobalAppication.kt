package com.example.virtualshop.localstorage

import android.app.Application
import android.content.Context

class GlobalAppication: Application() {
    override fun onCreate() {
        super.onCreate()

        prefs = Prefs(applicationContext)
    }

    companion object{
        lateinit var prefs: Prefs
    }
}