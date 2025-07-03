import com.chalo.buildsrc.Modules

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinSerialization)
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.cholo.history"
}

dependencies {
    implementation(project(Modules.CORE))
    implementation(project(Modules.MQTT))
    implementation(project(Modules.RATING))
    implementation(project(Modules.TICKET))
}
