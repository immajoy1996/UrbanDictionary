package com.example.urbandic.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.urbandic.R
import io.reactivex.disposables.Disposable

open class BaseActivity : AppCompatActivity() {
    private var disposableArray = arrayListOf<Disposable>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_activity)
    }

    fun addDisposable(disposable: Disposable){
        disposableArray.add(disposable)
    }

    override fun onDestroy() {
        for (disposable in disposableArray) {
            disposable.dispose()
        }
        disposableArray.clear()
        super.onDestroy()
    }
}
