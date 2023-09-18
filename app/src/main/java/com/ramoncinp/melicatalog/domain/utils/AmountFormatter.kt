package com.ramoncinp.melicatalog.domain.utils

import java.text.NumberFormat

fun Double.formatAsCurrency(currencyId: String? = null): String {
    val suffix = currencyId?.let { " $currencyId" } ?: ""
    return NumberFormat.getCurrencyInstance().format(this) + suffix
}
