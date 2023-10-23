package com.vynguyen.toeicvocabularywords.utils

import android.content.Context
import android.media.MediaPlayer
import com.vynguyen.toeicvocabularywords.R

object SoundHelper {

    private var mediaPlayer: MediaPlayer? = null

    fun playCorrectSound(context: Context) {
        if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
            mediaPlayer!!.stop()
        }
        mediaPlayer = MediaPlayer.create(context, R.raw.correct_answer)
        mediaPlayer!!.start()
    }

    fun playWrongSound(context: Context) {
        if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
            mediaPlayer!!.stop()
        }
        mediaPlayer = MediaPlayer.create(context, R.raw.wrong_answer)
        mediaPlayer!!.start()
    }
}