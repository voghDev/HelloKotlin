/*
 * Copyright (C) 2018 Olmo Gallegos Hernández.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.voghdev.hellokotlin

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import es.voghdev.hellokotlin.features.user.SomeDetailPresenter
import es.voghdev.hellokotlin.features.user.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyList
import org.mockito.MockitoAnnotations

class SomeDetailPresenterTest {
    lateinit var presenter: SomeDetailPresenter
    private val testCoroutineContext = TestCoroutineDispatcher()
    val mockUserRepository: UserRepository = mock()

    val mockView: SomeDetailPresenter.MVPView = mock()
    val mockNavigator: SomeDetailPresenter.Navigator = mock()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testCoroutineContext as CoroutineDispatcher)
        presenter = SomeDetailPresenter(mockUserRepository)
        presenter.view = mockView
        presenter.navigator = mockNavigator
    }

    @After
    fun tearDown() {
        presenter.destroy()
        Dispatchers.resetMain()
    }

    @Test
    fun `should fetch the list of users on start`() = runBlockingTest {
        presenter.initialize()

        presenter.resume()

        verify(mockView).showUsers(any())
        verify(mockView, times(1))?.showUsers(anyList())
    }
}
