package com.ramoncinp.melicatalog.domain

import com.ramoncinp.melicatalog.data.repository.MeLiRepository
import javax.inject.Inject

class SearchItemsUseCase @Inject constructor(
    private val meLiRepository: MeLiRepository
) {

}
