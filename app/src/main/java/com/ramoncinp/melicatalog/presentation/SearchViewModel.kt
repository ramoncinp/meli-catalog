package com.ramoncinp.melicatalog.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramoncinp.melicatalog.data.models.SearchedItem
import com.ramoncinp.melicatalog.di.DispatcherProvider
import com.ramoncinp.melicatalog.domain.models.OperationResult
import com.ramoncinp.melicatalog.domain.usecase.SearchItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val searchItemsUseCase: SearchItemsUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _searchedItems = MutableLiveData<List<SearchedItem>>()
    val searchedItems: LiveData<List<SearchedItem>> = _searchedItems

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun searchItems(query: String) {
        viewModelScope.launch(dispatcherProvider.io()) {
            searchItemsUseCase(query).collect { response ->
                when (response) {
                    is OperationResult.Error -> {
                        _isLoading.postValue(false)
                        _errorMessage.postValue(response.message)
                    }
                    is OperationResult.Loading -> {
                        _isLoading.postValue(true)
                    }
                    is OperationResult.Success -> {
                        _isLoading.postValue(false)
                        response.data?.let { _searchedItems.postValue(it) }
                    }
                }
            }
        }
    }
}
