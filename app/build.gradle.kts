plugins {
    id("com.android.application")
}

android {
    namespace = "com.e2p.myecf"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.e2p.myecf"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.7.5")
    implementation("androidx.navigation:navigation-ui:2.7.5")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.google.android.material:material:1.12.0-alpha01")

    //____Glide : image loading framework____//
    implementation("com.github.bumptech.glide:glide:4.15.1")

    //____Retrofit2____//
    implementation("com.squareup.retrofit2:retrofit:2.4.0")
    implementation("com.squareup.retrofit2:converter-gson:2.4.0")
    implementation("com.google.code.gson:gson:2.9.1")

    //____SDP - a scalable size unit____//
    implementation("com.intuit.sdp:sdp-android:1.1.0")

    //____SSP - a scalable size unit for texts____//
    implementation("com.intuit.ssp:ssp-android:1.1.0")

    //____SpinKit - loading animations____//
    implementation("com.github.ybq:Android-SpinKit:1.4.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}