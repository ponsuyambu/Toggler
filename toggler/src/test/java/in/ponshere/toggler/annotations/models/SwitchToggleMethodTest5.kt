package `in`.ponshere.toggler.annotations.models

import `in`.ponshere.toggler.mocks.A_FIREBASE_CONFIG_KEY
import `in`.ponshere.toggler.mocks.A_SHARED_PREFERENCES_KEY
import `in`.ponshere.toggler.providers.LocalProvider
import `in`.ponshere.toggler.toggles.SwitchToggleImpl
import io.mockk.every
import io.mockk.verify
import org.junit.Assert
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

private const val SWITCH_TOGGLE_DEFAULT_VALUE = true


class SwitchToggleMethodTest5 : ToggleMethodTest() {

    private lateinit var switchToggleMethod: SwitchToggleImpl

    @BeforeEach
    override fun setup() {
        super.setup()

        switchToggleMethod = SwitchToggleImpl(
            A_SHARED_PREFERENCES_KEY,
            A_FIREBASE_CONFIG_KEY,
            sharedPreferences,
            SWITCH_TOGGLE_DEFAULT_VALUE
        )
    }

    @Nested
    inner class WhenProviderIsLocal {
        @Nested
        inner class WhenValueMethodIsInvoked {
            @Test
            fun `should return the value from shared preferences `() {
                every { sharedPreferences.getBoolean(any(), any())} returns false

                val value = switchToggleMethod.resolvedValue(LocalProvider)

                Assert.assertFalse(value)
                verify { sharedPreferences.getBoolean(A_SHARED_PREFERENCES_KEY, SWITCH_TOGGLE_DEFAULT_VALUE) }
            }
        }

        @Nested
        inner class WhenUpdateMethodIsInvoked {
            @Test
            fun `should update the value in shared preferences`() {
                val expected = false
                switchToggleMethod.updateLocalProvider(expected)

                verify {
                    sharedPreferences.edit()
                    sharedPreferencesEditor.putBoolean(A_SHARED_PREFERENCES_KEY, expected)
                    sharedPreferencesEditor.apply()
                }
            }
        }
    }
}