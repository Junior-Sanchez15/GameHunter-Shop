package com.gamehunter.shop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.statusBarsPadding
import com.gamehunter.shop.data.CartManager
import com.gamehunter.shop.model.Game
import com.gamehunter.shop.model.gameList
import com.gamehunter.shop.ui.screens.CartScreen
import com.gamehunter.shop.ui.screens.CheckoutScreen
import com.gamehunter.shop.ui.screens.GameDetailScreen
import com.gamehunter.shop.ui.screens.PurchaseSuccessScreen
import com.gamehunter.shop.ui.theme.GameHunterShopTheme
import androidx.compose.runtime.collectAsState
import com.gamehunter.shop.data.NetworkMonitor
import com.gamehunter.shop.ui.screens.NetworkStatusIndicator


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar y recuperar el carrito guardado
        CartManager.initialize(this)

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

    val context = LocalContext.current

    val networkMonitor = remember {
        NetworkMonitor(context)
    }

    val isOnline by networkMonitor.isOnline.collectAsState()

    // Texto introducido en el buscador
    var searchText by remember {
        mutableStateOf("")
    }

    // Videojuego seleccionado
    var selectedGame by remember {
        mutableStateOf<Game?>(null)
    }

    // Categoría seleccionada
    var selectedCategory by remember {
        mutableStateOf("Todos")
    }

    // Mostrar carrito
    var showCart by remember {
        mutableStateOf(false)
    }

    // Mostrar pantalla de checkout
    var showCheckout by remember {
        mutableStateOf(false)
    }

    // Mensaje del carrito
    var cartMessage by remember {
        mutableStateOf(false)
    }

    // Indica si la compra fue completada
    var purchaseCompleted by remember {
        mutableStateOf(false)
    }

    // Total de la compra realizada
    var purchaseTotal by remember {
        mutableStateOf(0.0)
    }

    // Categorías disponibles
    val categories = listOf(
        "Todos",
        "Nintendo",
        "PlayStation",
        "PC"
    )

    /*
     * Navegación principal:
     *
     * 1. Compra completada
     * 2. Checkout
     * 3. Carrito
     * 4. Detalles
     * 5. Catálogo
     */

    if (purchaseCompleted) {

        PurchaseSuccessScreen(
            total = purchaseTotal,
            onBackToCatalog = {
                purchaseCompleted = false
            }
        )

    } else if (showCheckout) {

        CheckoutScreen(
            onBack = {
                showCheckout = false
            },
            onPurchaseComplete = {

                // Guardamos el total antes de vaciar el carrito
                purchaseTotal = CartManager.getTotal()

                // Vaciar carrito y guardar el cambio
                CartManager.clearCart(context)

                showCheckout = false
                showCart = false
                purchaseCompleted = true
            }
        )

    } else if (showCart) {

        CartScreen(
            onBack = {
                showCart = false
            },
            onCheckout = {
                showCheckout = true
            }
        )

    } else if (selectedGame != null) {

        GameDetailScreen(
            game = selectedGame!!,
            onBack = {
                selectedGame = null
            },
            onAddToCart = {

                // Agregar producto y guardar carrito
                CartManager.addToCart(
                    selectedGame!!,
                    context
                )

                cartMessage = true
            }
        )

    } else {

        Scaffold(
            topBar = {
                GameHunterTopBar(
                    onCartClick = {
                        showCart = true
                    }
                )
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
                NetworkStatusIndicator(
                    isOnline = isOnline
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

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

                // Categorías
                Text(
                    text = "Categorías",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                // Botones de categorías
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
                 * Filtrar videojuegos:
                 *
                 * 1. Por nombre.
                 * 2. Por plataforma.
                 */

                val filteredGames = gameList.filter { game ->

                    val matchesSearch = game.name.contains(
                        searchText,
                        ignoreCase = true
                    )

                    val matchesCategory =
                        selectedCategory == "Todos" ||
                                game.platform.contains(
                                    selectedCategory,
                                    ignoreCase = true
                                )

                    matchesSearch && matchesCategory
                }

                // Mostrar resultados
                if (filteredGames.isEmpty()) {

                    Text(
                        text = "No se encontraron videojuegos.",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )

                } else {

                    filteredGames.forEach { game ->

                        GameCard(
                            game = game,
                            onDetailsClick = {

                                // Abrir detalles
                                selectedGame = game
                            }
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun GameHunterTopBar(
    onCartClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary
            )
            .statusBarsPadding()
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

        // Botón del carrito
        Text(
            text = "🛒",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .clip(
                    RoundedCornerShape(12.dp)
                )
                .clickable {
                    onCartClick()
                }
                .padding(12.dp)
        )
    }
}


@Composable
fun GameCard(
    game: Game,
    onDetailsClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            // Imagen del videojuego
            Image(
                painter = painterResource(
                    id = game.imageResId
                ),
                contentDescription = game.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(
                        RoundedCornerShape(12.dp)
                    ),
                contentScale = ContentScale.Crop
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            // Nombre
            Text(
                text = game.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            // Plataforma
            Text(
                text = "Plataforma: ${game.platform}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            // Descripción
            Text(
                text = game.description,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            // Precio
            Text(
                text = game.price,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            // Botón de detalles
            Button(
                onClick = onDetailsClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver detalles")
            }
        }
    }
}