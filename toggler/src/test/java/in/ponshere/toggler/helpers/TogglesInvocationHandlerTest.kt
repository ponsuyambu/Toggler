package `in`.ponshere.toggler.helpers

import `in`.ponshere.toggler.annotations.models.BaseToggleMethodImplementation
import `in`.ponshere.toggler.annotations.models.SelectToggleMethodImplementation
import `in`.ponshere.toggler.annotations.models.SwitchToggleMethodImplementation
import `in`.ponshere.toggler.mocks.aNonAnnotatedMethod
import `in`.ponshere.toggler.mocks.aSelectToggleWithoutAnyValuesMethod
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

const val SELECT_TOGGLE_VALUE = "VALUE"


internal class TogglesInvocationHandlerTest {

    @MockK
    private lateinit var methodCreatorMock: ToggleMethodCreator

    @MockK
    private lateinit var switchToggleMethodImplementationMock: SwitchToggleMethodImplementation
    @MockK
    private lateinit var selectToggleMethodImplementationMock: SelectToggleMethodImplementation

    private  var cacheSpy = spyk(mutableMapOf<Method, BaseToggleMethodImplementation<*>>())

    private lateinit var togglesInvocationHandler: TogglesInvocationHandler

    @MockK
    private lateinit var cacheMock : MutableMap<Method, BaseToggleMethodImplementation<*>>


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        every { switchToggleMethodImplementationMock.value() } returns false
        every { selectToggleMethodImplementationMock.value() } returns SELECT_TOGGLE_VALUE
        every { methodCreatorMock.createSwitchToggleMethod(any(), any()) } returns switchToggleMethodImplementationMock
        every { methodCreatorMock.createSelectToggleMethod(any(), any()) } returns selectToggleMethodImplementationMock
        togglesInvocationHandler = TogglesInvocationHandler(methodCreatorMock, cacheSpy)
        cacheSpy.clear()
    }

    @Test
    fun `should create switch toggle implementation if it is invoked first`() {
        val value = togglesInvocationHandler.invoke(Any(), aSwitchToggleWithoutAnyValuesMethod, arrayOf())
        verify {
            methodCreatorMock.createSwitchToggleMethod(any(), eq(aSwitchToggleWithoutAnyValuesMethod))
        }
        verify { cacheSpy[any()] = any() }
        Assert.assertFalse(value as Boolean)
        assertEquals(switchToggleMethodImplementationMock, cacheSpy[aSwitchToggleWithoutAnyValuesMethod])

    }

    @Test
    fun `should get the switch toggle implementation from cache from the second invocation `() {
        togglesInvocationHandler.invoke(Any(), aSwitchToggleWithoutAnyValuesMethod, arrayOf())
        val value = togglesInvocationHandler.invoke(Any(), aSwitchToggleWithoutAnyValuesMethod, arrayOf())

        verify { cacheSpy[aSwitchToggleWithoutAnyValuesMethod] }
        Assert.assertFalse(value as Boolean)

    }

    @Test
    fun `should create select toggle implementation if it is invoked first`() {
        val value = togglesInvocationHandler.invoke(Any(), aSelectToggleWithoutAnyValuesMethod, arrayOf())
        verify {
            methodCreatorMock.createSelectToggleMethod(any(), eq(aSelectToggleWithoutAnyValuesMethod))
        }
        verify { cacheSpy[any()] = any() }
        assertEquals(SELECT_TOGGLE_VALUE, value)
        assertEquals(selectToggleMethodImplementationMock, cacheSpy[aSelectToggleWithoutAnyValuesMethod])

    }

    @Test
    fun `should get the select toggle implementation from cache from the second invocation `() {
        togglesInvocationHandler.invoke(Any(), aSelectToggleWithoutAnyValuesMethod, arrayOf())
        val value = togglesInvocationHandler.invoke(Any(), aSelectToggleWithoutAnyValuesMethod, arrayOf())

        verify {
            cacheSpy[aSelectToggleWithoutAnyValuesMethod]
        }
        assertEquals(SELECT_TOGGLE_VALUE, value)
    }

    @Test(expected = IllegalStateException::class)
    fun `should throw exception if method does not have any annotation`() {
        togglesInvocationHandler.invoke(Any(), aNonAnnotatedMethod, arrayOf())
    }
}