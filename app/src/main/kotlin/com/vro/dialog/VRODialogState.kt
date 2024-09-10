package com.vro.dialog

import com.vro.state.VROState

interface VRODialogState : VROState {
    val proportionWidth: Float?
    val proportionHeight: Float?
}