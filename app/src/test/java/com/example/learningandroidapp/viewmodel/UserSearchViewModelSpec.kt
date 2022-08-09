package com.example.learningandroidapp.viewmodel

import com.example.learningandroidapp.models.User
import com.example.learningandroidapp.repository.UserRepository
import com.example.learningandroidapp.util.MainDispatcherListener
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class UserSearchViewModelSpec : DescribeSpec({

    extension(MainDispatcherListener())

    val userRepository = mockk<UserRepository>()
    lateinit var viewModel: UserSearchViewModel

    val userSearchResult = User(
        userName = "user",
        avatarUrl = "https://placehold.jp/240x240.png"
    )

    beforeSpec {
        viewModel = UserSearchViewModel(userRepository)
    }

    describe("#onSearchTextChanged") {
        val inputUserName = "user"
        context("input user") {
            viewModel.onUserNameChanged(inputUserName)

            it("searchText should be user") {
                viewModel.uiState.userName shouldBe inputUserName
            }
        }
    }

    describe("#searchUser") {
        val inputUserName = "user"
        context("response success") {
            coEvery {
                userRepository.getUserList(inputUserName)
            } returns listOf(userSearchResult)

            viewModel.onUserNameChanged(inputUserName)
            viewModel.searchUser()

            it("userList should be collect value") {
                viewModel.uiState shouldBe UserSearchUiState(
                    isLoading = false,
                    userName = inputUserName,
                    userList = listOf(userSearchResult),
                    hasError = false
                )
            }

            it("verify searchUser") {
                coVerify(exactly = 1) { userRepository.getUserList(inputUserName) }
            }
        }

        context("response fail") {
            coEvery {
                userRepository.getUserList(inputUserName)
            } throws Exception()
            viewModel.onUserNameChanged(inputUserName)
            viewModel.searchUser()

            it("event should be FetchError") {
                viewModel.uiState shouldBe UserSearchUiState(
                    userName = inputUserName,
                    hasError = true
                )
            }

            it("verify searchUser") {
                coVerify(exactly = 1) { userRepository.getUserList(inputUserName) }
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
