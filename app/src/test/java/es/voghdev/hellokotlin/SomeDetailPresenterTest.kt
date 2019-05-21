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

import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import es.voghdev.hellokotlin.domain.ResLocator
import es.voghdev.hellokotlin.features.invoice.Invoice
import es.voghdev.hellokotlin.features.user.SomeDetailPresenter
import es.voghdev.hellokotlin.features.user.User
import es.voghdev.hellokotlin.features.user.UserRepository
import es.voghdev.hellokotlin.features.user.usecase.GetUsers
import es.voghdev.hellokotlin.features.user.usecase.InsertUser
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SomeDetailPresenterTest {
    val mockUserRepository: UserRepository = mock()

    val mockResLocator: ResLocator = mock()

    val mockView: SomeDetailPresenter.MVPView = mock()

    val mockGetUsersApi: GetUsers = mock()

    val mockGetUsersDb: GetUsers = mock()

    val mockInsertUser: InsertUser = mock()

    lateinit var presenter: SomeDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        presenter = SomeDetailPresenter(Dispatchers.Unconfined, mockResLocator, mockUserRepository)
        presenter.view = mockView
    }

    @Test
    fun `should request a List of users on start`() {
        givenAllStringsAreMocked()

        assertNotNull(presenter)

        presenter.initialize()

        verify(mockUserRepository, times(1))?.getUsers()
    }

    @Test
    fun `should show user list if request has results`() {
        givenAllStringsAreMocked()

        whenever(mockUserRepository.getUsers()).thenReturn(listOf(User(name = "John")))

        assertNotNull(presenter)

        presenter.initialize()

        verify(mockView, times(1))?.showUsers(anyList())
    }

    @Test
    fun `should show empty case if request has no results`() {
        givenAllStringsAreMocked()

        assertNotNull(presenter)

        presenter.initialize()

        verify(mockView, times(1))?.showEmptyCase()
    }

    @Test
    fun `should call a simple method containing a coroutine in runBlocking mode`() {

        presenter.onSomeEventHappened()


        verify(mockView).showSomeResult()
    }

    @Test
    fun `should call a method that contains a coroutine and returns a result using runBlocking`() {
        whenever(mockUserRepository.performSomeBlockingOperationWithResult()).thenReturn(listOf(
            User(name = "User 001"),
            User(name = "User 002")
        ))

        presenter.onSomeOtherEventHappened()

        val captor = argumentCaptor<List<User>>()

        verify(mockView).showUsers(captor.capture())

        assertEquals(2, captor.firstValue.size)
        assertEquals("User 001", captor.firstValue[0].name)
        assertEquals("User 002", captor.firstValue[1].name)
    }

    @Test
    fun `should call a method that contains a coroutine and verify assertions in the repository`() {
        val invoice = Invoice(customerId = 15L, amount = 10f)

        presenter.onEventWithParameterHappened(invoice.customerId)

        val captor = argumentCaptor<Invoice>()

        verify(mockUserRepository).performSomeBlockingOperationWithAParameter(captor.capture())
        verify(mockView).showSomeResult()

        assertEquals(15L, captor.firstValue.customerId)
        assertEquals(50f, captor.firstValue.amount)
        assertNotEquals(10f, captor.firstValue.amount)
    }

    @Test
    fun `should call a method that contains a coroutine using a non-mocked repository with mock DataSources`() {
        val nonMockedRepository = UserRepository(mockGetUsersApi, mockGetUsersDb, mockInsertUser)

        val presenter = SomeDetailPresenter(Dispatchers.Unconfined, mockResLocator, nonMockedRepository)
        presenter.view = mockView

        presenter.onSomeOtherEventHappened()

        val captor = argumentCaptor<List<User>>()

        verify(mockView).showUsers(captor.capture())

        assertEquals(2, captor.firstValue.size)
        assertEquals("John doe", captor.firstValue[0].name)
        assertEquals("Jane doe", captor.firstValue[1].name)
    }

    private fun givenAllStringsAreMocked() {
        whenever(mockResLocator.getString(R.string.tech_debt_is_paid)).thenReturn("Relax man, I pay my tech debt!")
    }
}
