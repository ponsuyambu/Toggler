package `in`.ponshere.toggler.annotations.models

import `in`.ponshere.toggler.mocks.AN_EMPTY_FIREBASE_CONFIG_KEY
import `in`.ponshere.toggler.mocks.A_FIREBASE_CONFIG_KEY
import `in`.ponshere.toggler.mocks.A_SHARED_PREFERENCES_KEY
import `in`.ponshere.toggler.providers.FirebaseProvider
import `in`.ponshere.toggler.providers.LocalProvider
import `in`.ponshere.toggler.providers.ToggleValueProvider
import `in`.ponshere.toggler.toggles.SelectToggleImpl
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

private const val SELECT_TOGGLE_DEFAULT_VALUE = "SELECT_TOGGLE_DEFAULT_VALUE"
private val SELECT_OPTIONS = arrayOf("Option 1", "Options 2")

class SelectToggleMethodTest : ToggleMethodTest() {

    private lateinit var selectToggle: SelectToggleImpl

    @Before
    override fun setup() {
        super.setup()

        selectToggle = SelectToggleImpl(
            A_SHARED_PREFERENCES_KEY,
            AN_EMPTY_FIREBASE_CONFIG_KEY,
            sharedPreferences,
            SELECT_TOGGLE_DEFAULT_VALUE,
            SELECT_OPTIONS,
            ToggleValueProvider.Type.LOCAL
        )
    }

    @Test
    fun `should return the value from shared preferences when 'value' method is invoked and firebase config key is empty AND value provider is local`() {
        val expectedValue = "an_expected_value"
        every { sharedPreferences.getString(any(), any())} returns expectedValue

        val value = selectToggle.resolvedValue(LocalProvider)

        assertEquals(expectedValue, value)
        verify { sharedPreferences.getString(A_SHARED_PREFERENCES_KEY, SELECT_TOGGLE_DEFAULT_VALUE) }
    }

    @Test
    fun `should return the value from firebase WHEN 'value' method is invoked AND firebase config key is not empty AND value provider is firebase`() {
        selectToggle = SelectToggleImpl(
            A_SHARED_PREFERENCES_KEY,
            A_FIREBASE_CONFIG_KEY,
            sharedPreferences,
            SELECT_TOGGLE_DEFAULT_VALUE,
            SELECT_OPTIONS,
            ToggleValueProvider.Type.FIREBASE
        )
        mockkStatic(FirebaseRemoteConfig::class)
        val expectedValue = "an_expected_value"
        every { sharedPreferences.getString(any(), any())} returns expectedValue
        val remoteConfigMock = mockk<FirebaseRemoteConfig>()
        every { FirebaseRemoteConfig.getInstance() } returns remoteConfigMock
        every { remoteConfigMock.getString(any()) } returns expectedValue

        val value = selectToggle.resolvedValue(FirebaseProvider)

        assertEquals(expectedValue, value)
        verify {
            FirebaseRemoteConfig.getInstance()
            remoteConfigMock.getString(A_FIREBASE_CONFIG_KEY)
        }
    }

    @Test
    fun `should update the value in shared preferences when 'update' method is invoked`() {
        val valueToBeUpdated = "Options 2"

        selectToggle.updateLocalProvider(valueToBeUpdated)

        verify {
            sharedPreferences.edit()
            sharedPreferencesEditor.putString(A_SHARED_PREFERENCES_KEY, valueToBeUpdated)
            sharedPreferencesEditor.apply()
        }
    }
}
