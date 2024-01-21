plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("ru.practicum.android.diploma.plugins.developproperties")
    id ("kotlin-kapt")
}

android {
    namespace = "ru.practicum.android.diploma"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "ru.practicum.android.diploma"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(type = "String", name = "HH_ACCESS_TOKEN", value = "\"${developProperties.hhAccessToken}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidX.core)
    implementation(libs.androidX.appCompat)
    // UI layer libraries
    implementation(libs.ui.material)
    implementation(libs.ui.constraintLayout)
    // region Unit tests
    testImplementation(libs.unitTests.junit)
    // endregion
    // region UI tests
    androidTestImplementation(libs.uiTests.junitExt)
    androidTestImplementation(libs.uiTests.espressoCore)
    // endregion

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    //Koin
    implementation(libs.koin.android)

    //Retrofit
    implementation(libs.retrofit)

    //Gson
    implementation(libs.gson)
    implementation(libs.converter.gson)

    //NavigationGraph
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //Fragment
    implementation(libs.androidx.fragment.ktx)
    implementation (libs.androidx.preference.ktx)

    //Recyclerview
    implementation (libs.androidx.recyclerview.selection)

    //Glide
    implementation(libs.glide)

    //ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    //Room
    //val room_version=("2.6.1")
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    // To use Kotlin annotation processing tool (kapt)
    kapt (libs.androidx.room.compiler)
    // To use Kotlin Symbol Processing (KSP)
    //ksp("androidx.room:room-compiler:2.6.1")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)

}
