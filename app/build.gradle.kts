plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.attendancemarker"  // Update this if your package name differs
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.attendancemarker"  // Ensure this matches your intended package
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true  // Enables View Binding for your layouts
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    // QR Code generation
    implementation("com.google.zxing:core:3.5.2")
    // Permissions handling
    implementation("com.karumi:dexter:6.2.3")
    // Optional: QR scanning library (uncomment if you add scanning later)
    // implementation("com.journeyapps:zxing-android-embedded:4.3.0")
}