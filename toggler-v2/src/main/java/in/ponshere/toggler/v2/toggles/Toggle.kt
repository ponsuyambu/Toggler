package `in`.ponshere.toggler.v2.toggles

import `in`.ponshere.toggler.v2.Toggler
import `in`.ponshere.toggler.v2.helpers.TogglerUtils
import `in`.ponshere.toggler.v2.provider.LocalValueProvider
import `in`.ponshere.toggler.v2.provider.ToggleValueProvider
import java.lang.reflect.Method

abstract class Toggle<T> constructor(val type: Type,
                                     val key: String,
                                     val defaultValue: T,
                                     val displayName: String,
                                     val method: Method,
                                     var isExpanded: Boolean = false
) {

    abstract fun classType() : Class<T>

    /**
     * Should not throw any exceptions. Always handle fallbacks
     */
    fun value() : T {
        if(Toggler.highPriorityValueProvider.isSupported(this)) {
            return Toggler.highPriorityValueProvider.getValue(this, classType())
        }

        //fallback to local provider
        return LocalValueProvider.getValue(this, classType())
    }

    fun displayValue() : String {
        return value().toString()
    }

    fun isSupported(toggleValueProvider: ToggleValueProvider) : Boolean {
        return toggleValueProvider.isSupported(this)
    }

    fun getProviderDisplayValue(toggleValueProvider: ToggleValueProvider) : CharSequence {
        return try {
            getProviderValue(toggleValueProvider).toString()
        }catch (e: Exception) {
            TogglerUtils.notConfiguredNotation()
        }
    }

    fun getProviderValue(toggleValueProvider: ToggleValueProvider) : T {
        return toggleValueProvider.getValue(this, classType())
    }

    fun saveProviderValue(toggleValueProvider: ToggleValueProvider, value: T) {
        toggleValueProvider.save(key, value, classType())
    }

    fun getConfiguration() : Map<String, CharSequence> {
        val defaultMap = mutableMapOf<String, CharSequence>(
            "Key" to key,
            "Default Value" to defaultValue.toString()
        )

        Toggler.valueProviders.forEach {
            defaultMap.putAll(it.configurationMap(this))
        }
        return defaultMap
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