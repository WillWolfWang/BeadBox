package com.will.beatbox.viewmodel

import android.os.Looper
import com.will.beatbox.BeatBox
import com.will.beatbox.Sound
import org.hamcrest.core.Is.`is`
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * 对于大多数对象来说，测试就是要创建对象实例
 * 及其依赖的其他对象，为了避免为每一个测试类写重复代码，Junit 提供了 @Before
 * 这个注解。
 * 以 @Before 注解的包含公共代码的函数会在所有测试之前运行一次。按照约定
 * 所有单元测试类都要有一个以 @Before 注解的 setUp() 函数
 */
class SoundViewModelTest {

    private lateinit var beatBox: BeatBox
    private lateinit var sound: Sound
    // 这里用 subject 命名，是测试的一个习惯约定
    // 这样一看就知道，subject 是要测试的对象（与其他对象区别开）
    // 测试其他函数时，可以省去重命名的麻烦
    private lateinit var subject: SoundViewModel
    @Before
    fun setUp() {
//        val looper = mock(Looper::class.java)
//        val method = Looper::class.java.getDeclaredMethod("getMainLooper")
//        method.isAccessible = true
//        method.invoke(null, looper)

        // 使用 mock 静态函数创建模拟的类
        beatBox = mock(BeatBox::class.java)

        sound = Sound("assetPath")
        subject = SoundViewModel(beatBox)
        subject.sound = sound
    }

    @Test
    fun exposesSoundNameAsTitle() {
        assertThat(subject.title.value, `is`(sound.name)) // 这里的 is 是 org.hamcrest.core.Is.`is` 方法
    }

    @Test
    fun callsBeatBoxPlayOnButtonClicked() {
        subject.onButtonClick()

        // verify(beatBox) 是说 我要验证 beatBox 对象的某个函数是否调用类
        verify(beatBox)
        // beatBox.play(sound) 是说，验证这个函数是这样调用的
        beatBox.play(sound)
        // 合起来就是验证以 sound 作为参数，调用了 beatBox 对象的 play 函数
    }
}