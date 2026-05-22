package com.hoamz.a21_5

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.hoamz.a21_5.databinding.FragmentTestBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


class TestFragment : Fragment() {

    private var _binding : FragmentTestBinding? = null
    private val binding get() = _binding!!

    private val testViewModel : TestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View{
        _binding = FragmentTestBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        testViewModel.test()

        testViewModel.isLoading.observe(requireActivity()){ isLoading ->
            Log.e("TEST","$isLoading")
        }
    }

//    fun test() = runBlocking {
//        val mutex = Mutex()
//        var counter = 0
//
//        List(1000){
//            launch(Dispatchers.Default){
//                repeat(1000){
//                    mutex.withLock {
//                        counter++
//                    }
//                }
//            }
//        }.joinAll()
//
//        Log.d("TEST","$counter")
//
//    }

}