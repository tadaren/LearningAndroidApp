package com.example.learningandroidapp.viewmodel

import com.example.learningandroidapp.models.UserDetail
import com.example.learningandroidapp.models.UserRepo
import com.example.learningandroidapp.repository.UserDetailRepository
import com.example.learningandroidapp.util.MainDispatcherListener
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class UserDetailViewModelSpec : DescribeSpec({
    extension(MainDispatcherListener())

    val userDetailRepository = mockk<UserDetailRepository>()
    lateinit var viewModel: UserDetailViewModel

    val userRepos = listOf(
        UserRepo(
            name = "RepositoryName1",
            description = "Description1",
            language = "Kotlin",
            star = 1,
            url = "",
            isForked = false
        ),
        UserRepo(
            name = "Repository2",
            description = "Description2",
            language = "Kotlin",
            star = 2,
            url = "",
            isForked = true
        ),
        UserRepo(
            name = "Repository3",
            description = "Description3",
            language = "Kotlin",
            star = 3,
            url = "",
            isForked = false
        ),
    )
    val filteredUserRepos = userRepos.filter { !it.isForked }
    val userDetail = UserDetail(
        userName = "user",
        screenName = "User",
        avatarUrl = "https://placehold.jp/240x240.png",
        followers = 8,
        following = 16,
        repos = userRepos
    )

    beforeSpec {
        viewModel = UserDetailViewModel(userDetailRepository)
    }

    describe("#loadUserDetail") {
        val inputUserName = "user"
        context("response success") {
            coEvery {
                userDetailRepository.getUserDetail(inputUserName)
            } returns userDetail

            viewModel.loadUserDetail(userName = inputUserName)

            it("userDetail should be collect value") {
                viewModel.uiState shouldBe UserDetailUiState(
                    userDetail = userDetail,
                    userRepos = filteredUserRepos,
                    hasError = false,
                    isLoading = false,
                )
            }

            it("verify getUserDetail") {
                coVerify(exactly = 1) { userDetailRepository.getUserDetail(inputUserName) }
            }
        }

        context("response fail") {
            coEvery {
                userDetailRepository.getUserDetail(inputUserName)
            } throws Exception()

            viewModel.loadUserDetail(inputUserName)

            it("event should be FetchError") {
                viewModel.uiState shouldBe UserDetailUiState(
                    userDetail = null,
                    userRepos = listOf(),
                    hasError = true,
                    isLoading = false
                )
            }

            it("verify getUserDetail") {
                coVerify(exactly = 1) { userDetailRepository.getUserDetail(inputUserName) }
            }
        }
    }

    describe("#errorMessageShown") {
        context("consume event") {
            viewModel.errorMessageShown()

            it("event should be null") {
                viewModel.uiState.hasError.shouldBeFalse()
            }
        }
    }
})
