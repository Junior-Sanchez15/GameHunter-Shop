package com.gamehunter.shop.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object OfflineManager {

    private const val PREFS_NAME = "gamehunter_offline"
    private const val PENDING_KEY = "pending_operations"

    fun savePendingOperation(
        context: Context,
        gameName: String,
        operation: String
    ) {

        val preferences = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )

        val savedOperations = preferences.getString(
            PENDING_KEY,
            null
        )

        val jsonArray =
            if (savedOperations.isNullOrEmpty()) {
                JSONArray()
            } else {
                JSONArray(savedOperations)
            }

        val operationObject = JSONObject()

        operationObject.put(
            "gameName",
            gameName
        )

        operationObject.put(
            "operation",
            operation
        )

        jsonArray.put(operationObject)

        preferences.edit()
            .putString(
                PENDING_KEY,
                jsonArray.toString()
            )
            .apply()
    }

    fun getPendingOperations(
        context: Context
    ): JSONArray {

        val preferences = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )

        val savedOperations = preferences.getString(
            PENDING_KEY,
            null
        )

        return if (savedOperations.isNullOrEmpty()) {
            JSONArray()
        } else {
            JSONArray(savedOperations)
        }
    }

    fun getPendingCount(
        context: Context
    ): Int {

        return getPendingOperations(context).length()
    }

    fun clearPendingOperations(
        context: Context
    ) {

        context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
            .edit()
            .remove(PENDING_KEY)
            .apply()
    }
}