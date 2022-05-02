package com.quang.vncovid.util

import android.content.Context
import android.util.TypedValue

fun dpToPx(context: Context, dimenId: Int): Int {
    val resources= context.resources
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dimenId.toFloat(),
        resources.displayMetrics
    ).toInt()
}