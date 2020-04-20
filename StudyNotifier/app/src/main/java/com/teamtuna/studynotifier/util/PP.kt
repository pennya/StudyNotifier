package com.teamtuna.studynotifier.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import java.util.UUID.randomUUID

enum class PP {
    IS_FIRST_RUN, // 앱 최초 실행 여부
    PUSH_TOKEN,
    IS_READ_PUSH,
    ACCESS_TOKEN,
    REFRESH_TOKEN,
    UUID,
    ;

    companion object {
        private lateinit var PREFERENCES: SharedPreferences

        private val DEFVALUE_STRING = ""
        private val DEFVALUE_FLOAT = -1f
        private val DEFVALUE_INT = -1
        private val DEFVALUE_LONG = -1L
        private val DEFVALUE_BOOLEAN = false

        val uuid get() = UUID.get(randomUUID().toString())

        fun CREATE(context: Context) {
            PREFERENCES = PreferenceManager.getDefaultSharedPreferences(context)
        }

        //실재값에 변화가 있을때만 event가 날라온다
        fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
            PREFERENCES.registerOnSharedPreferenceChangeListener(listener)
        }

        fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
            PREFERENCES.unregisterOnSharedPreferenceChangeListener(listener)
        }

        fun clear() = PREFERENCES.edit().clear().commit()
    }

    //@formatter:off
    fun getBoolean   (defValues: Boolean      = DEFVALUE_BOOLEAN ) =  PREFERENCES.getBoolean  (name, defValues)
    fun getInt       (defValues: Int          = DEFVALUE_INT     ) =  PREFERENCES.getInt      (name, defValues)
    fun getLong      (defValues: Long         = DEFVALUE_LONG    ) =  PREFERENCES.getLong     (name, defValues)
    fun getFloat     (defValues: Float        = DEFVALUE_FLOAT   ) =  PREFERENCES.getFloat    (name, defValues)
    fun getString    (defValues: String       = DEFVALUE_STRING  ) =  PREFERENCES.getString   (name, defValues)
    fun getStringSet (defValues: Set<String>? = null             ) =  PREFERENCES.getStringSet(name, defValues)

    fun set(v: Boolean     ) = PREFERENCES.edit().putBoolean    (name, v).commit()
    fun set(v: Int         ) = PREFERENCES.edit().putInt        (name, v).commit()
    fun set(v: Long        ) = PREFERENCES.edit().putLong       (name, v).commit()
    fun set(v: Float       ) = PREFERENCES.edit().putFloat      (name, v).commit()
    fun set(v: String     ?) = PREFERENCES.edit().putString     (name, v).commit()
    fun set(v: Set<String>?) = PREFERENCES.edit().putStringSet  (name, v).commit()

    fun toggle() = set(!getBoolean())
    fun get(defValue: String = DEFVALUE_STRING) = getString(defValue)
    fun isit(defValue: Boolean = DEFVALUE_BOOLEAN) = getBoolean(defValue)

    fun contain() = PREFERENCES.contains(name)
    fun remove() = PREFERENCES.edit().remove(name).commit()

    fun first(): Boolean {
        val result = getBoolean(false)
        set(true)
        return result
    }
}