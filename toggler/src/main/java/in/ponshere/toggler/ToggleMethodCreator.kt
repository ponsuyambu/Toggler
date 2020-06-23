package `in`.ponshere.toggler

import `in`.ponshere.toggler.annotations.SelectToggle
import `in`.ponshere.toggler.annotations.SwitchToggle
import `in`.ponshere.toggler.annotations.models.SelectToggleMethod
import `in`.ponshere.toggler.annotations.models.SwitchToggleMethod
import android.content.SharedPreferences

internal class ToggleMethodCreator(private val sharedPreferences: SharedPreferences) {
    internal fun createSwitchToggleMethod(switchToggleAnnotation: SwitchToggle): SwitchToggleMethod {
        val sharedPreferencesKey = switchToggleAnnotation.sharedPreferencesKey
        val defaultValue = switchToggleAnnotation.defaultValue
        val fireBaseRemoteConfigKey = switchToggleAnnotation.fireBaseRemoteConfigKey
        return SwitchToggleMethod(
            sharedPreferencesKey,
            fireBaseRemoteConfigKey,
            sharedPreferences,
            defaultValue
        )
    }

    internal fun createSelectToggleMethod(switchToggleAnnotation: SelectToggle): SelectToggleMethod {
        val sharedPreferencesKey = switchToggleAnnotation.sharedPreferencesKey
        val defaultValue = switchToggleAnnotation.defaultValue
        val fireBaseRemoteConfigKey = switchToggleAnnotation.fireBaseRemoteConfigKey
        return SelectToggleMethod(
            sharedPreferencesKey,
            fireBaseRemoteConfigKey,
            sharedPreferences,
            defaultValue,
            switchToggleAnnotation.selectOptions
        )
    }
}