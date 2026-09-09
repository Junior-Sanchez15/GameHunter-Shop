package com.gamehunter.shop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gamehunter.shop.model.Game
import com.gamehunter.shop.model.gameList
import com.gamehunter.shop.ui.theme.GameHunterShopTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            GameHunterShopTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    GameHunterHome()
                }
            }
        }
    }
}


@Composable
fun GameHunterHome() {

    // Texto introducido en la barra de búsqueda
    var searchText by remember {
        mutableStateOf("")
    }

    // Categoría seleccionada actualmente
    var selectedCategory by remember {
        mutableStateOf("Todos")
    }

    // Categorías disponibles
    val categories = listOf(
        "Todos",
        "Nintendo",
        "PlayStation",
        "PC"
    )

    Scaffold(
        topBar = {
            GameHunterTopBar()
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(16.dp)
        ) {

            // Título principal
            Text(
                text = "Encuentra tus videojuegos favoritos",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )


            // Barra de búsqueda
            OutlinedTextField(
                value = searchText,

                onValueChange = {
                    searchText = it
                },

                modifier = Modifier.fillMaxWidth(),

                placeholder = {
                    Text("Buscar videojuego...")
                },

                singleLine = true,

                shape = RoundedCornerShape(12.dp)
            )


            Spacer(
                modifier = Modifier.height(24.dp)
            )


            // Título de categorías
            Text(
                text = "Categorías",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )


            Spacer(
                modifier = Modifier.height(12.dp)
            )


            // Botones de filtros
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(
                        rememberScrollState()
                    ),

                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                categories.forEach { category ->

                    Button(
                        onClick = {

                            // Guardamos la categoría seleccionada
                            selectedCategory = category
                        }
                    ) {

                        Text(category)
                    }
                }
            }


            Spacer(
                modifier = Modifier.height(28.dp)
            )


            // Título del catálogo
            Text(
                text = "Videojuegos destacados",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )


            Spacer(
                modifier = Modifier.height(12.dp)
            )


            /*
             * Filtramos los videojuegos utilizando dos condiciones:
             *
             * 1. El nombre debe coincidir con la búsqueda.
             * 2. La plataforma debe coincidir con la categoría seleccionada.
             *
             * Si se selecciona "Todos", se muestran todas las plataformas.
             */
            val filteredGames = gameList.filter { game ->

                val matchesSearch = game.name.contains(
                    searchText,
                    ignoreCase = true
                )

                val matchesCategory =
                    selectedCategory == "Todos" ||
                            game.platform == selectedCategory

                matchesSearch && matchesCategory
            }


            // Mostrar resultados
            if (filteredGames.isEmpty()) {

                Text(
                    text = "No se encontraron videojuegos.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),

                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,

                    style = MaterialTheme.typography.bodyLarge
                )

            } else {

                filteredGames.forEach { game ->

                    GameCard(game)

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun GameHunterTopBar() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary
            )
            .padding(
                horizontal = 16.dp,
                vertical = 14.dp
            ),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "🎮 GameHunter Shop",

            modifier = Modifier.weight(1f),

            color = MaterialTheme.colorScheme.onPrimary,

            style = MaterialTheme.typography.titleLarge,

            fontWeight = FontWeight.Bold
        )


        // Carrito.
        // Lo haremos funcional más adelante.
        Text(
            text = "🛒",

            color = MaterialTheme.colorScheme.onPrimary,

            style = MaterialTheme.typography.titleLarge
        )
    }
}


@Composable
fun GameCard(game: Game) {

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(16.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            // Espacio reservado para la imagen
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,

                        shape = RoundedCornerShape(12.dp)
                    ),

                verticalArrangement = Arrangement.Center,

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "🎮",

                    style = MaterialTheme.typography.displayMedium
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "Imagen del videojuego"
                )
            }


            Spacer(
                modifier = Modifier.height(12.dp)
            )


            // Nombre del videojuego
            Text(
                text = game.name,

                style = MaterialTheme.typography.titleMedium,

                fontWeight = FontWeight.Bold
            )


            Spacer(
                modifier = Modifier.height(4.dp)
            )


            // Plataforma
            Text(
                text = "Plataforma: ${game.platform}",

                style = MaterialTheme.typography.bodyMedium
            )


            Spacer(
                modifier = Modifier.height(4.dp)
            )


            // Descripción
            Text(
                text = game.description,

                style = MaterialTheme.typography.bodyMedium
            )


            Spacer(
                modifier = Modifier.height(4.dp)
            )


            // Precio
            Text(
                text = game.price,

                style = MaterialTheme.typography.titleMedium,

                fontWeight = FontWeight.Bold
            )


            Spacer(
                modifier = Modifier.height(10.dp)
            )


            // Botón para la pantalla de detalle
            Button(
                onClick = {
                    // La pantalla de detalle
                    // la implementaremos próximamente.
                },

                modifier = Modifier.fillMaxWidth()
            ) {

                Text("Ver detalles")
            }
        }
    }
}