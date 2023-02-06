package com.example.multilanguages.LocaleManager

import androidx.annotation.StringDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.ARABIC
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.BULGARIAN
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.CATALAN
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.CHINESE
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.CROATIAN
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.CZECH
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.DANISH
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.DUTCH
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.ENGLISH
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.FINNISH
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.FRENCH
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.GERMAN
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.GREEK
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.HEBREW
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.HINDI
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.HUNGARIAN
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.INDONESIAN
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.ITALIAN
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.JAPANESE
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.KOREAN
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.LATVIAN
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.LITHUANIAN
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.NORWEGIAN
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.POLISH
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.PORTUGUESE
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.ROMANIAN
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.RUSSIAN
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.SERBIAN
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.SLOVAK
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.SLOVENIAN
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.SPANISH
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.SWEDISH
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.SYSTEM
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.TAGALOG
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.THAI
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.TURKISH
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.UKRAINIAN
import com.example.multilanguages.LocaleManager.LanguageSupport.Language.Companion.VIETNAMESE


abstract class LanguageSupport {

    /*
        Available languages
     */
    @Retention(RetentionPolicy.SOURCE)
    @StringDef(*[GERMAN, CHINESE, CZECH, DUTCH, FRENCH, ITALIAN, JAPANESE, KOREAN, POLISH, RUSSIAN, SPANISH, ARABIC, BULGARIAN, CATALAN, CROATIAN, DANISH, FINNISH, GREEK, HEBREW, HINDI, HUNGARIAN, INDONESIAN, LATVIAN, LITHUANIAN, NORWEGIAN, PORTUGUESE, ROMANIAN, SERBIAN, SLOVAK, SLOVENIAN, SWEDISH, TAGALOG, THAI, TURKISH, UKRAINIAN, VIETNAMESE, SYSTEM, ENGLISH])
    /*
        Interface for accessing available languages
     */
    annotation class Language {
        companion object {
            const val SYSTEM = "sys"
            const val ENGLISH = "en"
            const val GERMAN = "de"
            const val CHINESE = "zh"
            const val CZECH = "cs"
            const val DUTCH = "nl"
            const val FRENCH = "fr"
            const val ITALIAN = "it"
            const val JAPANESE = "ja"
            const val KOREAN = "ko"
            const val POLISH = "pl"
            const val RUSSIAN = "ru"
            const val SPANISH = "es"
            const val ARABIC = "ar"
            const val BULGARIAN = "bg"
            const val CATALAN = "ca"
            const val CROATIAN = "hr"
            const val DANISH = "da"
            const val FINNISH = "fi"
            const val GREEK = "el"
            const val HEBREW = "iw"
            const val HINDI = "hi"
            const val HUNGARIAN = "hu"
            const val INDONESIAN = "in"
            const val LATVIAN = "lv"
            const val LITHUANIAN = "lt"
            const val NORWEGIAN = "nb"
            const val PORTUGUESE = "pt"
            const val ROMANIAN = "ro"
            const val SERBIAN = "sr"
            const val SLOVAK = "sk"
            const val SLOVENIAN = "sl"
            const val SWEDISH = "sv"
            const val TAGALOG = "tl"
            const val THAI = "th"
            const val TURKISH = "tr"
            const val UKRAINIAN = "uk"
            const val VIETNAMESE = "vi"
        }
    }
}