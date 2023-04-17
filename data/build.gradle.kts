plugins {
    BuildPlugin
    com.android.library
    `kotlin-kapt`
    `kotlin-android`
}

dependencies {
    testImplementation(kotlin("test"))

    implementation(project(":core"))
    implementation(project(":networking"))
    implementation(project(":storage"))
    implementation(project(":domain"))

    implementation(Dependencies.Dagger)
    implementation(Dependencies.Coroutines)

    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.withType<Test> {
    useJUnitPlatform()
}