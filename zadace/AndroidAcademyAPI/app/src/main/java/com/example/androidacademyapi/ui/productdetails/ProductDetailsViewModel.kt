package com.example.androidacademyapi.ui.productdetails

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.androidacademyapi.data.model.Product
import com.example.androidacademyapi.data.repository.ProductRepository
import kotlinx.coroutines.launch

//2.zadatak
sealed interface ProductDetailsUIState {
    data object Loading : ProductDetailsUIState
    data class Success(val product: Product) : ProductDetailsUIState
    data class Error(val message: String) : ProductDetailsUIState
}

class ProductDetailsViewModel(
    private val repository: ProductRepository,
    private val productId: Int
) : ViewModel() {
 //2.zadatak
    private val _uiState: MutableState<ProductDetailsUIState> = mutableStateOf(
     ProductDetailsUIState.Loading
    )
    val uiState: State<ProductDetailsUIState> = _uiState

    init {
        getProduct()
    }

    private fun getProduct(){
        viewModelScope.launch {
            repository.getProduct(productId)
                .onSuccess {
                    _uiState.value = ProductDetailsUIState.Success(it)
                }
                .onFailure {
                    _uiState.value = ProductDetailsUIState.Error(
                        it.message ?: "Product loading failed"
                    )
                }
        }
    }
}

class ProductDetailsViewModelFactory(
    private val repository: ProductRepository,
    private val productId: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductDetailsViewModel(repository,productId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}