package `in`.ponshere.toggler.helpers

import `in`.ponshere.toggler.Toggler
import `in`.ponshere.toggler.annotations.models.SelectToggleImpl
import `in`.ponshere.toggler.annotations.models.SwitchToggleImpl
import `in`.ponshere.toggler.annotations.models.Toggle
import `in`.ponshere.toggler.mocks.TogglesConfigWith3Toggles
import `in`.ponshere.toggler.mocks.aNonAnnotatedMethod
import `in`.ponshere.toggler.mocks.aSelectToggleWithoutAnyValuesMethod
import `in`.ponshere.toggler.mocks.aSwitchToggleWithoutAnyValuesMethod
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.lang.reflect.Method
import kotlin.reflect.jvm.javaMethod

const val SELECT_TOGGLE_VALUE = "VALUE"


internal class TogglesInvocationHandlerTest {

    @MockK
    private lateinit var factoryMock: ToggleFactory

    @MockK
    private lateinit var switchToggleMethodImplementationMock: SwitchToggleImpl
    @MockK
    private lateinit var selectToggleImplMock: SelectToggleImpl

    private  var cacheSpy = spyk(mutableMapOf<Method, Toggle<*>>())

    private val togglerMOck = mockk<Toggler>()

    private lateinit var togglesInvocationHandler: TogglesInvocationHandler

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        every { switchToggleMethodImplementationMock.resolvedValue() } returns false
        every { selectToggleImplMock.resolvedValue() } returns SELECT_TOGGLE_VALUE
        every { factoryMock.createSwitchToggle(any(), any()) } returns switchToggleMethodImplementationMock
        every { factoryMock.createSelectToggleMethod(any(), any()) } returns selectToggleImplMock
        togglesInvocationHandler = TogglesInvocationHandler(factoryMock, togglerMOck , cacheSpy)
        cacheSpy.clear()
    }

    @Test
    fun `should create switch toggle implementation if it is invoked first`() {
        val value = togglesInvocationHandler.invoke(Any(), aSwitchToggleWithoutAnyValuesMethod, arrayOf())
        verify {
            factoryMock.createSwitchToggle(any(), eq(aSwitchToggleWithoutAnyValuesMethod))
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
            factoryMock.createSelectToggleMethod(any(), eq(aSelectToggleWithoutAnyValuesMethod))
        }
        verify { cacheSpy[any()] = any() }
        assertEquals(SELECT_TOGGLE_VALUE, value)
        assertEquals(selectToggleImplMock, cacheSpy[aSelectToggleWithoutAnyValuesMethod])

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

    @Test
    fun `should return the number of toggles when @NumberOfToggles annotation is used`() {
        val listMock = mockk<MutableList<Toggle<*>>>()
        every { listMock.size } returns 3
        every { togglerMOck.allToggles } returns listMock


        val numberOfToggles = togglesInvocationHandler.invoke(Any(), TogglesConfigWith3Toggles::totalNumberOfToggles.javaMethod!!, arrayOf())

        assertEquals(3, numberOfToggles)
    }
}