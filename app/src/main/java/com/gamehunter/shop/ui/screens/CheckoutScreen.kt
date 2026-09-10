package com.gamehunter.shop.ui.screens

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gamehunter.shop.data.CartManager

@Composable
fun CheckoutScreen(
    onBack: () -> Unit,
    onPurchaseComplete: () -> Unit
) {

    val context = LocalContext.current

    BackHandler {
        onBack()
    }

    Scaffold { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Button(
                onClick = onBack
            ) {
                Text("← Volver al carrito")
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "🛒 Finalizar compra",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "Resumen del pedido",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            CartManager.cartItems.forEach { item ->

                val price = item.game.price
                    .replace("$", "")
                    .toDoubleOrNull() ?: 0.0

                val subtotal = price * item.quantity

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {

                        Image(
                            painter = painterResource(
                                id = item.game.imageResId
                            ),
                            contentDescription = item.game.name,
                            modifier = Modifier
                                .size(90.dp)
                                .clip(
                                    RoundedCornerShape(12.dp)
                                ),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(
                            modifier = Modifier.size(12.dp)
                        )

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text = item.game.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(6.dp)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Text(
                                    text = "Cantidad: ${item.quantity}"
                                )

                                Text(
                                    text = "$${"%.2f".format(subtotal)}",
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "TOTAL: $${"%.2f".format(CartManager.getTotal())}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Button(
                onClick = {

                    // Notificar que la compra terminó
                    onPurchaseComplete()

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirmar compra")
            }
        }
    }
}