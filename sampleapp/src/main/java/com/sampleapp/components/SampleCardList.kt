package com.sampleapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SampleCardList(itemList: List<CardListItem>) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp),
        maxItemsInEachRow = 2
    ) {
        itemList.forEach {
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
                    .padding(8.dp)
            ) {
                SampleCard(
                    text = it.text,
                    onClick = it.onClick
                )
            }
        }
    }
}

data class CardListItem(
    val text: String,
    val onClick: () -> Unit
)

@Preview
@Composable
private fun SampleCardListPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SampleCardList(
            itemList = listOf(
                CardListItem(
                    text = "SampleCard",
                    onClick = {}
                ),
                CardListItem(
                    text = "SampleCard",
                    onClick = {}
                ),
                CardListItem(
                    text = "Dialog State Management",
                    onClick = {}
                ),
                CardListItem(
                    text = "SampleCard",
                    onClick = {}
                )
            )
        )
    }
}
