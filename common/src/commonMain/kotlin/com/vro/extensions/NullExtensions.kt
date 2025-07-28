package com.vro.extensions

import com.vro.constants.DOUBLE_ZERO
import com.vro.constants.FLOAT_ZERO
import com.vro.constants.INT_ZERO
import com.vro.constants.LONG_ZERO

fun Boolean?.checkNull() = this ?: false

fun Double?.checkNull() = this ?: DOUBLE_ZERO

fun Long?.checkNull() = this ?: LONG_ZERO

fun Int?.checkNull() = this ?: INT_ZERO

fun Float?.checkNull() = this ?: FLOAT_ZERO