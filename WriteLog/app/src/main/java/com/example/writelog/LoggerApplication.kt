package com.example.writelog

import android.app.Application
import com.example.writelog.logger.Logger

class LoggerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Logger.writeToLogFile(value = true).build(this).start()
    }


}