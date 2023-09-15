package com.ramoncinp.melicatalog.data.repository

import com.ramoncinp.melicatalog.data.MeLiService
import com.ramoncinp.melicatalog.data.models.SearchedItem
import com.ramoncinp.melicatalog.domain.models.OperationError
import com.ramoncinp.melicatalog.domain.models.OperationException
import java.io.IOException
import javax.inject.Inject

class MeLiRepositoryImpl @Inject constructor(
    private val meLiService: MeLiService
) : MeLiRepository {

    override suspend fun searchItems(
        siteId: String,
        query: String,
        limit: Int,
        offset: Int
    ): List<SearchedItem> {

        val response = try {
            meLiService.searchItems(siteId, query, limit, offset)
        } catch (e: IOException) {
            throw OperationException(OperationError.NETWORK_ERROR)
        }

        if (!response.isSuccessful) {
            throw OperationException(OperationError.SERVER_ERROR)
        }

        return response.body()?.results ?: listOf()
    }
}
