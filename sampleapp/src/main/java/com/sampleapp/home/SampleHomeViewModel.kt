package com.sampleapp.home

import com.sampleapp.base.SampleBaseViewModel
import com.sampleapp.home.SampleHomeEvent.ButtonClick
import com.sampleapp.home.SampleHomeEvent.HomeNavigation
import com.sampleapp.home.SampleHomeEvent.ProfileNavigation
import com.sampleapp.main.SampleDestinations
import com.sampleapp.main.SampleDestinations.Home
import com.sampleapp.main.SampleDestinations.Profile
import java.io.Serializable

class SampleHomeViewModel : SampleBaseViewModel<SampleHomeState, SampleDestinations, SampleHomeEvent>() {

    override val initialViewState: SampleHomeState = SampleHomeState.INITIAL

    override fun eventListener(event: SampleHomeEvent) {
        when (event) {
            ButtonClick -> changeText()
            ProfileNavigation -> navigate(Profile)
            HomeNavigation -> navigate(Home)
        }
    }

    override fun setOnResult(result: Serializable) {
        super.setOnResult(result)
        if (result is Boolean) {
            if (result) updateDataState { copy("esto es una prueba") }
        }
    }

    override suspend fun onStart() {
        updateDataState {
            copy(text = FIRST_TEXT)
        }
    }

    private fun changeText() {
        checkDataState().also {
            updateDataState {
                copy(
                    text =
                    when (it.text) {
                        FIRST_TEXT -> SECOND_TEXT
                        SECOND_TEXT -> FIRST_TEXT
                        else -> ""
                    }
                )
            }
        }
    }

    companion object {
        const val FIRST_TEXT = "This is a text"
        const val SECOND_TEXT = "Another text"
    }
}