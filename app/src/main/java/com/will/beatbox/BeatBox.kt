package com.will.beatbox

import android.content.res.AssetManager
import android.util.Log

private const val TAG = "BeatBox"
private const val SOUNDS_FOLDER = "sample_sounds"
// AssetManager 类可以访问 assets 下的资源
class BeatBox(private val assets: AssetManager) {


    fun loadSound(): List<String> {
        val soundNames = assets.list(SOUNDS_FOLDER)
        if (soundNames.isNullOrEmpty()) {
            return emptyList()
        } else {
            Log.e("WillWolf", "found ${soundNames.size} sounds")
            return soundNames.asList()
        }
    }
}
