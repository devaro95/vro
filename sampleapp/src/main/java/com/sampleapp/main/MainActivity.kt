package com.sampleapp.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.sampleapp.home.HomeScreen
import com.sampleapp.main.Destinations.*
import com.sampleapp.profile.ProfileScreen
import com.vro.compose.VROSimpleComposableActivity
import com.vro.compose.extensions.destinationRoute
import com.vro.compose.extensions.vroComposableScreen
import org.koin.androidx.compose.koinViewModel

class MainActivity : VROSimpleComposableActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun CreateContent() {
        val navController = rememberNavController()
        var title by remember { mutableStateOf("") }
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(
                            title,
                            fontSize = 14.sp
                        )
                    },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            },
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Home.destinationRoute(),
                modifier = Modifier.padding(innerPadding)
            ) {
                vroComposableScreen(
                    viewModelSeed = { koinViewModel() },
                    navController = navController,
                    content = {
                        LaunchedEffect(Unit) { title = "Home" }
                        HomeScreen()
                    },
                    destination = Home
                )
                vroComposableScreen(
                    viewModelSeed = { koinViewModel() },
                    navController = navController,
                    content = {
                        LaunchedEffect(Unit) { title = "Profile" }
                        ProfileScreen()
                    },
                    destination = Profile
                )
            }
        }
    }
}