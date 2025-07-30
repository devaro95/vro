package com.vro.state

import kotlinx.serialization.Serializable

@Serializable
data class VRODialogData(val type: Int, val value: Any? = null)