// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version Versions.AGP apply false
    id("com.android.library") version Versions.AGP apply false
    id("org.jetbrains.kotlin.android") version Versions.kotlin apply false
}

buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.3.14")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.2")
    }
}