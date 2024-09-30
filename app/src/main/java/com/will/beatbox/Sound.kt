package com.will.beatbox

// 管理声音资源文件名、用户可见文件名
private const val WAV = ".wav"
class Sound(val assetPath: String, var soundId: Int? = null) {
    // 为了有效显示声音文件名，先使用 split 和 last 函数分离出文件名
    // 再用 removeSuffix 删除后缀
    val name = assetPath.split("/").last().removeSuffix(WAV)
}