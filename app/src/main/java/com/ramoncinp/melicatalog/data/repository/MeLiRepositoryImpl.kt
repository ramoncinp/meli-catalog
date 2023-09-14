package com.ramoncinp.melicatalog.data.repository

import com.ramoncinp.melicatalog.data.MeLiService
import com.ramoncinp.melicatalog.data.models.SearchedItem
import com.ramoncinp.melicatalog.data.models.ServiceResult
import retrofit2.HttpException
import timber.log.Timber
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
    ): ServiceResult<List<SearchedItem>> {
        return try {
            val response = meLiService.searchItems(
                siteId, query, limit, offset
            )
            if (response.isSuccessful) {
                val result = response.body()?.results ?: listOf()
                ServiceResult.Success(result)
            } else {
                ServiceResult.Error("Error fetching data")
            }
        } catch (e: IOException) {
            Timber.e(e.message)
            ServiceResult.Error(e.toString())
        } catch (e: HttpException) {
            Timber.e(e.message)
            ServiceResult.Error(e.message())
        }
    }
}
