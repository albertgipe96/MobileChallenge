package com.example.mobilechallenge.cabifystore.presentation.vm

import com.example.mobilechallenge.cabifystore.domain.model.onError
import com.example.mobilechallenge.cabifystore.domain.model.onException
import com.example.mobilechallenge.cabifystore.domain.model.onSuccess
import com.example.mobilechallenge.cabifystore.domain.usecases.GetProductsUseCase
import com.example.mobilechallenge.common.presentation.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : BaseViewModel() {

    init {
        withUseCaseScope {
            getProductsUseCase().resource
                .onSuccess {

                }
                .onError {

                }
                .onException {

                }
        }
    }

}