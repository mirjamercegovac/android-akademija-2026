package com.example.androidacademyapi.data.repository

import com.example.androidacademyapi.data.network.apiservice.KtorProductApiService
import com.example.androidacademyapi.data.model.Product
import com.example.androidacademyapi.data.model.ProductRequest

class KtorProductRepository(private val ktorProductApiService: KtorProductApiService): ProductRepository {
    override suspend fun getProducts(): Result<List<Product>> {
        return runCatching {
            ktorProductApiService.getProducts().products
        }
    }

    //3.zadatak
    override suspend fun getProduct(id: Int): Result<Product> {
        if (id < 0) {
            return Result.failure(
                IllegalArgumentException("Product id cannot be negative")
            )
        }

        return runCatching {
            ktorProductApiService.getProduct(id)
        }
    }

    override suspend fun addProduct(request: ProductRequest): Result<Product> {
        return runCatching {
            ktorProductApiService.addProduct(request)
        }
    }

    override suspend fun updateProduct(
        id: Int,
        request: ProductRequest
    ): Result<Product> {
        return runCatching {
            ktorProductApiService.updateProduct(id,request)
        }
    }

    override suspend fun deleteProduct(id: Int): Result<Product> {
        return runCatching {
            ktorProductApiService.deleteProduct(id)
        }
    }
}