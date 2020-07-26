package `in`.ponshere.toggler.annotations.models

import `in`.ponshere.toggler.providers.LocalProvider
import android.content.Context
import android.content.SharedPreferences
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Before
import org.junit.jupiter.api.BeforeEach

open class ToggleMethodTest {
    @MockK
    protected lateinit var sharedPreferences: SharedPreferences

    @MockK
    protected lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    @Before
    @BeforeEach
    open fun setup() {
        MockKAnnotations.init(this)
        mockSharedPreferences()
        val mockContext = mockk<Context>()
        every { mockContext.getSharedPreferences(any(), any()) } returns sharedPreferences
        LocalProvider.init(mockContext)

    }

    private fun mockSharedPreferences() {
        every { sharedPreferences.getBoolean(any(), any()) } returns true
        every { sharedPreferences.edit() } returns sharedPreferencesEditor
        every { sharedPreferencesEditor.putBoolean(any(), any()) } returns sharedPreferencesEditor
        every { sharedPreferencesEditor.putString(any(), any()) } returns sharedPreferencesEditor
        every { sharedPreferencesEditor.apply() } returns Unit
    }
}