package `in`.ponshere.toggler.annotations.models

import `in`.ponshere.toggler.FeatureToggleType
import android.content.SharedPreferences

internal abstract class FeatureToggleMethod<T>(
    val sharedPreferences: SharedPreferences,
    val sharedPreferencesKey: String,
    val firebaseConfigKey: String,
    val featureToggleType: FeatureToggleType
) {
    abstract fun value(): T
    abstract fun update(value: T)
}