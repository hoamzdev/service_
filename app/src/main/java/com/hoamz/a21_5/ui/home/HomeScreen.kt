package com.hoamz.a21_5.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.hoamz.a21_5.R
import com.hoamz.a21_5.databinding.FragmentHomeScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeScreen : Fragment() {

    private val CLASS_NAME = javaClass.simpleName
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!

    private var mInterstitialAd: InterstitialAd? = null

    private var isUserWaitingForAd: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
        loadInterstitialAd()
    }


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

        loadBannerAds()

        binding.apply {
            //show interstitial ads
            btnShowAdsMod.setOnClickListener {
                showInterstitialAds()
            }
            //nav to rewardScreen
            btnNavToReward.setOnClickListener {
                findNavController().navigate(R.id.action_homeScreen_to_rewardScreen)
            }
        }
    }

    private fun loadBannerAds() {
        val requestBannerAds = AdRequest.Builder().build()
        binding.adViewBanner.loadAd(requestBannerAds)
        binding.adViewBanner.adListener = object : AdListener(){
            override fun onAdClicked() {
                super.onAdClicked()
                Log.d(CLASS_NAME,"clicked banner ads")
            }

            override fun onAdOpened() {
                super.onAdOpened()
                Log.d(CLASS_NAME,"opened banner ads")
            }

            override fun onAdClosed() {
                super.onAdClosed()
                Log.d(CLASS_NAME,"closed banner ads")
            }
        }
    }

    private fun loadInterstitialAd() {
        val requestAd = AdRequest.Builder().build()
        val adUnitId = "ca-app-pub-3940256099942544/1033173712"
        InterstitialAd.load(
            requireContext(), adUnitId, requestAd, object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    super.onAdLoaded(interstitialAd)
                    mInterstitialAd = interstitialAd
                    setUpInterstitialAdCallback()
                    if (isUserWaitingForAd) {
                        isUserWaitingForAd = false
                        showInterstitialAds()
                    }
                }

                override fun onAdFailedToLoad(interstitialAd: LoadAdError) {
                    super.onAdFailedToLoad(interstitialAd)
                    if (isUserWaitingForAd) {
                        isUserWaitingForAd = false
                        hideProgressBarLoadingAds()
                    }
                }
            })
    }

    private fun setUpInterstitialAdCallback() {
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                super.onAdClicked()
                Log.d(CLASS_NAME, "user clicked ad")
            }

            override fun onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent()
                Log.d(CLASS_NAME, "user clicked x")
                loadInterstitialAd()
            }
        }
    }

    fun showInterstitialAds() {
        if (mInterstitialAd != null) {
            viewLifecycleOwner.lifecycleScope.launch {
                showProgressBarLoadingAds()
                delay(800)
                hideProgressBarLoadingAds()
                mInterstitialAd?.show(requireActivity())
            }
        } else {
            isUserWaitingForAd = true
            showProgressBarLoadingAds()
        }
    }

    private fun showProgressBarLoadingAds() {
        binding.partialProgressbar.root.visibility = View.VISIBLE
    }

    private fun hideProgressBarLoadingAds() {
        binding.partialProgressbar.root.visibility = View.INVISIBLE
    }

    override fun onResume() {
        super.onResume()
        binding.adViewBanner.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.adViewBanner.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.adViewBanner.destroy()
        _binding = null
    }




}