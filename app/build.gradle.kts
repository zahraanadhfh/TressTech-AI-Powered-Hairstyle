plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.example.tresstech"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tresstech"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        mlModelBinding = true
    }
}

dependencies {
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("com.google.android.gms:play-services-base:18.0.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.google.android.libraries.places:places:2.6.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation("androidx.drawerlayout:drawerlayout:1.1.1")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.gridlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("org.tensorflow:tensorflow-lite-task-vision:0.4.4")
    implementation("org.tensorflow:tensorflow-lite-support:0.4.4")
    implementation("org.tensorflow:tensorflow-lite-metadata:0.4.4")
    implementation("org.tensorflow:tensorflow-lite:2.7.0")
    implementation("org.tensorflow:tensorflow-lite-gpu:2.7.0")
    implementation("androidx.camera:camera-core:1.3.3")

    implementation ("androidx.activity:activity-ktx:1.6.0")
    implementation ("androidx.fragment:fragment-ktx:1.5.2")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation ("com.intuit.sdp:sdp-android:1.0.6")
    implementation ("com.intuit.ssp:ssp-android:1.0.6")
}