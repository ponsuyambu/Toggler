package `in`.ponshere.toggler.annotations.models

import `in`.ponshere.toggler.LocalProvider
import `in`.ponshere.toggler.ToggleValueProvider
import `in`.ponshere.toggler.ToggleValueProviderType
import `in`.ponshere.toggler.annotations.FeatureToggleType
import android.content.SharedPreferences

internal class SwitchToggleImpl(
    sharedPreferencesKey: String,
    firebaseConfigKey: String,
    sharedPreferences: SharedPreferences,
    val defaultValue: Boolean,
    toggleValueProviderType: ToggleValueProviderType
) : Toggle<Boolean>(
    sharedPreferences,
    sharedPreferencesKey,
    firebaseConfigKey,
    FeatureToggleType.SWITCH
) {
    override fun value(): Boolean {
        return sharedPreferences.getBoolean(sharedPreferencesKey, defaultValue)
    }

    override fun updateLocalProvider(value: Boolean) {
        sharedPreferences.edit().apply {
            this.putBoolean(sharedPreferencesKey, value).apply()
        }
    }

    override fun value(toggleValueProvider: ToggleValueProvider): String {
        return toggleValueProvider.getStringValue(sharedPreferencesKey)
    }

    override fun booleanValue(toggleValueProvider: ToggleValueProvider): Boolean {
        return value(toggleValueProvider).toBoolean()
    }

    override fun update(value: Boolean, toggleValueProvider: ToggleValueProvider) {
        if(toggleValueProvider is LocalProvider) toggleValueProvider.setBooleanValue(sharedPreferencesKey, value)
    }
}