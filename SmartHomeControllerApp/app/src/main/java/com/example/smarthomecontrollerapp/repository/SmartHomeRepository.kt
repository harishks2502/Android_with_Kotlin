package com.example.smarthomecontrollerapp.repository

import javax.inject.Inject

class SmartHomeRepository @Inject constructor() {
    fun getDeviceStatus(): String {
        return "All smart devices are connected."
    }
}