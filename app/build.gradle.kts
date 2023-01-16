import io.lostimagin4tion.vkcup.CompileVersions
import io.lostimagin4tion.vkcup.Dependencies

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = CompileVersions.CURRENT_COMPILE_VERSION
    namespace = "io.lostimagin4tion.vkcup"

    defaultConfig {
        applicationId = namespace!!
        minSdk = CompileVersions.MINIMUM_COMPILE_VERSION
        targetSdk = CompileVersions.CURRENT_COMPILE_VERSION
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = CompileVersions.JAVA_COMPILE_VERSION
        targetCompatibility = CompileVersions.JAVA_COMPILE_VERSION
    }

    kotlinOptions {
        jvmTarget = CompileVersions.JVM_VERSION
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Compose.COMPOSE_VERSION
    }
    
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Core
    Dependencies.AndroidCore.ALL_DEPS.forEach { implementation(it) }

    // Coroutines
    implementation(Dependencies.Coroutines.ANDROID)

    // Compose
    Dependencies.Compose.ALL_DEPS.forEach { implementation(it) }
    Dependencies.Compose.Core.ALL_CORE_DEBUG_DEPS.forEach { debugImplementation(it) }

    // UI components
    Dependencies.UI.ALL_DEPS.forEach { implementation(it) }

    // Dagger
    implementation(Dependencies.Dagger.ANDROID)
    Dependencies.Dagger.KAPT_DEPS.forEach { kapt(it) }
}