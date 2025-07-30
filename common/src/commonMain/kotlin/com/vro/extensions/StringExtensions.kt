package com.vro.extensions

import com.vro.constants.CHAR_ZERO
import com.vro.constants.COMMA
import com.vro.constants.POINT
import com.vro.constants.SPACE

fun String.capitalize() = replaceFirstChar(Char::titlecase)

fun String.capitalizeWords(): String = split(SPACE).joinToString(SPACE) { it.lowercase().capitalize() }

fun String.fromPointToComma() = replace(POINT, COMMA)

fun String.fromCommaToPoint() = replace(COMMA, POINT)

fun String.addLeadingZeros(numberOrChars: Int) = padStart(numberOrChars, CHAR_ZERO)

fun String.removeLeadingZeros() = trimStart(CHAR_ZERO)