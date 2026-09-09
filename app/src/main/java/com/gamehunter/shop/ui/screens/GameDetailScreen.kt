package com.gamehunter.shop.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gamehunter.shop.model.Game
import androidx.compose.ui.draw.clip

@Composable
fun GameDetailScreen(
    game: Game,
    onBack: () -> Unit,
    onAddToCart: () -> Unit
) {
    BackHandler {
        onBack()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .padding(16.dp)
    ) {

        // Botón para regresar al catálogo
        Button(
            onClick = onBack
        ) {
            Text("← Volver")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // Información principal del videojuego
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = game.imageResId),
                    contentDescription = game.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                // Nombre
                Text(
                    text = game.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                // Plataforma
                Text(
                    text = "Plataforma: ${game.platform}",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                // Descripción
                Text(
                    text = game.description,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                // Precio
                Text(
                    text = game.price,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                // Agregar al carrito
                Button(
                    onClick = onAddToCart,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Agregar al carrito")
                }
            }
        }
    }
}