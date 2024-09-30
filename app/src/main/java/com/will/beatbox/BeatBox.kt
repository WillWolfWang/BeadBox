package com.will.beatbox

import android.content.res.AssetManager
import android.util.Log

private const val TAG = "BeatBox"
private const val SOUNDS_FOLDER = "sample_sounds"
// AssetManager 类可以访问 assets 下的资源
class BeatBox(private val assets: AssetManager) {
    val sounds: List<Sound?>

    // init 方法位于主构造器之后，从构造器之前执行， 创建了对象后， init 函数就执行了
    init {
        sounds = loadSound()
    }

    private fun loadSound(): List<Sound?> {
        // 这里传入相对路径
        val soundNames = assets.list(SOUNDS_FOLDER)
        if (soundNames.isNullOrEmpty()) {
            return emptyList()
        } else {
//            Log.e("WillWolf", "found ${soundNames.size} sounds")
            val sounds = mutableListOf<Sound?>()
            // 获取到所有的文件名
            soundNames.sortWith(object : Comparator<String>{
                override fun compare(o1: String?, o2: String?): Int {
                    if (o1?.length!! < o2?.length!!) {
                        return -1
                    }
                    if (o1 > o2) {
                        return 1
                    }
                    return -1
                }
            })
            soundNames.forEach {fileName ->
//                拼装文件路径
                val assetPath = "$SOUNDS_FOLDER/$fileName"
                val sound = Sound(assetPath)
                sounds.add(sound)
            }
            sounds.add(null)
            return sounds
        }
    }
}
