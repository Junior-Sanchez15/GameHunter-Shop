package com.gamehunter.shop.data

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import com.gamehunter.shop.model.CartItem
import com.gamehunter.shop.model.Game
import com.gamehunter.shop.model.gameList
import org.json.JSONArray
import org.json.JSONObject

object CartManager {

    val cartItems = mutableStateListOf<CartItem>()

    private const val PREFS_NAME = "gamehunter_preferences"
    private const val CART_KEY = "cart_items"

    private var initialized = false

    fun initialize(context: Context) {

        if (initialized) return

        loadCart(context.applicationContext)

        initialized = true
    }

    fun addToCart(
        game: Game,
        context: Context
    ) {

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

        saveCart(context)
    }

    fun removeFromCart(
        game: Game,
        context: Context
    ) {

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

            saveCart(context)
        }
    }

    fun clearCart(context: Context) {

        cartItems.clear()

        saveCart(context)
    }

    fun getTotal(): Double {

        return cartItems.sumOf { item ->

            val price = item.game.price
                .replace("$", "")
                .toDoubleOrNull() ?: 0.0

            price * item.quantity
        }
    }

    private fun saveCart(context: Context) {

        val preferences = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )

        val jsonArray = JSONArray()

        cartItems.forEach { item ->

            val jsonObject = JSONObject()

            jsonObject.put(
                "gameName",
                item.game.name
            )

            jsonObject.put(
                "quantity",
                item.quantity
            )

            jsonArray.put(jsonObject)
        }

        preferences.edit()
            .putString(
                CART_KEY,
                jsonArray.toString()
            )
            .apply()
    }

    private fun loadCart(context: Context) {

        val preferences = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )

        val savedCart = preferences.getString(
            CART_KEY,
            null
        )

        if (savedCart.isNullOrEmpty()) {
            return
        }

        try {

            val jsonArray = JSONArray(savedCart)

            cartItems.clear()

            for (i in 0 until jsonArray.length()) {

                val jsonObject = jsonArray.getJSONObject(i)

                val gameName = jsonObject.getString(
                    "gameName"
                )

                val quantity = jsonObject.getInt(
                    "quantity"
                )

                val game = gameList.find {
                    it.name == gameName
                }

                if (game != null) {

                    cartItems.add(
                        CartItem(
                            game = game,
                            quantity = quantity
                        )
                    )
                }
            }

        } catch (e: Exception) {

            cartItems.clear()
        }
    }
}