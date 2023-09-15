package com.ramoncinp.melicatalog.domain.models

sealed class OperationResult<T>(val data: T?, val message: String?) {
    class Success<T>(data: T) : OperationResult<T>(data, null)
    class Error<T>(message: String) : OperationResult<T>(null, message)
    class Loading<T> : OperationResult<T>(null, null)
}
