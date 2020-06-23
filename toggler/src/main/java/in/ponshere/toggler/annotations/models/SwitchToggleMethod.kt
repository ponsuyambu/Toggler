package `in`.ponshere.toggler.annotations.models

import `in`.ponshere.toggler.annotations.FeatureToggleType
import android.content.SharedPreferences

internal class SwitchToggleMethod(
    sharedPreferencesKey: String,
    firebaseConfigKey: String,
    sharedPreferences: SharedPreferences,
    val defaultValue: Boolean
) : FeatureToggleMethod<Boolean>(
    sharedPreferences,
    sharedPreferencesKey,
    firebaseConfigKey,
    FeatureToggleType.SWITCH
) {
    override fun value(): Boolean {
        return sharedPreferences.getBoolean(sharedPreferencesKey, defaultValue)
    }

    override fun update(value: Boolean) {
        sharedPreferences.edit().apply {
            this.putBoolean(sharedPreferencesKey, value).apply()
        }
    }
}