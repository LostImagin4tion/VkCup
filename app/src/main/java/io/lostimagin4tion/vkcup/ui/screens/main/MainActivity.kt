package io.lostimagin4tion.vkcup.ui.screens.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import io.lostimagin4tion.vkcup.ui.components.navigation.Navigation
import io.lostimagin4tion.vkcup.ui.theme.VkCupTheme

/**
 * [MainActivity] - the initial activity that starts navigation across the app
 *
 * @author Egor Danilov
 */
class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }

            VkCupTheme {
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackbarHostState,
                            modifier = Modifier.navigationBarsPadding()
                        ) {
                            Snackbar(
                                snackbarData = it,
                                containerColor = MaterialTheme.colorScheme.inverseSurface,
                                contentColor = contentColorFor(MaterialTheme.colorScheme.inverseSurface),
                                shape = MaterialTheme.shapes.small
                            )
                        }
                    },
                    content = {
                        Navigation(
                            snackbarHostState = snackbarHostState,
                            paddingValues = it,
                            navController = navController
                        )
                    }
                )
            }
        }
    }
}