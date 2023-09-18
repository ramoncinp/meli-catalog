package com.ramoncinp.melicatalog.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramoncinp.melicatalog.di.DispatcherProvider
import com.ramoncinp.melicatalog.domain.models.OperationResult
import com.ramoncinp.melicatalog.domain.usecase.SearchItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val searchItemsUseCase: SearchItemsUseCase
) : ViewModel() {

    fun searchItems(query: String) {
        viewModelScope.launch(dispatcherProvider.io()) {
            searchItemsUseCase(query).collect { response ->
                when (response) {
                    is OperationResult.Error -> Timber.e("Error -> ${response.message}")
                    is OperationResult.Loading -> Timber.d("is Loading")
                    is OperationResult.Success -> Timber.d("Success -> ${response.data}")
                }
            }
        }
    }
}
