package mx.cristobal.aichatcourseapp.utils

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private object PreferenceKeys {
    const val SHOW_TABBAR_VIEW = "showTabbarView"
}

/**
 * Extension property to directly read and write `showTabbarView` on a [SharedPreferences] instance.
 *
 * Usage:
 * ```
 * val isShown = sharedPreferences.showTabbarView
 * sharedPreferences.showTabbarView = true
 * ```
 */
var SharedPreferences.showTabbarView: Boolean
    get() = getBoolean(PreferenceKeys.SHOW_TABBAR_VIEW, false)
    set(value) = edit { putBoolean(PreferenceKeys.SHOW_TABBAR_VIEW, value) }

/**
 * Generic property delegate helper for boolean preferences.
 *
 * Usage:
 * ```
 * var showTabbarView by sharedPreferences.boolean("showTabbarView", false)
 * ```
 */
fun SharedPreferences.boolean(
    key: String,
    defaultValue: Boolean = false
): ReadWriteProperty<Any?, Boolean> = object : ReadWriteProperty<Any?, Boolean> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean =
        getBoolean(key, defaultValue)

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
        edit { putBoolean(key, value) }
    }
}
