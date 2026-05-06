package com.example.androidacademyapi.ui.productdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.androidacademyapi.AppContainer
import com.example.androidacademyapi.ui.productlistscreen.ProductListViewModel
import com.example.androidacademyapi.ui.productlistscreen.ProductListViewModelFactory

@Composable
fun ProductDetailsScreen(navController: NavController,productId: Int){
    val viewModel: ProductDetailsViewModel =
        viewModel(factory = ProductDetailsViewModelFactory(AppContainer.productRepository,productId))
    ProductDetailsContent (
        productDetailsUIState = viewModel.uiState.value,
        onNavigateBack = {
            navController.popBackStack()
        }

    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsContent(
    productDetailsUIState: ProductDetailsUIState,
    onNavigateBack:()-> Unit
){
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text("Product title", style = MaterialTheme.typography.titleMedium)
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)

            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            when (productDetailsUIState) {
                ProductDetailsUIState.Loading -> {
                    Text("Loading...")
                }

                is ProductDetailsUIState.Error -> {
                    Text(productDetailsUIState.message)
                }
                //2.zadatak
                is ProductDetailsUIState.Success -> {
                    val product = productDetailsUIState.product

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        AsyncImage(
                            model = product.thumbnail,
                            contentDescription = product.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = product.title,
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        product.description?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}