package com.example.learningandroidapp.repository

import com.example.learningandroidapp.api.GitHubApiService
import com.example.learningandroidapp.api.UserDetailApiModel
import com.example.learningandroidapp.api.UserRepoApiModel
import com.example.learningandroidapp.models.UserDetail
import com.example.learningandroidapp.models.UserRepo
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

class UserDetailRepositorySpec : DescribeSpec({
    val githubApi = mockk<GitHubApiService>()
    lateinit var repository: UserDetailRepository

    val userDetailApiModel = UserDetailApiModel(
        name = "User",
        login = "user",
        following = 8,
        followers = 16,
        avatarUrl = "https://placehold.jp/240x240.png"
    )
    val userRepos = listOf(
        UserRepoApiModel(
            name = "Repository1",
            description = "Description1",
            language = "Kotlin",
            fork = false,
            htmlUrl = "https://example.com/1",
            stargazersCount = 1
        ),
    )

    beforeSpec {
        repository = UserDetailRepositoryImpl(githubApi)
    }

    describe("#getUserDetail") {
        val query = "user"
        context("response success") {
            coEvery {
                githubApi.getUserDetail(query)
            } returns userDetailApiModel
            coEvery {
                githubApi.getUserRepos(query)
            } returns userRepos

            val response = repository.getUserDetail(query)

            it("response should be user detail") {
                response shouldBe UserDetail(
                    userName = "user",
                    screenName = "User",
                    avatarUrl = "https://placehold.jp/240x240.png",
                    following = 8,
                    followers = 16,
                    repos = listOf(
                        UserRepo(
                            name = "Repository1",
                            description = "Description1",
                            language = "Kotlin",
                            isForked = false,
                            url = "https://example.com/1",
                            star = 1,
                        )
                    )
                )
            }

            it("verify getUserDetail") {
                coVerify(exactly = 1) { githubApi.getUserDetail(query) }
            }
            it("verify getUserRepos") {
                coVerify(exactly = 1) { githubApi.getUserRepos(query) }
            }
        }

        context("response fail") {
            context("fail getUserDetail") {
                coEvery {
                    githubApi.getUserDetail(query)
                } throws Exception()
                coEvery {
                    githubApi.getUserRepos(query)
                } returns userRepos

                it("throw Exception") {
                    shouldThrow<Exception> { repository.getUserDetail(query) }
                }

                it("verify getUserDetail") {
                    coVerify(exactly = 0) { githubApi.getUserDetail(query) }
                }
                it("verify getUserRepos") {
                    coVerify(exactly = 0) { githubApi.getUserRepos(query) }
                }
            }
            context("fail getUserRepos") {
                coEvery {
                    githubApi.getUserDetail(query)
                } returns userDetailApiModel
                coEvery {
                    githubApi.getUserRepos(query)
                } throws Exception()

                it("throw Exception") {
                    shouldThrow<Exception> { repository.getUserDetail(query) }
                }

                it("verify getUserDetail") {
                    coVerify(exactly = 0) { githubApi.getUserDetail(query) }
                }
                it("verify getUserRepos") {
                    coVerify(exactly = 0) { githubApi.getUserRepos(query) }
                }
            }
        }
    }
})
