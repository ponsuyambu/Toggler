package `in`.ponshere.toggler.helpers

import `in`.ponshere.toggler.annotations.SelectToggle
import `in`.ponshere.toggler.annotations.SwitchToggle
import io.mockk.mockk
import org.junit.Before
import java.lang.reflect.Method

internal open class ToggleFactoryTest {
    protected lateinit var toggleFactory : ToggleFactory

    @Before
    fun setup() {
        toggleFactory =
            ToggleFactory(mockk())
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