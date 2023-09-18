package com.ramoncinp.melicatalog.domain.usecase

import com.ramoncinp.melicatalog.data.models.ItemDetail
import com.ramoncinp.melicatalog.data.repository.MeLiRepository
import com.ramoncinp.melicatalog.domain.models.OperationException
import com.ramoncinp.melicatalog.domain.models.OperationResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetItemUseCase @Inject constructor(
    private val meLiRepository: MeLiRepository
) {

    operator fun invoke(id: String): Flow<OperationResult<ItemDetail?>> = flow {
        emit(OperationResult.Loading())
        try {
            val result = meLiRepository.getItem(id)
            emit(OperationResult.Success(result))
        } catch (e: OperationException) {
            emit(OperationResult.Error("Error fetching item data: ${e.error}"))
        }
    }
}
