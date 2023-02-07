package com.example.multilanguages.LocaleManager

import androidx.annotation.StringDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Retention(RetentionPolicy.SOURCE)
@StringDef(*[LanguageVariant.GERMAN, LanguageVariant.CHINESE, LanguageVariant.CZECH, LanguageVariant.DUTCH, LanguageVariant.FRENCH, LanguageVariant.ITALIAN, LanguageVariant.JAPANESE, LanguageVariant.KOREAN, LanguageVariant.POLISH, LanguageVariant.RUSSIAN, LanguageVariant.SPANISH, LanguageVariant.ARABIC, LanguageVariant.BULGARIAN, LanguageVariant.CATALAN, LanguageVariant.CROATIAN, LanguageVariant.DANISH, LanguageVariant.FINNISH, LanguageVariant.GREEK, LanguageVariant.HEBREW, LanguageVariant.HINDI, LanguageVariant.HUNGARIAN, LanguageVariant.INDONESIAN, LanguageVariant.LATVIAN, LanguageVariant.LITHUANIAN, LanguageVariant.NORWEGIAN, LanguageVariant.PORTUGUESE, LanguageVariant.ROMANIAN, LanguageVariant.SERBIAN, LanguageVariant.SLOVAK, LanguageVariant.SLOVENIAN, LanguageVariant.SWEDISH, LanguageVariant.TAGALOG, LanguageVariant.THAI, LanguageVariant.TURKISH, LanguageVariant.UKRAINIAN, LanguageVariant.VIETNAMESE, LanguageVariant.SYSTEM, LanguageVariant.ENGLISH])
annotation class Language

object LanguageVariant {
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