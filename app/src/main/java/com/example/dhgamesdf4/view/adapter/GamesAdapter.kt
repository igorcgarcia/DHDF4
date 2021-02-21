package com.example.dhgamesdf4.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dhgamesdf4.R
import com.example.dhgamesdf4.model.GameList
import java.util.*
import kotlin.collections.ArrayList

class GamesAdapter(private val gameList : List<GameList>,
                   private val onItemMenuClicked : (String) -> Unit
) : RecyclerView.Adapter<GamesAdapter.ViewHolder>(), Filterable {

    var listGames = ArrayList<GameList>()

    init {
        listGames = ArrayList(gameList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_recycler_games,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listGames[position],onItemMenuClicked)
    }

    override fun getItemCount(): Int {
        return listGames.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private lateinit var ivGamePhoto :ImageView
        private lateinit var tvGameName : TextView
        private lateinit var tvGameCreateAt : TextView

        fun bind(list : GameList, onItemMenuClicked: (String) -> Unit) = with(itemView) {
            tvGameName = findViewById<TextView>(R.id.tvGameName)
            tvGameCreateAt = findViewById<TextView>(R.id.tvGameCreateAt)
            ivGamePhoto = findViewById<ImageView>(R.id.ivGamePhoto)

            Glide.with(itemView.context).load(list.gameImage).into(ivGamePhoto)
            tvGameName.text = list.gameName
            tvGameCreateAt.text = list.gameCreateAt

            ivGamePhoto.setOnClickListener {
                onItemMenuClicked(list.gameName ?: "")
            }

        }
    }

    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filtro = constraint?.toString()?.toLowerCase(Locale.ROOT)
                if(filtro.isNullOrEmpty()) {
                    listGames = ArrayList(gameList)
                } else {
                    val resultList = ArrayList<GameList>()
                    for(game in gameList) {
                        if (game.gameName?.toLowerCase(Locale.ROOT)?.contains(filtro) == true) {
                            resultList.add(game)
                        }
                    }
                    listGames = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = listGames
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listGames = results?.values as ArrayList<GameList>
                notifyDataSetChanged()
            }
        }
    }
}
