package com.ramoncinp.melicatalog.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramoncinp.melicatalog.data.models.ItemDetail
import com.ramoncinp.melicatalog.di.DefaultDispatcherProvider
import com.ramoncinp.melicatalog.domain.models.OperationResult
import com.ramoncinp.melicatalog.domain.usecase.GetItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val dispatcherProvider: DefaultDispatcherProvider,
    private val getItemUseCase: GetItemUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _itemData = MutableLiveData<ItemDetail>()
    val itemData: LiveData<ItemDetail> = _itemData

    fun getItem(itemId: String) {
        viewModelScope.launch(dispatcherProvider.io()) {
            delay(500)
            getItemUseCase(itemId).collect { response ->
                when (response) {
                    is OperationResult.Error -> {
                        _isLoading.postValue(false)
                        _errorMessage.postValue(response.message)
                    }

                    is OperationResult.Loading -> {
                        _isLoading.postValue(true)
                    }

                    is OperationResult.Success -> onItemData(response.data)
                }
            }
        }
    }

    private fun onItemData(itemData: ItemDetail?) {
        itemData?.let {
            _itemData.postValue(it)
        } ?: {
            _errorMessage.postValue("Error al obtener datos de producto")
        }
        _isLoading.postValue(false)
    }
}
