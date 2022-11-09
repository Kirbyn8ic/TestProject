import org.jetbrains.kotlin.konan.properties.loadProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.app.testproject"
    compileSdk = 33

    defaultConfig {
        applicationId = AppConfig.packageName
        minSdk = 21
        targetSdk = 33
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // 添加资源
        resValue("string", "app_name", AppConfig.appName)
        resValue("string", "facebook_app_id", AppConfig.facebookId)
        resValue("string", "facebook_client_token", AppConfig.facebookToken)
        // 添加java常量
        buildConfigField("String", "BASE_URL", "\"${AppConfig.baseUrl}\"")
        // 给AndroidManifest.xml添加变量
        manifestPlaceholders["ADMOB_APPLICATION_ID"] = AppConfig.admobId
    }

    signingConfigs {
        register("release") {
            val keyProperties = loadProperties(file("key.properties").path)
            storeFile = file(keyProperties.getProperty("storeFile"))
            storePassword = keyProperties.getProperty("storePassword")
            keyAlias = keyProperties.getProperty("keyAlias")
            keyPassword = keyProperties.getProperty("keyPassword")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xcontext-receivers")
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:${Versions.appcompat}")
    implementation("com.google.android.material:material:1.8.0-alpha02")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // Facebook Core only (Analytics)
    implementation("com.facebook.android:facebook-core:latest.release")
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:31.0.2"))
    // Add the dependency for the Firebase SDK for Google Analytics
    implementation("com.google.firebase:firebase-analytics-ktx:21.2.0")
    implementation("com.google.firebase:firebase-crashlytics-ktx:18.3.1")
    implementation("com.google.firebase:firebase-crashlytics-ndk")
}
