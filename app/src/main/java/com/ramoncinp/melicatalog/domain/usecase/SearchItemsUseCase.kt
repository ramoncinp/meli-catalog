package com.ramoncinp.melicatalog.domain.usecase

import com.ramoncinp.melicatalog.data.models.ServiceResult
import com.ramoncinp.melicatalog.data.repository.MeLiRepository
import com.ramoncinp.melicatalog.domain.models.OperationResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchItemsUseCase @Inject constructor(
    private val meLiRepository: MeLiRepository
) {

    operator fun <T> invoke(
        query: String,
        siteId: String = "MLM",
        limit: Int = 20,
        offset: Int = 0
    ): Flow<OperationResult<out T>> = flow {
        emit(OperationResult.Loading)
        try {
            val response = meLiRepository.searchItems(
                siteId, query, limit, offset
            )

            when(response) {
                is ServiceResult.Error -> {
                    OperationResult.Error(response.message)
                }
                is ServiceResult.Success -> {
                    OperationResult.Success(response.data)
                }
            }
        } catch (e: Exception) {
            emit(OperationResult.Error("Error fetching query results"))
        }
    }
}
