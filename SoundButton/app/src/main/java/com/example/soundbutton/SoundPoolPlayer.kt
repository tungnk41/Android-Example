package com.example.soundbutton

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log
import java.lang.Exception
import java.lang.ref.WeakReference

object SoundPoolPlayer {
    private var mSoundPool: SoundPool? = null
    private var mStreamId: Int? = null
    private var mSoundId: Int? = null
    private var mMaxStream: Int = 10
    private var mIsLoadedAudio: Boolean = false
    private var mAttrs = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_MEDIA)
        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
        .build()
    private var mContext: WeakReference<Context>? = null

    fun setSoundId(soundId : Int): SoundPoolPlayer {
        this.mSoundId = soundId
        return this
    }

    fun setMaxStream(maxStream : Int): SoundPoolPlayer {
        this.mMaxStream = maxStream
        return this
    }

    fun setAttrs(attrs : AudioAttributes): SoundPoolPlayer {
        this.mAttrs = attrs
        return this
    }
    fun build(context: Context) {
        mContext = WeakReference<Context>(context)
        mSoundPool = SoundPool.Builder().setMaxStreams(mMaxStream).setAudioAttributes(mAttrs).build()

        if(mSoundPool == null || mSoundId == null || mContext == null) return
        mStreamId = mSoundPool!!.load(mContext!!.get(), mSoundId!!,1)
        mIsLoadedAudio = true
    }

    fun play() {
        if(mSoundPool == null || mSoundId == null || mContext == null) return
        try {
            if (!mIsLoadedAudio) {
                mStreamId = mSoundPool!!.load(mContext!!.get(), mSoundId!!,1)
            }
           mSoundPool!!.play(mStreamId!!, 1f, 1f, 1,0,1f)
        }
        catch (e : Exception) {
            e.printStackTrace()
        }
    }

    fun stop() {
        if(mSoundPool == null || mStreamId == null ) return
        try {
            mSoundPool!!.stop(mStreamId!!)
        }
        catch (e : Exception) {
            e.printStackTrace()
        }
    }

    fun release() {
        mSoundPool?.release()
        mSoundPool = null
        mIsLoadedAudio = false
        mContext = null
    }
}