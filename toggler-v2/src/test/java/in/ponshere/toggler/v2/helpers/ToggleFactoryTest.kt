package `in`.ponshere.toggler.v2.helpers

import `in`.ponshere.toggler.isEqualArrays
import `in`.ponshere.toggler.isEquals
import `in`.ponshere.toggler.mocks.aSelectToggleWithAllValuesMethod
import `in`.ponshere.toggler.mocks.aSwitchToggleWithAllValuesMethod
import `in`.ponshere.toggler.v2.annotations.SelectToggle
import `in`.ponshere.toggler.v2.annotations.SwitchToggle
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ToggleFactoryTest {
    private lateinit var toggleFactory: ToggleFactory

    @MockK
    private lateinit var switchToggleAnnotationMock : SwitchToggle

    @MockK
    private lateinit var selectToggleMock : SelectToggle

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        toggleFactory = ToggleFactory()
    }

    @Test
    fun `should create switch toggle`() {
        every { switchToggleAnnotationMock.key } returns "Key"
        every { switchToggleAnnotationMock.defaultValue } returns true
        every { switchToggleAnnotationMock.displayName } returns "display name"

        val toggle = toggleFactory.createSwitchToggle(switchToggleAnnotationMock, aSwitchToggleWithAllValuesMethod)

        toggle.key isEquals switchToggleAnnotationMock.key
        toggle.defaultValue isEquals switchToggleAnnotationMock.defaultValue
        toggle.displayName isEquals switchToggleAnnotationMock.displayName
        toggle.method isEquals aSwitchToggleWithAllValuesMethod
    }

    @Test
    fun `should create select toggle`() {
        every { selectToggleMock.key } returns "Key"
        every { selectToggleMock.defaultValue } returns "default value"
        every { selectToggleMock.displayName } returns "display name"
        every { selectToggleMock.selectOptions } returns arrayOf("1","2")

        val toggle = toggleFactory.createSelectToggleMethod(selectToggleMock, aSelectToggleWithAllValuesMethod)

        toggle.key isEquals selectToggleMock.key
        toggle.defaultValue isEquals selectToggleMock.defaultValue
        toggle.displayName isEquals selectToggleMock.displayName
        toggle.selectOptions isEqualArrays selectToggleMock.selectOptions
        toggle.method isEquals aSelectToggleWithAllValuesMethod
    }
}