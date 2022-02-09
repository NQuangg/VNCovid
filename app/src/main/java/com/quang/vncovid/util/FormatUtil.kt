package com.quang.vncovid.util

import java.text.DecimalFormat

fun formatNumber(number: Int): String {
    return DecimalFormat().format(number)
}