package com.will.beatbox.viewmodel

import android.util.Log
import android.view.View
import android.widget.Button
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.will.beatbox.Sound

class SoundViewModel {
    val title: MutableLiveData<String?> = MutableLiveData<String?>()
    // 声明为 var，应该就直接有 set 方法了
    var sound: Sound? = null
        set(sound) {
            field = sound
            title.postValue(sound?.name)
        }

    // 注解在 Android 数据绑定库中扮演着重要角色。
    // @get 的意思是这个注解用于标记属性的 getter 方法，
    // bindable 表示该属性可以在数据绑定中被观察和通知变化。
//    @get:Bindable
//    val title: String?
//        get() = sound?.name
    // // 无需初始化：
    //    // 因为这是一种计算属性，它的值是基于其他属性（在这个例子中是 sound?.name）动态计算的。
    //    // 延迟求值：title 的值只有在被访问时才会计算，这是一种延迟求值的形式。


    // 可以使用 dateBinding 设置监听事件
    fun onButtonClick() {
        Log.e("WillWolf", "onButtonClick-->")
    }

    // 配合下面的 bindingAdapter 使用
    val isGone: Boolean
        get() {return false}
}


object MyBindAdapter {
    // 通常将 BindingAdapter 的方法放到 一个 类中，然后加 JvmStatic 注解
    // xml 布局中使用 dateBinding 相关属性，设定值的时候，必须用 @{} 的方式
    @JvmStatic
    @BindingAdapter("app:isGone")
    fun bindIsGone(button: Button, isGone: Boolean) {
        Log.e("WillWolf", "bindIsGone" + isGone)
        button.visibility = if (isGone) View.GONE else View.VISIBLE
    }
}
