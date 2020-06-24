package `in`.ponshere.toggler

import `in`.ponshere.toggler.mocks.aSelectToggleWithoutAllValuesMethod
import `in`.ponshere.toggler.mocks.aSelectToggleWithoutAnyValuesMethod
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Test

internal class SelectToggleMethodCreatorTest : ToggleMethodCreatorTest() {

    @Test
    fun `should use the provided shared preferences key`() {
        val selectToggle = toSelectToggle(aSelectToggleWithoutAllValuesMethod)

        val selectToggleMethod = toggleMethodCreator.createSelectToggleMethod(selectToggle, aSelectToggleWithoutAllValuesMethod)

        assertEquals(selectToggle.sharedPreferencesKey, selectToggleMethod.sharedPreferencesKey)
    }

    @Test
    fun `should use the provided firebase remote config key`() {
        val selectToggle = toSelectToggle(aSelectToggleWithoutAllValuesMethod)

        val selectToggleMethod = toggleMethodCreator.createSelectToggleMethod(selectToggle, aSelectToggleWithoutAllValuesMethod)

        assertEquals(selectToggle.fireBaseRemoteConfigKey, selectToggleMethod.firebaseConfigKey)
    }

    @Test
    fun `should use method name as shared preferences key if sharedPreferencesKey is not provided`() {
        val selectToggle = toSelectToggle(aSelectToggleWithoutAnyValuesMethod)

        val selectToggleMethod = toggleMethodCreator.createSelectToggleMethod(selectToggle, aSelectToggleWithoutAnyValuesMethod)

        Assert.assertEquals(aSelectToggleWithoutAnyValuesMethod.name, selectToggleMethod.sharedPreferencesKey)
    }

    @Test
    fun `should use default value as empty if default value is not provided`() {
        val selectToggle = toSelectToggle(aSelectToggleWithoutAnyValuesMethod)

        val selectToggleMethod = toggleMethodCreator.createSelectToggleMethod(selectToggle, aSelectToggleWithoutAnyValuesMethod)

        Assert.assertTrue(selectToggleMethod.defaultValue.isEmpty())
    }
}