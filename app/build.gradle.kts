plugins {
    BuildPlugin
    com.android.application
    id("com.google.android.gms.oss-licenses-plugin")
    id("at.allaboutapps.gradle-plugin")
    `kotlin-android`
    `kotlin-kapt`
    id("androidx.navigation.safeargs.kotlin")
    `kotlin-parcelize`
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    defaultConfig {
        applicationId = "xyz.savvamirzoyan.allaboutapps"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        multiDexEnabled = true

        addManifestPlaceholders(mapOf("apiKey" to "secret")) // use with ${apiKey} in manifest

        resConfigs("de")
    }


    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        // Flag to enable support for the new language APIs
        // https://developer.android.com/studio/write/java8-support
        isCoreLibraryDesugaringEnabled = true
    }

    buildTypes {
        getByName("debug") {
            // Speed up debug builds
            aaptOptions.cruncherEnabled = false
            // prevent crashlytics from updating its id
            ext["alwaysUpdateBuildId"] = false
        }
    }

    flavorDimensions("environment")

    productFlavors {
        create("development") {
            dimension = "environment"
            ext["neverBuildRelease"] = true

            buildConfigField("String", "SERVER_API_URL", "\"https://public.allaboutapps.at/hiring/\"")
        }
        create("staging") {
            dimension = "environment"
            ext["neverBuildRelease"] = true

            buildConfigField("String", "SERVER_API_URL", "\"https://public.allaboutapps.at/hiring/\"")
        }
        create("live") {
            dimension = "environment"

            buildConfigField("String", "SERVER_API_URL", "\"https://public.allaboutapps.at/hiring/\"")
        }
    }

    packagingOptions {
        exclude("LICENSE.txt")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/NOTICE")
    }

    dexOptions {
        javaMaxHeapSize = "4g"
        preDexLibraries = true
    }
}

dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")

    implementation(project(":core"))
    implementation(project(":storage"))
    implementation(project(":networking"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":unwrapretrofit"))
    implementation(project(":glide"))

    implementation(Dependencies.KotlinStdLib)
    implementation(Dependencies.MaterialComponents)
    implementation(Dependencies.AndroidxSwipeRefreshLayout)

    implementation(platform(Dependencies.FirebaseBom))
    // Firebase Libs
    implementation(Dependencies.FirebaseCrashlytics)
    implementation(Dependencies.FirebaseMessaging)
    implementation(Dependencies.FirebaseAnalytics)

    // ViewBinding helper
    implementation(Dependencies.ViewBindingDelegate)

    // aaa libs
    implementation(Dependencies.A3Utilities)
    implementation(Dependencies.A3RecyclerViewDecorations)

    // Splashscreen
    implementation(Dependencies.AndroidXSplashScreen)

    // Android Kotlin Extensions by Google
    // https://developer.android.com/kotlin/ktx
    implementation(Dependencies.AndroidXCoreKtx)
    implementation(Dependencies.AndroidXPreferenceManager)

    // Support library depends on this lightweight import
    implementation(Dependencies.AndroidXLifecycleViewModel)
    implementation(Dependencies.AndroidXLifecycleLiveData)

    kapt(Dependencies.Kapt.AndroidXLifecycleCompiler)

    // optional - ReactiveStreams support for LiveData
    implementation(Dependencies.AndroidXLifecycleReactiveStreams)

    // Play Services Licenses Lib
    implementation(Dependencies.PlayServicesLicenses)

    // logging
    implementation(Dependencies.Timber)

    // networking
    implementation(Dependencies.RetrofitConverterMoshi)

    implementation(Dependencies.OkHttpLoggingInterceptor)
    implementation(Dependencies.Moshi)
    implementation(Dependencies.MoshiAdapters)

    // dependency injection
    implementation(Dependencies.Dagger)
    implementation(Dependencies.DaggerAndroid)
    implementation(Dependencies.DaggerAndroidSupport)
    kapt(Dependencies.Kapt.DaggerCompiler)
    kapt(Dependencies.Kapt.DaggerCompilerAndroid)

    // Leak Canary
    // debugImplementation because LeakCanary should only run in debug builds.
    debugImplementation(Dependencies.LeakCanary)

    implementation(Dependencies.AndroidXNavigationFragment)
    implementation(Dependencies.AndroidXNavigationUI)
}

plugins.apply("com.google.gms.google-services")

fun osIsWindows(): Boolean {
    return System.getProperty("os.name").contains("windows", ignoreCase = true)
}

tasks.register("updateStrings", Exec::class) {
    group = "localization"

    val executableName =
        if (osIsWindows()) {
            listOf("cmd", "/c", "texterify")
        } else {
            listOf("texterify")
        }

    commandLine = (executableName + "download")
}
