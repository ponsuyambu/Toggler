package `in`.ponshere.toggler.v2.helpers

import `in`.ponshere.toggler.v2.Toggler
import `in`.ponshere.toggler.v2.annotations.NumberOfToggles
import `in`.ponshere.toggler.v2.annotations.SelectToggle
import `in`.ponshere.toggler.v2.annotations.SwitchToggle
import `in`.ponshere.toggler.v2.toggles.Toggle
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.util.*

internal class TogglesInvocationHandler(private val toggleFactory: ToggleFactory,
                                        private val toggler: Toggler,
                                        //Inject cache for testing
                                        private val cache : MutableMap<Method, Toggle<*>> = mutableMapOf()) :
    InvocationHandler {

    @Throws(Throwable::class)
    override operator fun invoke(proxy: Any?, method: Method, args: Array<Any?>?): Any {
        if (cache[method] != null) {
            return cache[method]?.value()!!
        } else {
            when {
                method.isAnnotationPresent(SwitchToggle::class.java) -> {
                    val switchToggleMethod =
                        toggleFactory.createSwitchToggle(
                            method.getAnnotation(
                                SwitchToggle::class.java
                            )!!,
                            method
                        )
                    cache[method] = switchToggleMethod
                    return switchToggleMethod.value()
                }
                method.isAnnotationPresent(SelectToggle::class.java) -> {
                    val selectToggleMethod =
                        toggleFactory.createSelectToggleMethod(
                            method.getAnnotation(
                                SelectToggle::class.java
                            )!!,
                            method
                        )
                    cache[method] = selectToggleMethod
                    return selectToggleMethod.value()
                }
                method.isAnnotationPresent(NumberOfToggles::class.java) -> {
                    return toggler.allToggles.size
                }
                method.name == "equals" -> {
                    if(args != null) {
                        return Objects.equals(proxy, args[0])
                    }
                    return false
                }
            }
        }

        throw IllegalStateException()
    }

}