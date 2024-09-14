plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.kapt)
}

android {
    namespace = "com.project.autoservicemobile"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.project.autoservicemobile"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

//        buildConfigField("String", "WEATHER_API_KEY", "\"0ab1b21790762bc0577c8fd4b075fb23\"")
        buildConfigField("String", "LOGIN_API_BASE_URL", "\"api/account/\"")
        buildConfigField("String", "API_BASE_URL", "\"https://192.168.0.16:7130/\"")
//        buildConfigField("String", "API_BASE_URL", "\"https://192.168.1.205:7130/\"")
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
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.dagger.hilt.android)
    implementation(project(":autoserviceDatabase"))
    kapt(libs.dagger.hilt.compiler)
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation(libs.kotlinx.serialization.json)

    implementation("com.squareup.picasso:picasso:2.71828")

    implementation(project(":autoserviceAPI"))
    implementation(project(":autoserviceData"))
}