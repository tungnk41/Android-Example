package com.example.writelog.logger

import android.content.Context
import com.example.writelog.R
import timber.log.Timber
import java.io.File
import java.lang.ref.WeakReference

object Logger {
    private var mTag: String = ">>>>>DEBUG>>>>>"
    private var mPriority: String = "V"
    private val logFileName = "log.txt"
    private val maxFileSize = 10 * 1024 * 1024
    private var mIsWriteToLogFile: Boolean = false
    private var mProcess: Process? = null
    private lateinit var mContext: WeakReference<Context>


    fun setTag(tag: String): Logger {
        mTag = tag
        return this
    }

    fun setPriority(priority: String): Logger {
        mPriority = priority
        return this
    }

    fun writeToLogFile(value: Boolean): Logger {
        mIsWriteToLogFile = value
        return this
    }

    fun build(context: Context) : Logger {
        mContext = WeakReference(context)
        mContext.get()?.let { context ->
            val applicationName = context.resources.getString(R.string.app_name)
            Timber.plant(object : Timber.DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    super.log(priority, mTag, "$tag :  $message", t)
                }

                override fun createStackElementTag(element: StackTraceElement): String {
                    return String.format(
                        "%s:%s:%s",
                        applicationName,
                        super.createStackElementTag(element),
                        element.methodName
                    )
                }
            })
            clearLogFileIfNeed()
        }

        return this
    }

    fun start() {
        mContext.get()?.let { context ->
            if (mIsWriteToLogFile) {
                val file = File(context.externalCacheDir, logFileName)
                if (!file.exists()) file.createNewFile()

                var cmdEnd = " *:E"
                val newEntry = " $mTag:$mPriority"
                if (!cmdEnd.contains(newEntry)) {
                    cmdEnd = newEntry + cmdEnd
                }
                Runtime.getRuntime().exec("logcat -b all -c") //clear All Logcat and Buffer
                val command = "logcat -f " + file.absolutePath + cmdEnd
                mProcess = Runtime.getRuntime().exec(command)
            }
        }
    }

    fun clearLogFileIfNeed() {
        mContext.get()?.let { context ->
            val files: Array<File> = context.externalCacheDir?.listFiles() ?: arrayOf()
            for (file in files) {
                if(file.name.equals(logFileName)){
                    var size: Long = file.length()
                    if(size >= maxFileSize){
                        file.delete()
                    }
                }
            }
        }
    }
}