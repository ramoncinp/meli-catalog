package com.ramoncinp.melicatalog.data.repository

import com.ramoncinp.melicatalog.data.models.ItemDetail
import com.ramoncinp.melicatalog.data.models.SearchedItem

interface MeLiRepository {

    suspend fun searchItems(
        siteId: String,
        query: String,
        limit: Int,
        offset: Int
    ): List<SearchedItem>

    suspend fun getItem(
        id: String
    ): ItemDetail?
}
