package com.example.dhgamesdf4.business

import android.net.Uri
import android.util.Log
import com.example.dhgamesdf4.model.GameList
import com.example.dhgamesdf4.repository.GameRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Suppress("UNREACHABLE_CODE")
class GameBusiness {
    private val gameRepository by lazy {
        GameRepository()
    }

    private val auth by lazy {
        Firebase.auth
    }

    suspend fun getAllGames(): List<GameList> {
        auth.currentUser?.let {
            try {
                return gameRepository.getAllGames(it.uid)
            } catch(e: Exception) {
                Log.i("Teste",e.message.toString())
                throw e
            }
        }
        return emptyList()
    }

    suspend fun getGame(gameRef: String): GameList? {
        auth.currentUser?.let {
            try {
                return gameRepository.getGame(it.uid,gameRef)
            } catch(e: Exception) {
                Log.i("Teste",e.message.toString())
                throw e
            }
        }
        return null
    }

    suspend fun saveGame(gameName: String, gameCreateAt: String, gameDescription: String, gameImage: Uri?) {
        try {
            val gameList = GameList(auth.currentUser?.uid ?: "",gameName,gameDescription,gameCreateAt,gameImage)
            gameRepository.saveGame(gameList, auth.currentUser?.uid ?: "")
        } catch (e: Exception) {
            throw e
        }
    }

}