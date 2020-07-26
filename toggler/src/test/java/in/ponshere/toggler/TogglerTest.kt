package `in`.ponshere.toggler
import `in`.ponshere.toggler.mocks.TogglesConfigWith3Toggles
import android.content.Context
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class TogglerTest {

    @MockK
    private lateinit var mockContext: Context

    private lateinit var toggles: TogglesConfigWith3Toggles

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        every { mockContext.getSharedPreferences(any(), any()) } returns mockk()
        toggles = Toggler.init(mockContext, TogglesConfigWith3Toggles::class.java)
    }

    @Test
    fun `should initialize the toggler when init method is invoked`() {
        assertNotNull(toggles)
        assertNotNull(Toggler.clazz)
        assertTrue(Toggler.clazz == TogglesConfigWith3Toggles::class.java)
        assertNotNull(Toggler.toggleFactory)

    }

    @Test
    fun `should have expected value for allToggles`() {
        assertTrue(Toggler.allToggles.size == 3)
    }

    @Test
    fun `should return the same toggles instance when get method invoked`() {
        assertEquals(toggles, Toggler.get())
    }


}