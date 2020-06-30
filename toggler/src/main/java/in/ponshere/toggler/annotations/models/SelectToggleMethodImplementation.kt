package `in`.ponshere.toggler.annotations.models

import `in`.ponshere.toggler.ToggleValueProvider
import `in`.ponshere.toggler.annotations.FeatureToggleType
import android.content.SharedPreferences
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

internal class SelectToggleMethodImplementation(
    sharedPreferencesKey: String,
    firebaseConfigKey: String,
    sharedPreferences: SharedPreferences,
    val defaultValue: String,
    val selectOptions: Array<String>,
    val valueProvider: ToggleValueProvider
) : BaseToggleMethodImplementation<String>(
    sharedPreferences,
    sharedPreferencesKey,
    firebaseConfigKey,
    FeatureToggleType.SELECT
) {
    override fun value(): String {
        if(valueProvider == ToggleValueProvider.LOCAL) return sharedPreferences.getString(sharedPreferencesKey, defaultValue)!!
        if (firebaseConfigKey.isNotEmpty() && valueProvider == ToggleValueProvider.FIREBASE) {
            return FirebaseRemoteConfig.getInstance().getString(firebaseConfigKey)
        }
        return sharedPreferences.getString(sharedPreferencesKey, defaultValue)!!
    }

    override fun updateLocalProvider(value: String) {
        sharedPreferences.edit().apply {
            this.putString(sharedPreferencesKey, value).apply()
        }
    }
}