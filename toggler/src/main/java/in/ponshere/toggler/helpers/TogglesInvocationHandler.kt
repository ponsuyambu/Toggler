package `in`.ponshere.toggler.helpers

import `in`.ponshere.toggler.Toggler
import `in`.ponshere.toggler.annotations.NumberOfToggles
import `in`.ponshere.toggler.annotations.SelectToggle
import `in`.ponshere.toggler.annotations.SwitchToggle
import `in`.ponshere.toggler.annotations.models.BaseToggleMethodImplementation
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.util.*

internal class TogglesInvocationHandler(private val methodCreator: ToggleMethodCreator,
                                        private val toggler: Toggler,
                                        //Inject cache for testing
                                        private val cache : MutableMap<Method, BaseToggleMethodImplementation<*>> = mutableMapOf()) :
    InvocationHandler {

    @Throws(Throwable::class)
    override operator fun invoke(proxy: Any?, method: Method, args: Array<Any?>?): Any {
        if (cache[method] != null) {
            return cache[method]?.value()!!
        } else {
            when {
                method.isAnnotationPresent(SwitchToggle::class.java) -> {
                    val switchToggleMethod =
                        methodCreator.createSwitchToggleMethod(
                            method.getAnnotation(
                                SwitchToggle::class.java
                            )!!,
                            method,
                            toggler.toggleValueProvider
                        )
                    cache[method] = switchToggleMethod
                    return switchToggleMethod.value()
                }
                method.isAnnotationPresent(SelectToggle::class.java) -> {
                    val selectToggleMethod =
                        methodCreator.createSelectToggleMethod(
                            method.getAnnotation(
                                SelectToggle::class.java
                            )!!,
                            method,
                            toggler.toggleValueProvider
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