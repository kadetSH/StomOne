package com.example.stomone.dagger.retrofit

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class
    ]
)
interface NetworkComponent {
    operator fun plus(networkModule: NetworkModule?): NetworkModule?
}