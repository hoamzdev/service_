package com.hoamz.a21_5.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hoamz.a21_5.databinding.FragmentHomeScreenBinding


class HomeScreen : Fragment() {

    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        homeViewModel.allTask()
        homeViewModel.work()
        onBindViewModel()
    }


    //collect data from vm
    private fun onBindViewModel() {
        homeViewModel.isLoading.observe(requireActivity()){ state ->
            if(state){
                log("Loading đang hiển thị")
            }
            else{
                log("Loading đã bị ẩn")
            }
        }
    }

    fun log(msg : String){
        Log.e("hoamz",msg)
    }

}