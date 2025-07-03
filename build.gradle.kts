plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("maven-publish")
}

android {
    namespace = "com.chalo.history"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    
    kotlinOptions {
        jvmTarget = "1.8"
    }
    
    buildFeatures {
        compose = true
    }
    
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.5"
    }
}

dependencies {
    // Core Android dependencies
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    
    // Compose BOM
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    
    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    
    // Replace your original dependencies with external ones
    // You'll need to either:
    // 1. Remove dependencies on CORE, MQTT, RATING, TICKET
    // 2. Or bundle their source code into this library
    // 3. Or publish them separately and reference them
    
    // For now, comment out the original dependencies:
    // implementation(project(Modules.CORE))
    // implementation(project(Modules.MQTT))
    // implementation(project(Modules.RATING))
    // implementation(project(Modules.TICKET))
    
    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                
                groupId = "com.github.AppsDevMaruf"
                artifactId = "history"
                version = "1.0.0"
                
                pom {
                    name.set("Chalo History Library")
                    description.set("History management library for Android applications")
                    url.set("https://github.com/AppsDevMaruf/history")
                    
                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }
                    
                    developers {
                        developer {
                            id.set("AppsDevMaruf")
                            name.set("Maruf")
                            email.set("your.email@example.com")
                        }
                    }
                    
                    scm {
                        connection.set("scm:git:git://github.com/AppsDevMaruf/history.git")
                        developerConnection.set("scm:git:ssh://github.com/AppsDevMaruf/history.git")
                        url.set("https://github.com/AppsDevMaruf/history")
                    }
                }
            }
        }
    }
}
