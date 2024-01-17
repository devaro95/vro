package com.sampleapp.profile

import com.vro.navparam.VRONavParam

sealed class SampleProfileNavParam : VRONavParam {
    data class Initialize(val userId: String) : SampleProfileNavParam()
}