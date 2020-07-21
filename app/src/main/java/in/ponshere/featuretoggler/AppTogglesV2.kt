package `in`.ponshere.featuretoggler

import `in`.ponshere.toggler.firebase.provider.FirebaseToggle
import `in`.ponshere.toggler.v2.annotations.NumberOfToggles
import `in`.ponshere.toggler.v2.annotations.SelectToggle
import `in`.ponshere.toggler.v2.annotations.SwitchToggle
interface AppTogglesV2 {
    @SwitchToggle(
        key = "feature1",
        defaultValue = true
    )
    @FirebaseToggle
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

    @SwitchToggle(key = "a", defaultValue = false)
    fun a(): Boolean

    @SwitchToggle(key = "b", defaultValue = false)
    fun b(): Boolean

    @SwitchToggle(key = "c", defaultValue = false)
    fun c(): Boolean

    @SelectToggle(
        key = "d",
        defaultValue = "2",
        selectOptions = ["1", "2", "3", "4"]
    )
    fun d(): String

    @SwitchToggle(key = "e", defaultValue = false)
    fun e(): Boolean

    @SwitchToggle(key = "f", defaultValue = false)
    fun f(): Boolean

    @SwitchToggle(key = "g", defaultValue = false)
    fun g(): Boolean

    @SelectToggle(
        key = "h",
        defaultValue = "2",
        selectOptions = ["1", "2", "3", "4"]
    )
    fun h(): String

    @SwitchToggle(key = "i", defaultValue = false)
    fun i(): Boolean

    @SwitchToggle(key = "j", defaultValue = false)
    fun j(): Boolean

    @SwitchToggle(key = "k", defaultValue = false)
    fun k(): Boolean

    @SelectToggle(
        key = "l",
        defaultValue = "2",
        selectOptions = ["1", "2", "3", "4"]
    )
    fun l(): String


    @SwitchToggle
    fun f1(): String
}