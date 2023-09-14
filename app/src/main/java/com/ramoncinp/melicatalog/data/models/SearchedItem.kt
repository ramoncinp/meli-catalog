package com.ramoncinp.melicatalog.data.models

import com.squareup.moshi.Json

data class SearchedItem(
    val id: String,
    val title: String,
    @Json(name = "category_id") val categoryId: String,
    val thumbnail: String,
    @Json(name = "currency_id") val currencyId: String,
    val price: Double
)
