package com.vynguyen.toeicvocabularywords.utils

import android.content.Context
import android.media.MediaPlayer
import com.vynguyen.toeicvocabularywords.R

object SoundHelper {

    private var mediaPlayer: MediaPlayer? = null

    fun playCorrectSound(context: Context) {
        stopSound()
        mediaPlayer = MediaPlayer.create(context, R.raw.correct_answer)
        mediaPlayer!!.start()
    }

    fun playWrongSound(context: Context) {
        stopSound()
        mediaPlayer = MediaPlayer.create(context, R.raw.wrong_answer)
        mediaPlayer!!.start()
    }

    fun playClapSound(context: Context) {
        stopSound()
        mediaPlayer = MediaPlayer.create(context, R.raw.claps)
        mediaPlayer!!.start()
    }

    fun playSound(context: Context, soundRes: Int) {
        stopSound()
        mediaPlayer = MediaPlayer.create(context, soundRes)
        mediaPlayer!!.start()
    }

    fun stopSound() {
        if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
            mediaPlayer!!.stop()
        }
    }
}