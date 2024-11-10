plugins {
    id("com.android.application")
}

android {
    namespace = "mr.gov.listerouge"
    compileSdk = 34

    defaultConfig {
        applicationId = "mr.gov.listerouge"
        minSdk = 23
        targetSdk = 34
        versionCode = 4
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures{
        viewBinding = true
        dataBinding = true
    }
    sourceSets {
        getByName("main") {
            jniLibs.srcDirs("src/main/jniLibs")
        }
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

    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-mlkit-face-detection:17.1.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.android.volley:volley:1.2.1")
    implementation("androidx.room:room-common:2.6.1")
    implementation("androidx.room:room-runtime:2.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("androidx.recyclerview:recyclerview:1.3.2")
    implementation("io.github.nikartm:image-support:2.0.0")
    implementation ("com.google.android.material:material:1.12.0")
    implementation("com.github.leandroborgesferreira:loading-button-android:2.3.0")
    implementation ("com.airbnb.android:lottie:6.4.1")
    implementation ("com.squareup.picasso:picasso:2.8")
    implementation  ("androidx.fragment:fragment:1.7.1")
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.facebook.shimmer:shimmer:0.1.0")
    implementation("com.google.android.material:material:1.9.0")
    implementation ("com.fredporciuncula:phonemoji:1.5.2")
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.1")
    implementation ("com.google.code.gson:gson:2.8.7")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation("org.aspectj:aspectjrt:1.9.7")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    implementation ("com.google.android.gms:play-services-location:21.0.1")

    implementation ("androidx.work:work-runtime:2.9.1")
}