/*
 * Copyright (C) 2017 Olmo Gallegos Hernández.
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

import android.content.Context
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class SomeAsyncPresenterTest {

    @Mock
    lateinit var mockContext: Context

    @Mock
    lateinit var mockNavigator: SomeAsyncPresenter.Navigator

    @Mock
    lateinit var mockView: SomeAsyncPresenter.MVPView

    @Mock lateinit var mockAsyncCall: AsyncCall
    @Mock lateinit var mockListener: AsyncCall.Listener

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    private fun givenAMockedPresenter(): SomeAsyncPresenter {
        val presenter = SomeAsyncPresenter(mockContext, mockAsyncCall)
        presenter.view = mockView
        presenter.navigator = mockNavigator
        return presenter
    }

    @Test
    fun `should show loading on start`() {
        val presenter = givenAMockedPresenter()

        presenter.initialize()

        verify(mockView).showLoading()
    }

    @Test
    fun `should show success on start if DataSource returns success`() {
        givenADataSourceReturningSuccess(mockListener)

        val presenter = givenAMockedPresenter()

        presenter.initialize()

        waitForAsyncBlocksToFinish()

        verify(mockView).showSuccess(anyString())
    }

    @Test
    fun `should show error on start if DataSource returns error`() {
        givenADataSourceReturningFailure(mockListener)

        val presenter = givenAMockedPresenter()

        presenter.initialize()

        waitForAsyncBlocksToFinish()

        verify(mockView).showError(anyString())
    }

    protected fun givenADataSourceReturningSuccess(listener: AsyncCall.Listener) {
        doAnswer { invocation ->
            (invocation.arguments.elementAt(0) as AsyncCall.Listener).onSuccess("Hello!")
            null
        }.`when`(mockAsyncCall).execute(listener)
    }

    protected fun givenADataSourceReturningFailure(listener: AsyncCall.Listener) {
        doAnswer { invocation ->
            (invocation.arguments.elementAt(0) as AsyncCall.Listener).onSuccess("Nope :-/")
            null
        }.`when`(mockAsyncCall).execute(listener)
    }

    protected fun waitForAsyncBlocksToFinish() {
        Thread.sleep(30)
    }
}