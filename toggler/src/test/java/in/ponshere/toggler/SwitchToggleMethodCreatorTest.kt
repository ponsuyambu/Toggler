package `in`.ponshere.toggler

import `in`.ponshere.toggler.annotations.SwitchToggle
import `in`.ponshere.toggler.mocks.aSwitchToggleWithoutAnyValuesMethod
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SwitchToggleMethodCreatorTest {
    
    private lateinit var toggleMethodCreator : ToggleMethodCreator

    @Before
    fun setup() {
        toggleMethodCreator = ToggleMethodCreator(mockk())
    }

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

    private fun toSwitchToggle(): SwitchToggle {
        return aSwitchToggleWithoutAnyValuesMethod.getAnnotation(
            SwitchToggle::class.java
        )!! // it is a switch toggle, so can't be null.
    }
}