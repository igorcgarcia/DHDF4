package com.example.dhgamesdf4.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dhgamesdf4.business.GameBusiness
import com.example.dhgamesdf4.model.GameList
import kotlinx.coroutines.launch

class GameViewModel: ViewModel() {
    val games: MutableLiveData<List<GameList>> = MutableLiveData()
    val game: MutableLiveData<GameList> = MutableLiveData()
    val saveGame: MutableLiveData<Boolean> = MutableLiveData()
    val errMessage: MutableLiveData<String> = MutableLiveData()

    private val gameBusiness by lazy {
        GameBusiness()
    }

    init {
        saveGame.value = false
    }


    fun getAllGames() {
        viewModelScope.launch {
            try {
                games.postValue(gameBusiness.getAllGames())
            } catch(e: Exception) {
                Log.i("Teste",e.message.toString())
                errMessage.postValue(e.message)
            }
        }
    }

    fun getGame(gameRef: String) {
        viewModelScope.launch {
            viewModelScope.launch {
                game.postValue(gameBusiness.getGame(gameRef))
            }
        }
    }

    fun saveGame(gameName: String, gameCreateAt: String, gameDescription: String, gameImage: Uri?) {
//        val id = gameName.toLowerCase().replace(' ', '_')
        //val gameList = GameList(gameName,gameDescription,gameCreateAt,gameImage)
        viewModelScope.launch {
            try {
                gameBusiness.saveGame(gameName, gameCreateAt, gameDescription, gameImage)
                saveGame.postValue(true)
            } catch(e: Exception) {
                errMessage.postValue(e.message)
            }
        }
    }


}