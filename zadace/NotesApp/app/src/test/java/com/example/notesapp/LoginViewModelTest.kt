package com.example.notesapp

import com.example.notesapp.data.repository.TaskRepository
import com.example.notesapp.di.AppLogger
import com.example.notesapp.ui.login.LoginViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: TaskRepository = mockk()
    private val logger: AppLogger = mockk(relaxed = true)

    @Test
    fun `login sets loginSuccess to true when repository login succeeds`() = runTest {
        coEvery { repository.login("mirja", "1234") } returns Unit

        val viewModel = LoginViewModel(repository, logger)
        viewModel.username = "mirja"
        viewModel.password = "1234"

        viewModel.login()
        advanceUntilIdle()

        assertTrue(viewModel.loginSuccess)
        assertNull(viewModel.errorMessage)
        coVerify { repository.login("mirja", "1234") }
    }

    @Test
    fun `login sets errorMessage when repository login fails`() = runTest {
        coEvery { repository.login("mirja", "wrong") } throws RuntimeException("Login failed")

        val viewModel = LoginViewModel(repository, logger)
        viewModel.username = "mirja"
        viewModel.password = "wrong"

        viewModel.login()
        advanceUntilIdle()

        assertEquals("Login failed", viewModel.errorMessage)
    }
}