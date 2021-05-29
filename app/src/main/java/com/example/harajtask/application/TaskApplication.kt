package com.example.harajtask.application

import android.app.Application
import com.example.harajtask.di.ApplicationComponent
import com.example.harajtask.di.ContextModule
import com.example.harajtask.di.DaggerApplicationComponent
import com.example.harajtask.di.DaggerComponentProvider

@Suppress("unused")
internal class TaskApplication : Application(), DaggerComponentProvider {
    override val component: ApplicationComponent = DaggerApplicationComponent.builder()
        .contextModule(ContextModule(this))
        .build()
}
