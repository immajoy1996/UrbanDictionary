package com.example.urbandic.ui

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.urbandic.di.AppComponentHolder
import com.example.urbandic.R
import com.example.urbandic.room.WordItemDao
import com.example.urbandic.room.WordItemEntity
import com.example.urbandic.viewmodel.DictionaryViewModel.Companion.FAVORITE
import com.example.urbandic.viewmodel.DictionaryViewModel.Companion.LEAST
import com.example.urbandic.viewmodel.DictionaryViewModel.Companion.MOST
import com.example.urbandic.viewmodel.DictionaryViewModelInterface
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_dictionary.*
import kotlinx.android.synthetic.main.base_activity.*
import javax.inject.Inject

class DictionaryActivity : BaseActivity(), FavoritesStarClickInterface {
    @Inject
    lateinit var dictionaryViewModel: DictionaryViewModelInterface

    @Inject
    lateinit var wordItemDao: WordItemDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppComponentHolder.component.inject(this)
        setContentView(R.layout.activity_dictionary)
        setUpWordsRecycler()
        setUpSearch()
        setUpSortByButtons()
        setUpSortBySwitch()
    }

    private fun setUpSortBySwitch() {
        addDisposable(
            dictionaryViewModel.getSortByState()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    handleSortByState(it)
                })
        )
    }

    private fun handleSortByState(state: String) {
        when (state) {
            MOST -> (words_recycler.adapter as DictionaryAdapter).sortByMostLikes()
            LEAST -> (words_recycler.adapter as DictionaryAdapter).sortByLeastLikes()
            FAVORITE -> {
                Observable.just("").subscribeOn(Schedulers.io())
                    .subscribe({
                        showFavoritesList(
                            wordItemDao.getAllFavoriteWords()
                        )
                    },
                        { e -> e.printStackTrace() })
            }
        }
    }

    private fun showFavoritesList(wordItemList: List<WordItemEntity>) {
        runOnUiThread {
            (words_recycler.adapter as DictionaryAdapter).showFavoritesList(wordItemList)
        }
    }

    private fun setUpSortByButtons() {
        most.setOnClickListener {
            dictionaryViewModel.setSortByState(MOST)
            most.setBackgroundResource(R.drawable.sort_by_button_enabled)
            least.setBackgroundResource(R.drawable.sort_by_button_disabled)
            favorite.setBackgroundResource(R.drawable.sort_by_button_disabled)
        }
        least.setOnClickListener {
            dictionaryViewModel.setSortByState(LEAST)
            least.setBackgroundResource(R.drawable.sort_by_button_enabled)
            most.setBackgroundResource(R.drawable.sort_by_button_disabled)
            favorite.setBackgroundResource(R.drawable.sort_by_button_disabled)
        }
        favorite.setOnClickListener {
            dictionaryViewModel.setSortByState(FAVORITE)
            favorite.setBackgroundResource(R.drawable.sort_by_button_enabled)
            least.setBackgroundResource(R.drawable.sort_by_button_disabled)
            most.setBackgroundResource(R.drawable.sort_by_button_disabled)
        }
    }

    private fun setUpSearch() {
        search_submit.setOnClickListener {
            val userInput = search.text.toString()
            lookupTerm(userInput)
        }
    }

    private fun lookupTerm(term: String) {
        addDisposable(
            dictionaryViewModel.getSomething(term).subscribe({
                (words_recycler.adapter as DictionaryAdapter).refreshWithSearchResults(it)
            }, {
                Toast.makeText(this, "error retrieving definitions", Toast.LENGTH_SHORT).show()
            })
        )
    }

    private fun setUpWordsRecycler(defaultWord: String = "Dog") {
        addDisposable(
            dictionaryViewModel.getSomething(defaultWord).subscribe({
                words_recycler.apply {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    adapter =
                        DictionaryAdapter(it, this@DictionaryActivity)
                }
            }, {
                Toast.makeText(this, "error retrieving definitions", Toast.LENGTH_SHORT).show()
            })
        )
    }

    override fun onFavoritesStarClick(wordItemEntity: WordItemEntity) {
        addDisposable(
            Observable.just("").subscribeOn(Schedulers.io()).subscribe({
                wordItemDao.insert(wordItemEntity)
            }, { e -> e.printStackTrace() })
        )
    }
}
