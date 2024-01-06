package com.radha.bhaktiguru

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.unity3d.ads.IUnityAdsLoadListener
import com.unity3d.ads.IUnityAdsShowListener
import com.unity3d.ads.UnityAds
import com.unity3d.ads.UnityAds.UnityAdsLoadError
import com.unity3d.ads.UnityAds.UnityAdsShowCompletionState
import com.unity3d.ads.UnityAds.UnityAdsShowError
import com.unity3d.ads.UnityAdsShowOptions

class ShowInterstitialAd : AppCompatActivity() {
    private var btn: TextView? = null
    private val int_ad_id = "Rewarded_Android"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_interstitial_ad)
        btn = findViewById<View>(R.id.btn) as TextView
        loadInt()
        btn!!.setOnClickListener { view: View? ->
            UnityAds.show(this@ShowInterstitialAd, int_ad_id,
                UnityAdsShowOptions(), object : IUnityAdsShowListener {
                    override fun onUnityAdsShowFailure(
                        placementId: String,
                        error: UnityAdsShowError,
                        message: String
                    ) {
                    }

                    override fun onUnityAdsShowStart(placementId: String) {}
                    override fun onUnityAdsShowClick(placementId: String) {}
                    override fun onUnityAdsShowComplete(
                        placementId: String,
                        state: UnityAdsShowCompletionState
                    ) {
                    }
                })
        }
    }

    fun loadInt() {
        UnityAds.load(int_ad_id, object : IUnityAdsLoadListener {
            override fun onUnityAdsAdLoaded(placementId: String) {}
            override fun onUnityAdsFailedToLoad(
                placementId: String,
                error: UnityAdsLoadError,
                message: String
            ) {
            }
        })
    }
}