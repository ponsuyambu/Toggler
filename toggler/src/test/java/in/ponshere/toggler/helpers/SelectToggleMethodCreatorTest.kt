package `in`.ponshere.toggler.helpers

import `in`.ponshere.toggler.annotations.FeatureToggleType
import `in`.ponshere.toggler.mocks.aSelectToggleWithAllValuesMethod
import `in`.ponshere.toggler.mocks.aSelectToggleWithoutAnyValuesMethod
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Assert.assertArrayEquals
import org.junit.Test

internal class SelectToggleMethodCreatorTest : ToggleMethodCreatorTest() {

    @Test
    fun `should use the provided shared preferences key`() {
        val selectToggle = toSelectToggle(aSelectToggleWithAllValuesMethod)

        val selectToggleMethod = toggleMethodCreator.createSelectToggleMethod(selectToggle, aSelectToggleWithAllValuesMethod)

        assertEquals(selectToggle.sharedPreferencesKey, selectToggleMethod.sharedPreferencesKey)
    }

    @Test
    fun `should use the provided firebase remote config key`() {
        val selectToggle = toSelectToggle(aSelectToggleWithAllValuesMethod)

        val selectToggleMethod = toggleMethodCreator.createSelectToggleMethod(selectToggle, aSelectToggleWithAllValuesMethod)

        assertEquals(selectToggle.fireBaseRemoteConfigKey, selectToggleMethod.firebaseConfigKey)
    }

    @Test
    fun `should use the provided select options`() {
        val selectToggle = toSelectToggle(aSelectToggleWithAllValuesMethod)

        val selectToggleMethod = toggleMethodCreator.createSelectToggleMethod(selectToggle, aSelectToggleWithAllValuesMethod)

        assertArrayEquals(selectToggle.selectOptions, selectToggleMethod.selectOptions)
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

    @Test
    fun `check feature toggle type`() {
        val selectToggle = toSelectToggle(aSelectToggleWithAllValuesMethod)

        val selectToggleMethod = toggleMethodCreator.createSelectToggleMethod(selectToggle, aSelectToggleWithAllValuesMethod)

        assertEquals(FeatureToggleType.SELECT, selectToggleMethod.featureToggleType)
    }
}