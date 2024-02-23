package com.vro.navigation

import java.io.Serializable

data class VROBackResult(
    val id: Int,
    val data: Serializable,
) : Serializable