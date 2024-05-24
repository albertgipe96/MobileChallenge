package com.example.mobilechallenge.cabifystore.data.remote.dataSource

import com.example.mobilechallenge.R
import com.example.mobilechallenge.cabifystore.domain.model.Offer
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import javax.inject.Inject

interface RemoteConfigProvider {
    fun init()
    fun getString(key: String): String
}

class RemoteConfigProviderImpl @Inject constructor(

) : RemoteConfigProvider {

    private val instance: FirebaseRemoteConfig = Firebase.remoteConfig

    override fun init() {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600 // Set 3 hours of cache
        }
        instance.setConfigSettingsAsync(configSettings)
        instance.setDefaultsAsync(R.xml.remote_config_defaults)
        instance.fetchAndActivate()
            .addOnCompleteListener { task ->
                // Handle the updated status if needed
                if (task.isSuccessful) {
                    val updated = task.result
                    Timber.d("Remote Config fetch and activate success - params updated: $updated")
                } else {
                    Timber.d("Remote Config fetch failed")
                }
            }
    }

    override fun getString(key: String): String {
        return instance.getString(key)
    }
}