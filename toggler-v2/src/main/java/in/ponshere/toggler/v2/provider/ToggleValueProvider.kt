package `in`.ponshere.toggler.v2.provider

import `in`.ponshere.toggler.v2.toggles.Toggle
import android.content.Context

abstract class ToggleValueProvider  {
    abstract val name: String
    abstract val isSaveAllowed: Boolean

    abstract fun init(context: Context)
    abstract fun isSupported(toggle: Toggle<*>): Boolean

    @Throws(UnsupportedOperationException::class, IllegalArgumentException::class)
    abstract fun<T> getValue(toggle: Toggle<*>, clazz: Class<T>) : T

    abstract fun<T> getDisplayValue(toggle: Toggle<*>, clazz: Class<T>) : CharSequence

    abstract fun<T> save(key: String, value: T, clazz: Class<T>)
    abstract fun configurationMap(toggle: Toggle<*>) : Map<String, CharSequence>
}