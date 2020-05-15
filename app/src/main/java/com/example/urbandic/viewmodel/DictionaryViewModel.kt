package com.example.urbandic.viewmodel

import com.example.urbandic.data.DictionaryTermResponse
import com.example.urbandic.room.WordItemDao
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

interface DictionaryViewModelInterface {
    fun getSomething(term: String): Flowable<DictionaryTermResponse>
    fun getSortByState(): PublishSubject<String>
    fun setSortByState(state: String)
}

class DictionaryViewModel @Inject constructor(
    private val baseRepository: BaseRepositoryInterface
) :
    DictionaryViewModelInterface {
    private val sortBySubject = PublishSubject.create<String>()

    init {
        sortBySubject.onNext(MOST)
    }

    override fun getSomething(term: String): Flowable<DictionaryTermResponse> {
        return baseRepository.getSomething(term)
    }

    override fun getSortByState(): PublishSubject<String> {
        return sortBySubject
    }

    override fun setSortByState(state: String) {
        sortBySubject.onNext(state)
    }

    companion object {
        const val MOST = "most"
        const val LEAST = "least"
        const val FAVORITE = "favorite"
    }
}