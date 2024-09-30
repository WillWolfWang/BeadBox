package com.will.beatbox.viewmodel

import com.will.beatbox.Sound

class SoundViewModel {
    // 声明为 var，应该就直接有 set 方法了
    var sound: Sound? = null
        set(sound) {
            field = sound
        }

    // 无需初始化：
    // 因为这是一种计算属性，它的值是基于其他属性（在这个例子中是 sound?.name）动态计算的。
    // 延迟求值：title 的值只有在被访问时才会计算，这是一种延迟求值的形式。
    val title: String?
        get() = sound?.name
}