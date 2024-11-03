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

        buildConfigField("String", "NEWS_API_KEY", "\"c4bf3d2d4c9a4b278aec23a1c09203c0\"")
        buildConfigField("String", "NEWS_API_URL", "\"https://newsapi.org/v2/\"")

        buildConfigField("String", "REGISTRATIONS_API_BASE_URL", "\"api/Registrations/\"")
        buildConfigField("String", "SLOTS_API_BASE_URL", "\"api/Slots/\"")
        buildConfigField("String", "CART_API_BASE_URL", "\"api/Cart/\"")
        buildConfigField("String", "CARS_API_BASE_URL", "\"api/Cars/\"")
        buildConfigField("String", "BREAKDOWNS_API_BASE_URL", "\"api/Breakdowns/\"")
        buildConfigField("String", "LOGIN_API_BASE_URL", "\"api/account/\"")
        buildConfigField("String", "API_BASE_URL", "\"https://192.168.0.16:7130/\"")
        //buildConfigField("String", "API_BASE_URL", "\"https://192.168.1.205:7130/\"")
        //buildConfigField("String", "API_BASE_URL", "\"http://10.0.2.2:8000/\"")
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
    implementation(project(":common"))

    kapt(libs.dagger.hilt.compiler)
    implementation(libs.logging.interceptor)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.picasso)

    implementation(project(":autoserviceAPI"))
    implementation(project(":newsAPI"))
    implementation(project(":newsData"))
    implementation(project(":autoserviceData"))
    implementation(project(":autoserviceDatabase"))
    implementation(project(":token"))
}