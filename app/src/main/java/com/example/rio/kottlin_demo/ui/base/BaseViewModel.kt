package com.example.rio.kottlin_demo.ui.base

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel<T:Any>:ViewModel(){

    protected var disposable: CompositeDisposable = CompositeDisposable()
    protected var loadingIndicator: MutableLiveData<Boolean> = MutableLiveData()
    protected var intenetDisconnectEvent: MutableLiveData<Boolean> = MutableLiveData()
    protected var viewData = MediatorLiveData<T>()

    override fun onCleared() {
        super.onCleared()

        Log.d("Rio", "onCleared")
        disposeAll()
        System.gc()
    }

    fun disposeAll() {
        if (disposable != null && disposable.size() > 0) {
            disposable.clear()
        }
    }

    fun getViewData(): LiveData<T> {
        return viewData
    }

    fun updateViewData(newViewData: T) {
        this.viewData.value = newViewData
    }
}