package `in`.ponshere.toggler.annotations.models

import `in`.ponshere.toggler.ToggleValueProvider
import `in`.ponshere.toggler.mocks.AN_EMPTY_FIREBASE_CONFIG_KEY
import `in`.ponshere.toggler.mocks.A_FIREBASE_CONFIG_KEY
import `in`.ponshere.toggler.mocks.A_SHARED_PREFERENCES_KEY
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

    private lateinit var selectToggleMethod: SelectToggleMethodImplementation

    @Before
    override fun setup() {
        super.setup()

        selectToggleMethod = SelectToggleMethodImplementation(A_SHARED_PREFERENCES_KEY,
            AN_EMPTY_FIREBASE_CONFIG_KEY,
            sharedPreferences,
            SELECT_TOGGLE_DEFAULT_VALUE,
            SELECT_OPTIONS,
            ToggleValueProvider.LOCAL
        )
    }

    @Test
    fun `should return the value from shared preferences when 'value' method is invoked and firebase config key is empty AND value provider is local`() {
        val expectedValue = "an_expected_value"
        every { sharedPreferences.getString(any(), any())} returns expectedValue

        val value = selectToggleMethod.value()

        assertEquals(expectedValue, value)
        verify { sharedPreferences.getString(A_SHARED_PREFERENCES_KEY, SELECT_TOGGLE_DEFAULT_VALUE) }
    }

    @Test
    fun `should return the value from firebase WHEN 'value' method is invoked AND firebase config key is not empty AND value provider is firebase`() {
        selectToggleMethod = SelectToggleMethodImplementation(A_SHARED_PREFERENCES_KEY,
            A_FIREBASE_CONFIG_KEY,
            sharedPreferences,
            SELECT_TOGGLE_DEFAULT_VALUE,
            SELECT_OPTIONS,
            ToggleValueProvider.FIREBASE
        )
        mockkStatic(FirebaseRemoteConfig::class)
        val expectedValue = "an_expected_value"
        every { sharedPreferences.getString(any(), any())} returns expectedValue
        val remoteConfigMock = mockk<FirebaseRemoteConfig>()
        every { FirebaseRemoteConfig.getInstance() } returns remoteConfigMock
        every { remoteConfigMock.getString(any()) } returns expectedValue

        val value = selectToggleMethod.value()

        assertEquals(expectedValue, value)
        verify {
            FirebaseRemoteConfig.getInstance()
            remoteConfigMock.getString(A_FIREBASE_CONFIG_KEY)
        }
    }

    @Test
    fun `should update the value in shared preferences when 'update' method is invoked`() {
        val valueToBeUpdated = "Options 2"

        selectToggleMethod.updateLocalProvider(valueToBeUpdated)

        verify {
            sharedPreferences.edit()
            sharedPreferencesEditor.putString(A_SHARED_PREFERENCES_KEY, valueToBeUpdated)
            sharedPreferencesEditor.apply()
        }
    }
}
