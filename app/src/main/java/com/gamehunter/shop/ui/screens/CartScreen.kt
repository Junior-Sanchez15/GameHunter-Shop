package com.gamehunter.shop.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gamehunter.shop.data.CartManager
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun CartScreen(
    onBack: () -> Unit,
    onCheckout: () -> Unit
) {

    BackHandler {
        onBack()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        // Botón para regresar
        Button(
            onClick = onBack,
        ) {
            Text("← Volver al catálogo")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // Título
        Text(
            text = "🛒 Carrito de compras",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // Carrito vacío
        if (CartManager.cartItems.isEmpty()) {

            Text(
                text = "El carrito está vacío.",
                style = MaterialTheme.typography.bodyLarge
            )

        } else {

            // Productos del carrito
            CartManager.cartItems.forEach { item ->

                val price = item.game.price
                    .replace("$", "")
                    .toDoubleOrNull() ?: 0.0

                val subtotal = price * item.quantity

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {

                        // Imagen del videojuego
                        Image(
                            painter = painterResource(
                                id = item.game.imageResId
                            ),
                            contentDescription = item.game.name,
                            modifier = Modifier
                                .size(110.dp)
                                .clip(
                                    RoundedCornerShape(12.dp)
                                ),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(
                            modifier = Modifier.size(12.dp)
                        )

                        // Información
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text = item.game.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(4.dp)
                            )

                            Text(
                                text = item.game.platform,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(
                                modifier = Modifier.height(4.dp)
                            )

                            Text(
                                text = "Precio: ${item.game.price}"
                            )

                            Text(
                                text = "Cantidad: ${item.quantity}"
                            )

                            Text(
                                text = "Subtotal: $${"%.2f".format(subtotal)}",
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            // Botones + y -
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {

                                Button(
                                    onClick = {
                                        CartManager.removeFromCart(
                                            item.game
                                        )
                                    }
                                ) {
                                    Text("−")
                                }

                                Button(
                                    onClick = {
                                        CartManager.addToCart(
                                            item.game
                                        )
                                    }
                                ) {
                                    Text("+")
                                }
                            }
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // Total
            Text(
                text = "Total: $${"%.2f".format(CartManager.getTotal())}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // Comprar
            Button(
                onClick = onCheckout,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Comprar")
            }
        }
    }
}