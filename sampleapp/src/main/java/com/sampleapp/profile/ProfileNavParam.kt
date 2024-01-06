package com.sampleapp.profile

import com.vro.navparam.VRONavParam

sealed class ProfileNavParam : VRONavParam {
    data class Initialize(val userId: String) : ProfileNavParam()
}