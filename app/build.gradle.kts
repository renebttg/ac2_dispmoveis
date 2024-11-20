plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.ac2dispmoveis"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.ac2dispmoveis"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.retrofit) // Retrofit
    implementation(libs.retrofit.gson) // Conversor Gson para JSON


    testImplementation(libs.junit)

}
