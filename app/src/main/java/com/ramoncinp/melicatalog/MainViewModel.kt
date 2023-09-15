package com.ramoncinp.melicatalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramoncinp.melicatalog.domain.models.OperationResult
import com.ramoncinp.melicatalog.domain.usecase.SearchItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchItemsUseCase: SearchItemsUseCase
) : ViewModel() {

    fun searchItems() {
        viewModelScope.launch(Dispatchers.IO) {
            Timber.d("Fetching items...")

            searchItemsUseCase("Taylor").collect { response ->
                when (response) {
                    is OperationResult.Error -> Timber.e("Error -> ${response.message}")
                    is OperationResult.Loading -> Timber.d("is Loading")
                    is OperationResult.Success -> Timber.d("Success -> ${response.data}")
                }
            }
        }
    }
}
