package `in`.ponshere.toggler.v2.provider

import `in`.ponshere.toggler.v2.toggles.Toggle
import android.content.Context
import java.lang.reflect.Method

abstract class ToggleValueProvider  {
    abstract val name: String
    abstract val isSaveAllowed: Boolean

    abstract fun init(context: Context)
    abstract fun isSupported(toggle: Toggle<*>): Boolean
    abstract fun<T> get(key: String, defaultValue: T, clazz: Class<T>, method: Method) : T
    abstract fun<T> save(key: String, value: T, clazz: Class<T>)
    abstract fun configurationMap(toggle: Toggle<*>) : Map<String, String>

}