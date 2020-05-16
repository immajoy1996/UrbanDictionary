package com.example.urbandic

import com.example.urbandic.data.DictionaryTermResponse
import com.example.urbandic.viewmodel.BaseRepositoryInterface
import com.example.urbandic.viewmodel.DictionaryViewModel
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Flowable
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class DictionaryViewModelTest {
    lateinit var dictionaryViewModel: DictionaryViewModel
    val baseRepository: BaseRepositoryInterface = mockk(relaxed = true)
    val defaultValue=Flowable.just(
        DictionaryTermResponse(
            listOf()
        )
    )

    @Before
    fun setup() {
        dictionaryViewModel = DictionaryViewModel(baseRepository)
        every { baseRepository.getSomething(any<String>()) } returns defaultValue
    }

    @Test
    fun testLookupMethod() {
        assertEquals(
            dictionaryViewModel.getSomething("2"), defaultValue
        )
    }
}
