package `in`.ponshere.toggler.annotations.models

import `in`.ponshere.toggler.FirebaseProvider
import `in`.ponshere.toggler.LocalProvider
import `in`.ponshere.toggler.ToggleValueProvider
import `in`.ponshere.toggler.annotations.FeatureToggleType
import android.content.SharedPreferences

internal abstract class Toggle<T>(
    val sharedPreferences: SharedPreferences,
    val sharedPreferencesKey: String,
    val firebaseConfigKey: String,
    val defaultValue: T,
    val featureToggleType: FeatureToggleType,
    var isExpanded: Boolean = false
) {
    abstract fun resolvedValue(highPriorityToggleValueProvider: ToggleValueProvider): T
    abstract fun value(toggleValueProvider: ToggleValueProvider): String
    abstract fun booleanValue(toggleValueProvider: ToggleValueProvider) : Boolean
    abstract fun update(value : T, toggleValueProvider: ToggleValueProvider = LocalProvider)
    abstract fun updateLocalProvider(value: T)

    fun localProviderValue(): String {
        return LocalProvider.getStringValue(sharedPreferencesKey)
    }

    fun firebaseProviderValue(): String {
        return FirebaseProvider.getStringValue(firebaseConfigKey)
    }

    fun isFirebaseKeyConfigured(): Boolean {
        return firebaseConfigKey.isNotBlank()
    }

    fun resolvedDisplayValue(highPriorityToggleValueProvider: ToggleValueProvider): String {
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
}