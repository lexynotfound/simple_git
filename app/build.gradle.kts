plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.googleDevtoolsKsp)
    id("androidx.navigation.safeargs.kotlin")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23"
}

android {
    namespace = "com.gege.gitgege"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.gege.gitgege"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")
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
        buildConfig = true
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
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.kotlinx.serialization.json)
    implementation (libs.androidx.lifecycle.extensions)
    implementation (libs.glide)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.runtime.ktx)
    implementation (libs.androidx.lifecycle.livedata.ktx)
    implementation (libs.circleimageview)
    implementation (libs.androidx.core.splashscreen)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation (libs.androidx.room.runtime)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.datastore.core)
    annotationProcessor (libs.compiler)
    implementation(libs.androidx.preference.ktx)
    implementation (libs.annotations)
    implementation (libs.shimmer)
    implementation (libs.retrofit)
    implementation(libs.androidx.datastore.preferences)
    ksp(libs.androidx.room.compiler)
    implementation(libs.picasso)
    implementation(libs.androidx.room.ktx)
    implementation (libs.converter.gson)
    ksp(libs.androidx.room.compiler)

}