package `in`.ponshere.toggler.helpers

import `in`.ponshere.toggler.mocks.aSwitchToggleWithAllValuesMethod
import `in`.ponshere.toggler.mocks.aSwitchToggleWithFalseDefaultValueMethod
import `in`.ponshere.toggler.mocks.aSwitchToggleWithTrueDefaultValueMethod
import `in`.ponshere.toggler.mocks.aSwitchToggleWithoutAnyValuesMethod
import `in`.ponshere.toggler.providers.ToggleValueProvider
import `in`.ponshere.toggler.toggles.Toggle
import org.junit.Assert.*
import org.junit.Test

internal class SwitchToggleFactoryTest : ToggleFactoryTest() {

    @Test
    fun `should use the provided shared preferences key`() {
        val switchToggle = toSwitchToggle(aSwitchToggleWithAllValuesMethod)

        val switchToggleMethod = toggleFactory.createSwitchToggle(switchToggle, aSwitchToggleWithAllValuesMethod, ToggleValueProvider.Type.LOCAL)

        assertEquals(switchToggle.sharedPreferencesKey, switchToggleMethod.sharedPreferencesKey)
    }

    @Test
    fun `should use the provided firebase remote config key`() {
        val switchToggle = toSwitchToggle(aSwitchToggleWithAllValuesMethod)

        val switchToggleMethod = toggleFactory.createSwitchToggle(switchToggle, aSwitchToggleWithAllValuesMethod, ToggleValueProvider.Type.LOCAL)

        assertEquals(switchToggle.fireBaseRemoteConfigKey, switchToggleMethod.firebaseConfigKey)
    }

    @Test
    fun `should use the provided defaultValue`() {
        toggleFactory.createSwitchToggle(
            toSwitchToggle(aSwitchToggleWithTrueDefaultValueMethod),
            aSwitchToggleWithTrueDefaultValueMethod, ToggleValueProvider.Type.LOCAL).run {
                assertTrue(defaultValue)
            }
        toggleFactory.createSwitchToggle(
            toSwitchToggle(aSwitchToggleWithFalseDefaultValueMethod),
            aSwitchToggleWithFalseDefaultValueMethod, ToggleValueProvider.Type.LOCAL).run {
            assertFalse(defaultValue)
        }


    }

    @Test
    fun `should use method name as shared preferences key if sharedPreferencesKey is not provided`() {
        val switchToggle = toSwitchToggle(aSwitchToggleWithoutAnyValuesMethod)

        val switchToggleMethod = toggleFactory.createSwitchToggle(switchToggle, aSwitchToggleWithoutAnyValuesMethod, ToggleValueProvider.Type.LOCAL)

        assertEquals(aSwitchToggleWithoutAnyValuesMethod.name, switchToggleMethod.sharedPreferencesKey)
    }

    @Test
    fun `should use default value as true if default value is not provided`() {
        val switchToggle = toSwitchToggle(aSwitchToggleWithoutAnyValuesMethod)

        val switchToggleMethod = toggleFactory.createSwitchToggle(switchToggle, aSwitchToggleWithoutAnyValuesMethod, ToggleValueProvider.Type.LOCAL)

        assertTrue(switchToggleMethod.defaultValue)
    }

    @Test
    fun `check feature toggle type`() {
        val switchToggle = toSwitchToggle(aSwitchToggleWithAllValuesMethod)

        val switchToggleMethod = toggleFactory.createSwitchToggle(switchToggle, aSwitchToggleWithAllValuesMethod, ToggleValueProvider.Type.LOCAL)

        assertTrue(switchToggleMethod.toggleType is Toggle.Type.Switch)
    }
}