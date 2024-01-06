package com.sampleapp.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sampleapp.base.BaseScreen
import com.sampleapp.main.Destinations
import com.vro.compose.extensions.GeneratePreview
import com.vro.compose.preview.VROMultiDevicePreview

class ProfileScreen :
    BaseScreen<ProfileViewModel, ProfileState, Destinations, ProfileEvent>() {

    @VROMultiDevicePreview
    @Composable
    override fun ComposablePreview() {
        GeneratePreview {
            ProfileContent(ProfileState.INITIAL)
        }
    }

    @Composable
    override fun ComposableContent(state: ProfileState) {
        ProfileContent(state)
    }

    @Composable
    private fun ProfileContent(user: ProfileState) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "Esto es el perfil")
        }
    }
}