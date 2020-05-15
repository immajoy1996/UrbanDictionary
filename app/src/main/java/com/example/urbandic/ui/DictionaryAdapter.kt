package com.example.urbandic.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.urbandic.R
import com.example.urbandic.data.DefinitionItem
import com.example.urbandic.data.DictionaryTermResponse
import com.example.urbandic.room.WordItemDao
import com.example.urbandic.room.WordItemEntity
import kotlinx.android.synthetic.main.word_item_view.view.*
import java.util.*
import java.util.logging.Logger

class DictionaryAdapter(
    private var dictionaryResponse: DictionaryTermResponse,
    private var favoritesStarClickInterface: FavoritesStarClickInterface
) : RecyclerView.Adapter<DictionaryItemViewHolder>() {
    private var showingFavorites = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.word_item_view, parent, false)
        return DictionaryItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: DictionaryItemViewHolder, position: Int) {
        holder.bind(dictionaryResponse.list[position])
        holder.bindFavoriteItem(showingFavorites)
        holder.itemView.favorites_star.setOnClickListener {
            val item = dictionaryResponse.list[position]
            val id = (Math.random() * MAX_ID).toString()
            favoritesStarClickInterface.onFavoritesStarClick(
                WordItemEntity(
                    wordId = id,
                    word = item.word,
                    definition = item.definition,
                    thumbs_up = item.thumbs_up,
                    thumbs_down = item.thumbs_down
                )
            )
            holder.bindFavoriteItem(showingFavorites, true)
        }
        Log.d("Favorite Clicked ", "true")
    }

    override fun getItemCount(): Int {
        return dictionaryResponse.list.size
    }

    fun refreshWithSearchResults(newResponse: DictionaryTermResponse) {
        dictionaryResponse = newResponse
        notifyDataSetChanged()
    }

    fun sortByMostLikes() {
        showingFavorites = false
        dictionaryResponse =
            DictionaryTermResponse(list = dictionaryResponse.list.sortedByDescending { it.thumbs_up })
        notifyDataSetChanged()
    }

    fun sortByLeastLikes() {
        showingFavorites = false
        dictionaryResponse =
            DictionaryTermResponse(list = dictionaryResponse.list.sortedByDescending { it.thumbs_down })
        notifyDataSetChanged()
    }

    fun showFavoritesList(favWordsList: List<WordItemEntity>) {
        showingFavorites = true
        var favoritesList: List<DefinitionItem> = listOf()
        for (item in favWordsList) {
            favoritesList = favoritesList.plus(
                DefinitionItem(
                    word = item.word,
                    definition = item.definition,
                    thumbs_down = item.thumbs_down,
                    thumbs_up = item.thumbs_up
                )
            )
        }
        dictionaryResponse = DictionaryTermResponse(list = favoritesList)
        notifyDataSetChanged()
    }

    companion object {
        private const val MAX_ID = 1000000
    }
}