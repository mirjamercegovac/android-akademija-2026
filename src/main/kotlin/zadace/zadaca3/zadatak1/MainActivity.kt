package com.example.androidacademy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidacademy.ui.theme.AndroidAcademyTheme

// ZADATAK 2.
data class MyData(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String = ""
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidAcademyTheme {
                // ZADATAK 5.
                AppNavigation()
            }
        }
    }
}

// ZADATAK 5.
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // ZADATAK 4.
    var itemsList by remember {
        mutableStateOf(
            listOf(
                MyData(1, "Naslov 1", "Opis 1"),
                MyData(2, "Naslov 2", "Opis 2"),
                MyData(3, "Naslov 3", "Opis 3"),
                MyData(4, "Naslov 4", "Opis 4"),
                MyData(5, "Naslov 5", "Opis 5")
            )
        )
    }

    NavHost(
        navController = navController,
        startDestination = "list_screen"
    ) {
        composable("list_screen") {
            ListScreen(
                itemsList = itemsList,
                onShuffle = {
                    itemsList = itemsList.shuffled()
                },
                onItemClick = { itemId ->
                    navController.navigate("detail_screen/$itemId")
                }
            )
        }

        composable(
            route = "detail_screen/{itemId}",
            arguments = listOf(
                navArgument("itemId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId")
            val selectedItem = itemsList.firstOrNull { it.id == itemId }

            selectedItem?.let { item ->
                DetailScreen(
                    item = item,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

// ZADATAK 5.
@Composable
fun ListScreen(
    itemsList: List<MyData>,
    onShuffle: () -> Unit,
    onItemClick: (Int) -> Unit
) {
    // ZADATAK 5. BONUS - SEARCH BAR
    var searchText by rememberSaveable { mutableStateOf("") }

    val filteredList = if (searchText.isBlank()) {
        itemsList
    } else {
        itemsList.filter {
            it.title.contains(searchText, ignoreCase = true) ||
                    it.description.contains(searchText, ignoreCase = true)
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // ZADATAK 1.
            TitleText(text = "Lista elemenata")

            Spacer(modifier = Modifier.height(8.dp))

            DescriptionText(text = "Pretraga, prikaz liste ...")

            Spacer(modifier = Modifier.height(16.dp))

            // ZADATAK 5. BONUS
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Search") },
                placeholder = { Text("Pretrazi po naslovu ili opisu") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ZADATAK 1.
            CustomButton(
                text = "Promijeni redoslijed",
                onClick = onShuffle
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ZADATAK 3.
            MyItemList(
                itemsList = filteredList,
                onItemClick = onItemClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

// ZADATAK 5.
@Composable
fun DetailScreen(
    item: MyData,
    onBackClick: () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Button(onClick = onBackClick) {
                Text("Back")
            }

            Spacer(modifier = Modifier.height(24.dp))

            TitleText(text = item.title)

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = item.description,
                fontSize = 18.sp
            )
        }
    }
}

// ZADATAK 1.
@Composable
fun TitleText(text: String) {
    Text(
        text = text,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Blue
    )
}

// ZADATAK 1.
@Composable
fun DescriptionText(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

// ZADATAK 1.
@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue,
            contentColor = Color.White
        )
    ) {
        Text(text = text)
    }
}

// ZADATAK 2.
@Composable
fun ItemCard(
    item: MyData,
    onItemClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                onItemClick(item.id)
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Item icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            TitleText(text = item.title)

            Spacer(modifier = Modifier.height(6.dp))

            DescriptionText(text = item.description)

            Spacer(modifier = Modifier.height(12.dp))

            Row {
                Button(onClick = { }) {
                    Text("Favorite")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = { }) {
                    Text("Edit")
                }
            }
        }
    }
}

// ZADATAK 3.
@Composable
fun MyItemList(
    itemsList: List<MyData>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(itemsList) { item ->
            ItemCard(
                item = item,
                onItemClick = onItemClick
            )
        }
    }
}
