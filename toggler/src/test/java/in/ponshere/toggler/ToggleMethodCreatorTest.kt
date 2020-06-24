package `in`.ponshere.toggler

import `in`.ponshere.toggler.annotations.SelectToggle
import `in`.ponshere.toggler.annotations.SwitchToggle
import `in`.ponshere.toggler.helpers.ToggleMethodCreator
import io.mockk.mockk
import org.junit.Before
import java.lang.reflect.Method

internal open class ToggleMethodCreatorTest {
    protected lateinit var toggleMethodCreator : ToggleMethodCreator

    @Before
    fun setup() {
        toggleMethodCreator =
            ToggleMethodCreator(mockk())
    }

    protected fun toSelectToggle(aSelectToggleMethod: Method): SelectToggle {
        return aSelectToggleMethod.getAnnotation(
            SelectToggle::class.java
        )!! // it is a select toggle, so can't be null.
    }

    protected fun toSwitchToggle(aSwitchToggleMethod: Method): SwitchToggle {
        return aSwitchToggleMethod.getAnnotation(
            SwitchToggle::class.java
        )!! // it is a switch toggle, so can't be null.
    }
}