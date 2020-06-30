package `in`.ponshere.toggler.annotations.models

import `in`.ponshere.toggler.annotations.FeatureToggleType
import android.content.SharedPreferences

internal abstract class BaseToggleMethodImplementation<T>(
    val sharedPreferences: SharedPreferences,
    val sharedPreferencesKey: String,
    val firebaseConfigKey: String,
    val featureToggleType: FeatureToggleType
) {
    abstract fun value(): T
    abstract fun updateLocalProvider(value: T)
}