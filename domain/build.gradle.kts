plugins {
    BuildPlugin
    kotlin
    `kotlin-kapt`
}

dependencies {
    implementation(project(":core"))

    implementation(Dependencies.Dagger)
    implementation(Dependencies.Coroutines)
}