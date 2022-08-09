package com.example.learningandroidapp.repository

import com.example.learningandroidapp.api.GetUsersResponse
import com.example.learningandroidapp.api.GitHubApiService
import com.example.learningandroidapp.api.UserApiModel
import com.example.learningandroidapp.models.User
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

class UserRepositoryImplSpec : DescribeSpec({

    val githubApi = mockk<GitHubApiService>()
    lateinit var repository: UserRepository

    val getUsersResponse = GetUsersResponse(
        totalCount = 1,
        incompleteResults = false,
        items = listOf(
            UserApiModel(
                login = "user",
                avatarUrl = "https://placehold.jp/240x240.png"
            )
        )
    )

    beforeSpec {
        repository = UserRepositoryImpl(githubApi)
    }

    describe("#getUsers") {
        val query = "user"
        context("response success") {
            coEvery {
                githubApi.getUsers(query)
            } returns getUsersResponse
            val response = repository.getUserList(query)

            it("response should be user list") {
                response shouldBe listOf(
                    User(
                        userName = "user",
                        avatarUrl = "https://placehold.jp/240x240.png"
                    )
                )
            }

            it("verify getUsers") {
                coVerify(exactly = 1) { githubApi.getUsers(query) }
            }
        }

        context("#response fail") {
            coEvery {
                githubApi.getUsers(query)
            } throws Exception()

            it("throw Exception") {
                shouldThrow<Exception> { repository.getUserList(query) }
            }

            it("verify getUsers") {
                coVerify(exactly = 0) { githubApi.getUsers(query) }
            }
        }
    }

})
