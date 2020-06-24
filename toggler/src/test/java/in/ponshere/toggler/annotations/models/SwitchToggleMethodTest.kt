package `in`.ponshere.toggler.annotations.models

import `in`.ponshere.toggler.mocks.A_FIREBASE_CONFIG_KEY
import `in`.ponshere.toggler.mocks.A_SHARED_PREFERENCES_KEY
import io.mockk.every
import io.mockk.verify
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

private const val SWITCH_TOGGLE_DEFAULT_VALUE = true

class SwitchToggleMethodTest : ToggleMethodTest() {

    private lateinit var switchToggleMethod: `SwitchToggleMethodImplementation`

    @Before
    override fun setup() {
        super.setup()

        switchToggleMethod = `SwitchToggleMethodImplementation`(A_SHARED_PREFERENCES_KEY,
            A_FIREBASE_CONFIG_KEY,
            sharedPreferences,
            SWITCH_TOGGLE_DEFAULT_VALUE
        )
    }

    @Test
    fun `should return the value from shared preferences when 'value' method is invoked`() {
        every { sharedPreferences.getBoolean(any(), any())} returns false

        val value = switchToggleMethod.value()

        assertFalse(value)
        verify { sharedPreferences.getBoolean(A_SHARED_PREFERENCES_KEY, SWITCH_TOGGLE_DEFAULT_VALUE) }
    }

    @Test
    fun `should update the value in shared preferences when 'update' method is invoked`() {
        val expected = false
        switchToggleMethod.update(expected)

        verify {
            sharedPreferences.edit()
            sharedPreferencesEditor.putBoolean(A_SHARED_PREFERENCES_KEY, expected)
            sharedPreferencesEditor.apply()
        }
    }
}
