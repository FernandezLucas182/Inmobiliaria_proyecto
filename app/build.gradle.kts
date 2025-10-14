plugins {
    alias(libs.plugins.android.application)
    // Añadimos el plugin de Kotlin que es necesario
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.inmob"
    // Usamos las versiones estables del archivo TOML
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.inmob"
        minSdk = 26// Lo bajamos para ser compatible como tu amigo
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        // Usamos Java 17, el estándar actual
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    // Necesario para que Kotlin y Java 17 funcionen juntos
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Core AndroidX
    implementation(libs.androidx.appcompat)
    implementation(libs.com.google.android.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.legacy.support.v4)

    // Lifecycle
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.androidx.lifecycle.viewmodel)

    // Navigation
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    // Networking (Retrofit)
    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.converter.gson)
    implementation(libs.squareup.converter.scalars)
    implementation(libs.squareup.okhttp.interceptor)

    // Mapas
    implementation(libs.google.play.services.maps)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
