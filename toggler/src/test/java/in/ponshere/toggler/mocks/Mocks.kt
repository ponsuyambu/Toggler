package `in`.ponshere.toggler.mocks

import `in`.ponshere.toggler.annotations.SelectToggle
import `in`.ponshere.toggler.annotations.SwitchToggle
import kotlin.reflect.jvm.javaMethod

val aSwitchToggleWithoutAnyValuesMethod = MockToggles::aSwitchToggleWithoutAnyValues.javaMethod!! //this can't be null, hence its ok to use !!
val aSelectToggleWithoutAnyValuesMethod = MockToggles::aSelectToggleWithoutAnyValues.javaMethod!! //this can't be null, hence its ok to use !!

interface MockToggles {
    @SwitchToggle
    fun aSwitchToggleWithoutAnyValues() : Boolean

    @SelectToggle
    fun aSelectToggleWithoutAnyValues() : String
}