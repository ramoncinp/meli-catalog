package com.ramoncinp.melicatalog.domain.usecase

import com.ramoncinp.melicatalog.data.models.SearchedItem
import com.ramoncinp.melicatalog.data.repository.MeLiRepository
import com.ramoncinp.melicatalog.domain.models.OperationException
import com.ramoncinp.melicatalog.domain.models.OperationResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val SPACE_SEPARATOR = "%20"

class SearchItemsUseCase @Inject constructor(
    private val meLiRepository: MeLiRepository
) {

    operator fun invoke(
        query: String,
        siteId: String = "MLM",
        limit: Int = 20,
        offset: Int = 0
    ): Flow<OperationResult<List<SearchedItem>>> = flow {
        emit(OperationResult.Loading())
        try {
            val q = query.replaceSpaces()
            val results = meLiRepository.searchItems(
                siteId, q, limit, offset
            )
            emit(OperationResult.Success(results))
        } catch (e: OperationException) {
            emit(OperationResult.Error("Error fetching query results: ${e.error}"))
        }
    }

    private fun String.replaceSpaces(): String = replace(" ", SPACE_SEPARATOR)
}
