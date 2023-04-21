plugins {
    BuildPlugin
    kotlin
    `kotlin-kapt`
}

dependencies {
    implementation(project(":core"))

    implementation(Dependencies.Dagger)
    implementation(Dependencies.Coroutines)

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.withType<Test> {
    useJUnitPlatform()
}