package com.ramoncinp.melicatalog.data.models

import com.squareup.moshi.Json

data class ItemDetail(
    val id: String,
    @Json(name = "site_id") val siteId: String,
    val title: String,
    val subtitle: String?,
    @Json(name = "seller_id") val sellerId: String,
    @Json(name = "category_id") val categoryId: String,
    @Json(name = "currency_id") val currencyId: String,
    val price: Double,
    @Json(name = "original_price") val originalPrice: Double?,
    @Json(name = "available_quantity") val availableQuantity: Int,
    val pictures: List<ItemPicture>,
    @Json(name = "accepts_mercadopago") val acceptsMercadoPago: Boolean,
    @Json(name = "date_created") val dateCreated: String
)

data class ItemPicture(
    val id: String,
    val url: String,
    @Json(name = "secure_url") val secureURL: String,
    val size: String,
    @Json(name = "max_size") val maxSize: String,
    val quality: String
)
