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
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import es.voghdev.hellokotlin.domain.AsyncCall
import es.voghdev.hellokotlin.domain.model.SampleData
import es.voghdev.hellokotlin.features.async.SomeAsyncPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class SomeAsyncPresenterTest {
    @Mock
    lateinit var mockContext: Context

    @Mock
    lateinit var mockNavigator: SomeAsyncPresenter.Navigator

    @Mock
    lateinit var mockView: SomeAsyncPresenter.MVPView

    @Mock
    lateinit var mockAsyncRepository: AsyncCall
    @Mock
    lateinit var mockListener: AsyncCall.Listener

    lateinit var presenter: SomeAsyncPresenter

    val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        Dispatchers.setMain(testCoroutineDispatcher)

        presenter = createMockedPresenter()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createMockedPresenter(): SomeAsyncPresenter {
        val presenter = SomeAsyncPresenter(mockContext, mockAsyncRepository)
        presenter.view = mockView
        presenter.navigator = mockNavigator
        return presenter
    }

    @Test
    fun `should show loading on start`() = runBlockingTest {
        presenter.initialize()

        verify(mockView).showLoading()
    }

    @Test
    fun `should show success on start if Repository returns success`() = runBlockingTest {
        givenADataSourceReturningSuccess(mockListener)

        presenter.initialize()

        verify(mockView).showSuccess(anyString())
    }

    @Test
    fun `should show error on start if Repository returns error`() = runBlockingTest {
        givenADataSourceReturningFailure(mockListener)

        presenter.initialize()

        verify(mockView).showError(anyString())
    }

    @Test
    fun `should show a sample name when data is received (using mockito-kotlin thenReturn)`() {
        whenever(mockContext.getString(anyInt(), anyString())).thenReturn("Name is Bob")

        val data = SampleData(id = 4L, name = "Bob")

        presenter.onDataReceived(data)

        verify(mockView).showName("Name is Bob")
    }

    @Test
    fun `should show a sample name when data is received (using mockito-kotlin doReturn)`() {
        mockContext = mock<Context> {
            on { getString(anyInt(), anyString()) } doReturn "Name is Martin"
        }

        presenter = createMockedPresenter()

        val data = SampleData(id = 5L, name = "Martin")

        presenter.onDataReceived(data)

        verify(mockView).showName(anyString())
    }

    @Test
    fun `should be able to mock a string using given and hasString operators`() {
        given(mockContext).hasString(R.string.app_name, "Example App")

        presenter.onFailureNotified()

        verify(mockView).showError("Error: Example App")
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
}