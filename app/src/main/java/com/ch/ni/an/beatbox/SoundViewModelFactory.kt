package com.ch.ni.an.beatbox

import android.content.res.AssetManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class SoundViewModelFactory(
    private val assets: AssetManager
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass :Class<T>) :T {
        if(modelClass.isAssignableFrom(BeatBox::class.java)) {
            return BeatBox(assets) as T
        } else {
            throw IllegalArgumentException(
                "Unknowing view model"
            )
        }
    }

}