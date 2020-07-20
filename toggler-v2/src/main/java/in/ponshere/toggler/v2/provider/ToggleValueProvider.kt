package `in`.ponshere.toggler.v2.provider

import android.content.Context

abstract class ToggleValueProvider  {
    abstract val name: String
    abstract val isSaveAllowed: Boolean
    abstract val priorityOrder: Priority

    abstract fun init(context: Context)
    abstract fun<T> get(key: String, defaultValue: T, clazz: Class<T>) : T
    abstract fun<T> save(key: String, value: T, clazz: Class<T>)
    abstract fun configurationMap() : Map<String, String>

    enum class Priority{
        VERY_LOW,  LOW, MEDIUM, HIGH, VERY_HIGH
    }
}