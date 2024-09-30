package com.will.beatbox.viewmodel

import android.content.res.AssetManager
import android.util.Log
import android.widget.SeekBar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.will.beatbox.BeatBox

class MainViewModel(val assets: AssetManager) : ViewModel() {

    val beatBox = BeatBox(assets)
    fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
//        Log.e("WillWolf", "progress-->" + progress)

        val rate = 1 +  (progress.toFloat() / 100)
        beatBox.updateRate(rate)
    }

    override fun onCleared() {
        super.onCleared()
        beatBox.release()
    }

    class MainViewModeFactory(val assets: AssetManager): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(assets) as T
            }
            return super.create(modelClass)
        }
    }
}