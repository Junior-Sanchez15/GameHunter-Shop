package com.gamehunter.shop.data

import androidx.compose.runtime.mutableStateListOf
import com.gamehunter.shop.model.CartItem
import com.gamehunter.shop.model.Game

object CartManager {

    val cartItems = mutableStateListOf<CartItem>()

    fun addToCart(game: Game) {

        val index = cartItems.indexOfFirst {
            it.game.name == game.name
        }

        if (index != -1) {

            val currentItem = cartItems[index]

            cartItems[index] = currentItem.copy(
                quantity = currentItem.quantity + 1
            )

        } else {

            cartItems.add(
                CartItem(
                    game = game,
                    quantity = 1
                )
            )
        }
    }

    fun removeFromCart(game: Game) {

        val index = cartItems.indexOfFirst {
            it.game.name == game.name
        }

        if (index != -1) {

            val currentItem = cartItems[index]

            if (currentItem.quantity > 1) {

                cartItems[index] = currentItem.copy(
                    quantity = currentItem.quantity - 1
                )

            } else {

                cartItems.removeAt(index)
            }
        }
    }

    fun clearCart() {
        cartItems.clear()
    }

    fun getTotal(): Double {

        return cartItems.sumOf { item ->

            val price = item.game.price
                .replace("$", "")
                .toDoubleOrNull() ?: 0.0

            price * item.quantity
        }
    }
}