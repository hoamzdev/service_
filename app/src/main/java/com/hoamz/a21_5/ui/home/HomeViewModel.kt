package com.hoamz.a21_5.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

/**
 * @author hwa..
 */

class HomeViewModel : ViewModel() {

    private var _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var counterLoading : Int = 0
    private val mutex = Mutex()



    //task 1(run in 5s)
    private fun task1() {
        viewModelScope.launch {
            log("Task 1 bắt đầu chạy")
            showLoading()
            delay(5000)//gia lap chay trong 5s
            log("Task 1 hoàn thành")
            hideLoading()
        }
    }

    //task 2 (run in 10s)
    private fun task2() {
        viewModelScope.launch {
            log("Task 2 bắt đầu chạy")
            showLoading()
            delay(10000)//gia lap chay trong 5s
            log("Task 2 hoàn thành")
            hideLoading()
        }
    }

    //task 3(run in 15s)
    private fun task3() {
        viewModelScope.launch {
            log("Task 3 bắt đầu chạy")
            showLoading()
            delay(15000)//gia lap chay trong 5s
            log("Task 3 hoàn thành")
            hideLoading()
        }
    }

    //run all task
    fun allTask() {
        task1()
        task2()
        task3()
    }

    //show loading
    fun showLoading() {
        viewModelScope.launch {
            mutex.withLock {
                counterLoading++
                _isLoading.value = counterLoading > 0
            }
        }
    }

    //hide loading
    fun hideLoading() {
        viewModelScope.launch {
            mutex.withLock {
                counterLoading--
                _isLoading.value = counterLoading > 0
            }
        }
    }

    private fun log(msg: String) {
        Log.e("hoamz", msg)
    }

    fun work() {
        viewModelScope.launch(Dispatchers.Default) {
            val jobs = List(100){
                launch(Dispatchers.IO) {
                    repeat(100){
                       mutex.withLock {
                           counterLoading++
                       }
                    }
                }
            }
            jobs.joinAll()
            withContext(Dispatchers.Main){
                log("$counterLoading")
            }
        }
    }

}