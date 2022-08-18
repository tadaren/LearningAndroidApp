package com.example.learningandroidapp.di

import com.example.learningandroidapp.repository.*
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

    @Binds
    abstract fun bindUserReposRepository(impl: UserReposRepositoryImpl): UserReposRepository
}
