package com.sampleapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.sampleapp.styles.Skeleton
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.compose.skeleton.*
import com.vro.compose.utils.vroVerticalScroll

class SampleHomeSkeleton : VROSkeleton() {
    @Composable
    override fun SkeletonContent() {
        Column(
            modifier = Modifier
                .vroVerticalScroll()
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            VROTextSkeleton(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(16.dp)),
                width = 500.dp,
                skeletonBrush = vroSkeletonBrush(
                    color = Skeleton
                ),
                height = 400.dp
            )
            Text(text = "Top Button Section")
        }
    }

    @VROLightMultiDevicePreview
    @Composable
    override fun SkeletonPreview() {
        SkeletonContent()
    }
}