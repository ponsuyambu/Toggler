package `in`.ponshere.toggler.helpers

import `in`.ponshere.toggler.annotations.models.BaseToggleMethodImplementation
import `in`.ponshere.toggler.annotations.models.SwitchToggleMethodImplementation
import `in`.ponshere.toggler.mocks.aSwitchToggleWithoutAnyValuesMethod
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.lang.reflect.Method

internal class TogglesInvocationHandlerTest {

    @MockK
    private lateinit var methodCreatorMock: ToggleMethodCreator

    @MockK
    private lateinit var switchToggleMethodImplementationMock: SwitchToggleMethodImplementation

    private  var cacheSpy = spyk(mutableMapOf<Method, BaseToggleMethodImplementation<*>>())

    private lateinit var togglesInvocationHandler: TogglesInvocationHandler

    @MockK
    private lateinit var cacheMock : MutableMap<Method, BaseToggleMethodImplementation<*>>


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        every { switchToggleMethodImplementationMock.value() } returns false
        every { methodCreatorMock.createSwitchToggleMethod(any(), any()) } returns switchToggleMethodImplementationMock
        togglesInvocationHandler = TogglesInvocationHandler(methodCreatorMock, cacheSpy)
        cacheSpy.clear()
    }

    @Test
    fun `should create switch toggle implementation in it is invoked first`() {
        val value = togglesInvocationHandler.invoke(Any(), aSwitchToggleWithoutAnyValuesMethod, arrayOf())
        verify {
            methodCreatorMock.createSwitchToggleMethod(any(), eq(aSwitchToggleWithoutAnyValuesMethod))
        }
        verify { cacheSpy[any()] = any() }
        Assert.assertFalse(value as Boolean)
        assertEquals(switchToggleMethodImplementationMock, cacheSpy[aSwitchToggleWithoutAnyValuesMethod])

    }

    @Test
    fun `should get the switch toggle implementation from cache since the second invocation `() {
        togglesInvocationHandler.invoke(Any(), aSwitchToggleWithoutAnyValuesMethod, arrayOf())
        togglesInvocationHandler.invoke(Any(), aSwitchToggleWithoutAnyValuesMethod, arrayOf())

        verify {
            cacheSpy[any()]
        }

    }
}