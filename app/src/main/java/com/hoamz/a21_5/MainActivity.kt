package com.hoamz.a21_5

import com.hoamz.a21_5.base.BaseActivity
import com.hoamz.a21_5.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay


class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun initView() {
        super.initView()
    }

    override fun bindView() {
        super.bindView()
        baseDelay(2000L) {
            binding.pbLoading.visible()
        }
    }

    override fun bindViewModel() {
        super.bindViewModel()
    }

}