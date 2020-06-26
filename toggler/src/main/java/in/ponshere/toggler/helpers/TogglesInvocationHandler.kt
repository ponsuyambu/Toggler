package `in`.ponshere.toggler.helpers

import `in`.ponshere.toggler.annotations.SelectToggle
import `in`.ponshere.toggler.annotations.SwitchToggle
import `in`.ponshere.toggler.annotations.models.BaseToggleMethodImplementation
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

internal class TogglesInvocationHandler(private val methodCreator: ToggleMethodCreator,
                                        //Inject cache for testing
                                        private val cache : MutableMap<Method, BaseToggleMethodImplementation<*>> = mutableMapOf()) :
    InvocationHandler {

    @Throws(Throwable::class)
    override operator fun invoke(proxy: Any?, method: Method, args: Array<Any?>?): Any {
        if (cache[method] != null) {
            return cache[method]?.value()!!
        } else {
            if (method.isAnnotationPresent(SwitchToggle::class.java)) {
                val switchToggleMethod =
                    methodCreator.createSwitchToggleMethod(
                        method.getAnnotation(
                            SwitchToggle::class.java
                        )!!,
                        method
                    )
                cache[method] = switchToggleMethod
                return switchToggleMethod.value()
            } else if (method.isAnnotationPresent(SelectToggle::class.java)) {
                val selectToggleMethod =
                    methodCreator.createSelectToggleMethod(
                        method.getAnnotation(
                            SelectToggle::class.java
                        )!!,
                        method
                    )
                cache[method] = selectToggleMethod
                return selectToggleMethod.value()
            }
        }
        throw IllegalStateException()
    }

}