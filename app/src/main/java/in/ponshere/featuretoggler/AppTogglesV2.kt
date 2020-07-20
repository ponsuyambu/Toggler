package `in`.ponshere.featuretoggler

import `in`.ponshere.toggler.v2.annotations.NumberOfToggles
import `in`.ponshere.toggler.v2.annotations.SelectToggle
import `in`.ponshere.toggler.v2.annotations.SwitchToggle
interface AppTogglesV2 {
    @SwitchToggle(
        key = "feature1",
        defaultValue = true
    )
    fun isFeatureAEnabled(): Boolean

    @SwitchToggle(key = "feature2",
        defaultValue = false)
    fun isFeature2Enabled(): Boolean

    @SelectToggle(
        key = "number_of_options",
        defaultValue = "3",
        selectOptions = ["1", "2", "3", "4"]
    )
    fun getFeature3Option(): String

    @NumberOfToggles
    fun totalNumberOfToggles() : Int

    @SwitchToggle(key = "new login", defaultValue = false)
    fun isNewLoginEnabled(): Boolean

//    @SwitchToggle(sharedPreferencesKey = "a", defaultValue = false)
//    fun a(): Boolean
//
//    @SwitchToggle(sharedPreferencesKey = "b", defaultValue = false)
//    fun b(): Boolean
//
//    @SwitchToggle(sharedPreferencesKey = "c", defaultValue = false)
//    fun c(): Boolean
//
//    @SelectToggle(
//        sharedPreferencesKey = "d",
//        defaultValue = "2",
//        fireBaseRemoteConfigKey = "feature3",
//        selectOptions = ["1", "2", "3", "4"]
//    )
//    fun d(): String
//
//    @SwitchToggle(sharedPreferencesKey = "e", defaultValue = false)
//    fun e(): Boolean
//
//    @SwitchToggle(sharedPreferencesKey = "f", defaultValue = false)
//    fun f(): Boolean
//
//    @SwitchToggle(sharedPreferencesKey = "g", defaultValue = false)
//    fun g(): Boolean
//
//    @SelectToggle(
//        sharedPreferencesKey = "h",
//        defaultValue = "2",
//        selectOptions = ["1", "2", "3", "4"]
//    )
//    fun h(): String
//
//    @SwitchToggle(sharedPreferencesKey = "i", defaultValue = false)
//    fun i(): Boolean
//
//    @SwitchToggle(sharedPreferencesKey = "j", defaultValue = false)
//    fun j(): Boolean
//
//    @SwitchToggle(sharedPreferencesKey = "k", defaultValue = false)
//    fun k(): Boolean
//
//    @SelectToggle(
//        sharedPreferencesKey = "l",
//        defaultValue = "2",
//        selectOptions = ["1", "2", "3", "4"]
//    )
//    fun l(): String
//
//
//    @SwitchToggle
//    fun f1(): String
}