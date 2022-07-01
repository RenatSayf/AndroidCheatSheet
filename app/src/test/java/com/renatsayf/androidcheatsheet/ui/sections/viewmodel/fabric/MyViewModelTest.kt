package com.renatsayf.androidcheatsheet.ui.sections.viewmodel.fabric

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.renatsayf.androidcheatsheet.domain.net.MockNetRepository
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


//region Hint ViewModel. Tests
@RunWith(JUnit4::class)
class MyViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val viewModel = MyViewModel(repository = MockNetRepository())

    @Test
    fun setState() {

        viewModel.setState(MyViewModel.State.Loading)

        val expectedState = MyViewModel.State.Loading
        val actualState = viewModel.state.value

        Assert.assertEquals(expectedState, actualState)
    }
}
//endregion