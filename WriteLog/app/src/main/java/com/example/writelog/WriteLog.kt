package com.example.writelog

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


/**
 * This singleton class is for debug purposes only. Use it to log your selected classes into file. <br></br> Needed permissions:
 * READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, READ_LOGS" <br></br><br></br>Example usage:<br></br> ` FileLogHelper.getInstance().addLogTag(TAG);`
 *
 *
 * Created by bendaf on 2016-04-28
 */
class FileLogHelper private constructor() {
    private var logFileAbsolutePath: String? = null
    private var cmdEnd = " *:F"
    private var isLogStarted = false
    fun initLog(context: Context) {
        if (!isLogStarted && shouldLog) {
            val dF = SimpleDateFormat("yy-MM-dd_HH_mm''ss", Locale.getDefault())
            val fileName = "logcat_" + dF.format(Date()) + ".txt"
            val outputFile =
                File(context.getExternalFilesDir("Logs"),fileName)
            if (outputFile.mkdirs() || outputFile.isDirectory) {
                logFileAbsolutePath = outputFile.absolutePath + "/" + fileName
                startLog()
            }
        }
    }

    private fun startLog() {
        if (shouldLog) {
            try {
                val prevLogFile = File(logFileAbsolutePath)
                prevLogFile.delete()
                Runtime.getRuntime().exec(cmdBegin + logFileAbsolutePath + cmdEnd)
                isLogStarted = true
            } catch (ignored: IOException) {
                Log.e(TAG, "initLogCat: failed")
            }
        }
    }
    /**
     * Add a new tag to file log.
     *
     * @param tag      The android [Log] tag, which should be logged into the file.
     * @param priority The priority which should be logged into the file. Can be V, D, I, W, E, F
     *
     * @see [Filtering Log Output](http://developer.android.com/tools/debugging/debugging-log.html.filteringOutput)
     */
    /**
     * Add a new tag to file log with default priority, which is Verbose.
     *
     * @param tag The android [Log] tag, which should be logged into the file.
     */
    @JvmOverloads
    fun addLogTag(tag: String, priority: String = "V", context: Context) {
        val newEntry = " $tag:$priority"
        if (!cmdEnd.contains(newEntry)) {
            cmdEnd = newEntry + cmdEnd
            if (isLogStarted) {
                startLog()
            } else {
                initLog(context)
            }
        }
    }

    companion object {
        private const val cmdBegin = "logcat -f "
        private const val shouldLog = true //TODO: set to false in final version of the app
        private const val TAG = "FileLogHelper"
        private var mInstance: FileLogHelper? = null
        val instance: FileLogHelper?
            get() {
                if (mInstance == null) {
                    mInstance = FileLogHelper()
                }
                return mInstance
            }
    }
}