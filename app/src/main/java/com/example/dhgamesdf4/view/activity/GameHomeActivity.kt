package com.example.dhgamesdf4.view.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dhgamesdf4.R
import com.example.dhgamesdf4.view.adapter.GamesAdapter
import com.example.dhgamesdf4.viewModel.GameViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class GameHomeActivity : AppCompatActivity() {

    private val rvHomeGameList:RecyclerView by lazy {
        findViewById(R.id.rvHomeGameList)
    }

    private val searchView: androidx.appcompat.widget.SearchView by lazy {
        findViewById(R.id.searchView)
    }

    private val fabHomeAdicionar : FloatingActionButton by lazy {
        findViewById(R.id.fabHomeAdicionar)
    }


    private lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_home)

        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                Log.i("Pesquisa", query)
                searchView.setQuery(query, true)
            }
        }

        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        iniComponents()
        setupObervables()
    }

    private fun iniComponents() {

        gameViewModel.getAllGames()

        gameViewModel.games.observe(this) { list ->
            rvHomeGameList.apply {
                layoutManager = GridLayoutManager(
                    this@GameHomeActivity, 2,
                    GridLayoutManager.VERTICAL, false
                )
                adapter = GamesAdapter(list) { position ->
                }
            }
        }
    }

    private fun setupObervables(){
        // configuração da busca
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        searchView.setIconifiedByDefault(false)

        searchView.setOnCloseListener {
            Log.i("Pesquisa", "pesquisa foi limpada")
            true
        }

        searchView.setOnQueryTextListener(object: OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    val adapter =rvHomeGameList.adapter as GamesAdapter
                    if(it.length >= 1) {
                        adapter.filter.filter(it)
                    } else {
                        adapter.filter.filter(null)
                    }

                }
                return false
            }
        })

        // botão adicionar
        fabHomeAdicionar.setOnClickListener {
            val intent = Intent(this, GameRegisterActivity::class.java)
            intent.putExtra("jogoid", 1)
            startActivity(intent)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            Log.i("Pesquisa", query ?: "sem texto")
            query?.let {
                searchView.setQuery(query, true)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Firebase.auth.signOut()
    }

}
