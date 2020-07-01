package `in`.ponshere.toggler.annotations.models

import `in`.ponshere.toggler.FirebaseProvider
import `in`.ponshere.toggler.LocalProvider
import `in`.ponshere.toggler.ToggleValueProvider
import `in`.ponshere.toggler.annotations.FeatureToggleType
import android.content.SharedPreferences

internal class SwitchToggleImpl(
    sharedPreferencesKey: String,
    firebaseConfigKey: String,
    sharedPreferences: SharedPreferences,
    defaultValue: Boolean
) : Toggle<Boolean>(
    sharedPreferences,
    sharedPreferencesKey,
    firebaseConfigKey,
    defaultValue,
    FeatureToggleType.SWITCH
) {
    override fun resolvedValue(highPriorityToggleValueProvider: ToggleValueProvider): Boolean {
        if(isFirebaseKeyConfigured() && highPriorityToggleValueProvider == FirebaseProvider)
            return booleanValue(FirebaseProvider)
        return booleanValue(LocalProvider)
    }

    override fun updateLocalProvider(value: Boolean) {
        LocalProvider.setBooleanValue(sharedPreferencesKey, value)
    }

    override fun value(toggleValueProvider: ToggleValueProvider): String {
        return toggleValueProvider.getStringValue(sharedPreferencesKey, defaultValue.toString())
    }

    override fun booleanValue(toggleValueProvider: ToggleValueProvider): Boolean {
        return toggleValueProvider.getBooleanValue(sharedPreferencesKey, defaultValue)
    }

    override fun update(value: Boolean, toggleValueProvider: ToggleValueProvider) {
        if(toggleValueProvider is LocalProvider) toggleValueProvider.setBooleanValue(sharedPreferencesKey, value)
    }
}