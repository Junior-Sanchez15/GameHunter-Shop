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

    var searchText by remember {
        mutableStateOf("")
    }

    val categories = listOf(
        "Nintendo",
        "PlayStation",
        "PC"
    )

    val games = listOf(
        Game(
            name = "The Legend of Zelda",
            platform = "Nintendo",
            price = "$59.99"
        ),
        Game(
            name = "EA Sports FC",
            platform = "PlayStation",
            price = "$69.99"
        ),
        Game(
            name = "Minecraft",
            platform = "PC",
            price = "$29.99"
        )
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

            Text(
                text = "Encuentra tus videojuegos favoritos",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

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

            Text(
                text = "Categorías",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

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
                            // Más adelante implementaremos
                            // el filtro por plataforma.
                        }
                    ) {
                        Text(category)
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            Text(
                text = "Videojuegos destacados",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            games
                .filter { game ->
                    game.name.contains(
                        searchText,
                        ignoreCase = true
                    )
                }
                .forEach { game ->

                    GameCard(game)

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )
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

            Text(
                text = game.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "Plataforma: ${game.platform}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = game.price,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Button(
                onClick = {
                    // Más adelante abriremos
                    // la pantalla de detalle.
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver detalles")
            }
        }
    }
}

data class Game(
    val name: String,
    val platform: String,
    val price: String
)