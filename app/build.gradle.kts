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

    // Dependencias estables que ya tenías bien
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)

    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("androidx.lifecycle:lifecycle-livedata:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.7.0")
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")


    implementation(libs.legacy.support.v4)
    implementation(libs.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
