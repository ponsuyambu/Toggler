package `in`.ponshere.toggler.annotations.models

import `in`.ponshere.toggler.mocks.A_FIREBASE_CONFIG_KEY
import `in`.ponshere.toggler.mocks.A_SHARED_PREFERENCES_KEY
import android.content.SharedPreferences
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

private const val SWITCH_TOGGLE_DEFAULT_VALUE = true

class SwitchToggleMethodTest {

    @MockK
    private lateinit var sharedPreferences: SharedPreferences

    @MockK
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    private lateinit var switchToggleMethod: SwitchToggleMethod

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        mockSharedPreferences()

        switchToggleMethod = SwitchToggleMethod(A_SHARED_PREFERENCES_KEY,
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

    private fun mockSharedPreferences() {
        every { sharedPreferences.getBoolean(any(), any()) } returns true
        every { sharedPreferences.edit() } returns sharedPreferencesEditor
        every { sharedPreferencesEditor.putBoolean(any(), any()) } returns sharedPreferencesEditor
        every { sharedPreferencesEditor.apply() } returns Unit
    }
}
