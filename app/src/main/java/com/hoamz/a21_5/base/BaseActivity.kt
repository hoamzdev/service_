package com.hoamz.a21_5.base

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay

/**
 * @author hwa..
 */
abstract class BaseActivity<VB : ViewBinding>(inflate: (LayoutInflater) -> VB) :
    AppCompatActivity() {

    protected val binding by lazy { inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setupOnBackPress()
        initView()
        bindView()
        bindViewModel()
    }

    open fun initView() {}
    open fun bindView() {}
    open fun bindViewModel() {}

    private fun setupOnBackPress() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    open fun onFinish() {
        finish()
    }

    open fun baseDelay(duration : Long, block :() -> Unit){
        lifecycleScope.launch {
            delay(duration)
            block()
        }
    }

}