package com.will.beatbox

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.will.beatbox.databinding.ActivityMainBinding
import com.will.beatbox.databinding.ListItemSoundBinding
import com.will.beatbox.manager.FileShareManager
import com.will.beatbox.viewmodel.MainViewModel
import com.will.beatbox.viewmodel.SoundViewModel
// 如果继承 ComponentActivity，会发现给 dateBinding 设置 lifecycleOwner 时，会报错
// 需要改用 AppCompatActivity
class MainActivity : AppCompatActivity() {

    private lateinit var beatBox: BeatBox
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 使用 DataBindingUtil 类创建 ActivityMainBinding 对象
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        // 这个要放在 recyclerView apply 的前面
//        beatBox = BeatBox(assets)

        val factory = MainViewModel.MainViewModeFactory(assets)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        beatBox = viewModel.beatBox

        // apply 函数会一起执行
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = SoundAdapter(beatBox.sounds)
        }


        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        }

        onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("WillWolf", "onDestroy-->")
    }

    private fun shareFile() {
        val fileShareManager = FileShareManager(this)
        fileShareManager.createAndShareFile(
            "example.txt",
            "This is the content of the shared file.",
            "text/plain"
        )
    }

    // ViewHolder 类，super 类需要使用 view，所以要传 binding.root
    private inner class SoundHolder(private var binding: ListItemSoundBinding): RecyclerView.ViewHolder(binding.root) {
        // 关联使用视图模型，创建一个 SoundViewModel，把它添加给绑定类
        init {
            binding.viewModel = SoundViewModel(beatBox)
//            Log.e("willWolf", "SoundHolder--init")
        }
        // 绑定方法中更新视图需要的数据
        fun bind(sound: Sound?) {
            binding.apply {
                viewModel?.sound = sound
                // 它的主要作用是立即执行所有待处理的数据绑定操作。
                // 1.主要功能：
                // 强制执行所有待处理的数据绑定更新。
                // 确保视图立即反映最新的数据状态。
                // 2.正常绑定过程：
                //  通常，数据绑定库会在下一个布局通道中更新绑定的视图。
                //  这种延迟更新机制通常能提供良好的性能。
                // 3. 使用 executePendingBindings() 的原因：
                //  当你需要立即更新 UI，而不是等待下一个布局周期时。
                //  在某些情况下，确保视图在特定时刻反映最新数据状态很重要。
                // 4. 常见使用场景：
                //  在 RecyclerView 的 Adapter 中，确保视图在被回收前更新。
                //  在动画开始前，确保视图显示正确的数据。
                //  在进行单元测试时，立即验证绑定结果。
                executePendingBindings()
            }
        }
    }

    // RecycleView 的 adapter 类
    private inner class SoundAdapter(private val sounds: List<Sound?>): RecyclerView.Adapter<SoundHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
            val binding: ListItemSoundBinding = DataBindingUtil.inflate<ListItemSoundBinding>(layoutInflater, R.layout.list_item_sound, parent, false)
            // 使用 liveData 替换 dataBing 的 BaseObservable 时，需要指定 binging 的 lifecycleOwner 对象
            // 只要 title 属性有变化， MainActivity 视图就变化
            binding.lifecycleOwner = this@MainActivity

            return SoundHolder(binding)
        }

        override fun getItemCount(): Int {
            return sounds.size
        }

        override fun onBindViewHolder(holder: SoundHolder, position: Int) {
            val sound = sounds[position]
            holder.bind(sound)
        }

    }

}