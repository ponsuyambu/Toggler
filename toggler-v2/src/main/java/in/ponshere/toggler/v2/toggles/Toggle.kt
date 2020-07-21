package `in`.ponshere.toggler.v2.toggles

import `in`.ponshere.toggler.v2.Toggler
import `in`.ponshere.toggler.v2.provider.ToggleValueProvider
import java.lang.reflect.Method

abstract class Toggle<T> constructor(val type: Type,
                                     val key: String,
                                     private val defaultValue: T,
                                     val displayName: String,
                                     val method: Method,
                                     var isExpanded: Boolean = false
) {

    abstract fun classType() : Class<T>

    fun value() : T {
        return Toggler.highPriorityValueProvider.get(key, defaultValue, classType(), method)
    }

    fun isSupported(toggleValueProvider: ToggleValueProvider) : Boolean {
        return toggleValueProvider.isSupported(this)
    }

    fun getProviderValue(toggleValueProvider: ToggleValueProvider) : T {
        return toggleValueProvider.get(key, defaultValue, classType(), method)
    }

    fun saveProviderValue(toggleValueProvider: ToggleValueProvider, value: T) {
        toggleValueProvider.save(key, value, classType())
    }

    sealed class Type : Comparable<Type> {
        abstract val value: Int
        override fun compareTo(other: Type) = value - other.value

        internal object Switch : Type() {
            override val value: Int = 1
        }

        internal object Select : Type() {
            override val value: Int = 2

        }
    }
}