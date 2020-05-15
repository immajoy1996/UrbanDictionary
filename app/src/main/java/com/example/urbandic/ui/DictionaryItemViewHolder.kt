package com.example.urbandic.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.urbandic.R
import com.example.urbandic.data.DefinitionItem
import kotlinx.android.synthetic.main.word_item_view.view.*

class DictionaryItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(item: DefinitionItem) {
        setWord(item.word)
        setDefinition(item.definition)
        setLikes(item.thumbs_up)
        setDislikes(item.thumbs_down)
    }

    fun bindFavoriteItem(showingFavorites: Boolean, favItemClicked: Boolean = false) {
        setUpFavorite(showingFavorites, favItemClicked)
    }

    private fun setUpFavorite(showingFavorites: Boolean, favItemClicked: Boolean) {
        if (showingFavorites) {
            itemView.favorites_star.visibility = View.GONE
        } else if (favItemClicked) {
            itemView.favorites_star.visibility = View.VISIBLE
            itemView.favorites_star.setImageResource(R.drawable.filled_star)
        } else {
            itemView.favorites_star.visibility = View.VISIBLE
            itemView.favorites_star.setImageResource(R.drawable.clear_star)
        }
    }

    private fun setWord(word: String) {
        itemView.word.text = word
    }

    private fun setDefinition(definition: String) {
        itemView.definition.text = definition
    }

    private fun setLikes(likes: Int) {
        itemView.thumbs_up_count.text = likes.toString()
    }

    private fun setDislikes(dislikes: Int) {
        itemView.thumbs_down_count.text = dislikes.toString()
    }

}