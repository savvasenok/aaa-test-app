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

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.withType<Test> {
    useJUnitPlatform()
}