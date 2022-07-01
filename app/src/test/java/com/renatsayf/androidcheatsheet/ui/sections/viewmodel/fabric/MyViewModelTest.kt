package com.renatsayf.androidcheatsheet.ui.sections.viewmodel.fabric

import com.renatsayf.androidcheatsheet.domain.NetRepository
import org.junit.Assert
import org.junit.Test


//region Hint ViewModel. Tests
class MyViewModelTest {

    @Test
    fun getState() {
        val viewModel = MyViewModel(repository = NetRepository())
        Assert.assertTrue(viewModel is MyViewModel)
    }

    @Test
    fun setState() {
    }
}
//endregion