package com.example.learningandroidapp.di

import com.example.learningandroidapp.repository.UserDetailRepository
import com.example.learningandroidapp.repository.UserDetailRepositoryImpl
import com.example.learningandroidapp.repository.UserRepository
import com.example.learningandroidapp.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindUserDetailRepository(impl: UserDetailRepositoryImpl): UserDetailRepository
}
