package com.hoamz.a21_5.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.hoamz.a21_5.databinding.FragmentRewardScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RewardScreen : Fragment() {
    private val CLASS_NAME = javaClass.simpleName
    private var _binding: FragmentRewardScreenBinding? = null
    private val binding
        get() = _binding!!
    private var mRewardAd: RewardedAd? = null

    private var isUserWaitingForAd : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
        loadRewardAd()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRewardScreenBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnShowAdsMod.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    showLoading()
                    delay(1000)
                    showRewardAd()
                }
            }
        }
    }


    private fun loadRewardAd() {
        val adRequest = AdRequest.Builder().build()
        val adUnitId = "ca-app-pub-3940256099942544/5224354917"
        RewardedAd.load(requireContext(), adUnitId, adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
                mRewardAd = null
                if(isUserWaitingForAd){
                    isUserWaitingForAd = false
                    hideLoading()
                    Toast.makeText(requireContext(),"Something wrong", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onAdLoaded(p0: RewardedAd) {
                super.onAdLoaded(p0)
                mRewardAd = p0
                Log.e(CLASS_NAME, "loaded success")
                setUpCallBack()
                if(isUserWaitingForAd){
                    isUserWaitingForAd = false
                    hideLoading()
                    showRewardAd()
                }
            }
        })
    }

    private fun setUpCallBack() {
        mRewardAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                super.onAdClicked()
                Log.d(CLASS_NAME, "clicked")
            }

            override fun onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent()
            }

            //user tat quang cao
            override fun onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent()
                mRewardAd = null
                Log.d(CLASS_NAME, "user back to application")
                loadRewardAd()
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                super.onAdFailedToShowFullScreenContent(p0)
                mRewardAd = null
                loadRewardAd()
            }
        }
    }

    private fun showRewardAd() {
        if (mRewardAd != null) {
            hideLoading()
            mRewardAd?.show(requireActivity()) { rewardItem ->
                val rewardAmount = rewardItem.amount//so luong phan thuong
                val rewardType = rewardItem.type//loai phan thuong

                Log.d(CLASS_NAME, "$rewardType $rewardAmount")
                //lm gi do vs phan thuong o day
            }
        } else {
            isUserWaitingForAd = true
        }
    }

    private fun showLoading(){
        binding.partialProgressbar.root.visibility = View.VISIBLE
    }

    private fun hideLoading(){
        binding.partialProgressbar.root.visibility = View.INVISIBLE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}