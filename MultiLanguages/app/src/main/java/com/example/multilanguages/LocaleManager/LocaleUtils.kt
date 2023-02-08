package com.example.multilanguages.LocaleManager

import android.app.Activity
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Build.VERSION_CODES
import androidx.annotation.NonNull
import java.util.*

object Utils {
    /**
     * Simply compares the given version with the [ ][Build.VERSION.SDK_INT] of the Android System.
     *
     * @param version version to compare.
     *
     * @return `boolean` evaluated 'True' if the current version is the
     * same or higher than the given one. Else, 'false' is returned.
     */
    fun isAtLeastAndroidVersion(version: Int): Boolean {
        return Build.VERSION.SDK_INT >= version
    }

    /**
     * Obtains the current locale being used by the system.
     *
     * @param resources the system resources (obtained from a [Context],
     * for example) from which the locale is obtained.
     *
     * @return `Locale` with the user configuration.
     */
    fun getLocale(@NonNull resources: Resources): Locale {
        val config = resources.configuration
        return if (isAtLeastAndroidVersion(VERSION_CODES.N)) config.locales[0] else config.locale
    }

    /**
     * Obtains the Android system locale, even if the user has changed the
     * application one.
     *
     * @param config configuration from which the system locale will be
     * obtained.
     *
     * @return `Locale` with the system language.
     */
    fun getSystemLocale(@NonNull config: Configuration): Locale {
        return if (isAtLeastAndroidVersion(VERSION_CODES.N)) config.locales[0] else config.locale
    }

    /**
     * Updates the string resource identifier (in the package's resources) of
     * the activity's label.
     *
     * @param activity the source activity from which the title will be changed
     * - it cannot be `null` and it must exist.
     *
     * @see PackageManager.getActivityInfo
     */
    fun resetActivityTitle(@NonNull activity: Activity) {
        try {
            val info = activity.packageManager
                .getActivityInfo(activity.componentName, PackageManager.GET_META_DATA)
            if (info.labelRes != 0) {
                activity.setTitle(info.labelRes)
            }
        } catch (ignored: PackageManager.NameNotFoundException) {
            // We do not handle this error as it should never happen - we are
            // getting the componentName from the proper activity, so the
            // package with that given name must exists
        }
    }
}