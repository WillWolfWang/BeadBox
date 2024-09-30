package com.will.beatbox.viewmodel

import android.util.Log
import android.widget.SeekBar
import com.will.beatbox.BeatBox

class MainViewModel(val beatBox: BeatBox) {
    fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
//        Log.e("WillWolf", "progress-->" + progress)

        val rate = 1 +  (progress.toFloat() / 100)
        beatBox.updateRate(rate)
    }

}