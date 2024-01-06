package com.sampleapp.main

import com.vro.navigation.VRODestination

sealed class Destinations : VRODestination {
    data object Home : Destinations()
    data object Profile : Destinations()
}