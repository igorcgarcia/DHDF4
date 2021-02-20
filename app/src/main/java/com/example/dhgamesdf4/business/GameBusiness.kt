package com.example.dhgamesdf4.business

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
        //auth.currentUser?.let {
            try {
                return gameRepository.getAllGames()
            } catch(e: Exception) {
                throw e
            }
        //}
        return emptyList()
    }

}