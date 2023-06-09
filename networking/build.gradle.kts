@file:Suppress("DEPRECATION")

plugins {
    BuildPlugin
    java
    kotlin
    `kotlin-kapt`
    idea
}

sourceSets.main.get().java.srcDirs += (sourceSets.main.get() as org.gradle.api.internal.HasConvention)
    .convention
    .getPlugin(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class.java)
    .kotlin

dependencies {
    implementation(project(":unwrapretrofit"))
    implementation(project(":core"))
    implementation(Dependencies.Retrofit)
    implementation(Dependencies.OkHttp)

    implementation(Dependencies.Moshi)
    kapt(Dependencies.Kapt.MoshiCodegen)

    implementation(Dependencies.KotlinStdLib)
}

idea {
    module {
        sourceDirs.addAll(files("build/generated/source/kapt/main", "build/generated/source/kaptKotlin/main"))
        generatedSourceDirs.addAll(files("build/generated/source/kapt/main", "build/generated/source/kaptKotlin/main"))
    }
}
