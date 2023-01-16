package io.lostimagin4tion.vkcup

import android.app.Application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import io.lostimagin4tion.vkcup.dagger.AppComponent
import io.lostimagin4tion.vkcup.dagger.DaggerAppComponent

class VkCupApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}