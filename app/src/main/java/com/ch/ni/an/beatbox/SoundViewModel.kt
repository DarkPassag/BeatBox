package com.ch.ni.an.beatbox

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class SoundViewModel(
    private val beatBox :BeatBox
) :BaseObservable() {
    fun onButtonClicked() {
        sound?.let {
            beatBox.play(it)
        }
    }

    fun setSound(volume: Float){
            beatBox.setVolumeSound(volume)
    }

    var sound :Sound? = null
        set(sound) {
            field = sound
            notifyChange()
        }

    @get: Bindable
    val title :String?
        get() = sound?.name



}