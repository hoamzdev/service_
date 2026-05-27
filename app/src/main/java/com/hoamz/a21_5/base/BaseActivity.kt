package com.hoamz.a21_5.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * @author hwa..
 */
open class BaseMainActivity : AppCompatActivity() {

    open fun initView() {}
    open fun onBindView() {}
    open fun onObserverData() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        onBindView()
        onObserverData()
    }

    fun onToast(message : String){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }

    

}