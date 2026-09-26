package com.example.discover237.ui.map

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.discover237.data.model.Region
import androidx.core.graphics.toColorInt

class RegionMapView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    var regions: List<Region> = emptyList()
        set(value) {
            field = value
            recalculateBoxes()
            invalidate()
        }

    var onRegionTapped: (Region) -> Unit = {}

    private val boxWidth = 70f
    private val boxHeight = 60f

    private val boxPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = "#005A44".toColorInt()
        style = Paint.Style.FILL
    }

    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = "#F2C113".toColorInt()
        style = Paint.Style.STROKE
        strokeWidth = 4f
    }

    private val labelPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = "#F9F9F6".toColorInt()
        textSize = 36f
        textAlign = Paint.Align.CENTER
        isFakeBoldText = true
    }

    private var regionBoxes: List<Pair<Region, RectF>> = emptyList()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        recalculateBoxes()
    }

    private fun recalculateBoxes() {
        if (width == 0 || height == 0) return

        regionBoxes = regions.map { region ->
            val centerX = (region.mapX / 100f) * width
            val centerY = (region.mapY / 100f) * height
            val rect = RectF(
                centerX - boxWidth / 2,
                centerY - boxHeight / 2,
                centerX + boxWidth / 2,
                centerY + boxHeight / 2
            )
            region to rect
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        regionBoxes.forEachIndexed { index, (_, rect) ->
            canvas.drawRoundRect(rect, 12f, 12f, boxPaint)
            canvas.drawRoundRect(rect, 12f, 12f, borderPaint)

            val letter = ('A' + index).toString()
            val textY = rect.centerY() - (labelPaint.descent() + labelPaint.ascent()) / 2
            canvas.drawText(letter, rect.centerX(), textY, labelPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            val tappedEntry = regionBoxes.firstOrNull { (_, rect) -> rect.contains(event.x, event.y) }
            tappedEntry?.let { (region, _) -> onRegionTapped(region) }
        }
        return true
    }
}