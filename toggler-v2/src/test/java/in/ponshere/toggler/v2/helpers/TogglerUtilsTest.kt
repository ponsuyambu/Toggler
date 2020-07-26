package `in`.ponshere.toggler.v2.helpers

import `in`.ponshere.toggler.isEquals
import `in`.ponshere.toggler.mocks.aSwitchToggleWithAllValuesMethod
import org.junit.jupiter.api.Test

class TogglerUtilsTest {

    @Test
    fun `should return key if it is not blank`() {
        val key = "key"

        val resolvedKey = TogglerUtils.resolveKey(key, aSwitchToggleWithAllValuesMethod)

        resolvedKey isEquals key
    }

    @Test
    fun `should return method name as key if it is blank`() {
        val blankKey = ""

        val resolvedKey = TogglerUtils.resolveKey(blankKey, aSwitchToggleWithAllValuesMethod)

        resolvedKey isEquals aSwitchToggleWithAllValuesMethod.name
    }
}