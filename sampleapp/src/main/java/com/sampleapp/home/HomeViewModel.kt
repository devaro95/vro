package com.sampleapp.home

import com.sampleapp.base.BaseViewModel
import com.sampleapp.home.HomeEvent.ButtonClick
import com.sampleapp.main.Destinations

class HomeViewModel : BaseViewModel<HomeState, Destinations, HomeEvent>() {

    override val initialViewState: HomeState = HomeState.INITIAL

    override fun eventListener(event: HomeEvent) {
        when (event) {
            ButtonClick -> changeText()
        }
    }

    override fun onStart() {
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