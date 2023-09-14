package com.ramoncinp.melicatalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramoncinp.melicatalog.data.models.ServiceResult
import com.ramoncinp.melicatalog.data.repository.MeLiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val meLiRepository: MeLiRepository
) : ViewModel() {

    fun searchItems() {
        viewModelScope.launch(Dispatchers.IO) {
            Timber.d("Fetching items...")

            val response = meLiRepository.searchItems(
                siteId = "MLM",
                query = "Café",
                limit = 20,
                offset = 0
            )

            when (response) {
                is ServiceResult.Error -> Timber.e("Error searching data ${response.message}")
                is ServiceResult.Success -> Timber.d("Fetched data -> ${response.data}")
            }
        }
    }
}
