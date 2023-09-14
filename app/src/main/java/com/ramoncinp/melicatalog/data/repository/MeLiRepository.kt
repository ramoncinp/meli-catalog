package com.ramoncinp.melicatalog.data.repository

import com.ramoncinp.melicatalog.data.models.SearchedItem
import com.ramoncinp.melicatalog.data.models.ServiceResult

interface MeLiRepository {

    suspend fun searchItems(
        siteId: String,
        query: String,
        limit: Int,
        offset: Int
    ): ServiceResult<List<SearchedItem>>
}
