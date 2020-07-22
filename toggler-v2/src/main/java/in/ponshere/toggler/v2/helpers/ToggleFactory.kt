package `in`.ponshere.toggler.v2.helpers

import `in`.ponshere.toggler.v2.annotations.SelectToggle
import `in`.ponshere.toggler.v2.annotations.SwitchToggle
import `in`.ponshere.toggler.v2.helpers.TogglerUtils.resolveKey
import `in`.ponshere.toggler.v2.toggles.SelectToggleImpl
import `in`.ponshere.toggler.v2.toggles.SwitchToggleImpl
import java.lang.reflect.Method

internal class ToggleFactory {
    internal fun createSwitchToggle(switchToggleAnnotation: SwitchToggle, method: Method): SwitchToggleImpl {
        val sharedPreferencesKey = switchToggleAnnotation.key
        val defaultValue = switchToggleAnnotation.defaultValue

        return SwitchToggleImpl(
            resolveKey(sharedPreferencesKey, method),
            defaultValue,
            switchToggleAnnotation.displayName,
            method
        )
    }

    internal fun createSelectToggleMethod(switchToggleAnnotation: SelectToggle, method: Method): SelectToggleImpl {
        val sharedPreferencesKey = switchToggleAnnotation.key
        val defaultValue = switchToggleAnnotation.defaultValue
        return SelectToggleImpl(
            resolveKey(sharedPreferencesKey, method),
            defaultValue,
            switchToggleAnnotation.displayName,
            switchToggleAnnotation.selectOptions,
            method
        )
    }


}