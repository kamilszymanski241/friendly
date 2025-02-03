import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

fun getLocalProperty(key: String, defaultValue: String = ""): String {
    val properties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        properties.load(localPropertiesFile.inputStream())
    }
    return properties.getProperty(key, defaultValue)
}

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.gmazzo.build.config)
}

buildConfig{
    buildConfigField("String", "SUPABASE_ANON_KEY", "\"${getLocalProperty("SUPABASE_ANON_KEY")}\"")
    buildConfigField("String", "SECRET", "\"${getLocalProperty("SECRET")}\"")
    buildConfigField("String", "SUPABASE_URL", "\"${getLocalProperty("SUPABASE_URL")}\"")
    buildConfigField("String", "PROFILE_PICTURES_STORAGE_URL", "\"${getLocalProperty("PROFILE_PICTURES_STORAGE_URL")}\"")
    buildConfigField("String", "EVENT_PICTURES_STORAGE_URL", "\"${getLocalProperty("EVENT_PICTURES_STORAGE_URL")}\"")
    buildConfigField("String", "GOOGLE_MAPS_ANDROID_API_KEY", "\"${getLocalProperty("GOOGLE_MAPS_ANDROID_API_KEY")}\"")
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.insert.koin.android)
            implementation(libs.insert.koin.androidx.compose)

            implementation(libs.ktor.client.okhttp)
            implementation(libs.accompanist.permissions)

            implementation(libs.android.google.maps.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(libs.compose.material3.icons)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.navigation.compose)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation((libs.ktor.client.content.negotiation))
            implementation((libs.ktor.client.utils))

            implementation(libs.kotlinx.coroutines.core)

            implementation(libs.insert.koin.core)
            implementation(libs.insert.koin.compose)
            implementation(libs.insert.koin.compose.viewmodel)
            implementation(libs.insert.koin.compose.viewmodel.navigation)

            implementation(libs.supabase.postgrest)
            implementation(libs.supabase.storage)
            implementation(libs.supabase.auth)

            implementation(libs.coil.compose.core)
            implementation(libs.coil.compose)
            implementation(libs.coil.mp)
            implementation(libs.coil.network.ktor)

            implementation(libs.kotlinx.datetime)

            api(libs.moko.permissions)
            api(libs.moko.permissions.compose)

            implementation(libs.konnectivity)
        }
        iosMain.dependencies {
            implementation((libs.ktor.client.darwin))
        }
    }
}

android {
    namespace = "com.friendly"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.friendly"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        manifestPlaceholders["MAPS_API_KEY"] = getLocalProperty("GOOGLE_MAPS_ANDROID_API_KEY")
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.friendly.generated.resources"
    generateResClass = always
}

dependencies {
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.extensions)
    implementation(libs.play.services.location)
    implementation(libs.google.places)
    debugImplementation(compose.uiTooling)
}
