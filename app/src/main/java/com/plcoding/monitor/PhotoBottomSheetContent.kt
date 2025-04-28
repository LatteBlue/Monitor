package com.plcoding.monitor

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp

@Composable
fun PhotoBottomSheetContent(
    bitmaps: List<Bitmap>,
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Project") }
    )

    var captionText by remember { mutableStateOf("") }
    TextField(
        value = captionText,
        onValueChange = { captionText = it },
        label = { Text("Caption") }
    )

    if(bitmaps.isEmpty()) {
        Box(
            modifier = modifier
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("There are no photos yet")
        }
    } else {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp,
            contentPadding = PaddingValues(16.dp),
            modifier = modifier
        ) {


            items(bitmaps) { bitmap ->

                val bitmapWithText = addTextToBitmap(
                    originalBitmap = bitmap,
                    text = captionText
                )

                Image(
                    bitmap = bitmapWithText.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                )
            }

        }
    }
}
fun addTextToBitmap(
    originalBitmap: Bitmap,
    text: String,
    textSize: Float = 40f,
    textColor: Int = Color.WHITE,
    x: Float = 10f,
    y: Float = 50f
): Bitmap {
    // Create a mutable copy of the original bitmap
    val mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true)

    // Create a canvas to draw on the mutable bitmap
    val canvas = Canvas(mutableBitmap)

    // Setup paint for drawing text
    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = textColor
        this.textSize = textSize
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        style = Paint.Style.FILL
        // Optional: add shadow for better visibility
        setShadowLayer(5f, 2f, 2f, Color.BLACK)
    }

    // Draw the text on the canvas
    canvas.drawText(text, x, y, paint)

    return mutableBitmap
}