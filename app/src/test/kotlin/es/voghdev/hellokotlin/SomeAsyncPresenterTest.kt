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
import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SomeAsyncPresenterTest {

    @Mock
    lateinit var mockContext: Context

    @Mock
    lateinit var mockNavigator: SomeAsyncPresenter.Navigator

    @Mock
    lateinit var mockView: SomeAsyncPresenter.MVPView

    @Mock lateinit var mockAsyncRepository: AsyncCall
    @Mock lateinit var mockListener: AsyncCall.Listener

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    private fun givenAMockedPresenter(): SomeAsyncPresenter {
        val presenter = SomeAsyncPresenter(mockContext, mockAsyncRepository)
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
    fun `should show success on start if Repository returns success`() {
        givenADataSourceReturningSuccess(mockListener)

        val presenter = givenAMockedPresenter()

        presenter.initialize()

        waitForAsyncBlocksToFinish()

        verify(mockView).showSuccess(anyString())
    }

    @Test
    fun `should show error on start if Repository returns error`() {
        givenADataSourceReturningFailure(mockListener)

        val presenter = givenAMockedPresenter()

        presenter.initialize()

        waitForAsyncBlocksToFinish()

        verify(mockView).showError(anyString())
    }

    @Test
    fun `should show a sample name when data is received (using mockito-kotlin thenReturn)`() {
        whenever(mockContext.getString(anyInt(), anyString())).thenReturn("Name is Bob")

        val presenter = givenAMockedPresenter()

        val data = SampleData(id = 4L, name = "Bob")

        presenter.onDataReceived(data)

        verify(mockView).showName("Name is Bob")
    }

    @Test
    fun `should show a sample name when data is received (using mockito-kotlin doReturn)`() {
        mockContext = mock<Context> {
            on { getString(anyInt(), anyString()) } doReturn "Name is Martin"
        }
        val presenter = givenAMockedPresenter()

        val data = SampleData(id = 5L, name = "Martin")

        presenter.onDataReceived(data)

        verify(mockView).showName(anyString())
    }

    protected fun givenADataSourceReturningSuccess(listener: AsyncCall.Listener) {
        doAnswer {
            val callback = it.arguments[0] as AsyncCall.Listener

            callback.onSuccess("Hello!")
            null
        }.`when`(mockAsyncRepository).execute(any())
    }

    protected fun givenADataSourceReturningFailure(listener: AsyncCall.Listener) {
        doAnswer {
            val callback = it.arguments[0] as AsyncCall.Listener

            callback.onFailure(Exception("Nope :-/"))
            null
        }.`when`(mockAsyncRepository).execute(any())
    }

    protected fun waitForAsyncBlocksToFinish() {
        Thread.sleep(30)
    }
}