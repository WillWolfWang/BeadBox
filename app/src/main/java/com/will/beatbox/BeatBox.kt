package com.will.beatbox

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log

private const val TAG = "BeatBox"
private const val SOUNDS_FOLDER = "sample_sounds"

private const val MAX_SOUNDS = 5
// AssetManager 类可以访问 assets 下的资源
class BeatBox(private val assets: AssetManager) {
    val sounds: List<Sound?>
    //
    val playSoundSet = hashSetOf<Int>()
    private val soundPool: SoundPool = SoundPool.Builder()
        .setMaxStreams(MAX_SOUNDS)// 指定某个时刻同时播放多少个音频，如果尝试播放第 6 个，SoundPool 会停止播放最早播放的那个
        .build()
    // init 方法位于主构造器之后，从构造器之前执行， 创建了对象后， init 函数就执行了
    init {
        sounds = loadSound()
        soundPool.setOnLoadCompleteListener { soundPool, sampleId, status ->
            if (playSoundSet.contains(sampleId)) {
                playSoundSet.remove(sampleId)
            }
        }
    }

    // 将 assets 下的文件全都加到缓存集合中
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
                // 将音频加载到 SoundPool 中
                load(sound)
                sounds.add(sound)
            }
            return sounds
        }
    }
    // 加载音乐到 SoundPool 中
    private fun load(sound: Sound) {
        val afd: AssetFileDescriptor = assets.openFd(sound.assetPath)
        // load 函数可以把音频文件载入 SoundPool 待播，为了方便管理，重播卸载音频文件，load 函数返回一个 int 型的 id
        val soundId = soundPool.load(afd, 1)
        sound.soundId = soundId
    }

    var soundId: Int? = null
    // 播放音频
    fun play(sound: Sound) {
        sound.soundId?.let {
            Log.e("WillWolf", "soundId-->" + it)
            soundId = it
            soundPool.play(it, 1f, 1f, 1, 0, currentRate)

//            soundPool.setRate(it, 2f)
            if (!playSoundSet.contains(it)) {
                playSoundSet.add(it)
            }
        }
    }

    var currentRate = 1.0f
    fun updateRate(rate: Float) {
        Log.e("WillWolf", "updateRate-->" + soundId + ", " + rate)
        currentRate = rate
        // 播放后，修改 rate 不生效
//        if (soundId != null) {
//            soundPool.setRate(soundId!!, rate)
//        }
    }

    //
    fun release() {
        playSoundSet.forEach {
            Log.e("WillWolf", "release id -->" + it)
            soundPool.stop(it)
        }
        soundPool.release()
    }
}
