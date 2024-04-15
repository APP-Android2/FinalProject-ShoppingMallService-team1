plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    kotlin("kapt")
}

android {
    namespace = "kr.co.lion.finalproject_shoppingmallservice_team1"
    compileSdk = 34

    defaultConfig {
        applicationId = "kr.co.lion.finalproject_shoppingmallservice_team1"
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
    dataBinding{
        enable = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity-ktx:1.8.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("androidx.core:core-splashscreen:1.0.1") // SplashScreen
    implementation("androidx.viewpager2:viewpager2:1.0.0") // ViewPager2 - 슬라이드 화면
    implementation("me.relex:circleindicator:2.1.6") // circle -

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    implementation("com.google.firebase:firebase-auth-ktx:22.3.1") // auth - 인증 관리
    implementation("com.google.firebase:firebase-analytics-ktx:21.6.2") // analytics - 분석
    implementation("com.google.firebase:firebase-firestore-ktx:24.11.0") // firestore - 데이터베이스
    implementation("com.google.firebase:firebase-storage-ktx:20.3.0") // storage - 파일

    implementation("com.google.android.gms:play-services-auth:21.0.0") // 구글 로그인

    implementation("com.google.android.gms:play-services-maps:18.2.0") // 구글 맵 지도
}