package com.example.learningandroidapp.repository

import com.example.learningandroidapp.api.GitHubApiService
import com.example.learningandroidapp.api.UserDetailApiModel
import com.example.learningandroidapp.api.UserRepoApiModel
import com.example.learningandroidapp.models.UserDetail
import com.example.learningandroidapp.models.UserRepo
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDetailRepositoryImpl @Inject constructor(
    private val gitHubApi: GitHubApiService
) : UserDetailRepository {
    override suspend fun getUserDetail(userName: String): UserDetail {
        return coroutineScope {
            val userDetail = async { gitHubApi.getUserDetail(userName) }
            val userRepos = async { gitHubApi.getUserRepos(userName) }

            convertToUserDetail(userDetail.await(), userRepos.await())
        }
    }

    private fun convertToUserDetail(
        userDetailApiModel: UserDetailApiModel,
        userRepoApiModels: List<UserRepoApiModel>
    ): UserDetail {
        val repos = userRepoApiModels
            .map {
                UserRepo(
                    name = it.name,
                    description = it.description ?: "",
                    language = it.language ?: "",
                    star = it.stargazersCount,
                    url = it.htmlUrl,
                    isForked = it.fork
                )
            }
        return UserDetail(
            userName = userDetailApiModel.login,
            avatarUrl = userDetailApiModel.avatarUrl,
            screenName = userDetailApiModel.name,
            followers = userDetailApiModel.followers,
            following = userDetailApiModel.following,
            repos = repos
        )
    }
}
