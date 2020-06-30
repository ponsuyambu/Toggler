package `in`.ponshere.toggler.annotations.models

import `in`.ponshere.toggler.ToggleValueProvider
import `in`.ponshere.toggler.annotations.FeatureToggleType
import android.content.SharedPreferences

internal class SwitchToggleMethodImplementation(
    sharedPreferencesKey: String,
    firebaseConfigKey: String,
    sharedPreferences: SharedPreferences,
    val defaultValue: Boolean,
    toggleValueProvider: ToggleValueProvider
) : BaseToggleMethodImplementation<Boolean>(
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
}