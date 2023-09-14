package com.ramoncinp.melicatalog.data.models

import com.squareup.moshi.Json

data class SearchItemsResponse(
    @Json(name = "site_id") val siteId: String,
    val query: String,
    val paging: Paging,
    val results: List<SearchedItem>
)

data class Paging(
    val total: Int,
    @Json(name = "primary_results") val primaryResults: Int,
    val offset: Int,
    val limit: Int
)
