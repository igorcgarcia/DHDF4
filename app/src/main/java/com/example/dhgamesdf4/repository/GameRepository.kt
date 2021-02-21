package com.example.dhgamesdf4.repository

import android.util.Log
import com.example.dhgamesdf4.model.GameList
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class GameRepository {

    val db by lazy {
        FirebaseFirestore.getInstance()
    }

    var list : MutableList<GameList> = mutableListOf()

    suspend fun getAllGames() : List<GameList> {

        db.collection("games")
            .orderBy("gameCreateAt", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {

                    val gameName: String = document.getString("gameName").toString()
                    val gameDescription: String = document.getString("gameDescription").toString()
                    val gameCreateAt: String = document.getString("gameCreateAt").toString()
                    val gameImage: String = document.getString("gameImage").toString()
                    list.add(GameList(gameName, gameDescription, gameCreateAt, gameImage))

                }

            }
            .addOnFailureListener { exception ->
                Log.i("FIREBASE", "Error getting documents: ", exception)
            }
            .await()

        return list
    }
}