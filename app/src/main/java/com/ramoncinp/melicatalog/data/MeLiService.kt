package com.ramoncinp.melicatalog.data

import com.ramoncinp.melicatalog.data.models.SearchItemsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MeLiService {

    @GET("sites/{siteId}/search")
    suspend fun searchItems(
        @Path("siteId") siteId: String,
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<SearchItemsResponse>

    @GET("items")
    suspend fun getItem(
        @Query("ids") ids: String
    )
}
