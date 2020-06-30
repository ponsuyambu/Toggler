package `in`.ponshere.toggler.helpers

import `in`.ponshere.toggler.ToggleValueProvider
import `in`.ponshere.toggler.annotations.SelectToggle
import `in`.ponshere.toggler.annotations.SwitchToggle
import `in`.ponshere.toggler.annotations.models.SelectToggleMethodImplementation
import `in`.ponshere.toggler.annotations.models.SwitchToggleMethodImplementation
import android.content.SharedPreferences
import java.lang.reflect.Method

internal class ToggleMethodCreator(private val sharedPreferences: SharedPreferences) {
    internal fun createSwitchToggleMethod(switchToggleAnnotation: SwitchToggle, method: Method, toggleValueProvider: ToggleValueProvider): SwitchToggleMethodImplementation {
        val sharedPreferencesKey = switchToggleAnnotation.sharedPreferencesKey
        val defaultValue = switchToggleAnnotation.defaultValue
        val fireBaseRemoteConfigKey = switchToggleAnnotation.fireBaseRemoteConfigKey

        return SwitchToggleMethodImplementation(
            resolveSharedPreferencesKey(sharedPreferencesKey, method),
            fireBaseRemoteConfigKey,
            sharedPreferences,
            defaultValue,
            toggleValueProvider
        )
    }

    internal fun createSelectToggleMethod(switchToggleAnnotation: SelectToggle, method: Method, toggleValueProvider: ToggleValueProvider): SelectToggleMethodImplementation {
        val sharedPreferencesKey = switchToggleAnnotation.sharedPreferencesKey
        val defaultValue = switchToggleAnnotation.defaultValue
        val fireBaseRemoteConfigKey = switchToggleAnnotation.fireBaseRemoteConfigKey
        return SelectToggleMethodImplementation(
            resolveSharedPreferencesKey(sharedPreferencesKey, method),
            fireBaseRemoteConfigKey,
            sharedPreferences,
            defaultValue,
            switchToggleAnnotation.selectOptions,
            toggleValueProvider
        )
    }

    private fun resolveSharedPreferencesKey(
        sharedPreferencesKey: String,
        method: Method
    ) = if (sharedPreferencesKey.isBlank()) method.name else sharedPreferencesKey

}