package com.ramoncinp.melicatalog.domain.models

sealed class OperationResult<T> {
    data class Success<T>(val data: T): OperationResult<T>()
    data class Error(val message: String): OperationResult<Nothing>()
    object Loading: OperationResult<Nothing>()
}
