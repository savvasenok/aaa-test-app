plugins {
    BuildPlugin
    com.android.library
    `kotlin-kapt`
    `kotlin-android`
}

dependencies {

    implementation(project(":core"))
    implementation(project(":networking"))
    implementation(project(":storage"))
    implementation(project(":domain"))

    implementation(Dependencies.Dagger)
    implementation(Dependencies.Coroutines)
    // implementation(Dependencies.DaggerAndroid)
    // implementation(Dependencies.DaggerAndroidSupport)
}