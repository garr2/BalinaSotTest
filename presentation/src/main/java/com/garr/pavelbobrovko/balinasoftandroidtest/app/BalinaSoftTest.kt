package com.garr.pavelbobrovko.balinasoftandroidtest.app

import android.app.Application

class BalinaSoftTest: Application() {

    companion object {
        lateinit var instance: BalinaSoftTest
    }

    init {
        instance = this
    }
}