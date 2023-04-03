plugins {
    BuildPlugin
    com.android.library
    `kotlin-android`
    `kotlin-kapt`
}

dependencies {
    implementation(project(":core"))

    implementation(Dependencies.RoomRuntime)
    implementation(Dependencies.RoomKotlin)
    implementation(Dependencies.RoomAndroid)
    implementation(Dependencies.Dagger)
    implementation(Dependencies.DaggerAndroid)
    implementation(Dependencies.DaggerAndroidSupport)

    kapt(Dependencies.Kapt.DaggerCompiler)
    kapt(Dependencies.Kapt.DaggerCompilerAndroid)
    kapt(Dependencies.Kapt.RoomCompiler)
}