package `in`.ponshere.toggler.helpers

import `in`.ponshere.toggler.Toggler
import `in`.ponshere.toggler.annotations.NumberOfToggles
import `in`.ponshere.toggler.annotations.SelectToggle
import `in`.ponshere.toggler.annotations.SwitchToggle
import `in`.ponshere.toggler.toggles.Toggle
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
            return cache[method]?.resolvedValue(toggler.toggleValueProvider)!!
        } else {
            when {
                method.isAnnotationPresent(SwitchToggle::class.java) -> {
                    val switchToggleMethod =
                        toggleFactory.createSwitchToggle(
                            method.getAnnotation(
                                SwitchToggle::class.java
                            )!!,
                            method,
                            toggler.toggleValueProviderType
                        )
                    cache[method] = switchToggleMethod
                    return switchToggleMethod.resolvedValue(toggler.toggleValueProvider)
                }
                method.isAnnotationPresent(SelectToggle::class.java) -> {
                    val selectToggleMethod =
                        toggleFactory.createSelectToggleMethod(
                            method.getAnnotation(
                                SelectToggle::class.java
                            )!!,
                            method,
                            toggler.toggleValueProviderType
                        )
                    cache[method] = selectToggleMethod
                    return selectToggleMethod.resolvedValue(toggler.toggleValueProvider)
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