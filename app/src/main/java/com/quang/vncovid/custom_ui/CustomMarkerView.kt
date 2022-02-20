package com.quang.vncovid.custom_ui

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.quang.vncovid.R
import com.quang.vncovid.util.formatNumber

class CustomMarkerView(context: Context?, layoutResource: Int) :
    MarkerView(context, layoutResource) {
    private val tvNumber: TextView by lazy {
        findViewById(R.id.tv_number);
    }

    override fun refreshContent(e: Entry, highlight: Highlight) {
        tvNumber.text = formatNumber(e.y.toInt())
        super.refreshContent(e, highlight);
    }

    override fun getOffset(): MPPointF {
        return MPPointF(-(width / 2f), -(height + 8).toFloat())
    }
}