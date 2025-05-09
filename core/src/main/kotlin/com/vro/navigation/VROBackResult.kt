package com.vro.navigation

import java.io.Serializable

data class VROBackResult(
    val id: String,
    val data: Serializable? = null,
) : Serializable