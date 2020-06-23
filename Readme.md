# Toggler  ![master](https://github.com/ponsuyambu/Toggler/workflows/master/badge.svg?branch=master)

Handle the feature toggles in your Android app seamlessly

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
Create an interface with SwitchToggle annotation
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


