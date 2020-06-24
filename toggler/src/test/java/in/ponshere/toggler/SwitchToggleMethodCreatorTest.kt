package `in`.ponshere.toggler

import `in`.ponshere.toggler.annotations.FeatureToggleType
import `in`.ponshere.toggler.mocks.aSwitchToggleWithAllValuesMethod
import `in`.ponshere.toggler.mocks.aSwitchToggleWithFalseDefaultValueMethod
import `in`.ponshere.toggler.mocks.aSwitchToggleWithTrueDefaultValueMethod
import `in`.ponshere.toggler.mocks.aSwitchToggleWithoutAnyValuesMethod
import org.junit.Assert.*
import org.junit.Test

internal class SwitchToggleMethodCreatorTest : ToggleMethodCreatorTest() {

    @Test
    fun `should use the provided shared preferences key`() {
        val switchToggle = toSwitchToggle(aSwitchToggleWithAllValuesMethod)

        val switchToggleMethod = toggleMethodCreator.createSwitchToggleMethod(switchToggle, aSwitchToggleWithAllValuesMethod)

        assertEquals(switchToggle.sharedPreferencesKey, switchToggleMethod.sharedPreferencesKey)
    }

    @Test
    fun `should use the provided firebase remote config key`() {
        val switchToggle = toSwitchToggle(aSwitchToggleWithAllValuesMethod)

        val switchToggleMethod = toggleMethodCreator.createSwitchToggleMethod(switchToggle, aSwitchToggleWithAllValuesMethod)

        assertEquals(switchToggle.fireBaseRemoteConfigKey, switchToggleMethod.firebaseConfigKey)
    }

    @Test
    fun `should use the provided defaultValue`() {
        toggleMethodCreator.createSwitchToggleMethod(
            toSwitchToggle(aSwitchToggleWithTrueDefaultValueMethod),
            aSwitchToggleWithTrueDefaultValueMethod).run {
                assertTrue(defaultValue)
            }
        toggleMethodCreator.createSwitchToggleMethod(
            toSwitchToggle(aSwitchToggleWithFalseDefaultValueMethod),
            aSwitchToggleWithFalseDefaultValueMethod).run {
            assertFalse(defaultValue)
        }


    }

    @Test
    fun `should use method name as shared preferences key if sharedPreferencesKey is not provided`() {
        val switchToggle = toSwitchToggle(aSwitchToggleWithoutAnyValuesMethod)

        val switchToggleMethod = toggleMethodCreator.createSwitchToggleMethod(switchToggle, aSwitchToggleWithoutAnyValuesMethod)

        assertEquals(aSwitchToggleWithoutAnyValuesMethod.name, switchToggleMethod.sharedPreferencesKey)
    }

    @Test
    fun `should use default value as true if default value is not provided`() {
        val switchToggle = toSwitchToggle(aSwitchToggleWithoutAnyValuesMethod)

        val switchToggleMethod = toggleMethodCreator.createSwitchToggleMethod(switchToggle, aSwitchToggleWithoutAnyValuesMethod)

        assertTrue(switchToggleMethod.defaultValue)
    }

    @Test
    fun `check feature toggle type`() {
        val switchToggle = toSwitchToggle(aSwitchToggleWithAllValuesMethod)

        val switchToggleMethod = toggleMethodCreator.createSwitchToggleMethod(switchToggle, aSwitchToggleWithAllValuesMethod)

        assertEquals(FeatureToggleType.SWITCH, switchToggleMethod.featureToggleType)
    }
}