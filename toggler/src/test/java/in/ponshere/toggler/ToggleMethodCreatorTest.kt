package `in`.ponshere.toggler

import `in`.ponshere.toggler.annotations.SelectToggle
import `in`.ponshere.toggler.annotations.SwitchToggle
import `in`.ponshere.toggler.mocks.aSelectToggleWithoutAnyValuesMethod
import `in`.ponshere.toggler.mocks.aSwitchToggleWithoutAnyValuesMethod
import io.mockk.mockk
import org.junit.Before

internal open class ToggleMethodCreatorTest {
    protected lateinit var toggleMethodCreator : ToggleMethodCreator

    @Before
    fun setup() {
        toggleMethodCreator = ToggleMethodCreator(mockk())
    }

    protected fun toSelectToggle(): SelectToggle {
        return aSelectToggleWithoutAnyValuesMethod.getAnnotation(
            SelectToggle::class.java
        )!! // it is a select toggle, so can't be null.
    }

    protected fun toSwitchToggle(): SwitchToggle {
        return aSwitchToggleWithoutAnyValuesMethod.getAnnotation(
            SwitchToggle::class.java
        )!! // it is a switch toggle, so can't be null.
    }
}