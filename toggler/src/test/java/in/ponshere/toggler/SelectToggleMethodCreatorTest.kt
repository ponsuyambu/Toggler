package `in`.ponshere.toggler

import `in`.ponshere.toggler.mocks.aSwitchToggleWithoutAnyValuesMethod
import org.junit.Assert
import org.junit.Test

internal class SelectToggleMethodCreatorTest : ToggleMethodCreatorTest() {

    @Test
    fun `should use method name as shared preferences key if sharedPreferencesKey is not provided`() {
        val switchToggle = toSelectToggle()

        val switchToggleMethod = toggleMethodCreator.createSelectToggleMethod(switchToggle, aSwitchToggleWithoutAnyValuesMethod)

        Assert.assertEquals(aSwitchToggleWithoutAnyValuesMethod.name, switchToggleMethod.sharedPreferencesKey)
    }

    @Test
    fun `should use default value as empty if default value is not provided`() {
        val switchToggle = toSelectToggle()

        val switchToggleMethod = toggleMethodCreator.createSelectToggleMethod(switchToggle, aSwitchToggleWithoutAnyValuesMethod)

        Assert.assertTrue(switchToggleMethod.defaultValue.isEmpty())
    }
}