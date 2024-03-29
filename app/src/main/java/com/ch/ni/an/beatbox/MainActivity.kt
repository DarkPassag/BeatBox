package com.ch.ni.an.beatbox

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ch.ni.an.beatbox.databinding.ActivityMainBinding
import com.ch.ni.an.beatbox.databinding.ListItemSoundBinding

class MainActivity : AppCompatActivity() {

    private lateinit var beatBox: BeatBox

    override fun onCreate(savedInstanceState :Bundle?) {
        super.onCreate(savedInstanceState)



        beatBox = BeatBox(assets)


        val viewModel: SoundViewModel = SoundViewModel(beatBox)


       val binding : ActivityMainBinding = DataBindingUtil
           .setContentView(
               this, R.layout.activity_main
           )



        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 3)
            adapter = SoundAdapter(beatBox.sounds)
        }
    }

    override fun onDestroy() {
        beatBox.release()
        super.onDestroy()
    }

    private inner class SoundHolder(
        private val binding: ListItemSoundBinding
    ): RecyclerView.ViewHolder(binding.root){
        init {
            binding.viewModel = SoundViewModel(
                beatBox
            )
        }

        fun bind(sound :Sound){
            binding.apply {
                viewModel?.sound = sound
                executePendingBindings()
            }
        }
    }

    private inner class SoundAdapter(
        private val sounds: List<Sound>
    ): RecyclerView.Adapter<SoundHolder>(){
        override fun onCreateViewHolder(parent :ViewGroup, viewType :Int) :SoundHolder {
            val binding = DataBindingUtil.inflate<ListItemSoundBinding>(
                layoutInflater,
                R.layout.list_item_sound,
                parent,
                false
            )
            return SoundHolder(binding)
        }

        override fun onBindViewHolder(holder :SoundHolder, position :Int){
            val sound = sounds[position]
            holder.bind(sound)
        }

        override fun getItemCount() :Int = sounds.size


    }
}