plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.inmob"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.inmob"
        minSdk = 33
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Para Retrofit (el cliente HTTP)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

// Para GSON (el conversor de JSON a objetos Java/Kotlin)
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

// Opcional pero recomendado: Para ver logs de las llamadas a la API en el Logcat
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.legacy.support.v4)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}