plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.destinations.ksp)
    alias(libs.plugins.kotlin.serialization)
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    alias(libs.plugins.google.services)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.androidgitversion)
}

/**
 * Check if a signing.gradle file exists and import it
 */
if (file(rootProject.projectDir.absolutePath + "/signing.gradle").exists()) {
    apply(rootProject.projectDir.absolutePath + "/signing.gradle")
}

androidGitVersion {
    codeFormat = "MNNPPPBBB"
    format = "%tag%%.count%%-dirty%"
    format = if (System.getenv("CI") == null) {
        "%tag%%.count%%-dirty%"
    } else {
        //if it's a CI build ignore dirty because bundle execute demands bundler/gems to be up to date (forces it server-side) --> gemlock always changed
        "%tag%%.count%"
    }
    //when their are uncommitted changes add dirty to version name
    untrackedIsDirty = true
}

android {
    namespace = "be.cbconnectit.portfolio.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "be.cbconnectit.portfolio.app"
        minSdk = 28
        targetSdk = 35
        versionName = androidGitVersion.name()
        versionCode = androidGitVersion.code()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
//            signingConfig = signingConfigs.getByName("release")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
//            signingConfig = signingConfigs.getByName("release")
            signingConfig = signingConfigs.getByName("debug") //TODO: should be removed and replaced by a proper signingConfig!!
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
        viewBinding = true
        dataBinding = true
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    flavorDimensions += "env"

    productFlavors {
        create("dev") {
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            dimension = "env"

            resValue("string","app_name","CB connect IT Portfolio Dev")
            buildConfigField("String", "API_BASE_URL", "\"https://www.cb-connect-it.com/api/v1/\"")
        }

        create("qa") {
            applicationIdSuffix = ".test"
            versionNameSuffix = "-test"
            dimension = "env"

            resValue("string","app_name","CB connect IT Portfolio Test")
            buildConfigField("String", "API_BASE_URL", "\"https://www.cb-connect-it.com/api/v1/\"")
        }

        create("staging") {
            applicationIdSuffix = ".staging"
            versionNameSuffix = "-staging"
            dimension = "env"

            resValue("string","app_name","CB connect IT Portfolio Staging")
            buildConfigField("String", "API_BASE_URL", "\"https://www.cb-connect-it.com/api/v1/\"")
        }

        create("production") {
            dimension = "env"

            resValue("string","app_name","CB connect IT Portfolio")
            buildConfigField("String", "API_BASE_URL", "\"https://www.cb-connect-it.com/api/v1/\"")
        }
    }
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.androidx.splashcreen)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.runtime)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.constraintlayout.compose)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.activity.compose)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)

    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)

    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    implementation(libs.compose.destinations.core)
    ksp(libs.compose.destinations.ksp.lib)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso)

    implementation(libs.browser)

    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.nav)
    implementation(libs.koin.compose)
    testImplementation(libs.koin.junit)

    // Ktor
    implementation(libs.ktor.core)
    implementation(libs.ktor.android)
    implementation(libs.ktor.logging)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.ktor.serialization)
    implementation(libs.ktor.kotlinx.json)
    implementation(libs.serialization.json)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    implementation(libs.flowtextview)
    implementation(libs.compose.markdown)
    implementation(libs.collapsing.toolbar)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
}
