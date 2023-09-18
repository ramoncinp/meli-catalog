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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private const val PAGE_SIZE = 20

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val searchItemsUseCase: SearchItemsUseCase
) : ViewModel() {

    private var allPages = false
    private var currentOffset = 0
    private var currentQuery = ""

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _searchedItems = MutableLiveData<List<SearchedItem>>()
    val searchedItems: LiveData<List<SearchedItem>> = _searchedItems

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val items: MutableList<SearchedItem> = mutableListOf()

    private val _isFetchingNextPage = MutableLiveData<Boolean>()
    val isFetchingNextPage: LiveData<Boolean> = _isFetchingNextPage

    fun searchItems(query: String) {
        currentQuery = query
        currentOffset = 0
        items.clear()
        allPages = false
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch(dispatcherProvider.io()) {
            searchItemsUseCase(
                query = currentQuery,
                limit = PAGE_SIZE,
                offset = currentOffset
            ).collect { response ->
                when (response) {
                    is OperationResult.Error -> {
                        _isLoading.postValue(false)
                        _isFetchingNextPage.postValue(false)
                        _errorMessage.postValue(response.message)
                    }
                    is OperationResult.Loading -> {
                        if (_isFetchingNextPage.value != true) _isLoading.postValue(true)
                    }
                    is OperationResult.Success -> {
                        _isLoading.postValue(false)
                        _isFetchingNextPage.postValue(false)
                        response.data?.let {
                            if (it.isEmpty() && items.isNotEmpty()) {
                                allPages = true
                            } else {
                                items.addAll(it)
                            }
                            _searchedItems.postValue(items.toList())
                            Timber.d("Current items size is ${items.size}")
                        }
                    }
                }
            }
        }
    }

    fun checkPagination(visibleItemCount: Int, firstVisibleItemPosition: Int) {
        val totalItemCount = items.size
        val currentScrolledItems = visibleItemCount + firstVisibleItemPosition
        if (totalItemCount == currentScrolledItems && _isFetchingNextPage.value == false && !allPages) {
            _isFetchingNextPage.value = true
            currentOffset += PAGE_SIZE
            fetchItems()
        }
    }
}
