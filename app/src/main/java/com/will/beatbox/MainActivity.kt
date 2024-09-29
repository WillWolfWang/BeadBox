package com.will.beatbox

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.will.beatbox.databinding.ActivityMainBinding
import com.will.beatbox.databinding.ListItemSoundBinding
import com.will.beatbox.ui.theme.BeatBoxTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 使用 DataBindingUtil 类创建 ActivityMainBinding 对象
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = SoundAdapter()
        }

    }

    // ViewHolder 类，super 类需要使用 view，所以要传 binding.root
    private inner class SoundHolder(private var binding: ListItemSoundBinding): RecyclerView.ViewHolder(binding.root) {

    }

    // RecycleView 的 adapter 类
    private inner class SoundAdapter(): RecyclerView.Adapter<SoundHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
            val binding: ListItemSoundBinding = DataBindingUtil.inflate<ListItemSoundBinding>(layoutInflater, R.layout.list_item_sound, parent, false)
            return SoundHolder(binding)
        }

        override fun getItemCount(): Int {
            return 0
        }

        override fun onBindViewHolder(holder: SoundHolder, position: Int) {

        }

    }

}