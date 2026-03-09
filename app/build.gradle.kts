plugins {
    alias(libs.plugins.android.application)
    // Kotlin Android support
    alias(libs.plugins.kotlin.android)

    // Kotlin Parcelize support for easier object serialization
    id("kotlin-parcelize")

    // KSP plugin
    alias(libs.plugins.google.ksp)

}

android {
    namespace = "com.abudsystem.techaudit"
    compileSdk =36

    defaultConfig {
        applicationId = "com.abudsystem.techaudit"
        minSdk = 30
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
    // Añade o modifica este bloque para sincronizar Kotlin
    kotlinOptions {
        jvmTarget = "11"
    }
    // Feature flags for the Android Gradle Plugin
    buildFeatures {
        // Enables View Binding to safely access views from layouts
        viewBinding = true
    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)



    implementation("androidx.recyclerview:recyclerview:1.3.2") // es una componente de avanzado para mostrar grandes conjuntos de datos que se pueden desplegaren una direccion especifico
    implementation("androidx.cardview:cardview:1.0.0") // es ek maquillaje para los componentes modernosy oprofesionales
    implementation("com.github.bumptech.glide:glide:4.16.0") // libreria de recursos visuales


    // ROOM DATABASE
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion") // Para Corutinas
    ksp("androidx.room:room-compiler:$roomVersion")      // El procesador


    // ViewModel y LiveData
    val lifecycle_version = "2.7.0"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

    //retrofit para peticioens http
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    //usan converter para transformar JSON a DataClases automaticamente
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //

}