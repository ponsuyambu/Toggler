package `in`.ponshere.toggler.annotations.models

import `in`.ponshere.toggler.annotations.FeatureToggleType
import android.content.SharedPreferences
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

internal class SelectToggleMethodImplementation(
    sharedPreferencesKey: String,
    firebaseConfigKey: String,
    sharedPreferences: SharedPreferences,
    val defaultValue: String,
    val selectOptions: Array<String>
) : BaseToggleMethodImplementation<String>(
    sharedPreferences,
    sharedPreferencesKey,
    firebaseConfigKey,
    FeatureToggleType.SELECT
) {
    override fun value(): String {
        if (firebaseConfigKey.isNotEmpty()) {
            return FirebaseRemoteConfig.getInstance().getString(firebaseConfigKey)
        }
        return sharedPreferences.getString(sharedPreferencesKey, defaultValue)!!
    }

    override fun update(value: String) {
        sharedPreferences.edit().apply {
            this.putString(sharedPreferencesKey, value).apply()
        }
    }
}