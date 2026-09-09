package com.gamehunter.shop.model

import com.gamehunter.shop.R

val gameList = listOf(

    Game(
        name = "The Legend of Zelda Breath Of The Wild",
        platform = "Nintendo",
        price = "$59.99",
        description = "Aventura y exploración en un mundo fantástico.",
        imageResId = R.drawable.zelda
    ),

    Game(
        name = "Mario Kart 8 Deluxe",
        platform = "Nintendo",
        price = "$49.99",
        description = "Juego de carreras con personajes clásicos de Nintendo.",
        imageResId = R.drawable.mario_kart
    ),

    Game(
        name = "God Of War Ragnarok",
        platform = "PlayStation, PC",
        price = "$69.99",
        description = "Videojuego de fútbol con diferentes equipos y competiciones.",
        imageResId = R.drawable.ragnarok
    ),

    Game(
        name = "Spider-Man 2",
        platform = "PlayStation, PC",
        price = "$69.99",
        description = "Aventura y acción protagonizada por Spider-Man.",
        imageResId = R.drawable.spiderman
    ),

    Game(
        name = "Minecraft",
        platform = "PC, Nintendo, PlayStation",
        price = "$29.99",
        description = "Juego de construcción, exploración y supervivencia.",
        imageResId = R.drawable.minecraft
    ),

    Game(
        name = "Cyberpunk 2077",
        platform = "PC, Nintendo, PlayStation",
        price = "$59.99",
        description = "Juego de rol y acción ambientado en Night City.",
        imageResId = R.drawable.cyberpunk
    )
)