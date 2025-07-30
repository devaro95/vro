package com.vro.navigation

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class VROBackResult(
    val id: String,
    @Contextual
    val data: @Serializable Any? = null,
)