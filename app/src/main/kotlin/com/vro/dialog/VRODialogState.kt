package com.vro.dialog

import java.io.Serializable

data class VRODialogState(val type: Int, val value: Any? = null) : Serializable