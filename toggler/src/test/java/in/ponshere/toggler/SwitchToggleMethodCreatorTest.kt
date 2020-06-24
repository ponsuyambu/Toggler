package `in`.ponshere.toggler

import `in`.ponshere.toggler.mocks.aSwitchToggleWithoutAnyValuesMethod
import org.junit.Assert
import org.junit.Test

internal class SwitchToggleMethodCreatorTest : ToggleMethodCreatorTest() {

    @Test
    fun `should use method name as shared preferences key if sharedPreferencesKey is not provided`() {
        val switchToggle = toSwitchToggle()

        val switchToggleMethod = toggleMethodCreator.createSwitchToggleMethod(switchToggle, aSwitchToggleWithoutAnyValuesMethod)

        Assert.assertEquals(aSwitchToggleWithoutAnyValuesMethod.name, switchToggleMethod.sharedPreferencesKey)
    }

    @Test
    fun `should use default value as true if default value is not provided`() {
        val switchToggle = toSwitchToggle()

        val switchToggleMethod = toggleMethodCreator.createSwitchToggleMethod(switchToggle, aSwitchToggleWithoutAnyValuesMethod)

        Assert.assertTrue(switchToggleMethod.defaultValue)
    }
}