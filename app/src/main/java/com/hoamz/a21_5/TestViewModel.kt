package com.hoamz.a21_5

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * @author hwa..
 */
class TestViewModel : ViewModel() {
    private var _isLoading = MutableLiveData(false)
    val isLoading : LiveData<Boolean> get() = _isLoading

    private var counter = 0//khi tat ca cac task de ket thuc thi ms hideLoading()
    private val mutex = Mutex()

    fun task1(){
        viewModelScope.launch {
            showLoading()
            delay(5000)
            Log.d("TEST","task1 chay xong")
            hideLoading()
        }
    }

    fun task2(){
        viewModelScope.launch {
            showLoading()
            delay(10000)
            Log.d("TEST","task2 chay xong")
            hideLoading()
        }
    }

    fun task3(){
        viewModelScope.launch {
            showLoading()
            delay(20000)
            Log.d("TEST","task3 chay xong")
            hideLoading()
        }
    }

    fun test(){
        task1()
        task2()
        task3()
    }

    fun showLoading(){
        viewModelScope.launch {
            mutex.withLock {
                counter++
                _isLoading.value = counter > 0
            }
        }
    }

    fun hideLoading(){
        viewModelScope.launch {
            mutex.withLock {
                counter--
                _isLoading.value = counter > 0
            }
        }
    }
}