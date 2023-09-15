package com.ramoncinp.melicatalog.domain.models

enum class OperationError {
    NETWORK_ERROR,
    SERVER_ERROR,
    UNKNOWN_ERROR
}

class OperationException(val error: OperationError) : Exception(
    "Error trying to get data: $error"
)
