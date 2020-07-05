package `in`.ponshere.toggler.toggles

import `in`.ponshere.toggler.providers.FirebaseProvider
import `in`.ponshere.toggler.providers.LocalProvider
import `in`.ponshere.toggler.providers.ToggleValueProvider
import android.content.SharedPreferences

internal abstract class Toggle<T>(
    val sharedPreferences: SharedPreferences,
    val sharedPreferencesKey: String,
    val firebaseConfigKey: String,
    val defaultValue: T,
    val toggleType: Type,
    var isExpanded: Boolean = false
) : LocalProvider.Adapter, FirebaseProvider.Adapter {
    abstract fun resolvedValue(highPriorityToggleValueProvider: ToggleValueProvider<*>): T
    abstract fun value(toggleValueProvider: ToggleValueProvider<*>): String
    abstract fun booleanValue(toggleValueProvider: ToggleValueProvider<*>) : Boolean
    abstract fun update(value : T, toggleValueProvider: ToggleValueProvider<*> = LocalProvider)
    abstract fun updateLocalProvider(value: T)

    override fun localProviderValue(): String {
        return LocalProvider.getStringValue(sharedPreferencesKey)
    }

    override fun firebaseProviderValue(): String {
        return FirebaseProvider.getStringValue(firebaseConfigKey)
    }

    override fun isFirebaseKeyConfigured(): Boolean {
        return firebaseConfigKey.isNotBlank()
    }

    fun resolvedDisplayValue(highPriorityToggleValueProvider: ToggleValueProvider<*>): String {
        var displayValue: String
        displayValue = if(isFirebaseKeyConfigured() && highPriorityToggleValueProvider == FirebaseProvider)
            firebaseProviderValue()
        else
            localProviderValue()
        if(this is SwitchToggleImpl) {
            displayValue = if(localProviderValue().toBoolean()) "ON" else "OFF"
        }
        return displayValue
    }

    internal sealed class Type : Comparable<Type> {
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

