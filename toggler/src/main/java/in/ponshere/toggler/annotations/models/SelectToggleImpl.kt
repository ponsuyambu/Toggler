package `in`.ponshere.toggler.annotations.models

import `in`.ponshere.toggler.LocalProvider
import `in`.ponshere.toggler.ToggleValueProvider
import `in`.ponshere.toggler.annotations.FeatureToggleType
import android.content.SharedPreferences
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

internal class SelectToggleImpl(
    sharedPreferencesKey: String,
    firebaseConfigKey: String,
    sharedPreferences: SharedPreferences,
    defaultValue: String,
    val selectOptions: Array<String>,
    val valueProviderType: ToggleValueProvider.Type
) : Toggle<String>(
    sharedPreferences,
    sharedPreferencesKey,
    firebaseConfigKey,
    defaultValue,
    FeatureToggleType.SELECT
) {
    override fun resolvedValue(highPriorityToggleValueProvider: ToggleValueProvider): String {
        if(valueProviderType == ToggleValueProvider.Type.LOCAL) return sharedPreferences.getString(sharedPreferencesKey, defaultValue)!!
        if (firebaseConfigKey.isNotEmpty() && valueProviderType == ToggleValueProvider.Type.FIREBASE) {
            return FirebaseRemoteConfig.getInstance().getString(firebaseConfigKey)
        }
        return sharedPreferences.getString(sharedPreferencesKey, defaultValue)!!
    }

    override fun updateLocalProvider(value: String) {
        sharedPreferences.edit().apply {
            this.putString(sharedPreferencesKey, value).apply()
        }
    }

    override fun value(toggleValueProvider: ToggleValueProvider): String {
        return toggleValueProvider.getStringValue(sharedPreferencesKey)
    }

    override fun booleanValue(toggleValueProvider: ToggleValueProvider): Boolean {
        throw UnsupportedOperationException("booleanValue is not supported in SelectToggle")
    }

    override fun update(value: String, toggleValueProvider: ToggleValueProvider) {
        if(toggleValueProvider is LocalProvider) toggleValueProvider.setStringValue(sharedPreferencesKey, value)
    }
}