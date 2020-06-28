# Toggler  ![master](https://github.com/ponsuyambu/Toggler/workflows/master/badge.svg?branch=master) [ ![Download](https://api.bintray.com/packages/suyambu/android/toggler/images/download.svg) ](https://bintray.com/suyambu/android/toggler/_latestVersion)  [![codecov](https://codecov.io/gh/ponsuyambu/Toggler/branch/master/graph/badge.svg)](https://codecov.io/gh/ponsuyambu/Toggler)  [![Maintainability](https://api.codeclimate.com/v1/badges/f5e9af491e5cbc1b88f3/maintainability)](https://codeclimate.com/github/ponsuyambu/Toggler/maintainability)

Handle the feature toggles in your Android app seamlessly!

## Setup

### Dependency
```groovy
repositories {
    maven { url 'https://dl.bintray.com/suyambu/android/' }
}
dependencies {
    implementation 'in.ponshere:toggler:0.0.4'
}
```

### Toggles configuration
#### Basic usage
##### 1. Create an interface with SwitchToggle annotation

```kotlin
interface AppToggles {
    @SwitchToggle(
        sharedPreferencesKey = "featureA",
        defaultValue = true,
        fireBaseRemoteConfigKey = "featureA"
    )
    fun isFeatureAEnabled(): Boolean
}
```
##### 2. Initialize the toggler in application class
```kotlin
class TogglerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val appToggles = Toggler.init(this, AppToggles::class.java)
    }
}
```
##### 3. Use the toggle
``` kotlin
if(Toggler.get<AppToggles>().isFeatureAEnabled()) {
    //enable Feature A
}
```
or you can also use the `appToggles` instance created in application class.
``` kotlin
if(appToggles.isFeatureAEnabled()) {
    //enable Feature A
}
```
##### 4. Showing all toggles
To show all of the toggles, use the below API. In the screen, you can easily enable/disable/change toggle values.
```kotlin
Toggler.showAllToggles(context)
```

## Contributing
Please fork this repository and contribute back using pull requests. Features can be requested using issues. All code, comments, and critiques are greatly appreciated.




