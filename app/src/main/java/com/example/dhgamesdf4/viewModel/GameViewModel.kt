package com.example.dhgamesdf4.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dhgamesdf4.business.GameBusiness
import com.example.dhgamesdf4.model.GameList
import kotlinx.coroutines.launch

class GameViewModel: ViewModel() {
    val games: MutableLiveData<List<GameList>> = MutableLiveData()
    val errMessage: MutableLiveData<String> = MutableLiveData()

    private val gameBusiness by lazy {
        GameBusiness()
    }

    fun getAllGames() {
        viewModelScope.launch {
            try {
                games.postValue(gameBusiness.getAllGames())
            } catch(e: Exception) {
                errMessage.postValue(e.message)
            }
        }
    }


}