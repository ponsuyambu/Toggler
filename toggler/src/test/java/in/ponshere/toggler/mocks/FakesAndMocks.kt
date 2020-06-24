package `in`.ponshere.toggler.mocks

import `in`.ponshere.toggler.annotations.SelectToggle
import `in`.ponshere.toggler.annotations.SwitchToggle
import kotlin.reflect.jvm.javaMethod

val aSwitchToggleWithoutAnyValuesMethod = FakeToggles::aSwitchToggleWithoutAnyValues.javaMethod!! //this can't be null, hence its ok to use !!
val aSwitchToggleWithAllValuesMethod = FakeToggles::aSwitchToggleWithAllValues.javaMethod!! //this can't be null, hence its ok to use !!
val aSwitchToggleWithTrueDefaultValueMethod = FakeToggles::aSwitchToggleWithTrueDefaultValue.javaMethod!! //this can't be null, hence its ok to use !!
val aSwitchToggleWithFalseDefaultValueMethod = FakeToggles::aSwitchToggleWithFalseDefaultValue.javaMethod!! //this can't be null, hence its ok to use !!

val aSelectToggleWithoutAnyValuesMethod = FakeToggles::aSelectToggleWithoutAnyValues.javaMethod!! //this can't be null, hence its ok to use !!
val aSelectToggleWithoutAllValuesMethod = FakeToggles::aSelectToggleWithAllValues.javaMethod!! //this can't be null, hence its ok to use !!

interface FakeToggles {
    @SwitchToggle
    fun aSwitchToggleWithoutAnyValues() : Boolean

    @SwitchToggle(sharedPreferencesKey = "a_switch_toggle_key",
        defaultValue = false,
        fireBaseRemoteConfigKey = "a_switch_toggle_remote_config_key")
    fun aSwitchToggleWithAllValues() : Boolean

    @SwitchToggle(defaultValue = true)
    fun aSwitchToggleWithTrueDefaultValue() : Boolean

    @SwitchToggle(defaultValue = false)
    fun aSwitchToggleWithFalseDefaultValue() : Boolean

    @SelectToggle
    fun aSelectToggleWithoutAnyValues() : String

    @SelectToggle(sharedPreferencesKey = "a_select_toggle_key",
        defaultValue = "value1",
        fireBaseRemoteConfigKey = "a_select_toggle_remote_config_key",
        selectOptions = ["value1", "value2"]
    )
    fun aSelectToggleWithAllValues() : String
}