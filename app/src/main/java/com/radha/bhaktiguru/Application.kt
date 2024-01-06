package com.radha.bhaktiguru

import android.app.Application
import com.unity3d.ads.IUnityAdsInitializationListener
import com.unity3d.ads.UnityAds
import com.unity3d.ads.UnityAds.UnityAdsInitializationError

class Application : Application() {

    private val testMode = false
    private val unityGameID = "5506037"

    private val adUnitId = "Interstitial_Android"

    override fun onCreate() {
        super.onCreate()
        UnityAds.initialize(
            applicationContext, unityGameID, testMode,
            object : IUnityAdsInitializationListener {
                override fun onInitializationComplete() {}
                override fun onInitializationFailed(
                    error: UnityAdsInitializationError,
                    message: String
                ) {
                }
            })
    }
}