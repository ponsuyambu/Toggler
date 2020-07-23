package `in`.ponshere.toggler.v2.helpers

import `in`.ponshere.toggler.isEquals
import `in`.ponshere.toggler.mocks.aSwitchToggleWithAllValuesMethod
import `in`.ponshere.toggler.v2.Toggler
import `in`.ponshere.toggler.v2.annotations.SwitchToggle
import `in`.ponshere.toggler.v2.toggles.SwitchToggleImpl
import `in`.ponshere.toggler.v2.toggles.Toggle
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.lang.reflect.Method

class TogglesInvocationHandlerTest {

    @MockK
    private lateinit var toggleFactorMock: ToggleFactory

    @MockK
    private lateinit var togglerMock: Toggler

    @MockK
    private lateinit var cacheMockK: MutableMap<Method, Toggle<*>>

    @RelaxedMockK
    private lateinit var switchToggleImplMock : SwitchToggleImpl

    private lateinit var togglesInvocationHandler : TogglesInvocationHandler


    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        togglesInvocationHandler = TogglesInvocationHandler(toggleFactorMock, togglerMock, cacheMockK)
    }

    @Nested
    @DisplayName("When given method has switch toggle annotation")
    inner class GivenMethodHasSwitchToggleAnnotation {
        @Nested
        @DisplayName("When cache does not have the given method")
        inner class CacheDoesNotHaveMethod {
            @Test
            fun `should create the method from factory`() {
                every { cacheMockK[any()] } returns null
                every { cacheMockK.put(any(), any()) } returns mockk()
                every { toggleFactorMock.createSwitchToggle(any(), any()) } returns switchToggleImplMock

                togglesInvocationHandler.invoke(mockk(), aSwitchToggleWithAllValuesMethod, arrayOf())

                verify {
                    toggleFactorMock.createSwitchToggle(
                        aSwitchToggleWithAllValuesMethod.getAnnotation(SwitchToggle::class.java)!!,
                        aSwitchToggleWithAllValuesMethod
                    )
                }
            }
        }

        @Nested
        @DisplayName("When cache does not have the given method")
        inner class WhenCacheHasMethod {
            @Test
            fun `should get the method from cache`() {
                every { switchToggleImplMock.value() } returns true
                every { cacheMockK[any()] } returns switchToggleImplMock
                every { toggleFactorMock.createSwitchToggle(any(), any()) } returns switchToggleImplMock

                val value = togglesInvocationHandler.invoke(mockk(), aSwitchToggleWithAllValuesMethod, arrayOf())

                value isEquals true
                verify { cacheMockK[aSwitchToggleWithAllValuesMethod] }

            }
        }
    }
}