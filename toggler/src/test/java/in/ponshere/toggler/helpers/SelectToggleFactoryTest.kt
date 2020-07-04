package `in`.ponshere.toggler.helpers

import `in`.ponshere.toggler.mocks.aSelectToggleWithAllValuesMethod
import `in`.ponshere.toggler.mocks.aSelectToggleWithoutAnyValuesMethod
import `in`.ponshere.toggler.providers.ToggleValueProvider
import `in`.ponshere.toggler.toggles.Toggle
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertTrue
import org.junit.Test

internal class SelectToggleFactoryTest : ToggleFactoryTest() {

    @Test
    fun `should use the provided shared preferences key`() {
        val selectToggle = toSelectToggle(aSelectToggleWithAllValuesMethod)

        val selectToggleMethod = toggleFactory.createSelectToggleMethod(selectToggle, aSelectToggleWithAllValuesMethod, ToggleValueProvider.Type.LOCAL)

        assertEquals(selectToggle.sharedPreferencesKey, selectToggleMethod.sharedPreferencesKey)
    }

    @Test
    fun `should use the provided firebase remote config key`() {
        val selectToggle = toSelectToggle(aSelectToggleWithAllValuesMethod)

        val selectToggleMethod = toggleFactory.createSelectToggleMethod(selectToggle, aSelectToggleWithAllValuesMethod, ToggleValueProvider.Type.FIREBASE)

        assertEquals(selectToggle.fireBaseRemoteConfigKey, selectToggleMethod.firebaseConfigKey)
    }

    @Test
    fun `should use the provided select options`() {
        val selectToggle = toSelectToggle(aSelectToggleWithAllValuesMethod)

        val selectToggleMethod = toggleFactory.createSelectToggleMethod(selectToggle, aSelectToggleWithAllValuesMethod, ToggleValueProvider.Type.LOCAL)

        assertArrayEquals(selectToggle.selectOptions, selectToggleMethod.selectOptions)
    }


    @Test
    fun `should use method name as shared preferences key if sharedPreferencesKey is not provided`() {
        val selectToggle = toSelectToggle(aSelectToggleWithoutAnyValuesMethod)

        val selectToggleMethod = toggleFactory.createSelectToggleMethod(selectToggle, aSelectToggleWithoutAnyValuesMethod, ToggleValueProvider.Type.LOCAL)

        Assert.assertEquals(aSelectToggleWithoutAnyValuesMethod.name, selectToggleMethod.sharedPreferencesKey)
    }

    @Test
    fun `should use default value as empty if default value is not provided`() {
        val selectToggle = toSelectToggle(aSelectToggleWithoutAnyValuesMethod)

        val selectToggleMethod = toggleFactory.createSelectToggleMethod(selectToggle, aSelectToggleWithoutAnyValuesMethod, ToggleValueProvider.Type.LOCAL)

        Assert.assertTrue(selectToggleMethod.defaultValue.isEmpty())
    }

    @Test
    fun `check feature toggle type`() {
        val selectToggle = toSelectToggle(aSelectToggleWithAllValuesMethod)

        val selectToggleMethod = toggleFactory.createSelectToggleMethod(selectToggle, aSelectToggleWithAllValuesMethod, ToggleValueProvider.Type.LOCAL)

        assertTrue(selectToggleMethod.toggleType is Toggle.Type.Select)
    }
}