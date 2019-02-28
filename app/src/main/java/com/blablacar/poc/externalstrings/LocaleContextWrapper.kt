package com.blablacar.poc.externalstrings

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import java.util.*

class LocaleContextWrapper private constructor(base: Context) : ContextWrapper(base) {

    companion object {

        fun create(context: Context, desiredLocale: Locale): LocaleContextWrapper {
            val configuration = context.resources.configuration

            val systemLocale = getLocaleFromConfiguration(configuration)
            if (systemLocale.language != desiredLocale.language || systemLocale.country != desiredLocale.country) {
                configuration.setLocale(desiredLocale)
            }

            val newContext = context.createConfigurationContext(configuration)
            return LocaleContextWrapper(newContext)
        }

        private fun getLocaleFromConfiguration(configuration: Configuration): Locale {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                configuration.locales[0]
            } else {
                configuration.locale
            }
        }

    }

}
