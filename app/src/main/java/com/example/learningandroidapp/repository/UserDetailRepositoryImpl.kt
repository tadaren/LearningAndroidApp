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
import kotlin.math.min

@Singleton
class UserDetailRepositoryImpl @Inject constructor(
    private val gitHubApi: GitHubApiService
) : UserDetailRepository {
    override suspend fun getUserDetail(userName: String): UserDetail {
        return coroutineScope {
            val userDetail = async { gitHubApi.getUserDetail(userName) }
            val userRepos = async { getUserRepos(userName) }

            convertUserDetail(userDetail.await(), userRepos.await())
        }
    }

    private suspend fun getUserRepos(userName: String): List<UserRepoApiModel> {
        val userRepos = mutableListOf<UserRepoApiModel>()
        var requestPageIndex = 1
        while (userRepos.size < 50) {
            val repos = gitHubApi.getUserRepos(userName, page = requestPageIndex)
            requestPageIndex++
            if (repos.isEmpty()) {
                break
            }
            val filteredUserRepos = repos.filter { !it.fork }
            userRepos.addAll(filteredUserRepos)
        }
        return userRepos.slice(0 until min(50, userRepos.size))
    }

    private fun convertUserDetail(
        userDetailApiModel: UserDetailApiModel,
        userRepoApiModels: List<UserRepoApiModel>
    ): UserDetail {
        val repos = userRepoApiModels
            .filter { !it.fork }
            .map {
                UserRepo(
                    name = it.name,
                    description = it.description ?: "",
                    language = it.language ?: "",
                    star = it.stargazersCount,
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
