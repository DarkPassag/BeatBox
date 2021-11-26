package com.ch.ni.an.beatbox


import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import androidx.lifecycle.ViewModel
import java.io.IOException


private const val TAG = "BeatBox"
private const val SOUND_FOLDER = "sample_sounds"
private const val MAX_SOUNDS = 5



fun MutableList<Int>.newId(int :Int){
    if(this.size < MAX_SOUNDS){
        this.add(int)
    } else {
        this.removeAt(0)
    }
}


typealias soundListener = (Float) -> Unit

class BeatBox(
    private val assets: AssetManager
) : ViewModel() {

    /**
     * HomeWork
     * add soundListener - setVolume every sounds
     * add Extensions newId - setMaxSizeList
     * add fun setVolumeSound - set Volume with seekBar
     * some change setVolume = setRate
     * add ViewModel
     * Create viewModelFactory
     */

    val sounds :List<Sound>

    private var volume :Float = 1f

    private var rate: Float = 1f

    private val currentStreamId = mutableListOf<Int>()


    private val soundPool = SoundPool.Builder().setMaxStreams(MAX_SOUNDS).build()

    init {
        sounds = loadSounds()
    }

    val soundListener: soundListener = { someInt ->
        currentStreamId.forEach{
            soundPool.setRate(it, someInt)
        }
    }

    fun play(sound :Sound) {
        sound.soundId?.let {
            val streamId = soundPool.play(it, volume, volume, 1, 0, rate)
            currentStreamId.newId(streamId)
            }
        }


    fun setRate(rate :Float) {
        this.rate = rate
        soundListener(rate)

    }

    fun release() {
        soundPool.release()
    }



   private fun loadSounds() :List<Sound> {
       val soundNames: Array<String>
        try {
            soundNames = assets.list(SOUND_FOLDER)!!
        } catch (e :Exception) {
            Log.e(TAG, "Could not list sounds")
            return emptyList()
        }
       val sounds = mutableListOf<Sound>()
       soundNames.forEach { fileName ->
           val assetPath = "$SOUND_FOLDER/$fileName"
           val sound = Sound(assetPath)
           try {
               load(sound)
               sounds.add(sound)
           } catch (e: IOException){
               Log.e(TAG, "Could not load ${fileName}, ioe")
           }
       }
       return sounds
    }

    private fun load(sound :Sound){
        val afd: AssetFileDescriptor = assets.openFd(sound.assetPath)
        val soundId = soundPool.load(afd, 1)
        sound.soundId = soundId
    }


}