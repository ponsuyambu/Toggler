package `in`.ponshere.toggler.helpers

import `in`.ponshere.toggler.annotations.SelectToggle
import `in`.ponshere.toggler.annotations.SwitchToggle
import `in`.ponshere.toggler.providers.ToggleValueProvider
import `in`.ponshere.toggler.toggles.SelectToggleImpl
import `in`.ponshere.toggler.toggles.SwitchToggleImpl
import android.content.SharedPreferences
import java.lang.reflect.Method

internal class ToggleFactory(private val sharedPreferences: SharedPreferences) {
    internal fun createSwitchToggle(switchToggleAnnotation: SwitchToggle, method: Method, toggleValueProviderType: ToggleValueProvider.Type): SwitchToggleImpl {
        val sharedPreferencesKey = switchToggleAnnotation.sharedPreferencesKey
        val defaultValue = switchToggleAnnotation.defaultValue
        val fireBaseRemoteConfigKey = switchToggleAnnotation.fireBaseRemoteConfigKey

        return SwitchToggleImpl(
            resolveSharedPreferencesKey(sharedPreferencesKey, method),
            fireBaseRemoteConfigKey,
            sharedPreferences,
            defaultValue
        )
    }

    internal fun createSelectToggleMethod(switchToggleAnnotation: SelectToggle, method: Method, toggleValueProviderType: ToggleValueProvider.Type): SelectToggleImpl {
        val sharedPreferencesKey = switchToggleAnnotation.sharedPreferencesKey
        val defaultValue = switchToggleAnnotation.defaultValue
        val fireBaseRemoteConfigKey = switchToggleAnnotation.fireBaseRemoteConfigKey
        return SelectToggleImpl(
            resolveSharedPreferencesKey(sharedPreferencesKey, method),
            fireBaseRemoteConfigKey,
            sharedPreferences,
            defaultValue,
            switchToggleAnnotation.selectOptions,
            toggleValueProviderType
        )
    }

    private fun resolveSharedPreferencesKey(
        sharedPreferencesKey: String,
        method: Method
    ) = if (sharedPreferencesKey.isBlank()) method.name else sharedPreferencesKey

}