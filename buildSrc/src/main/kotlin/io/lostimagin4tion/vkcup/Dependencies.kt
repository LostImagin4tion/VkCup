package io.lostimagin4tion.vkcup

object Dependencies {

    object AndroidCore {
        private const val APPCOMPAT_VERSION = "1.5.1"
        private const val CORE_VERSION = "1.9.0"

        const val APPCOMPAT = "androidx.appcompat:appcompat:$APPCOMPAT_VERSION"
        const val CORE = "androidx.core:core-ktx:$CORE_VERSION"

        val ALL_DEPS = listOf(APPCOMPAT, CORE)
    }

    object Coroutines {
        private const val VERSION = "1.6.4"

        const val ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$VERSION"
    }

    object Compose {

        // ===========================================================================================
        // BE VERY CAREFUL WHEN UPDATING COMPOSE DEPENDENCIES, BECAUSE THEY CAN USE DIFFERENT VERSION!
        // ===========================================================================================

        const val COMPOSE_VERSION = "1.3.0"

        object Core {
            private const val ACTIVITY_COMPOSE_VERSION = "1.6.1"
            private const val CONSTRAINT_VERSION = "1.0.1"
            private const val MATERIAL3_VERSION = "1.0.0"

            const val UI = "androidx.compose.ui:ui:$COMPOSE_VERSION"
            const val UI_UTILS = "androidx.compose.ui:ui-util:$COMPOSE_VERSION"

            const val ANIMATION_GRAPHICS = "androidx.compose.animation:animation-graphics:$COMPOSE_VERSION"

            const val MATERIAL = "androidx.compose.material:material:$COMPOSE_VERSION"
            const val MATERIAL_YOU = "androidx.compose.material3:material3:$MATERIAL3_VERSION"

            const val UI_TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview:$COMPOSE_VERSION"
            const val UI_TOOLING = "androidx.compose.ui:ui-tooling:$COMPOSE_VERSION" // debugImplementation

            const val ACTIVITY_COMPOSE = "androidx.activity:activity-compose:$ACTIVITY_COMPOSE_VERSION"

            const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout-compose:$CONSTRAINT_VERSION"

            val ALL_CORE_DEPS = listOf(
                UI, UI_UTILS, ANIMATION_GRAPHICS, MATERIAL, MATERIAL_YOU, UI_TOOLING_PREVIEW,
                ACTIVITY_COMPOSE, CONSTRAINT_LAYOUT
            )
            val ALL_CORE_DEBUG_DEPS = listOf(UI_TOOLING)
        }

        object Navigation {
            private const val VERSION = "2.5.3"

            const val NAVIGATION_COMPOSE = "androidx.navigation:navigation-compose:$VERSION"

            val ALL_NAVIGATION_DEPS = listOf(NAVIGATION_COMPOSE)
        }

        object Lifecycle {
            private const val LIFECYCLE_VERSION = "2.5.1"

            const val RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:$LIFECYCLE_VERSION"
            const val VIEW_MODEL = "androidx.lifecycle:lifecycle-viewmodel-compose:$LIFECYCLE_VERSION"

            val ALL_LIFECYCLE_DEPS = listOf(RUNTIME, VIEW_MODEL)
        }

        object Foundation {

            const val COMPOSE_FOUNDATION = "androidx.compose.foundation:foundation:$COMPOSE_VERSION"
            const val COMPOSE_FOUNDATION_LAYOUT = "androidx.compose.foundation:foundation-layout:$COMPOSE_VERSION"

            val ALL_FOUNDATION_DEPS = listOf(COMPOSE_FOUNDATION, COMPOSE_FOUNDATION_LAYOUT)
        }

        object Coil {
            private const val VERSION = "2.2.2"

            const val COIL_COMPOSE = "io.coil-kt:coil-compose:$VERSION"
            const val COIL_GIF = "io.coil-kt:coil-gif:$VERSION"

            val ALL_COIL_DEPS = listOf(COIL_COMPOSE, COIL_GIF)
        }

        object Accompanist {
            private const val ACCOMPANIST_VERSION = "0.28.0"

            const val FLOW_LAYOUT = "com.google.accompanist:accompanist-flowlayout:$ACCOMPANIST_VERSION"

            val ALL_ACCOMPANIST_DEPS = listOf(FLOW_LAYOUT)
        }

        val ALL_DEPS =
            Core.ALL_CORE_DEPS + Lifecycle.ALL_LIFECYCLE_DEPS + Foundation.ALL_FOUNDATION_DEPS +
                    Navigation.ALL_NAVIGATION_DEPS + Accompanist.ALL_ACCOMPANIST_DEPS +
                    Coil.ALL_COIL_DEPS
    }

    object Dagger {
        private const val VERSION = "2.44"

        const val ANDROID = "com.google.dagger:dagger-android:$VERSION"
        const val COMPILER = "com.google.dagger:dagger-compiler:$VERSION"
        const val PROCESSOR = "com.google.dagger:dagger-android-processor:$VERSION"

        val KAPT_DEPS = listOf(COMPILER, PROCESSOR)
    }

    object UI {
        private const val CONSTRAINT_LAYOUT_VERSION = "2.1.4"
        private const val MATERIAL_VERSION = "1.7.0"

        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:$CONSTRAINT_LAYOUT_VERSION"
        const val CORE = "com.google.android.material:material:$MATERIAL_VERSION"

        val ALL_DEPS = listOf(CONSTRAINT_LAYOUT, CORE)
    }
}