package com.gamehunter.shop.model

data class CartItem(
    val game: Game,
    var quantity: Int = 1
)