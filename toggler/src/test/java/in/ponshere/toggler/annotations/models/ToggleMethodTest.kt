package `in`.ponshere.toggler.annotations.models

import android.content.SharedPreferences
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before

open class ToggleMethodTest {
    @MockK
    protected lateinit var sharedPreferences: SharedPreferences

    @MockK
    protected lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    @Before
    open fun setup() {
        MockKAnnotations.init(this)
        mockSharedPreferences()

    }

    protected fun mockSharedPreferences() {
        every { sharedPreferences.getBoolean(any(), any()) } returns true
        every { sharedPreferences.edit() } returns sharedPreferencesEditor
        every { sharedPreferencesEditor.putBoolean(any(), any()) } returns sharedPreferencesEditor
        every { sharedPreferencesEditor.apply() } returns Unit
    }
}